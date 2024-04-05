package puzzlePieces;

public interface TileObserver
{
  public void handleTile(PuzzleTile tile);
  public void reset();
}
