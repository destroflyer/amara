/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class RegularCyclic extends SimpleConvex {

    public RegularCyclic() {
        
    }
    
    public RegularCyclic(double centerX, double centerY, int edges, double radius) {
        this(edges, radius);
        transform.translate(centerX, centerY);
    }
    public RegularCyclic(int edges, double radius) {
        super(calcBase(edges, radius));
    }
    
    private static Vector2D[] calcBase(int edges, double radius) {
        Vector2D rot = new Vector2D(0, radius);
        Vector2D[] base = new Vector2D[edges];
        for(int i = 0; i < edges; i++) {
            base[i] = rot.clone();
            rot.rotateByDegree(360 / (double)edges);
        }
        return base;
    }
    
}
