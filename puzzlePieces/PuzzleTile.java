package puzzlePieces;

import java.awt.Point;

public class PuzzleTile implements Component
{
  private Point location;
  private int degrees;
  
  
  public PuzzleTile()
  {
    // Should start with a random rotation, random position?, and 10% of being "flipped over". 
    // Scroll wheel to rotate a tile while it is picked up
    // Scroll wheel to zoom in and out while you are not holding a tile
    // Hold right click to pan the screen
    // Clicking a face-down tile flips it over
    // Clicking a face-up tile picks it up
    // - Clicking again drops it
    
    // All held tiles should check for connections at each tick interval
    // Tiles should be divided into grid cells for performance reasons
    this(new Point(), 0);
  }
  
  public PuzzleTile(final Point location)
  {
    this(location, 0);
  }
  
  public PuzzleTile(final int rotation)
  {
    this(new Point(), rotation);
  }
  
  public PuzzleTile(final Point location, final int rotation)
  {
    this.location = location;
    degrees = 0;
  }
  
  public int connectEdge(final PuzzleTile tile)
  {
    //TODO math stuff
    //check the side for valid connections (is the matching tiles nearby with the right rotation?),
    //make those connections if possible by combining the tiles into a composite
    //return 1 if a connection was made
    return 0;
  }
  
  /**
   * Call the connectEdge() method for each edge
   */
  public int connectEdges()
  {
    //TODO
    return 0;
  }
  
  public void rotate(final Point origin, final int degrees)
  {
    //TODO math stuff
  }
  
  public void translate(final int x, final int y)
  {
    location.translate(x, y);
  }
  
  public void translate(final Point start, final Point end)
  {
    int x = end.x - start.x;
    int y = end.y - start.y;
    translate(x,y);
  }
}
