package gui;

import puzzlePieces.PuzzleTile;
import puzzlePieces.visual.PuzzleTileContent;

public class PuzzleCursor
{
  private static boolean exists;
  private static PuzzleCursor instance;
  
  private PuzzleTileContent held;
  private PuzzleBoard       observer;
  
  private PuzzleCursor()
  {
    exists = true;
    held   = null;
  }
  
  public static PuzzleCursor createInstance()
  {
    //if (!exists)
    instance = new PuzzleCursor();
    
    return instance;
  }
  
  public void grab(PuzzleTileContent t)
  {
    if (held == null)
    {
      held = t;
      held.grab();
      observer.toBack(held);
    }
  }
  
  public void release()
  {
    if (held != null)
    {
      held.release();
      held = null;
    }
  }
  
  public PuzzleTileContent getHeld()
  {
    return held;
  }
  
  public void setObserver(PuzzleBoard observer)
  {
    this.observer = observer;
  }
}
