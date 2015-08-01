/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

/**
 *
 * @author Philipp
 */
public abstract class AbstractIntersector<A, B> implements Intersector<A, B> {
    private final Class<A> classA;
    private final Class<B> classB;

    public AbstractIntersector(Class<A> classA, Class<B> classB) {
        this.classA = classA;
        this.classB = classB;
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
