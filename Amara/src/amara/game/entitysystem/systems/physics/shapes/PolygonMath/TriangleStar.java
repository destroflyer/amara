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
public class TriangleStar
{
    public static HashSet<TriangleNode> triangles = new HashSet<TriangleNode>();
    public static ArrayList<Point2D> shortest = null;
    
    HashMap<TriangleNode, TriangleNode> parentMap = new HashMap<TriangleNode, TriangleNode>();
    HashMap<TriangleNode, Double> costMap = new HashMap<TriangleNode, Double>();
    HashMap<TriangleNode, Double> estimateMap = new HashMap<TriangleNode, Double>();
    HashSet<TriangleNode> open = new HashSet<TriangleNode>();
    HashSet<TriangleNode> closed = new HashSet<TriangleNode>();
    Point2D startPoint, endPoint;
    double agentRadius = 0;

    public TriangleStar(ArrayList<Point2D> tris)
    {
        for (int i = 0; i < tris.size(); i+=3)
        {
            Point2D a = tris.get(i);
            Point2D b = tris.get(i + 1);
            Point2D c = tris.get(i + 2);
            TriangleNode tri = new TriangleNode(a, b, c);
            int num = 0;
            for (TriangleNode triangle : triangles)
            {
                if(tri.tryNeighbor(triangle))
                {
                    if(++num == 3) break;
                }
            }
            triangles.add(tri);
        }
        for (TriangleNode tri : triangles)
        {
            calcWidths(tri);
        }
    }
    
    private static boolean isObtuse(TriangleNode tri, int corner)
    {
        return Math.PI / 2 < cornerAngle(tri, corner);
    }
    private static double cornerAngle(TriangleNode tri, int corner)
    {
        return Point2DUtil.angle(tri.point((corner + 2) % 3), tri.point(corner), tri.point((corner + 1) % 3));
    }
    private static double edgeLength(TriangleNode tri, int i)
    {
        return tri.point(i).distance(tri.point((i + 1) % 3));
    }
    private static void calcWidths(TriangleNode tri)
    {
        for (int i = 0; i < 3; i++)
        {
            tri.setCornerWidth(i, calcWidth(tri, i));
        }
    }
    private static double calcWidth(TriangleNode tri, int corner)
    {
        int a = (corner + 2) % 3;
        int b = corner;
        int c = (corner + 1) % 3;
        Point2D C = tri.point(b);
        Point2D A = tri.point(c);
        Point2D B = tri.point(a);
        double distance = Math.min(edgeLength(tri, a), edgeLength(tri, b));
        if(isObtuse(tri, c) || isObtuse(tri, a)) return distance;
        if(tri.neighbor(c) == null) return Point2DUtil.fromLineSegmentToPoint(A, B, C).length();
        return searchWidth(tri, C, c, distance);
    }
    private static double searchWidth(TriangleNode tri, Point2D C, int e, double distance)
    {
        int f = (e + 1) % 3;
        Point2D U = tri.point(e);
        Point2D V = tri.point(f);
        assert !C.equals(U) && !C.equals(V);
        if(isObtuse(tri, e) || isObtuse(tri, f)) return distance;
        double tmpDist = Point2DUtil.fromLineSegmentToPoint(U, V, C).length();
        if(tmpDist > distance) return distance;
        if(tri.neighbor(e) == null) return tmpDist;
        TriangleNode next = tri.neighbor(e);
        int eNext = next.indexOf(V);
        int e1 = (eNext + 1) % 3;
        int e2 = (eNext + 2) % 3;
        distance = searchWidth(next, C, e1, distance);
        return searchWidth(next, C, e2, distance);
    }
    
    
    private void init(Point2D startP, Point2D endP, double agentRadius)
    {
        parentMap.clear();
        costMap.clear();
        estimateMap.clear();
        open.clear();
        closed.clear();
        
        startPoint = startP;
        endPoint = endP;
        this.agentRadius = agentRadius;
    }
    
