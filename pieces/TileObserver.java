package pieces;

public interface TileObserver
{
  public void handleTile(Tile tile);
  public void reset();
}
