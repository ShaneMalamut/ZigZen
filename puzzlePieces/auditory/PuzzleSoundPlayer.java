package puzzlePieces.auditory;

import java.io.IOException;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import auditory.sampled.BoomBox;
import auditory.sampled.BufferedSound;
import auditory.sampled.BufferedSoundFactory;
import io.ResourceFinder;

/**
 * Plays audio related to Puzzle pieces.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 * 
 *          This work complies with the JMU Honor Code.
 */
public class PuzzleSoundPlayer implements LineListener
{
  private BoomBox boomboxSingle;
  private BoomBox boomboxDouble;
  private BoomBox boomboxMulti;
  
  /**
   * Constructor.
   */
  public PuzzleSoundPlayer()
  {
    BufferedSound thumpSound = createSound("thump.wav");
    boomboxSingle = new BoomBox(thumpSound);       
    boomboxSingle.addLineListener(this);
    
    BufferedSound snapSound = createSound("snap1.wav");
    boomboxDouble = new BoomBox(snapSound);       
    boomboxDouble.addLineListener(this);
    
    BufferedSound loudSnapSound = createSound("snap2.wav");
    boomboxMulti = new BoomBox(loudSnapSound);       
    boomboxMulti.addLineListener(this);
  }
  
  protected BufferedSound createSound(final String name)
  {
    BufferedSound        sound;       
    BufferedSoundFactory factory;       
    ResourceFinder       finder;       

    finder  = ResourceFinder.createInstance(resources.Marker.class);
    factory = new BufferedSoundFactory(finder);

    try
    {
      sound = factory.createBufferedSound(name);
    }
    catch (UnsupportedAudioFileException e)
    {
      sound = null;
    }
    catch (IOException e)
    {
      sound = null;
    }

    return sound;
  }
  
  /**
   * Play single-connection sound effect.
   */
  public void playConnectSound()
  {
    try
    {
      boomboxSingle.start(false);
    }
    catch (LineUnavailableException e)
    {
      System.out.println("Couldn't play connect sound");
    }
  }
  
  /**
   * Play double-connection sound effect.
   */
  public void playDoubleConnectSound()
  {
    try
    {
      boomboxDouble.start(false);
    }
    catch (LineUnavailableException e)
    {
      System.out.println("Couldn't play double connect sound");
    }
  }
  
  /**
   * Play multi-connection sound effect.
   */
  public void playMultiConnectSound()
  {
    try
    {
      boomboxMulti.start(false);
    }
    catch (LineUnavailableException e)
    {
      System.out.println("Couldn't play multi-connect sound");
    }
  }

  /**
   * Handle update events (Required by LineListener).
   */
  @Override
  public void update(final LineEvent event)
  {
    
  }
}
