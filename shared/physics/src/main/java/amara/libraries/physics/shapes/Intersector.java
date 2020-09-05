/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

/**
 *
 * @author Philipp
 */
public interface Intersector<A, B> {
    boolean intersect(A a, B b);
    Vector2D resolveVector(A a, B b);
    Class<A> getClassA();
    Class<B> getClassB();
}