    public ArrayList<TriangleNode> findChannel(Point2D startP, Point2D endP, double agentRadius)
    {
        return findChannel(container(startP), container(endP), startP, endP, agentRadius);
    }
    private ArrayList<TriangleNode> findChannel(TriangleNode start, TriangleNode end, Point2D startP, Point2D endP, double agentRadius)
    {
        init(startP, endP, agentRadius);
        open.add(start);
        costMap.put(start, 0d);
        estimateMap.put(start, startP.distance(endP));

        TriangleNode current = null;
        while (!(open.isEmpty() || (current = smallest()) == end))
        {
            open.remove(current);
            closed.add(current);
            TriangleNode parent = parentMap.get(current);
            for(int i = 0; i < 3; i++)
            {
                TriangleNode next = neighbor(current, i);
                if(parent == next) continue;
                if(next == null) continue;
                if(cornerWidth(current, i) < agentRadius) continue;
                
                double cost = cost(current, i);
//                assert !closed.contains(next) || costMap.get(next) <= cost;
                if(closed.contains(next))
                {
                    if(costMap.get(next) <= cost) continue;
                    closed.remove(next);
                }
                else if(open.contains(next) && costMap.get(next) <= cost) continue;
                double estimate = estimate(current, i, endPoint);
                parentMap.put(next, current);
                estimateMap.put(next, estimate);
                costMap.put(next, cost);
                open.add(next);
            }
        }
        
        System.out.println("estimated path length: " + (costMap.get(current) + estimateMap.get(current)));
        ArrayList<TriangleNode> channel = reverseChannel(current);
        Util.reverse(channel);
        return channel;
    }
    private ArrayList<TriangleNode> reverseChannel(TriangleNode node)
    {
        ArrayList<TriangleNode> channel = new ArrayList<TriangleNode>();
        channel.add(node);
        while (parentMap.containsKey(node))
        {
            node = parentMap.get(node);
            channel.add(node);
        }
        return channel;
    }
    private TriangleNode smallest()
    {
        TriangleNode smallest = null;
        double priority = Double.NaN;
        for(TriangleNode triNode: open)
        {
            double tmp = priority(triNode);
            if (!(priority <= tmp))
            {
                smallest = triNode;
                priority = tmp;
            }
        }
        return smallest;
    }
    private double priority(TriangleNode node)
    {
        return costMap.get(node) + estimateMap.get(node);
    }
    private TriangleNode neighbor(TriangleNode tri, int i)
    {
        return tri.neighbor(i);
    }
    private double cornerWidth(TriangleNode tri, int i)
    {
        int arc = arcIndex(tri, i);
        if(arc == -1) return edgeLength(tri, i);
        double width = tri.getCornerWidth(arc);
        assert width <= edgeLength(tri, arc);
        assert width <= edgeLength(tri, (arc + 2) % 3);
        return width;
        
//        Point2D a = point(tri, i);
//        Point2D b = point(tri, (i + 1) % 3);
//        return a.distance(b);
    }
    private Point2D point(TriangleNode tri, int i)
    {
        return tri.point(i);
    }
    private double cost(TriangleNode current, int i)
    {
        double c1 = estimate(current, i, startPoint);
        double prevCost = costMap.get(current);
        double arcLength;
        int arc = arcIndex(current, i);
        if(arc == -1) arcLength = 0;
        else
        {
            double arcAngle = Point2DUtil.angle(point(current, (arc + 2) % 3), point(current, arc), point(current, (arc + 1) % 3));
            assert 0 <= arcAngle && arcAngle <= Math.PI;
            double arcRadius = agentRadius;
            arcLength = arcAngle * arcRadius;
        }
        double c2 = prevCost + arcLength;
        
        double c4;
        TriangleNode p = parentMap.get(current);
        if(p != null)
        {
            int j = p.indexOf(current);
            c4 = prevCost - estimate(p, j, startPoint) + c1;
        }
        else c4 = c1;
        assert c1 <= c4;
        return Util.max(c2, c4);
    }
    private double estimate(TriangleNode parent, int i, Point2D p)
    {
        return estimate(point(parent, i), point(parent, (i + 1) % 3), p);
    }
    private double estimate(Point2D edgeA, Point2D edgeB, Point2D p)
    {
        double dist = Point2DUtil.fromLineSegmentToPoint(edgeA, edgeB, p).length();
        assert dist <= p.distance(edgeA);
        assert dist <= p.distance(edgeB);
        return dist;
    }
    private int arcIndex(TriangleNode current, int i)
    {
        TriangleNode parent = parentMap.get(current);
        if(parent == null) return -1;
        int j = current.indexOf(parent);
        assert j != -1;
        assert i != j;
        if(i == (j + 1) % 3) return i;
        assert j == (i + 1) % 3;
        return j;
    }
    private TriangleNode container(Point2D p)
    {
        for (TriangleNode tri : triangles)
        {
            if(tri.areaContains(p)) return tri;
        }
        throw new Error("point not contained in triangulation");
    }
    
