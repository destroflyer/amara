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
    
    public static double squaredHelper(double a, double b)
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
    
    public static ArrayList<Point2D> circleCircleIntersectionPoints(double x1, double y1, double r1, double x2, double y2, double r2)
    {
        assert circlesIntersect(x1, y1, r1, x2, y2, r2);
        double distance = Math.sqrt(squaredHelper(x2 - x1, y2 - y1));
        double a = (r1 * r1 - r2 * r2 + distance * distance) / (2 * distance);
        double h = Math.sqrt(r1 * r1 - a * a);
        double x = (x2 - x1) * (a / distance) + x1;
        double y = (y2 - y1) * (a / distance) + y1;
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        list.add(new Point2D(x + h * (y2 - y1) / distance, y - h * (x2 - x1) / distance));
        list.add(new Point2D(x - h * (y2 - y1) / distance, y + h * (x2 - x1) / distance));
        return list;
    }
    
    
}
