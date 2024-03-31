package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import io.ResourceFinder;
import resources.Marker;

public abstract class AbstractApplication extends JApplication implements ActionListener
{
  public static final int WIDTH  = 1024;
  public static final int HEIGHT = 768;

  protected static final String ABOUT = "About";
  protected static final String LOAD = "Load";
  
  private JButton aboutButton, loadButton;
  private JTextField fileField;
  private String aboutText;
  
  /**
   * Explicit value constructor.
   * 
   * @param args   The command line arguments
   */
  public AbstractApplication(final String[] args)
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
    PropertyReader in;
    try
    {
      BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
      if (fileName.endsWith("apartments")) in = new ApartmentReader(br);
      else in = new HouseReader(br);

      getPropertyObserver().reset();
      in.addObserver(getPropertyObserver());
      in.readAll();
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(getGUIComponent(), 
          "There was a problem reading " + fileName,
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  /**
   * Construct the GUI components to use to display the Property information.
   * 
   * @return The JComponent
   */
  protected abstract JComponent getGUIComponent();
  
  /**
   * Get the PropertyObserver to inform of changes.
   * 
   * @return The PropertyObserver
   */
  protected abstract PropertyObserver getPropertyObserver();
  
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

    JLabel label = new JLabel("File: ");
    label.setBounds(30, 30, 40, 30);
    contentPane.add(label);
    
    fileField = new JTextField();
    fileField.setBounds(80, 30, 200, 30);
    contentPane.add(fileField);
    
    loadButton = new JButton(LOAD);
    loadButton.setBounds(320, 30, 50, 30);
    loadButton.addActionListener(this);
    contentPane.add(loadButton);
    
    aboutButton = new JButton(ABOUT);
    aboutButton.setBounds(400, 30, 70, 30);
    aboutButton.addActionListener(this);
    contentPane.add(aboutButton);
    
    JComponent component = getGUIComponent();
    component.setBounds(0, 60, WIDTH, HEIGHT-60);
    contentPane.add(component);
  }
}
