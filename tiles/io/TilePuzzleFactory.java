package tiles.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import io.ResourceFinder;
import tiles.Tile;
import tiles.TilePuzzle;
import visual.statik.sampled.ImageFactory;

/**
 * A utility class for constructing/creating 
 * TilePuzzle objects.
 *
 * @author  Shane Malamut, James Madison University
 *          Based on visual.statik.sampled.ContentFactory by Prof. David Bernstein
 * @version 1.0
 * 
 *          This work complies with the JMU Honor Code.
 */
public class TilePuzzleFactory
{
  private ImageFactory imageFactory;
  
  private static final double TAB_LENGTH_PERCENTAGE = 0.25;
  
  /**
   * Default Constructor.
   */
  public TilePuzzleFactory()
  {
    imageFactory = new ImageFactory();
  }

  /**
   * Explicit Value Constructor.
   *
   * @param finder   The ResourceFinder to use (if needed)
   */
  public TilePuzzleFactory(ResourceFinder finder)
  {
    imageFactory = new ImageFactory(finder);
  }

  /**
   * Create a TilePuzzle from a BufferedImage.
   *
   * @param image       The BufferedImage
   * @param description The name or description associated with the puzzle
   * @param rows        The number of rows
   * @param cols        The number of columns
   * @return            The TilePuzzle
   */
  public TilePuzzle createTilePuzzle(BufferedImage image, String description, int rows, int cols)
  {
    TilePuzzle puzzle = new TilePuzzle(description);
    
    int height = image.getHeight();
    int width  = image.getWidth();
    
    int tileHeight = height/rows;
    int tileWidth  = width/cols;

    int hMaxTabLength = (int) (tileHeight * TAB_LENGTH_PERCENTAGE); // Left/right faces
    int vMaxTabLength = (int) (tileWidth * TAB_LENGTH_PERCENTAGE);  // Top/bottom faces
    
    List<Tile> tiles = new ArrayList<Tile>();
    
    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        tiles.add(new Tile());
      }
    }
    
    image.getSubimage(vMaxTabLength, hMaxTabLength, tileWidth, vMaxTabLength);
    
    return puzzle;
  }
  
  /**
   * Create a TilePuzzle from a BufferedImage,
   * with column number determined based on rows and image size
   * to result in square tiles.
   *
   * @param image       The BufferedImage
   * @param tabWidth    The distance each "tab" should protrude from the tile
   * @param description The name or description associated with the puzzle
   * @param rows        The number of rows
   * @return            The TilePuzzle
   */
  public TilePuzzle createTilePuzzle(BufferedImage image, String description, int rows)
  {
    int height = image.getHeight();
    int width  = image.getWidth();
    
    int tileHeight = height/rows;
    int cols = width/tileHeight;
    
    return createTilePuzzle(image, description, rows, cols);
  }
  
  /**
   * Create a TilePuzzle from an Image.
   *
   * @param image        The original Image
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param cols         The number of columns
   * @return             The TilePuzzle
   */
  public TilePuzzle createTilePuzzle(Image image, String description, int rows, int cols)
  {
    BufferedImage bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(image, 4);
    return createTilePuzzle(bi, description, rows, cols);
  }
  
  /**
   * Create a TilePuzzle from an Image,
   * with column number determined based on rows and image size
   * to result in square tiles.
   *
   * @param image        The original Image
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @return             The TilePuzzle
   */
  public TilePuzzle createTilePuzzle(Image image, String description, int rows)
  {
    BufferedImage bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(image, 4);
    return createTilePuzzle(bi, description, rows);
  }

  /**
   * Create a TilePuzzle from a file/resource
   * containing an Image.
   *
   * @param name         The name of the file/resource
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param cols         The number of columns
   * @return             The TilePuzzle
   */
  public TilePuzzle createTilePuzzle(String name, String description, int rows, int cols)
  {
    BufferedImage          bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(name, 4);
    return createTilePuzzle(bi, description, rows, cols);
  }
  
  /**
   * Create a TilePuzzle from a file/resource
   * containing an Image,
   * with column number determined based on rows and image size
   * to result in square tiles.
   *
   * @param name         The name of the file/resource
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param cols         The number of columns
   * @return             The TilePuzzle
   */
  public TilePuzzle createTilePuzzle(String name, String description, int rows)
  {
    BufferedImage          bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(name, 4);
    return createTilePuzzle(bi, description, rows);
  }
}
