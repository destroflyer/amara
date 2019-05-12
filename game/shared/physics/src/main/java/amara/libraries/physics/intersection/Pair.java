/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.intersection;

/**
 *
 * @author Philipp
 */
public class Pair<T>
{
    private T a, b;

    public Pair(T a, T b)
    {
        this.a = a;
        this.b = b;
    }

    public T getA()
    {
        return a;
    }

    public T getB()
    {
        return b;
    }

    @Override
    public int hashCode()
    {
        return a.hashCode() ^ b.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass() == Pair.class) return equals((Pair)obj);
        return false;
    }
    public boolean equals(Pair<T> pair)
    {
        return ((pair.getA().equals(b) && pair.getB().equals(a)) || (pair.getA().equals(a) && pair.getB().equals(b)));
    }
}
