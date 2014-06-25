/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import java.util.*;

/**
 *
 * @author Philipp
 */
class Util
{
    public static final double Epsilon = 1e-6d;
    public static boolean withinEpsilon(double d)
    {
        return Math.abs(d) < Epsilon;
    }
    public static boolean aboveEpsilon(double d)
    {
        return Epsilon <= d;
    }
    public static boolean belowEpsilon(double d)
    {
        return d <= -Epsilon;
    }
    
    public static <T> void reverse(ArrayList<T> list)
    {
        for (int i = 0; i < list.size() / 2; i++)
        {
            swap(list, i, list.size() - 1 - i);
        }
    }
    public static <T> void swap(ArrayList<T> list, int i, int j)
    {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }
    
    public static <T> void keySort(ArrayList<T> list, ArrayList<Double> keys)
    {
        assert keys.size() == list.size();
        boolean changed = true;
        while(changed)
        {
            changed = false;
            for (int i = 0; i + 1 < list.size(); i++)
            {
                int j = i + 1;
                if(keys.get(i) > keys.get(j))
                {
                    changed = true;
                    swap(list, i, j);
                    swap(keys, i, j);
                }
            }
        }
    }
}
