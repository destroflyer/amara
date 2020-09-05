/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class IntersectorsImpl implements Intersectors {
    private final ArrayList<Intersector> intersectors = new ArrayList<Intersector>();
    
    public void add(Intersector i) {
        intersectors.add(i);
    }
    
    public boolean intersect(Object a, Object b) {
        Intersector i = findIntersector(a.getClass(), b.getClass());
        return i.intersect(a, b);
    }
    
    public Vector2D resolveVector(Object a, Object b) {
        Intersector i = findIntersector(a.getClass(), b.getClass());
        return i.resolveVector(a, b);
    }
    
    protected Intersector findIntersector(Class classA, Class classB) {
        for (Intersector intersector : intersectors) {
            if(intersector.getClassA().isAssignableFrom(classA) && intersector.getClassB().isAssignableFrom(classB)) {
                return intersector;
            }
        }
        throw new RuntimeException("no valid intersector found: " + classA + " - " + classB);
    }
}
