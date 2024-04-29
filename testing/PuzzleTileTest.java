package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import io.ResourceFinder;
import puzzlePieces.Puzzle;
import puzzlePieces.PuzzleTile;
import resources.Marker;

class PuzzleTileTest
{

  @Test
  void constructorTest()
  {
    BufferedImage image = null;
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
    InputStream   is = rf.findInputStream("StarryNight.jpg");
    
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
        // Failed to read image
        fail();
      }
    }
    
    PuzzleTile p = new PuzzleTile(2, 3, 50, 45, image);
    assertEquals(2, p.getRow(), "constructor: row");
    assertEquals(3, p.getCol(), "constructor: column");
    assertEquals(50, p.getHeight(), "constructor: height");
    assertEquals(45, p.getWidth(), "constructor: width");
    assertEquals(image, p.getImage(), "constructor: image");
    
    Point point = p.getLocation();
    assertEquals(0, point.x, "constructor: location x");
    assertEquals(0, point.y, "constructor: location y");
    assertEquals(0, p.getLeft(), "constructor: left");
    assertEquals(0, p.getTop(), "constructor: top");
    
  }

}
