/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

/**
 *
 * @author Philipp
 */
public interface Intersectors {
    boolean intersect(Object a, Object b);
    Vector2D resolveVector(Object a, Object b);
    void add(Intersector i);
}
