/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class Triangulator
{
    public boolean isConvex(List<Vector2D> poly)
    {
        Vector2D[] points = poly.toArray(Vector2D[]::new);
        double side = 0;
        int i, j ,k;
        for(i = 0; side == 0 && i < points.length; i++) {
            j = (i + 1) % points.length;
            k = (j + 1) % points.length;
            side = Vector2DUtil.lineSide(points[k], points[i], points[j]);
        }
        if(side == 0) return true;
        for(i = 0; i < points.length; i++) {
            j = (i + 1) % points.length;
            k = (j + 1) % points.length;
            if(side * Vector2DUtil.lineSide(points[k], points[i], points[j]) < 0) return false;
        }
        return true;
    }
    
    public boolean canTriangulate(List<Vector2D> poly)
    {
        if(poly.size() < 3) return false;
        ArrayList<Integer> indices = indices(poly);
        for (int i = 0; i < poly.size(); i++)
        {
            if(intersect(poly, poly.get(i), poly.get((i + 1) % poly.size()), indices)) return false;
        }
        return true;
    }
    
    public ArrayList<SimpleConvexPolygon> createTrisFromPoly(List<Vector2D> vertices)
    {
        ArrayList<Integer> indices = counterClockwiseIndices(vertices);
        ArrayList<Integer> tris = triangulatePoly(vertices, indices);
        return toShapes(vertices, tris);
    }
    
    public ArrayList<SimpleConvexPolygon> createDelaunayTrisFromPoly(List<Vector2D> vertices)
    {
        ArrayList<Vector2D> arrVertices = new ArrayList<Vector2D>(vertices);
        ArrayList<Integer> indices = counterClockwiseIndices(vertices);
        ArrayList<Integer> tris = triangulatePoly(vertices, indices);
        Delaunay d = new Delaunay();
        tris = d.delaunay(arrVertices, tris);
        return toShapes(vertices, tris);
    }
    public ArrayList<Vector2D> delaunayTris(ArrayList<Vector2D> tris)
    {
        
        ArrayList<Vector2D> vertices = new ArrayList<Vector2D>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        indexTris(tris, vertices, indices);
        
        ArrayList<Vector2D> vecVerts = new ArrayList<Vector2D>();
        for (int i = 0; i < vertices.size(); i++)
        {
            vecVerts.add(new Vector2D(vertices.get(i).getX(), vertices.get(i).getY()));
        }
        
        indices = new Delaunay().delaunay(vecVerts, indices);
        ArrayList<Vector2D> result = new ArrayList<Vector2D>();
        for (int i = 0; i < indices.size(); i++)
        {
            Vector2D v = vecVerts.get(indices.get(i));
            result.add(new Vector2D(v.getX(), v.getY()));
        }
        return result;
    }
    
    private void indexTris(ArrayList<Vector2D> tris, ArrayList<Vector2D> vertices, ArrayList<Integer> indices)
    {
        for (int i = 0; i < tris.size(); i++)
        {
            int index = indexOf(vertices, tris.get(i));
            if(index == -1)
            {
                index = vertices.size();
                vertices.add(tris.get(i));
            }
            indices.add(index);
        }
    }
    private int indexOf(ArrayList<Vector2D> vertices, Vector2D vertex)
    {
        for (int i = 0; i < vertices.size(); i++)
        {
            if(vertices.get(i).withinEpsilon(vertex)) return i;
        }
        return -1;
    }
    
    private ArrayList<SimpleConvexPolygon> toShapes(List<Vector2D> vertices, ArrayList<Integer> tris)
    {
        ArrayList<SimpleConvexPolygon> list = new ArrayList<SimpleConvexPolygon>();
        for (int i = 0; i < tris.size(); i += 3)
        {
            Vector2D[] vecs = new Vector2D[]{vertices.get(tris.get(i)), vertices.get(tris.get(i + 1)), vertices.get(tris.get(i + 2))};
            list.add(new SimpleConvexPolygon(vecs));
        }
        return list;
    }
    
    private ArrayList<Integer> counterClockwiseIndices(List<Vector2D> vertices)
    {
        if(Util.isClockwise(vertices)) return reverseIndices(vertices);
        return indices(vertices);
    }
    
    private ArrayList<Integer> clockwiseIndices(List<Vector2D> vertices)
    {
        if(Util.isClockwise(vertices)) return indices(vertices);
        return reverseIndices(vertices);
    }
    
    private ArrayList<Integer> indices(List<Vector2D> vertices)
    {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < vertices.size(); i++)
        {
            indices.add(i);
        }
        return indices;
    }
    
    private ArrayList<Integer> reverseIndices(List<Vector2D> vertices)
    {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = vertices.size() - 1; i >= 0; i--)
        {
            indices.add(i);
        }
        return indices;
    }
    
    private ArrayList<Integer> triangulatePoly(List<Vector2D> vertices, ArrayList<Integer> poly)
    {
        ArrayList<Integer> indices = new ArrayList<Integer>(poly);
        ArrayList<Integer> tris = new ArrayList<Integer>();
        while(indices.size() > 3)
        {
            int i = findEar(vertices, indices);
            int prev = (i + indices.size() - 1) % indices.size();
            int next = (i + 1) % indices.size();
            
            tris.add(indices.get(prev));
            tris.add(indices.get(i));
            tris.add(indices.get(next));
            
            indices.remove(i);
        }
        tris.addAll(indices);
        return tris;
    }
    
    private int findEar(List<Vector2D> vertices, ArrayList<Integer> poly)
    {
        for (int i = 0; i < poly.size(); i++)
        {
            int prev = (i + poly.size() - 1) % poly.size();
            int next = (i + 1) % poly.size();
            Vector2D prevVec = vertices.get(poly.get(prev));
            Vector2D nextVec = vertices.get(poly.get(next));
            
            if(inside(vertices, prev, poly, nextVec))
            {
                if(inside(vertices, next, poly, prevVec))
                {
                    if(!intersect(vertices, prevVec, nextVec, poly)) return i;
                }
            }
        }
        throw new Error("no ear found");
    }
    
    private ArrayList<Integer> cutPoly(ArrayList<Vector2D> vertices, ArrayList<Integer> poly, ArrayList<ArrayList<Integer>> holes)
    {
        ArrayList<Integer> result = new ArrayList<Integer>(poly);
        ArrayList<ArrayList<Integer>> remaining = new ArrayList<ArrayList<Integer>>(holes);
        while(remaining.size() > 0)
        {
            ArrayList<Integer> hole = remaining.get(0);
            for (int j = 0; j < result.size(); j++)
            {
                int cut = tryFindCut(vertices, result, j, remaining, 0);
                if(cut < 0) continue;
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                tmp.add(result.get(j));
                for (int i = 0; i < hole.size(); i++)
                {
                    tmp.add(hole.get((cut + i) % hole.size()));
                }
                tmp.add(hole.get(cut));
                result.addAll(j, tmp);
                break;
            }
        }
        return result;
    }
    
    private int tryFindCut(ArrayList<Vector2D> vertices, ArrayList<Integer> poly, int index, ArrayList<ArrayList<Integer>> holes, int hole)
    {
        Vector2D a = vertices.get(poly.get(index));
        ArrayList<ArrayList<Integer>> obstacles = new ArrayList<ArrayList<Integer>>(holes);
        obstacles.add(poly);
        for (int i = 0; i < holes.get(hole).size(); i++)
        {
            Vector2D b = vertices.get(holes.get(hole).get(i));
            if(inside(vertices, index, poly, b) && inside(vertices, i, holes.get(hole), a))
            {
                if(!multiIntersect(vertices, a, b, holes)) return i;
            }
        }
        return -1;
    }
    
    private boolean inside(List<Vector2D> vertices, int i, ArrayList<Integer> poly, Vector2D p)
    {
        Vector2D prev = vertices.get(poly.get((i + poly.size() - 1) % poly.size()));
        Vector2D curr = vertices.get(poly.get(i));
        Vector2D next = vertices.get(poly.get((i + 1) % poly.size()));
        if(Vector2DUtil.lineSide(curr, prev, next) < 0)
        {
            return Vector2DUtil.lineSide(p, curr, next) < 0 || Vector2DUtil.lineSide(p, prev, curr) < 0;
        }
        return Vector2DUtil.lineSide(p, curr, next) < 0 && Vector2DUtil.lineSide(p, prev, curr) < 0;
    }
    
    private boolean multiIntersect(ArrayList<Vector2D> vertices, Vector2D a, Vector2D b, ArrayList<ArrayList<Integer>> polys)
    {
        for (ArrayList<Integer> poly : polys)
        {
            if(intersect(vertices, a, b, poly)) return true;
        }
        return false;
    }
    
    private boolean intersect(List<Vector2D> vertices, Vector2D a, Vector2D b, ArrayList<Integer> poly)
    {
        for (int j = 0; j < poly.size(); j++)
        {
            Vector2D c = vertices.get(poly.get(j));
            Vector2D d = vertices.get(poly.get((j + 1) % poly.size()));

            if((a.equals(c) && b.equals(d)) || (a.equals(d) && b.equals(c))) continue;
            if(Vector2DUtil.lineSegmentIntersectionPointWithoutCorners(a, b, c, d) != null) return true;
        }
        return false;
    }
}