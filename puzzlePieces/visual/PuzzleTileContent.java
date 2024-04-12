package puzzlePieces.visual;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

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
  private double     left, top;
  private PuzzleTile tile;

  public PuzzleTileContent(TransformableContent content, PuzzleTile tile)
  {
    super(content);
    
    this.tile = tile;
    left = tile.getLeft();
    top = tile.getTop();
    setLocation(left, top);
  }

  /**
   * When the tile is clicked and dragged.
   */
  @Override
  public void mouseDragged(MouseEvent e)
  {
    left = (double)e.getX() - tile.getWidth()/2;
    top  = (double)e.getY() - tile.getHeight()/2;
  }

  /**
   * When the tile is hovered over. Should have a glow effect.
   */
  @Override
  public void mouseMoved(MouseEvent e)
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void handleTick(int arg0)
  {
    // TODO Auto-generated method stub

    setLocation(left, top);
  }

}
