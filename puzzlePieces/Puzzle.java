package puzzlePieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Puzzle implements Iterable<PuzzleTile>
{
  protected String description;
  protected List<PuzzleTile> tiles;
  protected int rows;
  protected int cols;
  
  public Puzzle(String description, int rows, int cols)
  {
    this.description = description;
    this.rows = rows;
    this.cols = cols;
    
    tiles = new ArrayList<PuzzleTile>(rows*cols);
  }
  
  public void add(PuzzleTile tile)
  {
    if (tile != null) tiles.add(tile);
  }
  
  public String getDescription()
  {
    return description;
  }
  
  public PuzzleTile getElement(int index)
  {
    return tiles.get(index);
  }
  
  public int getRows()
  {
    return rows;
  }
  
  public int getCols()
  {
    return cols;
  }
  
  public void handleTile(PuzzleTile tile)
  {
    add(tile);
  }
  
  @Override
  public Iterator<PuzzleTile> iterator()
  {
    return tiles.iterator();
  }
  
  public void reset()
  {
    tiles.clear();
  }
  
  public int size()
  {
    return tiles.size();
  }

  /**
   * Constructs an empty Puzzle object from a String representation.
   * Does not include the individual PuzzleTiles.
   * @param line The String representation
   * @return     The Puzzle
   */
  public static Puzzle parsePuzzle(final String line)
  {
    // TODO Auto-generated method stub
    return null;
  }
}
