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
public class TrianglePath
{
    private TriangleNode startTri;
    private ArrayList<Integer> followTris = new ArrayList<Integer>();
    private Point2D start, end;
    private double radius;

    public TrianglePath(ArrayList<TriangleNode> channel, double radius)
    {
        this.radius = radius;
        startTri = channel.get(0);
        for (int i = 1; i < channel.size(); i++)
        {
            followTris.add(channel.get(i - 1).indexOf(channel.get(i)));
        }
    }
    
    private boolean setPathEnds(Point2D from, Point2D to)
    {
        TriangleNode current = startTri;
        int next = 0;
        while(!current.areaContains(from))
        {
            if(next == followTris.size())
            {
                return false;
            }
            current = current.neighbor(followTris.get(next++));
        }
        while(!current.areaContains(to))
        {
            if(next == followTris.size())
            {
                return false;
            }
            current = current.neighbor(followTris.get(next++));
        }
        start = from;
        end = to;
        return true;
    }
    
    private void prepareFunnel(ArrayList<Point2D> outTunnel, ArrayList<Byte> outSides)
    {
        outTunnel.add(start);
        outSides.add((byte)-1);
        TriangleNode current = startTri;
        boolean contained = false;
        for (int i = 0; i < followTris.size(); i++)
        {
            if(current.areaContains(start)) contained = true;
            if(current.areaContains(end)) contained = false;
            TriangleNode next = current.neighbor(followTris.get(i));
            
            if(contained)
            {
                int j = current.indexOf(next);
                int k = (j + 1) % 3;
                Point2D left = current.point(k);
                Point2D right = current.point(j);

                assert Point2DUtil.lineSide(current.point((k + 1) % 3), left, right) > 0;

                if(!outTunnel.contains(left))
                {
                    outTunnel.add(left);
                    outSides.add((byte)0);
                }
                if(!outTunnel.contains(right))
                {
                    outTunnel.add(right);
                    outSides.add((byte)1);
                }
            }
            current = next;
        }
        outTunnel.add(end);
        outSides.add((byte)0);
        outTunnel.add(end);
        outSides.add((byte)1);
    }
    
    private void prepareFunnelAlternate(ArrayList<Point2D> outTunnel, ArrayList<Byte> outSides)
    {
        outTunnel.add(start);
        outSides.add((byte)-1);
        TriangleNode current = startTri;
        boolean contained = false;
        for (int i = 0; i < followTris.size(); i++)
        {
            if(current.areaContains(start)) contained = true;
            if(current.areaContains(end)) contained = false;
            TriangleNode next = current.neighbor(followTris.get(i));
            
            if(contained)
            {
                int j = current.indexOf(next);
                int k = (j + 1) % 3;
                Point2D left = current.point(k);
                Point2D right = current.point(j);
                double d = left.distance(right);
                
                outTunnel.add(Point2DUtil.interpolate(left, right, radius / d));
                outSides.add((byte)0);
                
                outTunnel.add(Point2DUtil.interpolate(right, left, radius / d));
                outSides.add((byte)1);
            }
            current = next;
        }
        outTunnel.add(end);
        outSides.add((byte)0);
        outTunnel.add(end);
        outSides.add((byte)1);
    }
    
    private Point2D firstFunnel(ArrayList<Point2D> tunnel, ArrayList<Byte> sides)
    {
        final int apex = 0;
        int[] feelers = new int[]{0, 0};
        Point2D[] feelers_v = new Point2D[2];
        for (int i = 1; i < tunnel.size(); i++)
        {
            int side = sides.get(i);
            Point2D v = tunnel.get(i).sub(tunnel.get(apex));
            if(apex == feelers[side] || (v.cross(feelers_v[side]) < 0) == (side == 0))
            {
                feelers[side] = i;
                feelers_v[side] = v;
                if(i == 2) assert feelers_v[1].cross(feelers_v[0]) <= 0: feelers_v[1] + " | " + feelers_v[0];
                
                side ^= 1;
                if(apex != feelers[side] && (tunnel.get(i).equals(tunnel.get(feelers[side])) || (v.cross(feelers_v[side]) < 0) == (side != 0)))
                {
                    return tunnel.get(i);
                }
            }
        }
        return end;
    }
    
