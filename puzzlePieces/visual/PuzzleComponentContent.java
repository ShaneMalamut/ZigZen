package puzzlePieces.visual;

public interface PuzzleComponentContent
{
  public void alignConnections();
  public void observeAlignment();
  public void setComposite(PuzzleCompositeContent puzzleCompositeContent);
  public PuzzleCompositeContent getComposite();
}
