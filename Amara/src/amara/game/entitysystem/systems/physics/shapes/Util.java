/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class Util
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
    public static <T> void reverse(T[] arr)
    {
        for (int i = 0; i < arr.length / 2; i++)
        {
            swap(arr, i, arr.length - 1 - i);
        }
    }
    public static <T> void swap(T[] arr, int i, int j)
    {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    
    public static <T> ArrayList<T> toList(T[] arr)
    {
        ArrayList<T> list = new ArrayList<T>(arr.length);
        for (int i = 0; i < arr.length; i++)
        {
            list.add(arr[i]);
        }
        return list;
    }
    public static <T> void copy(T[] source, int startS, int num, T[] destination, int startD)
    {
        for (int i = 0; i < num; i++)
        {
            destination[i + startD] = source[i + startS];
        }
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
    
    public static double squaredHelper(double a, double b)
    {
        return a * a + b * b;
    }
    public static double squared(double a)
    {
        return a * a;
    }
    public static float squaredHelper(float a, float b)
    {
        return a * a + b * b;
    }
    public static int squaredHelper(int a, int b)
    {
        return a * a + b * b;
    }
    
    public static boolean circleContainsOrOnBorder(double px, double py, double cx, double cy, double r)
    {
        return squaredHelper(px - cx, py - cy) <= r * r;
    }
    public static boolean circlesIntersect(double x1, double y1, double r1, double x2, double y2, double r2)
    {
        return squaredHelper(x1 - x2, y1 - y2) < (r1 + r2) * (r1 + r2);
    }
    
    public static ArrayList<Vector2D> circleCircleIntersectionPoints(double x1, double y1, double r1, double x2, double y2, double r2)
    {
        assert circlesIntersect(x1, y1, r1, x2, y2, r2);
        x2 -= x1;
        y2 -= y1;
        double distance = Math.sqrt(squaredHelper(x2, y2));
        r1 *= r1;
        r2 = (r1 - r2 * r2 + distance * distance) / (2 * distance);
        r1 = Math.sqrt(r1 - r2 * r2);
        r2 /= distance;
        x1 += x2 * r2;
        y1 += y2 * r2;
        x2 *= r1 / distance;
        y2 *= r1 / distance;
        ArrayList<Vector2D> list = new ArrayList<Vector2D>();
        list.add(new Vector2D(x1 + y2, y1 - x2));
        list.add(new Vector2D(x1 - y2, y1 + x2));
        return list;
    }
    public static ArrayList<Vector2D> tangentPoints(Vector2D p, Vector2D c, double r)
    {
        return tangentPoints(p.getX(), p.getY(), c.getX(), c.getY(), r);
    }
    public static ArrayList<Vector2D> tangentPoints(double px, double py, double x, double y, double r)
    {
        assert !(circleContainsOrOnBorder(px, py, x, y, r));
        double x1 = (px + x) / 2;
        double y1 = (py + y) / 2;
        double r1 = Math.sqrt(Util.squaredHelper(x1 - x, y1 - y));
        return Util.circleCircleIntersectionPoints(x1, y1, r1, x, y, r);
    }
    
    public static double max(double... d)
    {
        double max = d[0];
        for (int i = 1; i < d.length; i++)
        {
            if(max < d[i]) max = d[i];
        }
        return max;
    }
}
