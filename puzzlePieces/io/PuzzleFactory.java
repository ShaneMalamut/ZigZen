package puzzlePieces.io;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
    
    //temp
    hMaxTabLength = 0;
    vMaxTabLength = 0;

    System.out.println(String.format("Height: %d", height));
    System.out.println(String.format("Width:  %d", width));
    System.out.println(String.format("rows:   %d", rows));
    System.out.println(String.format("cols:   %d", cols));
        
    int w = tileWidth+2*hMaxTabLength;
    int h = tileHeight+2*vMaxTabLength;
    
    for (int r = 0; r < rows; r++)
    {
      int y = r*tileHeight-vMaxTabLength;
      if (y < 0) y = 0;
      if (y > height - h) y = height - h;
      
      for (int c = 0; c < cols; c++)
      {
        int x = c*tileWidth-hMaxTabLength;
        if (x < 0) x = 0;
        if (x > width - w) x = width - w;
        
        puzzle.add(new PuzzleTile(r, c, tileHeight, tileWidth, image.getSubimage(x, y, w, h)));
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

    bi = null;
    
    try
    {
      bi = ImageIO.read(new File(name));
    }
    catch (IOException io)
    {
      bi = null;
    }
    
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
    BufferedImage bi;
    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"

    bi = null;
    
    try
    {
      bi = ImageIO.read(new File(name));
    }
    catch (IOException io)
    {
      bi = null;
    }
    
    return createPuzzle(bi, description, rows);
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
  public Puzzle createPuzzle(String name, String description, int rows, int cols, Dimension dimension)
  {
    BufferedImage bi;
    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"

    bi = null;
    
    try
    {
      bi = ImageIO.read(new File(name));
    }
    catch (IOException io)
    {
      bi = null;
    }
    
    Image image = null;
    if (bi.getHeight() > dimension.height)
    {
      image = bi.getScaledInstance(-1, dimension.height, 0);
      
      if (image.getWidth(null) > dimension.width)
        image = image.getScaledInstance(dimension.width, -1, 0);
    }
    else if (bi.getWidth() > dimension.width)
      image = bi.getScaledInstance(dimension.width, -1, 0);
      
    
    if (image == null)
      return createPuzzle(bi, description, rows, cols);
    else
      return createPuzzle(image, description, rows, cols);
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
  public Puzzle createPuzzle(String name, String description, int rows, Dimension dimension)
  {
    BufferedImage bi;
    //Channels has to be 4 for ARGB to allow for cutting out the "tabs" and "blanks"

    bi = null;
    
    try
    {
      bi = ImageIO.read(new File(name));
    }
    catch (IOException io)
    {
      bi = null;
    }
    
    Image image = null;
    if (bi.getHeight() > dimension.height)
    {
      image = bi.getScaledInstance(-1, dimension.height, 0);
      
      if (image.getWidth(null) > dimension.width)
        image = image.getScaledInstance(dimension.width, -1, 0);
    }
    else if (bi.getWidth() > dimension.width)
      image = bi.getScaledInstance(dimension.width, -1, 0);
      
    
    if (image == null)
      return createPuzzle(bi, description, rows);
    else
      return createPuzzle(image, description, rows);
  }
}
