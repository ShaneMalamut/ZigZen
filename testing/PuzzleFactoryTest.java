package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import io.ResourceFinder;
import puzzlePieces.Puzzle;
import puzzlePieces.PuzzleFactory;
import resources.Marker;

class PuzzleFactoryTest
{

  @Test
  void defaultConstructorTest()
  {
    PuzzleFactory pf = new PuzzleFactory();
    BufferedImage image = null;
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
    InputStream   is = rf.findInputStream("StarryNight.jpg");
    
    if (is != null)
    {
      try
      {
        image = ImageIO.read(is);
        is.close();
      }
      catch (IOException ioe)
      {
        fail("Failed to read image");
      }
    }
    
    Puzzle puzzle = pf.createPuzzle(image, "testdesc", 3, 2);
    assertEquals(2, puzzle.getCols(), "columns");
    assertEquals(3, puzzle.getRows(), "rows");
    assertEquals("testdesc", puzzle.getDescription(), "description");
    assertEquals(6, puzzle.size(), "size");
    
    puzzle = pf.createPuzzle(image, "testdesc", 3);
    assertEquals(3, puzzle.getCols(), "columns");
    assertEquals(3, puzzle.getRows(), "rows");
    assertEquals("testdesc", puzzle.getDescription(), "description");
    assertEquals(9, puzzle.size(), "size");
  }
  
  @Test
  void explicitValueConstructorTest()
  {
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
    PuzzleFactory pf = new PuzzleFactory(rf);
    BufferedImage image = null;
    InputStream   is = rf.findInputStream("StarryNight.jpg");
    
    if (is != null)
    {
      try
      {
        image = ImageIO.read(is);
        is.close();
      }
      catch (IOException ioe)
      {
        fail("Failed to read image");
      }
    }
    
    Puzzle puzzle = pf.createPuzzle(image, "testdesc", 3, 2);
    assertEquals(2, puzzle.getCols(), "columns");
    assertEquals(3, puzzle.getRows(), "rows");
    assertEquals("testdesc", puzzle.getDescription(), "description");
    assertEquals(6, puzzle.size(), "size");
    
    puzzle = pf.createPuzzle(image, "testdesc", 3);
    assertEquals(3, puzzle.getCols(), "columns");
    assertEquals(3, puzzle.getRows(), "rows");
    assertEquals("testdesc", puzzle.getDescription(), "description");
    assertEquals(9, puzzle.size(), "size");
  }

}
