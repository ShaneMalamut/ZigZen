package puzzlePieces.visual;

import java.util.ArrayList;
import java.util.List;

public class PuzzleCompositeContent implements PuzzleComponentContent
{
  List<PuzzleComponentContent> components;
  
  public PuzzleCompositeContent(PuzzleComponentContent root)
  {
    components = new ArrayList<PuzzleComponentContent>();
    components.add(root);
  }
  
  public void add(PuzzleComponentContent component)
  {
    component.setComposite(this);
    components.add(component);
    alignConnections();
  }
  
  public void addAll(PuzzleCompositeContent other)
  {
    for (PuzzleComponentContent component : other.getComponents())
    {
      component.setComposite(this);
    }
    
    components.addAll(other.getComponents());
    alignConnections();
  }
  
  @Override
  public void alignConnections()
  {
    PuzzleComponentContent root = components.get(0);
    alignConnections(root);
  }
  
  public void alignConnections(PuzzleComponentContent root)
  {
    if (root.getComposite() != this)
    {
      return;
    }
    
    for (PuzzleComponentContent component : components)
    {
      component.observeAlignment();
    }
    
    root.alignConnections();
  }
  
  protected List<PuzzleComponentContent> getComponents()
  {
    return components;
  }

  @Override
  public void observeAlignment()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setComposite(PuzzleCompositeContent puzzleCompositeContent)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public PuzzleCompositeContent getComposite()
  {
    // TODO Auto-generated method stub
    return null;
  }
}
