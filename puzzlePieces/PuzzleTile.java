package puzzlePieces;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * A Puzzle tile, containing a sub image of the larger Puzzle picture.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleTile
{
  private BufferedImage image;
  private int           row;
  private int           col;
  private int           tileHeight;
  private int           tileWidth;
  private Point         location;
  
  /**
   * Constructor.
   * @param row The row position of the tile
   * @param col The column position of the tile
   * @param tileHeight The height of the tile
   * @param tileWidth The width of the tile
   * @param image The image of the tile
   */
  public PuzzleTile(final int row, final int col, final int tileHeight, final int tileWidth, 
      final BufferedImage image)
  {
    this.row = row;
    this.col = col;
    this.tileHeight = tileHeight;
    this.tileWidth = tileWidth;
    this.image = image;
    this.location = new Point(0,0);
  }
  
  /**
   * Get the column of this tile.
   * @return The column
   */
  public int getCol()
  {
    return col;
  }
  
  /**
   * Get the height of this tile.
   * @return The height
   */
  public double getHeight()
  {
    return tileHeight;
  }
  
  /**
   * Get the image of this tile.
   * @return The image
   */
  public BufferedImage getImage()
  {
    return image;
  }
  
  /**
   * Get the left-side position of this tile.
   * @return The left-side position
   */
  public double getLeft()
  {
    return location.getX();
  }
  
  /**
   * Get the location of this tile.
   * @return The location
   */
  public Point getLocation()
  {
    return location;
  }
  
  /**
   * Get the row of this tile.
   * @return The row
   */
  public int getRow()
  {
    return row;
  }
  
  /**
   * Get the top-side position of this tile.
   * @return The top-side position
   */
  public double getTop()
  {
    return location.getY();
  }
  
  /**
   * Get the width of this tile.
   * @return The width
   */
  public double getWidth()
  {
    return tileWidth;
  }
}
