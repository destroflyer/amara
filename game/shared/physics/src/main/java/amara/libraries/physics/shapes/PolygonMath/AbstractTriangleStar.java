/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import amara.libraries.physics.shapes.Vector2D;
import amara.libraries.physics.shapes.Vector2DUtil;
import java.util.*;

/**
 *
 * @author Philipp
 */
public class AbstractTriangleStar
{
    ArrayList<AbstractTriangleNode> triangles = new ArrayList<AbstractTriangleNode>();

    public AbstractTriangleStar(ArrayList<Vector2D> tris)
    {
        for (int i = 0; i < tris.size(); i+=3)
        {
            Vector2D a = tris.get(i);
            Vector2D b = tris.get(i + 1);
            Vector2D c = tris.get(i + 2);
            AbstractTriangleNode tri = new AbstractTriangleNode(a, b, c);
            int num = 0;
            for (AbstractTriangleNode triangle : triangles)
            {
                if(tri.tryNeighbor(triangle))
                {
                    if(++num == 3) break;
                }
            }
            triangles.add(tri);
        }
        
        abstractLevel2(triangles);
        
        for (AbstractTriangleNode tri : triangles)
        {
            assert tri.getLevel() != -1;
            assert tri.getConnected() != -1;
            for (int i = 0; i < 3; i++)
            {
                AbstractTriangleNode n = tri.getNeighbor(i);
                if(n != null) assert n.getConnected() == tri.getConnected();
                n = tri.getAdjacent(i);
                if(n != null) assert n.getConnected() == tri.getConnected();
            }
        }
    }
    
    private static void collapseUnrootedTree(AbstractTriangleNode tri, int connected)
    {
        Deque<AbstractTriangleNode> stack = new ArrayDeque<AbstractTriangleNode>();
        stack.push(tri);
        while(!stack.isEmpty())
        {
            AbstractTriangleNode current = stack.pop();
            assert current.getConnected() == -1;
            current.setConnected(connected);
            for (int i = 0; i < 3; i++)
            {
                AbstractTriangleNode next = current.getNeighbor(i);
                if(next == null)
                {
                    current.setAngle(i, 0);
                    current.setChoke(i, 0);
                }
                else
                {
                    current.setAngle(i, Double.POSITIVE_INFINITY);
                    current.setChoke(i, Double.POSITIVE_INFINITY);
                    if(next.getConnected() == -1)
                    {
                        stack.push(next);
                    }
                }
            }
        }
    }
    
    private static void collapseRootedTree(AbstractTriangleNode root, AbstractTriangleNode tri)
    {
        int connected = root.getConnected();
        Deque<AbstractTriangleNode> stack = new ArrayDeque<AbstractTriangleNode>();
        stack.push(tri);
        Deque<Double> angle = new ArrayDeque<Double>();
        angle.push(0d);
        while(!stack.isEmpty())
        {
            AbstractTriangleNode current = stack.pop();
            assert current.getConnected() == -1;
            current.setConnected(connected);
            for (int i = 0; i < 3; i++)
            {
                AbstractTriangleNode last = current.getNeighbor(i);
                if(last.getConnected() == -1)
                {
                    current.setAdjacent(i, root);
                    current.setAngle(i, angle.pop());
                    if(last == root)
                    {
                        current.setChoke(i, edgeLength(current, i));
                    }
                    else
                    {
                        for (int j = 0; j < 3; j++)
                        {
                            if(last.getAdjacent(i) != null)
                            {
                                current.setChoke(i, Math.min(last.getChoke(j), widthBetween(current, i, last, j)));
                                break;
                            }
                        }
                    }
                    int right = (i + 1) % 3;
                    stack.push(current.getNeighbor(right));
                    angle.push(angleBetween(current, i, right));
                    
                    int left = (i + 2) % 3;
                    stack.push(current.getNeighbor(left));
                    angle.push(angleBetween(current, i, left));
                }
                else
                {
                    current.setAdjacent(i, null);
                    if(current.getNeighbor(i) == null)
                    {
                        current.setAngle(i, 0);
                        current.setChoke(i, 0);
                    }
                    else
                    {
                        current.setAngle(i, Double.POSITIVE_INFINITY);
                        current.setChoke(i, Double.POSITIVE_INFINITY);
                    }
                }
            }
        }
    }
    
