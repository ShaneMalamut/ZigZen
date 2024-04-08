package puzzlePieces.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import io.ResourceFinder;
import puzzlePieces.PuzzleTile;
import puzzlePieces.Puzzle;
import visual.statik.sampled.ImageFactory;

/**
 * A utility class for constructing/creating 
 * Puzzle objects.
 *
 * @author  Shane Malamut, James Madison University
 *          Based on visual.statik.sampled.ContentFactory by Prof. David Bernstein
 * @version 1.0
 * 
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleFactory
{
  private ImageFactory imageFactory;
  
  private static final double TAB_LENGTH_PERCENTAGE = 0.25;
  
  /**
   * Default Constructor.
   */
  public PuzzleFactory()
  {
    imageFactory = new ImageFactory();
  }

  /**
   * Explicit Value Constructor.
   *
   * @param finder   The ResourceFinder to use (if needed)
   */
  public PuzzleFactory(ResourceFinder finder)
  {
    imageFactory = new ImageFactory(finder);
  }

  /**
   * Create a Puzzle from a BufferedImage.
   *
   * @param image       The BufferedImage
   * @param description The name or description associated with the puzzle
   * @param rows        The number of rows
   * @param cols        The number of columns
   * @return            The Puzzle
   */
  public Puzzle createPuzzle(BufferedImage image, String description, int rows, int cols)
  {
    Puzzle puzzle = new Puzzle(description);
    
    int height = image.getHeight();
    int width  = image.getWidth();
    
    int tileHeight = height/rows;
    int tileWidth  = width/cols;

    int hMaxTabLength = (int) (tileHeight * TAB_LENGTH_PERCENTAGE); // Left/right faces
    int vMaxTabLength = (int) (tileWidth * TAB_LENGTH_PERCENTAGE);  // Top/bottom faces
    
    List<PuzzleTile> tiles = new ArrayList<PuzzleTile>();
    
    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        tiles.add(new PuzzleTile());
      }
    }
    
    image.getSubimage(vMaxTabLength, hMaxTabLength, tileWidth, vMaxTabLength);
    
    return puzzle;
  }
  
  /**
   * Create a Puzzle from a BufferedImage,
   * with column number determined based on rows and image size
   * to result in square tiles.
   *
   * @param image       The BufferedImage
   * @param tabWidth    The distance each "tab" should protrude from the tile
   * @param description The name or description associated with the puzzle
   * @param rows        The number of rows
   * @return            The Puzzle
   */
  public Puzzle createPuzzle(BufferedImage image, String description, int rows)
  {
    int height = image.getHeight();
    int width  = image.getWidth();
    
    int tileHeight = height/rows;
    int cols = width/tileHeight;
    
    return createPuzzle(image, description, rows, cols);
  }
  
  /**
   * Create a Puzzle from an Image.
   *
   * @param image        The original Image
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param cols         The number of columns
   * @return             The Puzzle
   */
  public Puzzle createPuzzle(Image image, String description, int rows, int cols)
  {
    BufferedImage bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(image, 4);
    return createPuzzle(bi, description, rows, cols);
  }
  
  /**
   * Create a Puzzle from an Image,
   * with column number determined based on rows and image size
   * to result in square tiles.
   *
   * @param image        The original Image
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @return             The Puzzle
   */
  public Puzzle createPuzzle(Image image, String description, int rows)
  {
    BufferedImage bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(image, 4);
    return createPuzzle(bi, description, rows);
  }

  /**
   * Create a Puzzle from a file/resource
   * containing an Image.
   *
   * @param name         The name of the file/resource
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param cols         The number of columns
   * @return             The Puzzle
   */
  public Puzzle createPuzzle(String name, String description, int rows, int cols)
  {
    BufferedImage          bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(name, 4);
    return createPuzzle(bi, description, rows, cols);
  }
  
  /**
   * Create a Puzzle from a file/resource
   * containing an Image,
   * with column number determined based on rows and image size
   * to result in square tiles.
   *
   * @param name         The name of the file/resource
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param cols         The number of columns
   * @return             The Puzzle
   */
  public Puzzle createPuzzle(String name, String description, int rows)
  {
    BufferedImage          bi;

    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"
    bi = imageFactory.createBufferedImage(name, 4);
    return createPuzzle(bi, description, rows);
  }
}
