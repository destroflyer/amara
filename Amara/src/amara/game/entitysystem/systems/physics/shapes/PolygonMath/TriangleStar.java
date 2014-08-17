/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

import amara.game.entitysystem.systems.physics.shapes.Vector2D;
import amara.game.entitysystem.systems.physics.shapes.Vector2DUtil;
import java.util.*;

/**
 *
 * @author Philipp
 */
public class TriangleStar
{
    private HashSet<TriangleNode> triangles = new HashSet<TriangleNode>();
    
    private HashMap<TriangleNode, TriangleNode> parentMap = new HashMap<TriangleNode, TriangleNode>();
    private HashMap<TriangleNode, Double> costMap = new HashMap<TriangleNode, Double>();
    private HashMap<TriangleNode, Double> estimateMap = new HashMap<TriangleNode, Double>();
    private HashSet<TriangleNode> open = new HashSet<TriangleNode>();
    private HashSet<TriangleNode> closed = new HashSet<TriangleNode>();
    private Vector2D startPoint, endPoint;
    private double agentRadius = 0;

    public TriangleStar(ArrayList<Vector2D> tris)
    {
        for (int i = 0; i < tris.size(); i+=3)
        {
            Vector2D a = tris.get(i);
            Vector2D b = tris.get(i + 1);
            Vector2D c = tris.get(i + 2);
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
        return Vector2DUtil.angle(tri.point((corner + 2) % 3), tri.point(corner), tri.point((corner + 1) % 3));
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
        Vector2D C = tri.point(b);
        Vector2D A = tri.point(c);
        Vector2D B = tri.point(a);
        double distance = Math.min(edgeLength(tri, a), edgeLength(tri, b));
        if(isObtuse(tri, c) || isObtuse(tri, a)) return distance;
        if(tri.neighbor(c) == null) return Vector2DUtil.fromLineSegmentToPoint(A, B, C).length();
        return searchWidth(tri, C, c, distance);
    }
    private static double searchWidth(TriangleNode tri, Vector2D C, int e, double distance)
    {
        int f = (e + 1) % 3;
        Vector2D U = tri.point(e);
        Vector2D V = tri.point(f);
        assert !C.equals(U) && !C.equals(V);
        if(isObtuse(tri, e) || isObtuse(tri, f)) return distance;
        double tmpDist = Vector2DUtil.fromLineSegmentToPoint(U, V, C).length();
        if(tmpDist > distance) return distance;
        if(tri.neighbor(e) == null) return tmpDist;
        TriangleNode next = tri.neighbor(e);
        int eNext = next.indexOf(V);
        int e1 = (eNext + 1) % 3;
        int e2 = (eNext + 2) % 3;
        distance = searchWidth(next, C, e1, distance);
        return searchWidth(next, C, e2, distance);
    }
    
    
    private void init(Vector2D startP, Vector2D endP, double agentRadius)
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
    
    public ArrayList<TriangleNode> findChannel(Vector2D startP, Vector2D endP, double agentRadius)
    {
        TriangleNode start = container(startP);
        TriangleNode end = container(endP);
        if(end == null || start == null) return null;
        return findChannel(start, end, startP, endP, agentRadius);
    }
    private ArrayList<TriangleNode> findChannel(TriangleNode start, TriangleNode end, Vector2D startP, Vector2D endP, double agentRadius)
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
                if(cornerWidth(current, i) < agentRadius * 2)
                {
                    continue;
                }
                
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
        if(current != end) return null;
//        System.out.println("estimated path length: " + (costMap.get(current) + estimateMap.get(current)));
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
    private Vector2D point(TriangleNode tri, int i)
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
            double arcAngle = Vector2DUtil.angle(point(current, (arc + 2) % 3), point(current, arc), point(current, (arc + 1) % 3));
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
    private double estimate(TriangleNode parent, int i, Vector2D p)
    {
        return estimate(point(parent, i), point(parent, (i + 1) % 3), p);
    }
    private double estimate(Vector2D edgeA, Vector2D edgeB, Vector2D p)
    {
        double dist = Vector2DUtil.fromLineSegmentToPoint(edgeA, edgeB, p).length();
        assert dist <= p.distance(edgeA) + Util.Epsilon: dist + " | " + p.distance(edgeA);
        assert dist <= p.distance(edgeB) + Util.Epsilon: dist + " | " + p.distance(edgeB);
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
    private TriangleNode container(Vector2D p)
    {
        for (TriangleNode tri : triangles)
        {
            if(tri.areaContains(p)) return tri;
        }
        return null;
    }
    
    private ArrayList<Vector2D> placeholderPath(ArrayList<TriangleNode> channel)
    {
        ArrayList<Vector2D> path = new ArrayList<Vector2D>();
        path.add(startPoint);
        for (int i = 1; i < channel.size(); i++)
        {
            path.add(Vector2DUtil.avg(channel.get(i).center(), channel.get(i - 1).center()));
        }
        path.add(endPoint);
        return path;
    }
    
    private void prepareFunnel(ArrayList<TriangleNode> channel, ArrayList<Vector2D> outTunnel, ArrayList<Byte> outSides)
    {
        outTunnel.add(startPoint);
        outSides.add((byte)-1);
        for (int i = 0; i + 1 < channel.size(); i++)
        {
            TriangleNode current = channel.get(i);
            TriangleNode next = channel.get(i + 1);
            
            int j = current.indexOf(next);
            int k = (j + 1) % 3;
            Vector2D left = current.point(k);
            Vector2D right = current.point(j);
            
            assert Vector2DUtil.lineSide(current.point((k + 1) % 3), left, right) > 0;
            
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
    private ArrayList<Vector2D> stupidFunnel(ArrayList<Vector2D> tunnel, ArrayList<Byte> sides)
    {
        ArrayList<Vector2D> path = new ArrayList<Vector2D>();
        
        int apex = 0;
        int[] feelers = new int[]{0, 0};
        Vector2D[] feelers_v = new Vector2D[2];
        for (int i = 1; i < tunnel.size(); i++)
        {
            int side = sides.get(i);
            Vector2D v = tunnel.get(i).sub(tunnel.get(apex));
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
        
//        double length = 0;
//        for (int i = 1; i < path.size(); i++)
//        {
//            length += path.get(i).distance(path.get(i - 1));
//        }
//        System.out.println("exact path length: " + length);
        
        return path;
    }
    //sides: 0 left, 1 right
    private ArrayList<Integer> radiusFunnel(ArrayList<Vector2D> tunnel, ArrayList<Byte> sides)
    {
        ArrayList<Integer> path = new ArrayList<Integer>();
        
        int apex = 0;
        int[] feelers = new int[]{0, 0};
        Vector2D[] feelers_v = new Vector2D[2];
        for (int i = 1; i < tunnel.size(); i++)
        {
            int side = sides.get(i);
            Vector2D v = tunnel.get(i).sub(tunnel.get(apex));
            
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
                        v = new Vector2D(v.getX() * cos - v.getY() * sin, v.getX() * sin + v.getY() * cos);
                    }
                }
                else if( i >= tunnel.size() - 2 ) //Current point is end point
                {
                    double len = v.length();
                    double sin = agentRadius / len * ( side == 1 ? 1 : -1 );
                    double cos = Math.sqrt( len * len - agentRadius * agentRadius ) / len;
                    v = new Vector2D(v.getX() * cos - v.getY() * sin, v.getX() * sin + v.getY() * cos);
                }
                else if(side != sides.get(apex)) //Opposite sides of list
                {
                    double len = v.length() * 0.5;
                    double sin = agentRadius / len * ( side == 1 ? -1 : 1 );
                    double cos = Math.sqrt( len * len - agentRadius * agentRadius ) / len;
                    v = new Vector2D(v.getX() * cos - v.getY() * sin, v.getX() * sin + v.getY() * cos);
                }
            }
            
            if(apex == feelers[side] || (v.cross(feelers_v[side]) < 0) == (side == 0))
            {
                feelers[side] = i;
                feelers_v[side] = v;
                
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
    
    private ArrayList<Vector2D> postRadiusFunnelPlaceholder(ArrayList<Integer> path, ArrayList<Vector2D> tunnel, ArrayList<Byte> sides)
    {
        ArrayList<Vector2D> tmp = new ArrayList<Vector2D>();
        for (int i = 0; i < path.size(); i++)
        {
            tmp.add(tunnel.get(path.get(i)));
        }
        return tmp;
    }
    private ArrayList<Vector2D> postRadiusFunnel(ArrayList<Integer> path, ArrayList<Vector2D> tunnel, ArrayList<Byte> sides)
    {
        ArrayList<Vector2D> tmp = new ArrayList<Vector2D>();
        for (int i = 1; i < path.size(); i++)
        {
            int sideA = i == 1? 0: (sides.get(i - 1) == 0? -1: 1);
            int sideB = i == path.size() - 1? 0: (sides.get(i) == 0? -1: 1);
            Vector2D a = tunnel.get(path.get(i - 1));
            Vector2D b = tunnel.get(path.get(i));
            
            Vector2D[] r = new Vector2D[2];
            offsetLine(a, b, sideA, sideB, r);
            tmp.add(r[0]);
            tmp.add(r[1]);
        }
        return tmp;
    }
    //-1 = left < 0 < right = 1
    private void offsetLine(Vector2D a, Vector2D b, int sideA, int sideB, Vector2D[] result)
    {
        if(sideA == 0)
        {
            result[0] = a;
            if(sideB == 0)
            {
                result[1] = b;
            }
            else
            {
                int i = sideB < 0? 0: 1;
                result[1] = Util.tangentPoints(a, b, agentRadius).get(i);
            }
        }
        else
        {
            if(sideB == 0)
            {
                int i = sideA < 0? 0: 1;
                result[0] = Util.tangentPoints(b, a, agentRadius).get(i);
                result[1] = b;
            }
            else
            {
                if(sideA == sideB)
                {
                    Vector2D offset = b.sub(a).unit();
                    if(sideA < 0) offset = offset.leftHand();
                    else offset = offset.rightHand();
                    result[0] = a.add(offset);
                    result[1] = b.add(offset);
                }
                else
                {
                    Vector2D c = Vector2DUtil.avg(a, b);
                    int i = sideA < 0? 0: 1;
                    result[0] = Util.tangentPoints(c, a, agentRadius).get(i);
                    result[1] = Util.tangentPoints(c, b, agentRadius).get(i ^ 1);
                }
            }
        }
    }
    
    public ArrayList<Vector2D> findPath(Vector2D start, Vector2D end, double radius)
    {
        ArrayList<TriangleNode> channel = findChannel(start, end, radius);
        if(channel == null) return null;
//        return placeholderPath(channel);
        ArrayList<Vector2D> tunnel = new ArrayList<Vector2D>();
        ArrayList<Byte> sides = new ArrayList<Byte>();
        prepareFunnel(channel, tunnel, sides);
        return stupidFunnel(tunnel, sides);
//        return postRadiusFunnelPlaceholder(radiusFunnel(tunnel, sides), tunnel, sides);
//        return postRadiusFunnel(radiusFunnel(tunnel, sides), tunnel, sides);
    }
    
    public TrianglePath findTriPath(Vector2D start, Vector2D end, double radius)
    {
        ArrayList<TriangleNode> channel = findChannel(start, end, radius);
        if(channel == null) return null;
        return new TrianglePath(channel, radius);
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
