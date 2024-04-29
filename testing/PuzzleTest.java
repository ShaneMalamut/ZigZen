package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import puzzlePieces.Puzzle;

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

}
