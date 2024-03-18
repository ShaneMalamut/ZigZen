package pieces;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;

public class Composite implements Component
{
  private Collection<Component>  aggregate;
  
  public Composite()
  {
     aggregate = new HashSet<Component>();
  }
  
  public void add(final Component c)
  {
    aggregate.add(c);
  }
  
  public int connectEdges(final Composite temp)
  {
    int count = 0;
    
    for (Component c: aggregate)
    {
      count += c.connectEdges();
    }
    
    return count;
  }
  
  public void remove(final Component c)
  {
    aggregate.remove(c);
  }
  
  public void rotate(final Point origin, final int degrees)
  {
    for (Component c: aggregate)
    {
      c.rotate(origin, degrees);
    }
  }
  
  public void translate(final int x, final int y)
  {
    for (Component c: aggregate)
    {
      c.translate(x, y);
    }
  }
  
  public void translate(final Point start, final Point end)
  {
    int x = end.x - start.x;
    int y = end.y - start.y;
    
    for (Component c: aggregate)
    {
      c.translate(x, y);
    }
  }
}
