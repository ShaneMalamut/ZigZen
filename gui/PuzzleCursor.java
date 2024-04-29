package gui;

import java.awt.event.MouseEvent;

import puzzlePieces.visual.PuzzleCompositeContent;
import puzzlePieces.visual.PuzzleTileContent;

/**
 * A representation of the user's cursor, able to "grab" and "release" puzzle tiles.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleCursor
{
  private PuzzleTileContent held;
  private PuzzleBoard       observer;
  
  /**
   * Constructor.
   * @param observer The PuzzleBoard to observe
   */
  public PuzzleCursor(final PuzzleBoard observer)
  {
    held = null;
    this.observer = observer;
  }
  
  /**
   * Grab the given tile, if one is not already being held.
   * 
   * If multiple cursors are under the cursor when the user clicks,
   * the tile that is actually grabbed is arbitrarily decided, based
   * on which method invocation is performed first. As it turns out,
   * this is usually whichever tile is the oldest, per Java implementation.
   * @param t The PuzzleTileContent to grab
   * @param e The mouse event to send to the grabbed tile
   */
  public void grab(final PuzzleTileContent t, final MouseEvent e)
  {
    if (held != null)
      return;

    System.out.println("Grabbing");
    
    held = t;
    
    PuzzleCompositeContent composite = held.getComposite();
    if (composite != null)
    {
      // If the grabbed tile is part of a composite, then "grab" all of the tiles in the composite
      for (PuzzleTileContent tile : composite)
      {
        tile.setRelativePosition(e.getX(), e.getY());
        tile.grab();
        observer.toBack(tile);
      }
    }
    else
    {
      // Otherwise, just grab the tile
      held.setRelativePosition(e.getX(), e.getY());
      held.grab();
      observer.toBack(held);
    }
  }
  
  /**
   * "Release" the current held tile, if there is one.
   */
  public void release()
  {
    if (held == null)
      return;
    
    System.out.println("Releasing");
    
    PuzzleCompositeContent composite = held.getComposite();
    if (composite != null)
    {
      // If the tile is part of a composite, then "release" all of the tiles in the composite
      // (Iterate through a copy to avoid a ConcurrentModificationException)
      PuzzleCompositeContent copy = new PuzzleCompositeContent(composite);
      for (PuzzleTileContent tile : copy)
      {
        tile.release();
      }
    }
    else
    {
      // Otherwise, just release the tile
      held.release();
    }
    
    held = null;
  }
  
  /**
   * Get the held tile.
   * @return The held tile, or null if there is none
   */
  public PuzzleTileContent getHeld()
  {
    return held;
  }
}
