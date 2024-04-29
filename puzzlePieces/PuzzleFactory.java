package puzzlePieces;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.ResourceFinder;
import visual.statik.sampled.ImageFactory;

/**
 * A utility class for constructing/creating Puzzle objects.
 *
 * @author  Shane Malamut, James Madison University
 *          Based on visual.statik.sampled.ContentFactory by Prof. David Bernstein
 * @version 1.0
 * 
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleFactory
{
  private static final double TAB_LENGTH_PERCENTAGE = 0.25;
  
  private ImageFactory imageFactory;
  
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
   * @param finder The ResourceFinder to use (if needed)
   */
  public PuzzleFactory(final ResourceFinder finder)
  {
    imageFactory = new ImageFactory(finder);
  }

  /**
   * Create a Puzzle from a BufferedImage.
   *
   * @param image       The BufferedImage
   * @param description The name or description associated with the puzzle
   * @param rows        The number of rows
   * @param columns     The number of columns, or -1 if it should be calculated
   * @return            The Puzzle
   */
  public Puzzle createPuzzle(final BufferedImage image, final String description,
      final int rows, final int columns)
  {
    int height = image.getHeight();
    int width  = image.getWidth();
    
    int tileHeight = height/rows;
    
    // If columns is -1, calculate cols based on the width and tileHeight 
    // to be as close to square shape as possible
    int cols = columns;
    if (cols == -1)
      cols = width/tileHeight;
    
    int tileWidth  = width/cols;

    // Maximum distance that a tab can extend outside the original image section
    // Used to calculate how much "extra" space in the image to include in each tile,
    // so that the tabs can be "cut out"
    int hMaxTabLength = (int) (tileHeight * TAB_LENGTH_PERCENTAGE); // Left/right faces
    int vMaxTabLength = (int) (tileWidth * TAB_LENGTH_PERCENTAGE);  // Top/bottom faces
    
    // Tabs are not currently implemented. Until tabs are implemented, these are set to 0.
    hMaxTabLength = 0;
    vMaxTabLength = 0;
    
    int w = tileWidth+2*hMaxTabLength;
    int h = tileHeight+2*vMaxTabLength;
    
    Puzzle puzzle = new Puzzle(description, rows, cols);
    
    // Loop through each row and column, and for each, construct a new PuzzleTile
    // with the appropriate subimage, and add it to the Puzzle.
    for (int r = 0; r < rows; r++)
    {
      // Calculate the expected top position of the tile
      int y = r*tileHeight-vMaxTabLength;
      
      // Prevent edge tiles from extending above the available image
      if (y < 0)
        y = 0;
      
      // Prevent edge tiles from extending below the available image
      if (y > height - h)
        y = height - h;
      
      for (int c = 0; c < cols; c++)
      {
        // Calculate the expected left position of the tile
        int x = c*tileWidth-hMaxTabLength;
        
        // Prevent edge tiles from extending left of the available image
        if (x < 0)
          x = 0;
        
        // Prevent edge tiles from extending right of the available image
        if (x > width - w)
          x = width - w;
        
        // Construct and add the tile
        PuzzleTile tile = new PuzzleTile(r, c, tileHeight, tileWidth,
            image.getSubimage(x, y, w, h));
        puzzle.add(tile);
      }
    }
    
    return puzzle;
  }
  
  /**
   * Create a Puzzle from a BufferedImage,
   * with column number determined based on rows and image size
   * to result in square tiles.
   *
   * @param image       The BufferedImage
   * @param description The name or description associated with the puzzle
   * @param rows        The number of rows
   * @return            The Puzzle
   */
  public Puzzle createPuzzle(final BufferedImage image, final String description, final int rows)
  {
    return createPuzzle(image, description, rows, -1);
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
  public Puzzle createPuzzle(final Image image, final String description, 
      final int rows, final int cols)
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
  public Puzzle createPuzzle(final Image image, final String description, final int rows)
  {
    return createPuzzle(image, description, rows, -1);
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
  public Puzzle createPuzzle(final String name, final String description, 
      final int rows, final int cols) throws IOException
  {
    BufferedImage bi;
    bi = ImageIO.read(new File(name));
    
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
   * @return             The Puzzle
   */
  public Puzzle createPuzzle(final String name, final String description, final int rows) 
      throws IOException
  {
    return createPuzzle(name, description, rows, -1);
  }
  
  /**
   * Create a Puzzle from a file/resource
   * containing an Image,
   * and a Dimension to scale the image to fit within.
   *
   * @param name         The name of the file/resource
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param cols         The number of columns
   * @param dimension    The dimension of the area to fit to
   * @return             The Puzzle
   */
  public Puzzle createPuzzle(final String name, final String description, 
      final int rows, final int cols, final Dimension dimension) throws IOException
  {
    BufferedImage bi;
    bi = ImageIO.read(new File(name));
    
    // Here, the Dimension should be used to check if the image is too big to fit in the window.
    // If so, it should be shrunk before continuing. Not yet implemented.
    
    return createPuzzle(bi, description, rows, cols);
  }
  
  /**
   * Create a Puzzle from a file/resource
   * containing an Image,
   * with column number determined based on rows and image size
   * to result in square tiles,
   * and a Dimension to scale the image to fit within.
   *
   * @param name         The name of the file/resource
   * @param description  The name or description associated with the puzzle
   * @param rows         The number of rows
   * @param dimension    The dimension of the area to fit to
   * @return             The Puzzle
   */
  public Puzzle createPuzzle(final String name, final String description, 
      final int rows, final Dimension dimension) throws IOException
  {
    return createPuzzle(name, description, rows, -1, dimension);
  }
}