    private void prepareFunnel(ArrayList<TriangleNode> channel, ArrayList<Point2D> outTunnel, ArrayList<Byte> outSides)
    {
        outTunnel.add(startPoint);
        outSides.add((byte)-1);
        for (int i = 0; i + 1 < channel.size(); i++)
        {
            TriangleNode current = channel.get(i);
            TriangleNode next = channel.get(i + 1);
            
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
        outTunnel.add(endPoint);
        outSides.add((byte)0);
        outTunnel.add(endPoint);
        outSides.add((byte)1);
    }
    
    //sides: 0 left, 1 right
    private ArrayList<Point2D> stupidFunnel(ArrayList<Point2D> tunnel, ArrayList<Byte> sides)
    {
        ArrayList<Point2D> path = new ArrayList<Point2D>();
        
        int apex = 0;
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
                    path.add(tunnel.get(apex));
                    apex = feelers[side];

                    i = apex;
                    feelers[0] = apex;
                    feelers[1] = apex;
                }
            }
        }
        path.add(endPoint);
        
        double length = 0;
        for (int i = 1; i < path.size(); i++)
        {
            length += path.get(i).distance(path.get(i - 1));
        }
        System.out.println("exact path length: " + length);
        
        return path;
    }
    //sides: 0 left, 1 right
    private ArrayList<Integer> radiusFunnel(ArrayList<Point2D> tunnel, ArrayList<Byte> sides)
    {
        ArrayList<Integer> path = new ArrayList<Integer>();
        
        int apex = 0;
        int[] feelers = new int[]{0, 0};
        Point2D[] feelers_v = new Point2D[2];
        for (int i = 1; i < tunnel.size(); i++)
        {
            int side = sides.get(i);
            Point2D v = tunnel.get(i).sub(tunnel.get(apex));
            
            if( v.squaredLength()> agentRadius * agentRadius ) //if v.length is below halfwidth, then the vertices are too close together
				//to form meaningful tangent calculations. This is not the case for vertices on the same side of funnel,
				//but they get straight-line calculations anyway, and no two vertices on opposite sides of funnel should be this close
				//because that would make path invalid (only start and end)
            {
                if( apex == 0  ) //Apex is start point
                {
                    if( i < tunnel.size() - 2 ) //If not true, the current
//element is the end point, so we actually want straight line between the apex and it after all
                    {
                        double len = v.length();
                        double sin = agentRadius / len * ( side == 1 ? -1 : 1 );
                        double cos = Math.sqrt( len * len - agentRadius * agentRadius ) / len;
                        v = new Point2D(v.getX() * cos - v.getY() * sin, v.getX() * sin + v.getY() * cos);
                    }
                }
                else if( i >= tunnel.size() - 2 ) //Current point is end point
                {
                    double len = v.length();
                    double sin = agentRadius / len * ( side == 1 ? 1 : -1 );
                    double cos = Math.sqrt( len * len - agentRadius * agentRadius ) / len;
                    v = new Point2D(v.getX() * cos - v.getY() * sin, v.getX() * sin + v.getY() * cos);
                }
                else if(side != sides.get(apex)) //Opposite sides of list
                {
                    double len = v.length() * 0.5;
                    double sin = agentRadius / len * ( side == 1 ? -1 : 1 );
                    double cos = Math.sqrt( len * len - agentRadius * agentRadius ) / len;
                    v = new Point2D(v.getX() * cos - v.getY() * sin, v.getX() * sin + v.getY() * cos);
                }
            }
            
            if(apex == feelers[side] || (v.cross(feelers_v[side]) < 0) == (side == 0))
            {
                feelers[side] = i;
                feelers_v[side] = v;
//                if(i == 2) assert feelers_v[1].cross(feelers_v[0]) <= 0;
                
                side ^= 1;
                if(apex != feelers[side] && (tunnel.get(i).equals(tunnel.get(feelers[side])) || (v.cross(feelers_v[side]) < 0) == (side != 0)))
                {
                    path.add(apex);
                    apex = feelers[side];
                    
                    //ADDED SECTION
                    //There is occasionaly an instance whereby the current vertex is actually closer
                    //than the one on the oposite left/right list
                    //If this is the case, SWAP them, because we want to move to apex to the closest one
                    if(v.squaredLength() < feelers_v[side].squaredLength())
                    {
                            Util.swap(tunnel, feelers[side], i);
                            Util.swap(sides, feelers[side], i);
                    }

                    i = apex;
                    feelers[0] = apex;
                    feelers[1] = apex;
                }
            }
        }
        path.add(apex);
        return path;
    }
    
    private ArrayList<Point2D> postRadiusFunnel(ArrayList<Integer> path, ArrayList<Point2D> tunnel, ArrayList<Byte> sides)
    {
        ArrayList<Point2D> tmp = new ArrayList<Point2D>();
        for (int i = 0; i < path.size(); i++)
        {
            tmp.add(tunnel.get(path.get(i)));
        }
        return tmp;
    }
    
    public ArrayList<Point2D> findPath(Point2D start, Point2D end)
    {
        ArrayList<TriangleNode> channel = findChannel(start, end, 0);
        ArrayList<Point2D> tunnel = new ArrayList<Point2D>();
        ArrayList<Byte> sides = new ArrayList<Byte>();
        prepareFunnel(channel, tunnel, sides);
        return stupidFunnel(tunnel, sides);
//        return postRadiusFunnel(radiusFunnel(tunnel, sides), tunnel, sides);
    }
//    public ArrayList<Point2D> funnel(ArrayList<TriangleNode> channel)
//    {
//        ArrayList<Point2D> path = new ArrayList<Point2D>();
//        if(channel.size() <= 2)
//        {
//            path.add(startPoint);
//            path.add(endPoint);
//            return path;
//        }
//        
//        path.add(startPoint);
//        ArrayDeque<Point2D> leftDeque = new ArrayDeque<Point2D>();
//        ArrayDeque<Point2D> rightDeque = new ArrayDeque<Point2D>();
//        leftDeque.add(startPoint);
//        rightDeque.add(startPoint);
//        TriangleNode first = channel.get(0);
//        int n = first.indexOf(channel.get(1));
//        Point2D leftEnd = first.point((n + 1) % 3);
//        Point2D rightEnd = first.point(n);
//    }
//    
//    private void funnelAdd(ArrayDeque<Point2D> deque, Point2D v, double sign, ArrayList<Point2D> path)
//    {
//        
//    }
}
