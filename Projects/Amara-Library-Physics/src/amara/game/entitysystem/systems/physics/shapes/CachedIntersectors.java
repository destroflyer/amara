/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import java.util.HashMap;

/**
 *
 * @author Philipp
 */
public class CachedIntersectors implements Intersectors {
    private final IntersectorsImpl intersectors;
    private final HashMap<ClassPair, Intersector> map = new HashMap<ClassPair, Intersector>();

    public CachedIntersectors(IntersectorsImpl intersectors) {
        this.intersectors = intersectors;
    }

    public boolean intersect(Object a, Object b) {
        return getIntersector(a, b).intersect(a, b);
    }

    public Vector2D resolveVector(Object a, Object b) {
        return getIntersector(a, b).resolveVector(a, b);
    }
    
    private Intersector getIntersector(Object a, Object b) {
        ClassPair pair = new ClassPair(a.getClass(), b.getClass());
        Intersector i = map.get(pair);
        if(i == null) {
            i = intersectors.findIntersector(a.getClass(), b.getClass());
            map.put(pair, i);
        }
        return i;
    }

    public void add(Intersector i) {
        intersectors.add(i);
    }
    
    
    
    protected class ClassPair {
        public final Class classA, classB;

        public ClassPair(Class classA, Class classB) {
            this.classA = classA;
            this.classB = classB;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 47 * hash + this.classA.hashCode();
            hash = 47 * hash + this.classB.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            return equals((ClassPair) obj);
        }
        public boolean equals(ClassPair other) {
            return this.classA == other.classA && this.classB == other.classB;
        }
    }
}
