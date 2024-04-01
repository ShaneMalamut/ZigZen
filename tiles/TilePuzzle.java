package tiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TilePuzzle implements Iterable<Tile>, TileObserver
{
  protected String description;
  protected List<Tile> tiles;
  
  public TilePuzzle(String description)
  {
    this.description = description;
    tiles = new ArrayList<Tile>();
  }
  
  public void add(Tile tile)
  {
    if (tile != null) tiles.add(tile);
  }
  
  public String getDescription()
  {
    return description;
  }
  
  public Tile getElement(int index)
  {
    return tiles.get(index);
  }
  
  @Override
  public void handleTile(Tile tile)
  {
    add(tile);
  }
  
  @Override
  public Iterator<Tile> iterator()
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
