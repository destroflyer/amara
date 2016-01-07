/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

/**
 *
 * @author Philipp
 */
public class IntersectorMirrorWrapper<A, B> implements Intersector {
    private final AbstractIntersector<B, A> intersector;

    public IntersectorMirrorWrapper(AbstractIntersector<B, A> intersector) {
        this.intersector = intersector;
    }
    
    @Override
    public boolean intersect(Object a, Object b) {
        return intersector.intersect((B)b, (A)a);
    }
    
    @Override
    public Vector2D resolveVector(Object a, Object b) {
        return intersector.resolveVector((B)b, (A)a).inverse();
    }

    @Override
    public Class getClassA() {
        return intersector.getClassB();
    }

    @Override
    public Class getClassB() {
        return intersector.getClassA();
    }
    
}
