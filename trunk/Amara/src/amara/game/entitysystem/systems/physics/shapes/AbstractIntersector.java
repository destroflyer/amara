/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

/**
 *
 * @author Philipp
 */
public abstract class AbstractIntersector<A, B> implements Intersector {
    private final Class<A> classA;
    private final Class<B> classB;

    public AbstractIntersector(Class<A> classA, Class<B> classB) {
        this.classA = classA;
        this.classB = classB;
    }
    
    protected abstract Vector2D resolve_(A a, B b);
    
    protected abstract boolean intersect_(A a, B b);
    
    @Override
    public boolean intersect(Object a, Object b) {
        return intersect_((A)a, (B)b);
    }
    
    @Override
    public Vector2D resolveVector(Object a, Object b) {
        return resolve_((A)a, (B)b);
    }

    @Override
    public Class getClassA() {
        return classA;
    }

    @Override
    public Class getClassB() {
        return classB;
    }
    
}
