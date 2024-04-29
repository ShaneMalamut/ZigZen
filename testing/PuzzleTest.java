package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import puzzlePieces.Puzzle;
import puzzlePieces.PuzzleTile;

class PuzzleTest
{

  @Test
  void constructorTest()
  {
    Puzzle p = new Puzzle("test", 3, 2);
    assertEquals(2, p.getCols(), "constuctor: columns");
    assertEquals(3, p.getRows(), "constuctor: rows");
    assertEquals("test", p.getDescription(), "constuctor: description");
  }
  
  @Test
  void addTest()
  {
    Puzzle p = new Puzzle("test", 3, 2);
    PuzzleTile tile = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile2 = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile3 = new PuzzleTile(0, 0, 0, 0, null);
    p.add(tile);
    p.add(tile2);
    p.add(tile3);
    
    assertEquals(tile, p.getElement(0), "first element is correct");
    assertEquals(3, p.size(), "size after adding 3 tiles");
    
    p.add(null);
    assertEquals(3, p.size(), "size after adding null");
  }
  
  @Test
  void resetTest()
  {
    Puzzle p = new Puzzle("test", 3, 2);
    PuzzleTile tile = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile2 = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile3 = new PuzzleTile(0, 0, 0, 0, null);
    p.add(tile);
    p.add(tile2);
    p.add(tile3);
    
    assertEquals(3, p.size(), "size before reset");
    p.reset();
    assertEquals(0, p.size(), "size after reset");
  }

  @Test
  void sizeTest()
  {
    Puzzle p = new Puzzle("test", 3, 2);
    assertEquals(0, p.size(), "size before adding any tiles");
    PuzzleTile tile = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile2 = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile3 = new PuzzleTile(0, 0, 0, 0, null);
    p.add(tile);
    assertEquals(1, p.size(), "size after adding 1 tile");
    p.add(tile2);
    assertEquals(2, p.size(), "size after adding 2 tiles");
    p.add(tile3);
    assertEquals(3, p.size(), "size after adding 3 tiles");
  }
  
  @Test
  void iteratorTest()
  {
    Puzzle p = new Puzzle("test", 3, 2);
    PuzzleTile tile = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile2 = new PuzzleTile(0, 0, 0, 0, null);
    PuzzleTile tile3 = new PuzzleTile(0, 0, 0, 0, null);
    p.add(tile);
    p.add(tile2);
    p.add(tile3);
    
    Iterator<PuzzleTile> iter = p.iterator();

    assertTrue(iter.hasNext(), "iter has 3 left");
    assertEquals(tile, iter.next(), "iter first element");
    assertTrue(iter.hasNext(), "iter has 2 left");
    assertEquals(tile2, iter.next(), "iter first element");
    assertTrue(iter.hasNext(), "iter has 1 left");
    assertEquals(tile3, iter.next(), "iter first element");
    assertFalse(iter.hasNext(), "iter has 0 left");
  }
}
