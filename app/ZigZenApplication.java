package app;

import javax.swing.JComponent;

import gui.PuzzleBoard;

public class ZigZenApplication extends AbstractApplication
{
  private PuzzleBoard puzzleBoard;
  
  /**
   * Constructor.
   * @param args The command line arguments
   */
  public ZigZenApplication(final String[] args)
  {
    super(args);
    
    puzzleBoard = new PuzzleBoard(WIDTH, HEIGHT-60);
  }
  
  @Override
  protected JComponent getGUIComponent()
  {
    return puzzleBoard.getView();
  }
  
  /**
   * Construct and invoke  (in the event dispatch thread) 
   * an instance of this JApplication.
   * 
   * @param args The command line arguments
   */
  public static void main(final String[] args)
  {
    JApplication app = new ZigZenApplication(args);
    invokeInEventDispatchThread(app);
  }

}
