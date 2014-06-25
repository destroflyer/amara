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
public class TriangleGraph
{
    HashMap<Point2D, TriNode> pointToNode = new HashMap<Point2D, TriNode>();
    HashMap<TriNode, Point2D> nodeToPoint = new HashMap<TriNode, Point2D>();
    HashMap<Triangle, HashSet<Point2D>> triPoints = new HashMap<Triangle, HashSet<Point2D>>();

    public TriangleGraph(ArrayList<Point2D> tris, double nodeDistance, int maxSplit)
    {
        for (int i = 0; i < tris.size(); i+=3)
        {
            Triangle tri = new Triangle(tris.get(i), tris.get(i + 1), tris.get(i + 2));
            HashSet<Point2D> hash = new HashSet<Point2D>();
            triPoints.put(tri, hash);
            int num = 0;
            for (Triangle triangle : triPoints.keySet())
            {
                if(tri.tryNeighbor(triangle))
                {
                    if(++num == 3) break;
                }
            }
        }
        for (Triangle tri : triPoints.keySet())
        {
            addTriangle(tri, nodeDistance, maxSplit);
        }
    }
    
    private void addTriangle(Triangle tri, double nodeDistance, int maxSplit)
    {
        HashSet<Point2D> hash = triPoints.get(tri);
        ArrayList<ArrayList<Point2D>> edges = new ArrayList<ArrayList<Point2D>>();
        for (int i = 0; i < 3; i++)
        {
            Point2D a = tri.getPoints()[i];
            Point2D b = tri.getPoints()[(i + 1) % 3];
            double len = Math.sqrt(a.squaredDistance(b));
            edges.add(new ArrayList<Point2D>());
            edges.get(i).add(a);
            hash.add(a);
            int amount = tri.getNeighbors()[i] == null? 0: Math.min((int)(len / nodeDistance), maxSplit);
            for (int j = 1; j < amount; j++)
            {
                Point2D c = Point2DUtil.weightAvg(a, j, b, amount - j);
                edges.get(i).add(c);
                hash.add(c);
            }
            edges.get(i).add(b);
            hash.add(b);
        }
        
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            
            for (int k = 1; k < edges.get(i).size() - 1; k++)
            {
                TriNode a = getOrCreate(edges.get(i).get(k));
                for (int l = 1; l < edges.get(j).size(); l++)
                {
                    TriNode b = getOrCreate(edges.get(j).get(l));
                    makeNeighbors(a, b);
                }
            }
            for (int k = 1; k < edges.get(i).size(); k++)
            {
                makeNeighbors(getOrCreate(edges.get(i).get(k - 1)), getOrCreate(edges.get(i).get(k)));
            }
        }
    }
    
    private TriNode getOrCreate(Point2D p)
    {
        for (Point2D point2D : pointToNode.keySet())
        {
            assert point2D.equals(p) || !point2D.withinEpsilon(p);
        }
        TriNode node = pointToNode.get(p);
        if(node == null)
        {
            node = new TriNode();
            pointToNode.put(p, node);
            nodeToPoint.put(node, p);
        }
        return node;
    }
    private void makeNeighbors(TriNode a, TriNode b)
    {
        a.getNeighbors().add(b);
        b.getNeighbors().add(a);
    }
    private void remove(TriNode node)
    {
        clearNeighbors(node);
        pointToNode.remove(nodeToPoint.get(node));
        nodeToPoint.remove(node);
    }
    private void clearNeighbors(TriNode node)
    {
        for (TriNode n : node.getNeighbors())
        {
            n.getNeighbors().remove(node);
        }
        node.getNeighbors().clear();
    }
    private Triangle container(Point2D p)
    {
        for (Triangle tri : triPoints.keySet())
        {
            if(tri.areaContainsOrBorder(p)) return tri;
        }
        assert false;
        return null;
    }
    
    public ArrayList<Point2D> findPath(Point2D start, Point2D end)
    {
        TriNode a = getOrCreate(start);
        TriNode b = getOrCreate(end);
        for (Point2D p : triPoints.get(container(start)))
        {
            makeNeighbors(a, getOrCreate(p));
        }
        for (Point2D p : triPoints.get(container(end)))
        {
            makeNeighbors(b, getOrCreate(p));
        }
        ArrayList<Point2D> path = findPath(a, b);
        remove(a);
        remove(b);
        return path;
    }
    
    private ArrayList<Point2D> findPath(TriNode start, TriNode end)
    {
        HashMap<TriNode, TriNode> parents = new HashMap<TriNode, TriNode>();
        HashSet<TriNode> open = new HashSet<TriNode>();
        HashSet<TriNode> closed = new HashSet<TriNode>();
        HashMap<TriNode, Double> costMap = new HashMap<TriNode, Double>();
        HashMap<TriNode, Double> priorityMap = new HashMap<TriNode, Double>();
        for(TriNode triNode: nodeToPoint.keySet())
        {
            costMap.put(triNode, Double.MAX_VALUE);
        }
        open.add(start);
        costMap.put(start, 0d);
        priorityMap.put(start, 0d);

        TriNode current;
        while (!open.isEmpty() && (current = smallest(open, priorityMap)) != end)
        {
            open.remove(current);
            closed.add(current);
            for(TriNode neighbor: current.getNeighbors())
            {
                double cost = costMap.get(current) + moveCost(current, neighbor);
                if (open.contains(neighbor) && cost < costMap.get(neighbor))
                {
                    open.remove(neighbor);
                    priorityMap.remove(neighbor);
                }
                if (closed.contains(neighbor) && cost < costMap.get(neighbor))
                {
                    closed.remove(neighbor);
                }
                if (!open.contains(neighbor) && !closed.contains(neighbor))
                {
                    costMap.put(neighbor, cost);
                    priorityMap.put(neighbor, cost + moveCost(neighbor, end));
                    open.add(neighbor);
                    parents.put(neighbor, current);
                }
            }
        }
        ArrayList<Point2D> path = new ArrayList<Point2D>();
        path.add(nodeToPoint.get(end));
        TriNode node = end;
        while (parents.containsKey(node))
        {
            node = parents.get(node);
            path.add(nodeToPoint.get(node));
        }
        Util.reverse(path);
        return path;
    }
    
    private TriNode smallest(HashSet<TriNode> open, HashMap<TriNode, Double> priority)
    {
        TriNode smallest = null;
        assert !open.isEmpty();
        for(TriNode triNode: open)
        {
            if (smallest == null || priority.get(smallest) > priority.get(triNode)) smallest = triNode;
        }
        assert smallest != null;
        return smallest;
    }

    private double moveCost(TriNode a, TriNode b)
    {
        return Math.sqrt(nodeToPoint.get(a).squaredDistance(nodeToPoint.get(b)));
    }
}
