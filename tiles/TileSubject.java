package tiles;

public interface TileSubject
{
  public void addObserver(TileObserver observer);
  public void notifyObservers(Tile tile);
  public void removeObserver(TileObserver observer);
}
