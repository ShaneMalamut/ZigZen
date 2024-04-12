package puzzlePieces;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class PuzzleTile implements Component
{
  private Point location;
  private int row;
  private int col;
  private int tileHeight;
  private int tileWidth;
  private BufferedImage image;
  
//Should start with a random rotation, random position?, and 10% of being "flipped over". 
  // Scroll wheel to rotate a tile while it is picked up
  // Scroll wheel to zoom in and out while you are not holding a tile
  // Hold right click to pan the screen
  // Clicking a face-down tile flips it over
  // Clicking a face-up tile picks it up
  // - Clicking again drops it
  
  // All held tiles should check for connections at each tick interval
  // Tiles should be divided into grid cells for performance reasons
  public PuzzleTile(int row, int col, int tileHeight, int tileWidth, BufferedImage image)
  {
    this.row = row;
    this.col = col;
    this.tileHeight = tileHeight;
    this.tileWidth = tileWidth;
    this.image = image;
    this.location = new Point(0,0);
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
  
  public BufferedImage getImage()
  {
    return image;
  }
  
  public Point getLocation()
  {
    return location;
  }
  
  public double getLeft()
  {
    return location.getX();
  }
  
  public double getTop()
  {
    return location.getY();
  }
  
  public double getWidth()
  {
    return tileWidth;
  }
  
  public double getHeight()
  {
    return tileHeight;
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

  /**
   * Constructs a PuzzleTile object from a String representation.
   * @param line The String representation
   * @return     The PuzzleTile
   */
  public static PuzzleTile parsePuzzleTile(final String line)
  {
    // TODO Auto-generated method stub
    return null;
  }
}
