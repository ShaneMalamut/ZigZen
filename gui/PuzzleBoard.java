package gui;

import java.awt.Color;
import java.util.Random;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import io.ResourceFinder;
import puzzlePieces.Puzzle;
import puzzlePieces.PuzzleTile;
import puzzlePieces.visual.PuzzleTileContent;
import resources.Marker;
import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.TransformableContent;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

/**
 * A Stage that displays the PuzzleTileContent objects of a puzzle.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleBoard extends Stage
{
  private static final Color BACKGROUND_COLOR = new Color(244, 239, 225);
  private static final int   TIME_STEP        = 15;
  
  private Content        watermark;
  private ResourceFinder jarFinder;
  private PuzzleCursor   cursor;
  private int            width;
  private int            height;
  
  /**
   * Constructor.
   * @param width The width of the GUI
   * @param height The height of the GUI
   */
  public PuzzleBoard(final int width, final int height)
  {
    super(TIME_STEP);
    
    this.width  = width;
    this.height = height;
    
    jarFinder = ResourceFinder.createInstance(new Marker());
    
    // Prepare the VisualizationView
    VisualizationView view = getView();
    view.setBounds(0, 0, width, height);
    view.setSize(width, height);
    view.setBackground(BACKGROUND_COLOR);
    
    // Add opaque Watermark
    handleWatermark(false);
    
    start();
  }
  
  private void handleWatermark(final boolean transparent)
  {
    BufferedImage image = null;
    
    // Read the appropriate Watermark file from the resources package
    String watermarkString;
    if (transparent)
    {
      watermarkString = "ZigZenTransparent.png";
    }
    else
    {
      watermarkString = "ZigZen.png";
    }
    InputStream is = jarFinder.findInputStream(watermarkString);
    
    // Attempt to get the image
    if (is != null)
    {
      try
      {
        image = ImageIO.read(is);
        is.close();
      }
      catch (IOException ioe)
      {
        return;
      }
    }
    
    // Create the Watermark as a Content object
    ContentFactory contentFactory = new ContentFactory(jarFinder);
    watermark = contentFactory.createContent(image);

    // Set the location of the Watermark to be centered
    Rectangle2D bounds = watermark.getBounds2D();
    watermark.setLocation(width/2.0 - bounds.getWidth()/2.0, height/2.0 - bounds.getHeight()/2.0);
    
    // Add the Watermark to the GUI and move it to the back
    add(watermark);
    toBack(watermark);
  }
  
  /**
   * Set and display the Puzzle.
   * @param puzzle The Puzzle
   */
  public void setPuzzle(final Puzzle puzzle)
  {
    int rows = puzzle.getRows();
    int cols = puzzle.getCols();

    ContentFactory        tcFactory = new ContentFactory();
    PuzzleTileContent[][] grid      = new PuzzleTileContent[rows][cols];
    Random                rand      = new Random();
    
    clear();
    
    //Switch to transparent watermark
    handleWatermark(true);
    
    // Create the Cursor
    cursor = new PuzzleCursor(this);

    // For each PuzzleTile, create and prepare its associated Content object
    for (PuzzleTile tile : puzzle)
    {
      // Create the Content from the appropriate subimage and set the Cursor
      TransformableContent content = tcFactory.createContent(tile.getImage());
      PuzzleTileContent tileContent = new PuzzleTileContent(content, tile);
      tileContent.setCursor(cursor);

      // Position the PuzzleTileContent at a random position on the screen
      // In the future, a more robust method of spreading out tiles should be implemented,
      // to more accurately simulate the way that dumping a box of puzzle pieces looks.
      // (i.e., most land in the center and a few spread out at the edges in a circle)
      double randX = rand.nextInt((int) (width * 0.5)) + width * 0.25;
      double randY = rand.nextInt((int) (height * 0.5)) + height * 0.25;
      tileContent.setPosition(randX, randY);
      
      // Add the Content to the Stage
      add(tileContent);
      toFront(tileContent);
      addMouseMotionListener(tileContent);
      
      // Add the tile to a temporary 2D array, used for building neighbor associations,
      // so that each tile knows which tiles it can connect to on each face.
      int row = tile.getRow();
      int col = tile.getCol();
      grid[row][col] = tileContent;
      
      // Set association with the tile above, skipped on first row
      if (row > 0)
      {
        grid[row-1][col].setSouth(tileContent);
        tileContent.setNorth(grid[row-1][col]);
      }
      
      // Set association with the tile to the left, skipped on first row
      if (col > 0)
      {
        grid[row][col-1].setEast(tileContent);
        tileContent.setWest(grid[row][col-1]);
      }
    }
    
    // Put the watermark behind the puzzle tiles.
    // If a more robust implementation of tile grabbing is implemented, this line can be removed,
    // and tiles should be sent toBack() and instead of toFront() in the above loop.
    toFront(watermark);
  }
}
