package puzzlePieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Puzzle, containing a List of PuzzleTile objects.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class Puzzle implements Iterable<PuzzleTile>
{
  protected String description;
  protected List<PuzzleTile> tiles;
  protected int rows;
  protected int cols;
  
  /**
   * Constructor.
   * 
   * @param description The description of the Puzzle
   * @param rows The number of rows in the Puzzle
   * @param cols The number of columns in the Puzzle
   */
  public Puzzle(final String description, final int rows, final int cols)
  {
    this.description = description;
    this.rows = rows;
    this.cols = cols;
    
    //Rows and columns are used to instantiate the ArrayList with the appropriate initial capacity,
    //to avoid unnecessary resizing while the List is being populated.
    tiles = new ArrayList<PuzzleTile>(rows*cols);
  }
  
  /**
   * Add a tile to the Puzzle.
   * @param tile The tile
   */
  public void add(final PuzzleTile tile)
  {
    if (tile != null)
      tiles.add(tile);
  }
  
  /**
   * Get the description of the Puzzle.
   * @return The description
   */
  public String getDescription()
  {
    return description;
  }
  
  /**
   * Get a tile from the Puzzle at a specific index in the List.
   * @param index The index to get
   * @return The tile at that index
   */
  public PuzzleTile getElement(final int index)
  {
    return tiles.get(index);
  }
  
  /**
   * Get the number of rows in the Puzzle.
   * @return The number of rows
   */
  public int getRows()
  {
    return rows;
  }
  
  /**
   * Get the number of columns in the Puzzle.
   * @return The number of columns
   */
  public int getCols()
  {
    return cols;
  }
  
  /**
   * Add a tile to the Puzzle.
   * @param tile The tile
   */
  public void handleTile(final PuzzleTile tile)
  {
    add(tile);
  }
  
  @Override
  public Iterator<PuzzleTile> iterator()
  {
    return tiles.iterator();
  }
  
  /**
   * Reset the Puzzle.
   */
  public void reset()
  {
    tiles.clear();
  }
  
  /**
   * Get the number of tiles in the Puzzle.
   * @return The number of tiles
   */
  public int size()
  {
    return tiles.size();
  }
}