    private Point2D centerWalk(Point2D start, Point2D end)
    {
        TriangleNode current = startTri;
        int next = 0;
        while(!current.areaContains(start))
        {
            if(next == followTris.size()) return null;
            current = current.neighbor(followTris.get(next++));
        }
        return centerWalk(current, next, start, end);
    }
    private Point2D centerWalk(TriangleNode startTri, int followIndex, Point2D start, Point2D end)
    {
        if(startTri.areaContains(end)) return end;
        int follow = followTris.get(followIndex);
        Point2D b = startTri.point(follow);
        Point2D a = startTri.point((follow + 1) % 3);
        Point2D c = Point2DUtil.avg(a, b);
        if(start.withinEpsilon(c)) return centerWalk(startTri.neighbor(follow), followIndex + 1, start, end);
        return c;
    }
    
    private Point2D firstTriFunnel(Point2D start, Point2D end)
    {
        TriangleNode current = startTri;
        int next = 0;
        while(!current.areaContains(start))
        {
            if(next == followTris.size()) return null;
            current = current.neighbor(followTris.get(next++));
        }
//        while(next < followTris.size() && current.neighbor(followTris.get(next)).areaContains(start))
//        {
//            current = current.neighbor(followTris.get(next++));
//        }
        return firstTriFunnel(current, next, start, end);
    }
    private Point2D firstTriFunnel(TriangleNode startTri, int followIndex, Point2D start, Point2D end)
    {
        assert startTri.areaContains(start);
        Point2D feelerA = null;
        Point2D feelerB = null;
        for (TriangleNode current = startTri; !current.areaContains(end); current = current.neighbor(followTris.get(followIndex++)))
        {
            int follow = followTris.get(followIndex);
            Point2D a = current.point((follow + 1) % 3).sub(start);
            Point2D b = current.point(follow).sub(start);
            
            double distance = a.distance(b);
            assert 2 * radius <= distance: radius + " | " + distance;
            Point2D A = Point2DUtil.interpolate(a, b, radius / distance);
            Point2D B = Point2DUtil.interpolate(b, a, radius / distance);
            assert Util.withinEpsilon(a.distance(A) + b.distance(B) + A.distance(B) - distance);
            
            if(current == startTri)
            {
                if(b.cross(a) <= Util.Epsilon)
                {
                    startTri = current.neighbor(followTris.get(followIndex));
                    continue;
                }
                feelerA = A;
                feelerB = B;
                continue;
            }
            assert !feelerA.equals(A);
            assert !feelerB.equals(B);
            assert !feelerA.equals(B);
            assert !feelerB.equals(A);
            if(feelerA.cross(A) <= 0)
            {
                if(feelerB.cross(A) <= 0)
                {
                    assert !feelerB.withinEpsilon();
                    return feelerB.add(start);
                }
                feelerA = A;
            }
            if(feelerB.cross(B) >= 0)
            {
                if(feelerA.cross(B) >= 0)
                {
                    assert !feelerA.withinEpsilon();
                    return feelerA.add(start);
                }
                feelerB = B;
            }
        }
        if(feelerA == null) return end;
        
        Point2D delta = end.sub(start);
        if(feelerB.cross(delta) < 0)
        {
            return feelerB.add(start);
        }
        if(feelerA.cross(delta) > 0)
        {
            return feelerA.add(start);
        }
        return end;
    }
    
    public Point2D moveDistance(Point2D from, Point2D to, double distance)
    {
        assert 0 < distance;
        if(from.squaredDistance(to) <= distance * distance) return to;
        if(setPathEnds(from, to))
        {
            Point2D result;
//            result = centerWalk(from, to);
            
            result = firstTriFunnel(from, to);
            
//            ArrayList<Point2D> tunnel = new ArrayList<Point2D>();
//            ArrayList<Byte> sides = new ArrayList<Byte>();
//            prepareFunnelAlternate(tunnel, sides);
//            result = firstFunnel(tunnel, sides);
            
            double d = from.distance(result);
            assert 0 < d;
            if(d < distance) return moveDistance(result, to, distance - d);
            return Point2DUtil.interpolate(from, result, distance / d);
        }
        return null;
    }
}
