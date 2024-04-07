package puzzlePieces.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

import puzzlePieces.Puzzle;
import puzzlePieces.PuzzleTile;

/**
 * Utility class for reading Puzzle objects from a BufferedReader.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleReader
{
  /**
   * Read a Puzzle.
   *
   * Note: This method uses the parsePuzzle() method in the Puzzle class,
   * and the parsePuzzleTile() method in the parsePuzzleTile class.
   * The first line of the BufferedReader is expected to be a String
   * representation of the Puzzle, with all following lines representing
   * PuzzleTiles. 
   * @param reader The BufferedReader
   * @return       The Puzzle, with all PuzzleTiles added
   */
  public static Puzzle read(final BufferedReader reader) throws IOException, NoSuchElementException, NumberFormatException
  {
    String line = reader.readLine();

    if (line == null)
      return null;
    
    Puzzle puzzle = Puzzle.parsePuzzle(line);
    
    line = reader.readLine();
    while (line != null)
    {
      PuzzleTile tile = PuzzleTile.parsePuzzleTile(line);
      puzzle.add(tile);
      
      line = reader.readLine();
    }
    
    return puzzle;
  }
}