    private static Deque<AbstractTriangleNode> abstractLevels0and1(ArrayList<AbstractTriangleNode> triangles, int[] connected)
    {
        Deque<AbstractTriangleNode> remaining = new ArrayDeque<AbstractTriangleNode>();
        Deque<AbstractTriangleNode> que = new ArrayDeque<AbstractTriangleNode>();
        for (AbstractTriangleNode tri : triangles)
        {
            calcWidths(tri);
            int n = numConstrained(tri);
            if(n == 3)
            {
                tri.setLevel(0);
                tri.setConnected(connected[0]++);
                for (int i = 0; i < 3; i++)
                {
                    tri.setAdjacent(i, null);
                    tri.setAngle(i, 0);
                    tri.setChoke(i, 0);
                }
            }
            else if(n == 2)
            {
                tri.setLevel(1);
                for (int i = 0; i < 3; i++)
                {
                    AbstractTriangleNode next = tri.getNeighbor(i);
                    if(next != null)
                    {
                        que.push(next);
                        break;
                    }
                }
            }
            else remaining.push(tri);
        }
        
        while(!que.isEmpty())
        {
            AbstractTriangleNode tri = que.pop();
            if(tri.getLevel() != -1)
            {
                int n = numConstrained(tri);
                int m = numAdjacentLevel(tri, 1);
                if(n + m >= 2)
                {
                    tri.setLevel(1);
                    for (int i = 0; i < 3; i++)
                    {
                        AbstractTriangleNode next = tri.getNeighbor(i);
                        if(next != null && next.getLevel() == -1)
                        {
                            que.push(next);
                        }
                    }
                }
                if(n + m == 3)
                {
                    collapseUnrootedTree(tri, connected[0]++);
                }
            }
        }
        
        return remaining;
    }
    
    private static void abstractLevel3(AbstractTriangleNode tri, int connected)
    {
        Deque<AbstractTriangleNode> que = new ArrayDeque<AbstractTriangleNode>();
        que.push(tri);
        while(!que.isEmpty())
        {
            AbstractTriangleNode base = que.pop();
            assert base.getLevel() == -1;
            base.setLevel(3);
            base.setConnected(connected);
            for (int i = 0; i < 3; i++)
            {
                double width = edgeLength(base, i);
                double angle = 0;
                AbstractTriangleNode current = base.getNeighbor(i);
                AbstractTriangleNode last = base;
                assert current != null;
                assert base.hasNeighbor(current);
                while(true)
                {
                    assert current.hasNeighbor(last);
                    AbstractTriangleNode next = null;
                    int n = numConstrained(current);
                    int m = numAdjacentLevel(current, 1);
                    if(n + m == 0)
                    {
                        if(current.getLevel() == -1) que.push(current);
                        base.setChoke(i, width);
                        base.setAngle(i, angle);
                        base.setAdjacent(i, current);
                        break;
                    }
                    else if(n + m == 1)
                    {
                        if(current.getLevel() == -1)
                        {
                            current.setLevel(2);
                        }
                        int edgeNext = -1;
                        int edgeLast = -1;
                        assert current.hasNeighbor(last);
                        for (int e = 0; e < 3; e++)
                        {
                            AbstractTriangleNode tmp = current.getNeighbor(e);
                            if(tmp != null)
                            {
                                if(tmp == last)
                                {
                                    current.setChoke(i, width);
                                    current.setAngle(i, angle);
                                    current.setAdjacent(i, base);
                                    edgeLast = e;
                                }
                                else if(tmp.getLevel() != 1)
                                {
                                    next = tmp;
                                    edgeNext = e;
                                }
                                else collapseRootedTree(current, next);
                            }
                        }
                        if(!(edgeNext != -1 && edgeLast != -1))
                        {
                            assert edgeNext != -1 && edgeLast != -1;
                        }
                        width = Math.min(width, widthBetween(current, edgeLast, current, edgeNext));
                        angle = angle + angleBetween(tri, edgeLast, edgeNext);
                    }
                    last = current;
                    current = next;
                }
            }
        }
    }
    
