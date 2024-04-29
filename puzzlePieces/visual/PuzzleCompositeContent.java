package puzzlePieces.visual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A composite of PuzzleTileContent objects.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 * 
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleCompositeContent implements Iterable<PuzzleTileContent>
{
  private List<PuzzleTileContent> tiles;
  
  /**
   * Constructor.
   * @param root The first tile of the composite
   */
  public PuzzleCompositeContent(final PuzzleTileContent root)
  {
    tiles = new ArrayList<PuzzleTileContent>();
    tiles.add(root);
  }
  
  /**
   * Copy Constructor.
   * @param original The composite to copy from
   */
  public PuzzleCompositeContent(final PuzzleCompositeContent original)
  {
    tiles = new ArrayList<PuzzleTileContent>();
    tiles.addAll(original.getTiles());
  }
  
  /**
   * Add a tile to the composite.
   * @param tile The tile to add
   */
  public void add(final PuzzleTileContent tile)
  {
    tile.setComposite(this);
    tiles.add(tile);
    alignConnections();
  }
  
  /**
   * Add all the tiles from another composite.
   * @param other The other composite
   */
  public void addAll(final PuzzleCompositeContent other)
  {
    // Tell each tile that this is now their composite
    for (PuzzleTileContent tile : other.getTiles())
    {
      tile.setComposite(this);
    }
    
    // Add the tiles to this composite's list
    tiles.addAll(other.getTiles());
    alignConnections();
  }
  
  /**
   * Align the connections of the composite using the original tile as the root.
   */
  private void alignConnections()
  {
    alignConnections(tiles.get(0));
  }
  
  /**
   * Recursively align the connections of the composite, starting at a given root.
   * @param root
   */
  public void alignConnections(final PuzzleTileContent root)
  {
    // Check that the given tile is actually part of this composite
    if (root.getComposite() != this)
      return;
    
    // Notify each tile that a recursive alignment process is beginning
    for (PuzzleTileContent tile : tiles)
    {
      tile.observeAlignment();
    }
    
    // Begin the process at the given root
    root.alignConnections();
  }
  
  protected List<PuzzleTileContent> getTiles()
  {
    return tiles;
  }

  @Override
  public Iterator<PuzzleTileContent> iterator()
  {
    return tiles.iterator();
  }
}
