package puzzlePieces.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

import puzzlePieces.PuzzleTile;
import puzzlePieces.TileObserver;
import puzzlePieces.TileSubject;

/**
 * An abstract class for reading Tile information from a BufferedReader.
 *
 *
 * @author Shane Malamut, James Madison University
 *         Based on TileReader by Prof. David Bernstein
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public abstract class TileReader implements TileSubject
{
  protected BufferedReader reader;
  protected List<TileObserver> observers;
  
  protected TileReader(BufferedReader reader)
  {

    this.reader = reader;

    // A CopyOnWriteArrayList is used so that
    // observers can be notified while the collection is being modified
    observers = new CopyOnWriteArrayList<TileObserver>();
  }
  
  /**
   * Add a TileObserver to the list of observers (required by TileSubject).
   *
   * @param observer
   *          The TileObserver to add
   */
  @Override
  public void addObserver(final TileObserver observer)
  {
    observers.add(observer);
  }

  /**
   * Notify all registered TileObserver objects of a change (required by TileSubject).
   *
   * @param PuzzleTile
   *          The new/changed Tile
   */
  @Override
  public void notifyObservers(final PuzzleTile tile)
  {
    Iterator<TileObserver> i = observers.iterator();
    while (i.hasNext())
    {
      TileObserver observer = i.next();
      observer.handleTile(tile);
    }
  }

  /**
   * Remove a TileObserver from the list of observers (required by TileSubject).
   *
   * @param observer
   *          The TileObserver to remove
   */
  @Override
  public void removeObserver(final TileObserver observer)
  {
    observers.remove(observer);
  }

  /**
   * Read a Tile.
   *
   * @return The next Tile object
   */
  protected abstract PuzzleTile readTile()
      throws IOException, NoSuchElementException, NumberFormatException;

  /**
   * Read one Tile object (and inform the observers).
   * 
   * Note: This method uses the readTile() method in the derived class.
   * 
   * @throws IOException
   *           if something goes wrong
   */
  public void readOne() throws IOException
  {
    PuzzleTile tile = readTile();
    if (tile != null)
      notifyObservers(tile);
  }

  /**
   * Read all (until the BufferedReader returns null) Tile objects (informing the observers).
   *
   * Note: This method uses the readTile() method in the derived class.
   *
   * @throws IOException
   *           if something goes wrong
   */
  public void readAll() throws IOException
  {
    PuzzleTile tile;

    while ((tile = readTile()) != null)
    {
      notifyObservers(tile);
    }
  }

}