    private static void abstractLevel2(ArrayList<AbstractTriangleNode> triangles)
    {
        int[] connected = new int[]{1};
        Deque<AbstractTriangleNode> que = abstractLevels0and1(triangles, connected);
        for (AbstractTriangleNode tri : que)
        {
            int n = numConstrained(tri);
            int m = numAdjacentLevel(tri, 1);
            if(n + m == 0 && tri.getLevel() == -1)
            {
                abstractLevel3(tri, connected[0]++);
            }
        }
        
        while(!que.isEmpty())
        {
            AbstractTriangleNode tri = que.pop();
            if(tri.getLevel() == -1)
            {
                AbstractTriangleNode current = tri;
                while(current != null)
                {
                    current.setLevel(2);
                    AbstractTriangleNode next = null;
                    for (int i = 0; i < 3; i++)
                    {
                        AbstractTriangleNode tmp = current.getNeighbor(i);
                        if(tmp == null || tmp.getLevel() == 1)
                        {
                            if(tmp != null)
                            {
                                collapseRootedTree(current, tmp);
                            }
                            current.setAngle(i, 0);
                            current.setChoke(i, 0);
                            current.setAdjacent(i, null);
                        }
                        else
                        {
                            if(tmp.getLevel() == -1)
                            {
                                next = tmp;
                            }
                            current.setAngle(i, Double.POSITIVE_INFINITY);
                            current.setChoke(i, Double.POSITIVE_INFINITY);
                            current.setAdjacent(i, null);
                        }
                    }
                    current = next;
                }
            }
        }
    }
    
    private static double widthBetween(AbstractTriangleNode triA, int edgeA, AbstractTriangleNode triB, int edgeB)
    {
        return Math.min(triA.getEdgeWidth(edgeA), triB.getEdgeWidth(edgeB));
    }
    private static boolean isObtuse(AbstractTriangleNode tri, int corner)
    {
        return Math.PI / 2 < cornerAngle(tri, corner);
    }
    private static double cornerAngle(AbstractTriangleNode tri, int corner)
    {
        return Vector2DUtil.angle(tri.getPoint((corner + 2) % 3), tri.getPoint(corner), tri.getPoint((corner + 1) % 3));
    }
    private static double angleBetween(AbstractTriangleNode tri, int a, int b)
    {
        if((a + 1) % 3 == b) return cornerAngle(tri, b);
        assert (b + 1) % 3 == a: a + " | " + b;
        return cornerAngle(tri, a);
        
    }
    
    private static double edgeLength(AbstractTriangleNode tri, int i)
    {
        return tri.getPoint(i).distance(tri.getPoint((i + 1) % 3));
    }
    
    private static void calcWidths(AbstractTriangleNode tri)
    {
        for (int i = 0; i < 3; i++)
        {
            tri.setEdgeWidth(i, calcWidth(tri, i));
        }
    }
    private static double calcWidth(AbstractTriangleNode tri, int corner)
    {
        int a = (corner + 2) % 3;
        int b = corner;
        int c = (corner + 1) % 3;
        Vector2D C = tri.getPoint(b);
        Vector2D A = tri.getPoint(c);
        Vector2D B = tri.getPoint(a);
        double distance = Math.min(edgeLength(tri, a), edgeLength(tri, b));
        if(isObtuse(tri, c) || isObtuse(tri, a)) return distance;
        if(tri.getNeighbor(c) == null) return Vector2DUtil.fromLineSegmentToPoint(A, B, C).length();
        return searchWidth(tri, C, c, distance);
    }
    private static double searchWidth(AbstractTriangleNode tri, Vector2D C, int e, double distance)
    {
        int f = (e + 1) % 3;
        Vector2D U = tri.getPoint(e);
        Vector2D V = tri.getPoint(f);
        assert !C.equals(U) && !C.equals(V);
        if(isObtuse(tri, e) || isObtuse(tri, f)) return distance;
        double tmpDist = Vector2DUtil.fromLineSegmentToPoint(U, V, C).length();
        if(tmpDist > distance) return distance;
        if(tri.getNeighbor(e) == null) return tmpDist;
        AbstractTriangleNode next = tri.getNeighbor(e);
        int eNext = next.indexOf(V);
        int e1 = (eNext + 1) % 3;
        int e2 = (eNext + 2) % 3;
        distance = searchWidth(next, C, e1, distance);
        return searchWidth(next, C, e2, distance);
    }
    
    private static int numConstrained(AbstractTriangleNode tri)
    {
        int num = 0;
        for (int i = 0; i < 3; i++)
        {
            if(tri.getNeighbor(i) == null) num++;
        }
        return num;
    }
    
    private static int numAdjacentLevel(AbstractTriangleNode tri, int level)
    {
        int num = 0;
        for (int i = 0; i < 3; i++)
        {
            AbstractTriangleNode n = tri.getNeighbor(i);
            if(n != null && n.getLevel() == level) num++;
        }
        return num;
    }
    
}
