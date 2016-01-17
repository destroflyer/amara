/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class RegularCyclic extends SimpleConvexPolygon
{

    public RegularCyclic()
    {
    }
    
    public RegularCyclic(Vector2D position, int edges, double radius, double offsetRadians)
    {
        super(calcBase(position, edges, radius, offsetRadians));
    }
    public RegularCyclic(Vector2D position, int edges, double radius)
    {
        super(calcBase(position, edges, radius, 0));
    }
    public RegularCyclic(int edges, double radius) {
        super(calcBase(Vector2D.Zero, edges, radius, 0));
    }
    
    private static Vector2D[] calcBase(Vector2D position, int edges, double radius, double offsetRadians) {
        Vector2D rot = new Vector2D(Math.sin(offsetRadians) * radius, Math.cos(offsetRadians) * radius);
        Vector2D[] base = new Vector2D[edges];
        Transform2D t = new Transform2D(1, Math.PI * 2 / edges, 0, 0);
        for(int i = 0; i < edges; i++)
        {
            rot = t.transform(rot);
            base[i] = rot.add(position);
        }
        return base;
    }
    
}
