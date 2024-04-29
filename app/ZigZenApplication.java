package app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.PuzzleBoard;
import io.ResourceFinder;
import puzzlePieces.Puzzle;
import puzzlePieces.PuzzleFactory;
import resources.Marker;

/**
 * An application that simulates a jigsaw puzzle.
 * 
 * @author Shane Malamut, James Madison University
 * @version 1.0
 *
 *          This work complies with the JMU Honor Code.
 */
public class ZigZenApplication extends JApplication implements ActionListener
{
  public static final int WIDTH  = 1024;
  public static final int HEIGHT = 768;
  
  protected static final int    DEFAULT_ROWS         = 4;
  protected static final int    DEFAULT_COLS         = 0;
  protected static final String DEFAULT_PUZZLE       = "StarryNight.jpg";
  protected static final String DEFAULT_PUZZLE_DESC  = "Starry Night";
  protected static final String UNTITLED_PUZZLE_DESC = "Untitled Puzzle";
  
  protected static final String ABOUT   = "About";
  protected static final String ERROR   = "Error";
  protected static final String LOAD    = "Load";
  protected static final String ROWS    = "Rows";
  protected static final String WARNING = "Warning";
  
  protected static final String WARNING_TEXT = 
      "Invalid input for %s field. The default value will be used instead.";
  
  protected static final Color BACKGROUND_COLOR = new Color(218, 204, 230);
  
  private JButton     aboutButton, loadButton;
  private JTextField  fileField;
  private JTextField  rowsField;
  private JTextField  colsField;
  private PuzzleBoard puzzleBoard;
  private String      aboutText;
  
  /**
   * Constructor.
   * @param args The command line arguments
   */
  public ZigZenApplication(final String[] args)
  {
    super(args, WIDTH, HEIGHT);
    
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
    InputStream    is = rf.findInputStream("about.txt");
    BufferedReader in = new BufferedReader(new InputStreamReader(is));
    
    String line;
    aboutText = "";
    try
    {
      while ((line = in.readLine()) != null)
      {
        aboutText += line + "\n";
      }
    }
    catch (IOException ioe)
    {
      aboutText = "ZigZen";
    }
    
    puzzleBoard = new PuzzleBoard(WIDTH, HEIGHT-60);
  }
  
  /**
   * Handle actionPerformed messages (required by ActionListener).
   * In particular, get the input, perform the requested conversion,
   * and display the result.
   * 
   * @param evt  The ActionEvent that generated the actionPerformed message
   */
  @Override
  public void actionPerformed(final ActionEvent evt)
  {
    String ac = evt.getActionCommand();
    
    if (ac.equalsIgnoreCase(LOAD)) handleLoad();
    else if (ac.equalsIgnoreCase(ABOUT)) handleAbout();
  }
  
  /**
   * Handle the ABOUT button.
   */
  protected void handleAbout()
  {
    JOptionPane.showMessageDialog(getGUIComponent(), 
        aboutText, ABOUT, JOptionPane.INFORMATION_MESSAGE);
  }
  
