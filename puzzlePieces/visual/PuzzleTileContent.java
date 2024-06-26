package puzzlePieces.visual;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import gui.PuzzleCursor;
import puzzlePieces.PuzzleTile;
import puzzlePieces.auditory.PuzzleSoundPlayer;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

/**
 * A PuzzleTile that knows how to render itself,
 * knows how to interact with the mouse cursor,
 * knows how to interact with other tiles,
 * and knows how to play audio effects.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 * 
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleTileContent extends RuleBasedSprite implements MouseMotionListener
{
  private static final int DEFAULT_WIGGLE_ROOM = 10;
  
  private boolean aligned;
  private boolean held;
  private boolean hover;
  
  private boolean joinedNorth;
  private boolean joinedSouth;
  private boolean joinedEast;
  private boolean joinedWest;
  
  private double  left, top;
  private double  relativeLeft;
  private double  relativeTop;
  
  private PuzzleCursor           cursor;
  private PuzzleCompositeContent composite;
  private PuzzleSoundPlayer      soundPlayer;
  private PuzzleTile             tile;
  
  private PuzzleTileContent north;
  private PuzzleTileContent south;
  private PuzzleTileContent east;
  private PuzzleTileContent west;
  
  /**
   * Constructor.
   * @param content The TransformableContent containing the tile's image
   * @param tile The PuzzleTile being represented
   */
  public PuzzleTileContent(final TransformableContent content, final PuzzleTile tile)
  {
    super(content);
    
    this.tile = tile;
    
    left = tile.getLeft();
    top  = tile.getTop();
    setLocation(left, top);
    
    hover = false;
    held  = false;
    
    relativeLeft = 0;
    relativeTop  = 0;
    
    north = null;
    south = null;
    east  = null;
    west  = null;
    
    composite = null;
    
    joinedNorth = false;
    joinedSouth = false;
    joinedEast  = false;
    joinedWest  = false;
    
    aligned = true;
  }
  
  /**
   * Recursive method that tells each neighbor to align itself with the edge of this tile
   * if not already aligned, and then to do the same with each of its neighbors.
   */
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
  
  protected void alignConnections(final double l, final double t)
  {
    if (!aligned)
    {
      setPosition(l, t);
      aligned = true;
      alignConnections();
    }
  }
  
  /**
   * Get the PuzzleCompositeContent, or null if this tile is not in a composite.
   * @return The PuzzleCompositeContent, or null
   */
  public PuzzleCompositeContent getComposite()
  {
    return composite;
  }
  
  /**
   * get the position of the PuzzleTileContent as a Point.
   * @return The Point located at the top left of the tile.
   */
  public Point getPosition()
  {
    Point p = new Point();
    p.setLocation(left, top);
    return p;
  }
  
  /**
   * Notify that this tile is being held by the cursor.
   */
  public void grab()
  {
    held = true;
  }
  
  @Override
  public void handleTick(final int arg0)
  {
    setLocation(left, top);
  }
  
  private void join(final PuzzleTileContent neighbor)
  {
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
  
  protected void joinEast()
  {
    joinedEast = true;
  }
  
  protected void joinNorth()
  {
    joinedNorth = true;
  }
  
  protected void joinSouth()
  {
    joinedSouth = true;
  }
  
  protected void joinWest()
  {
    joinedWest = true;
  }
  
  /**
   * When the tile is clicked and dragged.
   */
  @Override
  public void mouseDragged(final MouseEvent e)
  {
    if (hover)
    {
      cursor.grab(this, e);
    }
    
    if (held)
    {
      left = (double)e.getX() - relativeLeft;
      top  = (double)e.getY() - relativeTop;
    }
  }

  /**
   * When the tile is hovered over but not being clicked.
   */
  @Override
  public void mouseMoved(final MouseEvent e)
  {
    // Detect if the mouse cursor is located within the bounds of the tile
    hover = (e.getX() > left && e.getX() < left + tile.getWidth()
      && e.getY() > top  && e.getY() < top + tile.getHeight());
    
    // Check if the user has finished dragging the tile.
    if (hover && held)
      cursor.release();
  }
  
  private int notifyNeighbor(final PuzzleTileContent neighbor)
  {
    if (neighbor != null)
      return neighbor.observePosition(this);
    return 0;
  }
  
  /**
   * Notify neighbors that the tile has been released, 
   * so that they can check if their positions are close enough for a connection to be made.
   */
  public void notifyNeighbors()
  {
    int playSound = 0;
    
    if (north != null)
      playSound += notifyNeighbor(north);
    if (south != null)
      playSound += notifyNeighbor(south);
    if (east != null)
      playSound += notifyNeighbor(east);
    if (west != null)
      playSound += notifyNeighbor(west);
    
    if (playSound == 1)
      soundPlayer.playConnectSound();
    else if (playSound == 2)
      soundPlayer.playDoubleConnectSound();
    else if (playSound > 2)
      soundPlayer.playMultiConnectSound();
  }
  
  /**
   * Observe that the alignment process is beginning for the composite.
   */
  public void observeAlignment()
  {
    aligned = false;
  }
  
  /**
   * Check if the given tile is close enough and positioned correctly to connect.
   * @param neighbor The tile to check
   * @return 1 if a connection was made, 0 otherwise.
   */
  public int observePosition(final PuzzleTileContent neighbor)
  {
    Point p = neighbor.getPosition();
    
    double hDiff      = left - p.x;
    double vDiff      = top - p.y;
    double tileWidth  = tile.getWidth();
    double tileHeight = tile.getHeight();
    
    int playSound = 0;
    
    // In the future, "wiggle room" should be configurable by the user for accessibility purposes
    double wiggleRoom = DEFAULT_WIGGLE_ROOM;
    
    if (neighbor == north && !joinedNorth && Math.abs(hDiff) < wiggleRoom
        && vDiff > tileHeight - wiggleRoom && vDiff < tileHeight + wiggleRoom)
    {
      // connect with north
      joinNorth();
      north.joinSouth();
      join(north);
      playSound = 1;
    }
    else if (neighbor == south && !joinedSouth && Math.abs(hDiff) < wiggleRoom
        && -vDiff > tileHeight - wiggleRoom && -vDiff < tileHeight + wiggleRoom)
    {
      // connect with south
      joinSouth();
      south.joinNorth();
      join(south);
      playSound = 1;
    }
    else if (neighbor == east && !joinedEast && Math.abs(vDiff) < wiggleRoom
        && -hDiff > tileWidth - wiggleRoom && -hDiff < tileWidth + wiggleRoom)
    {
      // connect with east
      joinEast();
      east.joinWest();
      join(east);
      playSound = 1;
    }
    else if (neighbor == west && !joinedWest && Math.abs(vDiff) < wiggleRoom
        && hDiff > tileWidth - wiggleRoom && hDiff < tileWidth + wiggleRoom)
    {
      // connect with west
      joinWest();
      west.joinEast();
      join(west);
      playSound = 1;
    }
    
    return playSound;
  }
  
  /**
   * Notify that this tile is no longer being held by the cursor, and notify its neighbors.
   */
  public void release()
  {
    held = false;
    notifyNeighbors();
  }
  
  /**
   * Set the PuzzleCompositeContent.
   * @param composite The PuzzleCompositeContent that this tile is a part of
   */
  public void setComposite(final PuzzleCompositeContent composite)
  {
    this.composite = composite;
  }
  
  /**
   * Set the Cursor.
   * @param cursor The Cursor
   */
  public void setCursor(final PuzzleCursor cursor)
  {
    this.cursor = cursor;
  }
  
  /**
   * Set a PuzzleTileContent to be the east neighbor.
   * @param t The PuzzleTileContent
   */
  public void setEast(final PuzzleTileContent t)
  {
    east = t;
  }
  
  /**
   * Set a PuzzleTileContent to be the north neighbor.
   * @param t The PuzzleTileContent
   */
  public void setNorth(final PuzzleTileContent t)
  {
    north = t;
  }
  
  /**
   * Set the position.
   * @param l The left-side position
   * @param t The top-side position
   */
  public void setPosition(final double l, final double t)
  {
    left = l;
    top  = t;
  }
  
  /**
   * Determine and set the relative position from the top-left corner to the cursor.
   * @param x The x-coordinate location of the cursor
   * @param y The y-coordinate location of the cursor
   */
  public void setRelativePosition(final double x, final double y)
  {
    relativeLeft = x - this.left;
    relativeTop  = y - this.top;
  }
  
  /**
   * Set the sound player.
   * @param soundPlayer The sound player
   */
  public void setSoundPlayer(final PuzzleSoundPlayer soundPlayer)
  {
    this.soundPlayer = soundPlayer;
  }
  
  /**
   * Set a PuzzleTileContent to be the south neighbor.
   * @param t The PuzzleTileContent
   */
  public void setSouth(final PuzzleTileContent t)
  {
    south = t;
  }

  /**
   * Set a PuzzleTileContent to be the west neighbor.
   * @param t The PuzzleTileContent
   */
  public void setWest(final PuzzleTileContent t)
  {
    west = t;
  }
}
