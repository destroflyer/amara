/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersection;

/**
 *
 * @author Philipp
 */
public class Pair<T> {

    public Pair(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pair) return ((((Pair)obj).getA() == b && ((Pair)obj).getB() == a) || (((Pair)obj).getA() == a && ((Pair)obj).getB() == b));
        return false;
    }
    
    T a, b;
}