  /**
   * Handle the LOAD button.
   */
  protected void handleLoad()
  {
    String fileName = fileField.getText();
    String rows     = rowsField.getText();
    String cols     = colsField.getText();
    Puzzle puzzle = null;
    int rowNum = DEFAULT_ROWS;
    int colNum = DEFAULT_COLS;
    
    // Attempt to parse the rows field as an int
    if (rows.length() > 0)
    {
      try
      {
        rowNum = Integer.parseInt(rows);
      }
      catch (NumberFormatException nfe)
      {
        JOptionPane.showMessageDialog(getGUIComponent(), 
            String.format(WARNING_TEXT, "rows"), WARNING, JOptionPane.WARNING_MESSAGE);
      }
    }
    
    // Attempt to parse the columns field as an int
    if (cols.length() > 0)
    {
      try
      {
        colNum = Integer.parseInt(cols);
      }
      catch (NumberFormatException nfe)
      {
        JOptionPane.showMessageDialog(getGUIComponent(), 
            String.format(WARNING_TEXT, "columns"), WARNING, JOptionPane.WARNING_MESSAGE);
      }
    }
    
    if (fileName.length() == 0)
    {
      // Read default puzzle 
      PuzzleFactory pf = new PuzzleFactory();
      BufferedImage image = null;
      ResourceFinder rf = ResourceFinder.createInstance(new Marker());
      InputStream   is = rf.findInputStream(DEFAULT_PUZZLE);
      
      // Attempt to get the image
      if (is != null)
      {
        try
        {
          image = ImageIO.read(is);
          is.close();
        }
        catch (IOException ioe)
        {
          return;
        }
      }
      
      if (colNum > 0)
        puzzle = pf.createPuzzle(image, DEFAULT_PUZZLE_DESC, rowNum, colNum);
      else
        puzzle = pf.createPuzzle(image, DEFAULT_PUZZLE_DESC, rowNum);
    }
    else
    {
      if (isImageFile(fileName))
      {
        try
        {
          PuzzleFactory pf = new PuzzleFactory();
          
          // In the future, the user should be able to enter a description for their puzzle,
          // which would be used in place of UNTITLED_PUZZLE_DESC.
          // The description would act as a label to identify the puzzle with, and would be
          // the default file name when saving the puzzle (once saving and loading is implemented).
          if (colNum > 0)
            puzzle = pf.createPuzzle(fileName, UNTITLED_PUZZLE_DESC, rowNum, colNum);
          else
            puzzle = pf.createPuzzle(fileName, UNTITLED_PUZZLE_DESC, rowNum);
        }
        catch (IOException ioe)
        {
          JOptionPane.showMessageDialog(getGUIComponent(), 
              "There was a problem reading " + fileName,
              ERROR, JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
      else
      {
        JOptionPane.showMessageDialog(getGUIComponent(), 
            "Missing or invalid file extension: " + fileName,
            ERROR, JOptionPane.ERROR_MESSAGE);
        return;
      }
    }
    
    puzzleBoard.setPuzzle(puzzle);
  }
  
  /**
   * Construct the GUI components to use to display the Property information.
   * 
   * @return The JComponent
   */
  protected JComponent getGUIComponent()
  {
    return puzzleBoard.getView();
  }
  
  /**
   * Initialize this JApplication (required by JApplication).
   * Specifically, construct and layout the JFrame.
   */
  @Override
  public void init()
  {
    // Setup the content pane
    JPanel contentPane = (JPanel)getContentPane();
    contentPane.setLayout(null);
    contentPane.setBackground(BACKGROUND_COLOR);

    JLabel label = new JLabel("File: ");
    label.setBounds(30, 30, 40, 30);
    contentPane.add(label);
    
    fileField = new JTextField();
    fileField.setBounds(80, 30, 200, 30);
    contentPane.add(fileField);

    label = new JLabel("Rows: ");
    label.setBounds(320, 30, 50, 30);
    contentPane.add(label);
    
    rowsField = new JTextField();
    rowsField.setBounds(380, 30, 50, 30);
    contentPane.add(rowsField);
    
    label = new JLabel("Columns: ");
    label.setBounds(440, 30, 80, 30);
    contentPane.add(label);
    
    colsField = new JTextField();
    colsField.setBounds(530, 30, 50, 30);
    contentPane.add(colsField);
    
    loadButton = new JButton(LOAD);
    loadButton.setBounds(600, 30, 70, 30);
    loadButton.addActionListener(this);
    contentPane.add(loadButton);
    
    aboutButton = new JButton(ABOUT);
    aboutButton.setBounds(700, 30, 80, 30);
    aboutButton.addActionListener(this);
    contentPane.add(aboutButton);
    
    JComponent component = getGUIComponent();
    component.setBounds(0, 60, WIDTH, HEIGHT-60);
    contentPane.add(component);
  }
  
  private boolean isImageFile(final String fileName)
  {
    String[] imageExtensions = {".jpeg", ".jpg", ".png", ".bmp", ".webmp", ".gif"};
    
    for (int i = 0; i < imageExtensions.length; i++)
    {
      if (fileName.toLowerCase().endsWith(imageExtensions[i])) return true;
    }
    
    return false;
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
