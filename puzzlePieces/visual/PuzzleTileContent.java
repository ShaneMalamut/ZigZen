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
                               implements MouseMotionListener
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
        if (vDiff > tileHeight - wiggleRoom && vDiff < tileHeight + wiggleRoom
            && Math.abs(hDiff) < wiggleRoom)
          // connect with north
          System.out.println("Connect with north");
        break;
      case 1: // East of me
        if (-hDiff > tileWidth - wiggleRoom && -hDiff < tileWidth + wiggleRoom
            && Math.abs(vDiff) < wiggleRoom)
          // connect with east
          System.out.println("Connect with east");
        break;
      case 2: // South of me
        if (-vDiff > tileHeight - wiggleRoom && -vDiff < tileHeight + wiggleRoom
            && Math.abs(hDiff) < wiggleRoom)
          // connect with south
          System.out.println("Connect with south");
        break;
      case 3: // West of me
        if (hDiff > tileWidth - wiggleRoom && hDiff < tileWidth + wiggleRoom
            && Math.abs(vDiff) < wiggleRoom)
          // connect with west
          System.out.println("Connect with west");
        break;
      default:
        break;
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
  
  @Override
  public void handleTick(int arg0)
  {
    setLocation(left, top);
  }

}
