package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import io.ResourceFinder;
import puzzlePieces.Puzzle;
import resources.Marker;
import visual.VisualizationView;
import visual.dynamic.described.DescribedSprite;
import visual.dynamic.described.Stage;
import visual.statik.TransformableContent;
import visual.statik.sampled.BufferedImageOpFactory;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

public class PuzzleBoard extends Stage
{
  private static final Color BACKGROUND_COLOR = new Color(244, 239, 225);
  private static final int   TIME_STEP        = 100;
  
  private Content        watermark;
  private BufferedImage  watermarkTransparent;
  private ResourceFinder jarFinder;
  private Puzzle         puzzle;
  
  /**
   * Constructor.
   * @param width The width of the GUI
   * @param height The height of the GUI
   */
  public PuzzleBoard(final int width, final int height)
  {
    super(TIME_STEP);
    
    jarFinder = ResourceFinder.createInstance(new Marker());
    
    VisualizationView view = getView();
    view.setBounds(0, 0, width, height);
    view.setSize(width, height);
    view.setBackground(BACKGROUND_COLOR);
    
    handleWatermark(width, height);
  }
  
  private void handleWatermark(final int width, final int height)
  {
    BufferedImage image = null;
    InputStream   is = jarFinder.findInputStream("ZigZen.png");
    
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
    
    // Attempt to get the transparent version
    is = jarFinder.findInputStream("ZigZenTransparent.png");
    if (is != null)
    {
      try
      {
        watermarkTransparent = ImageIO.read(is);
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

    // Set the location of the Watermark to be centered at the bottom of the screen
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
    this.puzzle = puzzle;
    
    //Switch to transparent watermark when puzzle is displayed
    watermark.setImage(watermarkTransparent);
  }
}
