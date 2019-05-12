/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import java.util.*;
import amara.libraries.physics.shapes.Vector2D;

/**
 *
 * @author Philipp
 */
public class PolygonBuilder
{
    ArrayList<SimplePolygon> outlines = new ArrayList<SimplePolygon>();
    ArrayList<Boolean> holes = new ArrayList<Boolean>();
    
    public Polygon build(boolean infinite)
    {
        for (int i = 0; i < outlines.size(); i++)
        {
            assert !outlines.get(i).hasRepetitions();
            if(outlines.get(i).isHole() != holes.get(i)) outlines.get(i).invert();
        }
        return new Polygon(SetPolygonUtil.simpleSetToSetPolygon(outlines, infinite));
    }
    
    public void nextOutline(boolean hole)
    {
        outlines.add(new SimplePolygon());
        holes.add(hole);
    }
    
    public void add(double x, double y)
    {
        outlines.get(outlines.size() - 1).add(x, y);
    }
    public void add(Vector2D p)
    {
        outlines.get(outlines.size() - 1).add(p);
    }
    
    public void reset()
    {
        outlines.clear();
        holes.clear();
    }
}
