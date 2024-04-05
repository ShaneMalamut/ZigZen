package puzzlePieces;

public interface TileSubject
{
  public void addObserver(TileObserver observer);
  public void notifyObservers(PuzzleTile tile);
  public void removeObserver(TileObserver observer);
}
