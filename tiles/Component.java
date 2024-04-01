package tiles;

import java.awt.Point;

public interface Component
{
  public void rotate(final Point origin, final int degrees);
  public void translate(final int x, final int y);
  public void translate(final Point start, final Point end);
  //public int connectEdges(final Composite temp);
}
