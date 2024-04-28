package puzzlePieces.visual;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import gui.PuzzleCursor;
import puzzlePieces.PuzzleTile;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

/**
 * Presents the information in a PuzzleTile object.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleTileContent extends    RuleBasedSprite 
                               implements MouseMotionListener, 
                                          PuzzleComponentContent
{
  private double       left, top;
  private PuzzleTile   tile;
  private boolean      hover;
  private boolean      held;
  private PuzzleCursor cursor;
  
  private PuzzleTileContent north;
  private PuzzleTileContent south;
  private PuzzleTileContent east;
  private PuzzleTileContent west;
  
  private PuzzleCompositeContent composite;
  
  private boolean joinedNorth;
  private boolean joinedSouth;
  private boolean joinedEast;
  private boolean joinedWest;
  
  private boolean aligned;

  public PuzzleTileContent(TransformableContent content, PuzzleTile tile)
  {
    super(content);
    
    this.tile = tile;
    left = tile.getLeft();
    top = tile.getTop();
    setLocation(left, top);
    
    hover = false;
    held  = false;
    
    north = null;
    south = null;
    east =  null;
    west =  null;
    
    composite = null;
    
    joinedNorth = false;
    joinedSouth = false;
    joinedEast =  false;
    joinedWest =  false;
    
    aligned = true;
  }

  public PuzzleCompositeContent getComposite()
  {
    return composite;
  }
  
  @Override
  public void setComposite(PuzzleCompositeContent composite)
  {
    this.composite = composite;
  }
  
  public void setCursor(PuzzleCursor cursor)
  {
    this.cursor = cursor;
  }
  
  /**
   * When the tile is clicked and dragged.
   */
  @Override
  public void mouseDragged(MouseEvent e)
  {
    if (hover)
    {
      cursor.grab(this);
    }
    
    if (held)
    {
      left = (double)e.getX() - tile.getWidth()/2;
      top  = (double)e.getY() - tile.getHeight()/2;
    }
  }

  /**
   * When the tile is hovered over. Should have a glow effect.
   */
  @Override
  public void mouseMoved(MouseEvent e)
  {
    hover = (e.getX() > left && e.getX() < left + tile.getWidth() && 
             e.getY() > top  && e.getY() < top + tile.getHeight());
    
    if (hover && held)
    {
      cursor.release();
    }
  }
  
  public void setPosition(double left, double top)
  {
    this.left = left;
    this.top  = top;
  }
  
  public void grab()
  {
    held = true;
  }
  
  public void release()
  {
    held = false;
    notifyNeighbors();
    if (composite != null)
    {
      composite.alignConnections(this);
    }
  }
  
  public void notifyNeighbors()
  {
    if (north != null)
      north.observePosition(left, top, 2);
    if (south != null)
      south.observePosition(left, top, 0);
    if (east != null)
      east.observePosition(left, top, 3);
    if (west != null)
      west.observePosition(left, top, 1);
  }
  
  public void observePosition(double left, double top, int direction)
  {
    double hDiff      = this.left - left;
    double vDiff      = this.top - top;
    double tileWidth  = tile.getWidth();
    double tileHeight = tile.getHeight();
    double wiggleRoom = 10;
    
    //System.out.println(String.format("%f %f %f", hDiff, tileWidth, Math.abs(hDiff)));
    switch (direction)
    {
      case 0: // North of me
        if (!joinedNorth && Math.abs(hDiff) < wiggleRoom
            && vDiff > tileHeight - wiggleRoom && vDiff < tileHeight + wiggleRoom)
        {
          // connect with north
          joinNorth();
          north.joinSouth();
          System.out.println("Connect with north");
          join(north);
        }
        break;
      case 1: // East of me
        if (!joinedEast && Math.abs(vDiff) < wiggleRoom
            && -hDiff > tileWidth - wiggleRoom && -hDiff < tileWidth + wiggleRoom)
        {
          // connect with east
          joinEast();
          east.joinWest();
          System.out.println("Connect with east");
          join(east);
        }
        break;
      case 2: // South of me
        if (!joinedSouth && Math.abs(hDiff) < wiggleRoom
            && -vDiff > tileHeight - wiggleRoom && -vDiff < tileHeight + wiggleRoom)
        {
          // connect with south
          joinSouth();
          south.joinNorth();
          System.out.println("Connect with south");
          join(south);
        }
        break;
      case 3: // West of me
        if (!joinedWest && Math.abs(vDiff) < wiggleRoom
            && hDiff > tileWidth - wiggleRoom && hDiff < tileWidth + wiggleRoom)
        {
          // connect with west
          joinWest();
          west.joinEast();
          System.out.println("Connect with west");
          join(west);
        }
        break;
      default:
        break;
    }
  }
  
  public void join(PuzzleTileContent neighbor)
  {
    System.out.println("Joining");
    if (composite == null && neighbor.getComposite() == null)
    {
      // No composites involved
      composite = new PuzzleCompositeContent(this);
      composite.add(neighbor);
    }
    else if (composite != null && neighbor.getComposite() == null)
    {
      // This is in a composite
      composite.add(neighbor);
    }
    else if (composite == null && neighbor.getComposite() != null)
    {
      // Neighbor is in a composite
      neighbor.getComposite().add(this);
    }
    else
    {
      // Both are in composites
      composite.addAll(neighbor.getComposite());
    }
  }
  
  public void setNorth(PuzzleTileContent tile)
  {
    north = tile;
  }
  
  public void setSouth(PuzzleTileContent tile)
  {
    south = tile;
  }
  
  public void setEast(PuzzleTileContent tile)
  {
    east = tile;
  }
  
  public void setWest(PuzzleTileContent tile)
  {
    west = tile;
  }
  
  protected void joinNorth()
  {
    joinedNorth = true;
  }
  
  protected void joinSouth()
  {
    joinedSouth = true;
  }
  
  protected void joinEast()
  {
    joinedEast = true;
  }
  
  protected void joinWest()
  {
    joinedWest = true;
  }
  
  @Override
  public void handleTick(int arg0)
  {
    setLocation(left, top);
  }

  @Override
  public void alignConnections()
  {
    double tileHeight = tile.getHeight();
    double tileWidth  = tile.getWidth();
    
    if (north != null && north.getComposite() == composite)
    {
      north.alignConnections(left, top - tileHeight);
    }
    
    if (south != null && south.getComposite() == composite)
    {
      south.alignConnections(left, top + tileHeight);
    }
    
    if (east != null && east.getComposite() == composite)
    {
      east.alignConnections(left + tileWidth, top);
    }
    
    if (west != null && west.getComposite() == composite)
    {
      west.alignConnections(left - tileWidth, top);
    }
  }
  
  protected void alignConnections(double left, double top)
  {
    if (!aligned)
    {
      setPosition(left, top);
      aligned = true;
      alignConnections();
    }
  }
  
  @Override
  public void observeAlignment()
  {
    aligned = false;
  }

}
