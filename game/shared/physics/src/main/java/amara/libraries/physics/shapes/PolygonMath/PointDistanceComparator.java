/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import amara.libraries.physics.shapes.Vector2D;
import java.util.Comparator;

/**
 *
 * @author Philipp
 */
class PointDistanceComparator implements Comparator<Vector2D>
{
    private Vector2D p;

    public PointDistanceComparator(Vector2D p) {
        this.p = p;
    }
    
    
    public int compare(Vector2D o1, Vector2D o2) {
        return (int)Math.signum(p.squaredDistance(o1) - p.squaredDistance(o2));
    }
    
}
