package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.PuzzleBoard;
import io.ResourceFinder;
import puzzlePieces.Puzzle;
import puzzlePieces.io.PuzzleFactory;
import puzzlePieces.io.PuzzleReader;
import resources.Marker;
import visual.VisualizationView;
import visual.statik.sampled.ImageFactory;

public class ZigZenApplication extends JApplication implements ActionListener
{
  public static final int WIDTH  = 1024;
  public static final int HEIGHT = 768;

  protected static final String ABOUT = "About";
  protected static final String LOAD = "Load";
  protected static final String ROWS = "Rows";

  private static final Color BACKGROUND_COLOR = new Color(218, 204, 230);
  
  private JButton aboutButton, loadButton;
  private JTextField fileField;
  private JTextField rowsField;
  private JTextField colsField;
  private PuzzleBoard puzzleBoard;
  private String aboutText;
  
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
        String.format(aboutText, aboutText), ABOUT, JOptionPane.INFORMATION_MESSAGE);
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
    int rowNum = 4;
    int colNum = 0;
    
    if (rows.length() > 0)
    {
      try
      {
        rowNum = Integer.parseInt(rows);
      }
      catch (NumberFormatException nfe)
      {
        rowNum = 4;
      }
    }
    
    if (cols.length() > 0)
    {
      try
      {
        colNum = Integer.parseInt(cols);
      }
      catch (NumberFormatException nfe)
      {
        colNum = 0;
      }
    }
    
    if (fileName.length() == 0)
    {
      PuzzleFactory pf = new PuzzleFactory();
      System.out.println("Reading example puzzle");
      if (colNum > 0)
        puzzle = pf.createPuzzle("StarryNight.jpg", "Starry Night", rowNum, colNum, new Dimension((int) (width * 0.75), (int) (height * 0.75)));
      else
        puzzle = pf.createPuzzle("StarryNight.jpg", "Starry Night", rowNum, new Dimension((int) (width * 0.75), (int) (height * 0.75)));
    }
    else
    {
      try
      {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        if (isImageFile(fileName))
        {
          PuzzleFactory pf = new PuzzleFactory();
          
          if (colNum > 0)
            puzzle = pf.createPuzzle(fileName, "Untitled Puzzle", rowNum, colNum);
          else
            puzzle = pf.createPuzzle(fileName, "Untitled Puzzle", rowNum);
        }
        else
        {
          puzzle = PuzzleReader.read(br);
        }
      }
      catch (IOException ioe)
      {
        JOptionPane.showMessageDialog(getGUIComponent(), 
            "There was a problem reading " + fileName,
            "Error", JOptionPane.ERROR_MESSAGE);
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
