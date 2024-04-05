package puzzlePieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Puzzle implements Iterable<PuzzleTile>, TileObserver
{
  protected String description;
  protected List<PuzzleTile> tiles;
  
  public Puzzle(String description)
  {
    this.description = description;
    tiles = new ArrayList<PuzzleTile>();
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
  
  @Override
  public void handleTile(PuzzleTile tile)
  {
    add(tile);
  }
  
  @Override
  public Iterator<PuzzleTile> iterator()
  {
    return tiles.iterator();
  }
  
  @Override
  public void reset()
  {
    tiles.clear();
  }
  
  public int size()
  {
    return tiles.size();
  }
}
