/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.Serializer;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class Intersectors {
    public static final Intersectors SINGLETON;
    private final ArrayList<Intersector> intersectors = new ArrayList<Intersector>();
    
    static {
        SINGLETON = new Intersectors();
        new IntersectorsPopulator().populate(SINGLETON);
    }
    
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
    
    private Intersector findIntersector(Class classA, Class classB) {
        for (Intersector intersector : intersectors) {
            if(intersector.getClassA().isAssignableFrom(classA) && intersector.getClassB().isAssignableFrom(classB)) {
                return intersector;
            }
        }
        throw new RuntimeException("no valid intersector found: " + classA + " - " + classB);
    }
}
