/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import java.util.Comparator;

/**
 *
 * @author Philipp
 */
class PointDistanceComparator implements Comparator<Point2D>
{
    private Point2D p;

    public PointDistanceComparator(Point2D p) {
        this.p = p;
    }
    
    
    public int compare(Point2D o1, Point2D o2) {
        return (int)Math.signum(p.squaredDistance(o1) - p.squaredDistance(o2));
    }
    
}
