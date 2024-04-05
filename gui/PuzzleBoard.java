package gui;

import java.awt.Color;
import java.util.ArrayList;

import io.ResourceFinder;
import map.io.BaseMapReader;
import map.io.CentroidReader;
import realestate.PropertyObserver;
import realestate.visual.PropertyContent;
import realestate.visual.PropertyContentFactory;
import realestate.visual.StyleIconReader;
import resources.Marker;
import visual.VisualizationView;

public class PuzzleBoard extends visual.Visualization //implements PropertyObserver
{
  /**
   * Constructor.
   * @param width The width of the GUI
   * @param height The height of the GUI
   */
  public PuzzleBoard(final int width, final int height)
  {
    super();
    
    CentroidReader  cr;
    StyleIconReader sir;

    jarFinder        = ResourceFinder.createInstance(new Marker());
    propertyContents = new ArrayList<PropertyContent>();
    currentColor     = Color.BLUE;
    
    cr  = new CentroidReader(jarFinder);
    sir = new StyleIconReader(jarFinder);
    propertyContentFactory = new PropertyContentFactory(cr.read("zipcodes.loc"), sir.read());
    
    // Set the appearance of the GUI
    VisualizationView view = getView();
    view.setBounds(0, 0, width, height);
    view.setSize(width, height);
    view.setBackground(BACKGROUND_COLOR);
    
    // Prepare the polygon maps
    BaseMapReader mapReader = new BaseMapReader(jarFinder);
    augusta    = mapReader.read("augusta.map", Color.BLACK, Color.WHITE);
    rockingham = mapReader.read("rockingham.map", Color.BLACK, Color.WHITE);

    // Add the polygons to the GUI
    add(augusta);
    add(rockingham);
  }
}
