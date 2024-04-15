package puzzlePieces.visual;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import gui.PuzzleCursor;
import puzzlePieces.PuzzleTile;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.TransformableContent;

/**
 * Presents the information in a PuzzleTile object.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleTileContent extends    RuleBasedSprite 
                               implements MouseMotionListener
{
  private double       left, top;
  private PuzzleTile   tile;
  private boolean      hover;
  private boolean      held;
  private PuzzleCursor cursor;

  public PuzzleTileContent(TransformableContent content, PuzzleTile tile)
  {
    super(content);
    
    this.tile = tile;
    left = tile.getLeft();
    top = tile.getTop();
    setLocation(left, top);
    
    hover = false;
    held  = false;
  }

  public void setCursor(PuzzleCursor cursor)
  {
    this.cursor = cursor;
  }
  /**
   * When the tile is clicked and dragged.
   */
  @Override
  public void mouseDragged(MouseEvent e)
  {
    if (hover)
    {
      cursor.grab(this);
    }
    
    if (held)
    {
      left = (double)e.getX() - tile.getWidth()/2;
      top  = (double)e.getY() - tile.getHeight()/2;
    }
  }

  /**
   * When the tile is hovered over. Should have a glow effect.
   */
  @Override
  public void mouseMoved(MouseEvent e)
  {
    hover = (e.getX() > left && e.getX() < left + tile.getWidth() && 
             e.getY() > top  && e.getY() < top + tile.getHeight());
    
    if (hover && held)
    {
      cursor.release();
    }
  }
  
  public void setPosition(double left, double top)
  {
    this.left = left;
    this.top  = top;
  }
  
  public void grab()
  {
    held = true;
  }
  
  public void release()
  {
    held = false;
  }

  @Override
  public void handleTick(int arg0)
  {
    // TODO Auto-generated method stub

    setLocation(left, top);
  }

}
