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
class Delaunay
{
    private class Triangle
    {
        int[] corners;
        Triangle[] neighbors;

        public Triangle(int... corners) {
            this.corners = corners;
            neighbors = new Triangle[3];
        }
        
        void invert()
        {
            int tmp = corners[0];
            corners[0] = corners[2];
            corners[2] = tmp;
        }
        
        boolean circumCircleContains(ArrayList<Vector2D> vertices, int p)
        {
            double ax = vertices.get(corners[0]).getX();
            double bx = vertices.get(corners[1]).getX();
            double cx = vertices.get(corners[2]).getX();
            double ay = vertices.get(corners[0]).getY();
            double by = vertices.get(corners[1]).getY();
            double cy = vertices.get(corners[2]).getY();

            double a = ax * ax + ay * ay;
            double b = bx * bx + by * by;
            double c = cx * cx + cy * cy;

            double denominator = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));
            double x = (a * (by - cy) + b * (cy - ay) + c * (ay - by)) / denominator;
            double y = (a * (cx - bx) + b * (ax - cx) + c * (bx - ax)) / denominator;

            a = Util.square(bx - cx) + Util.square(by - cy);
            b = Util.square(ax - cx) + Util.square(ay - cy);
            c = Util.square(bx - ax) + Util.square(by - ay);

            double area = area(vertices);
            
            double rSquared = (a * b * c) / Util.square(4 * area);
            x -= vertices.get(p).getX();
            y -= vertices.get(p).getY();
            return x * x + y * y + 1e-6 < rSquared;
        }
        
        boolean hasCorner(int i)
        {
            for (int j = 0; j < 3; j++) {
                if(corners[j] == i) return true;
            }
            return false;
        }
        
        double area(ArrayList<Vector2D> vertices)
        {
            return Util.calcArea(vertices.get(corners[0]), vertices.get(corners[1]), vertices.get(corners[2]));
        }
        
        boolean hasNeighbor(Triangle neighbor)
        {
            return indexOf(neighbor) >= 0;
        }
        
        int indexOf(Triangle neighbor)
        {
            for (int j = 0; j < 3; j++) {
                if(neighbors[j] == neighbor) return j;
            }
            return -1;
        }
        
        void replace(Triangle a, Triangle b)
        {
            neighbors[indexOf(a)] = b;
        }
    }
    
    public ArrayList<Integer> delaunay(ArrayList<Vector2D> vertices, ArrayList<Integer> indices)
    {
        ArrayList<Triangle> tris = toTris(indices, vertices);
        
        int numFlips = 0;
        for (int i = 0; i < tris.size(); i++) {
            Triangle tri = tris.get(i);
            for (int j = 0; j < 3; j++) {
                Triangle n = tri.neighbors[j];
                if(n != null && flipable(vertices, tri, n) && delaunayDir(vertices, tri, n))
                {
                    flip(tris, tri, n);
                    assert ++numFlips <= tris.size() * tris.size();
                    i = -1;
                    break;
                }
            }
        }
        
        return FromTris(tris);
    }
    
    private boolean flipable(ArrayList<Vector2D> vertices, Triangle a, Triangle b)
    {
        int aI = 0;
        while(b.hasCorner(a.corners[aI])) aI++;
        int bI = 0;
        while(a.hasCorner(b.corners[bI])) bI++;
        
        int c = a.corners[(aI + 1) % 3];
        int d = a.corners[(aI + 2) % 3];
        int p = a.corners[aI];
        int r = b.corners[bI];
        
        return Util.lineSegmentsIntersect(vertices.get(c), vertices.get(d), vertices.get(p), vertices.get(r));
    }
    
    private boolean delaunayDir(ArrayList<Vector2D> vertices, Triangle a, Triangle b)
    {
        int aI = 0;
        while(b.hasCorner(a.corners[aI])) aI++;
        return b.circumCircleContains(vertices, a.corners[aI]);
    }
    
    private void flip(ArrayList<Triangle> tris, Triangle a, Triangle b)
    {
        int aI = 0;
        while(b.hasCorner(a.corners[aI])) aI++;
        int bI = 0;
        while(a.hasCorner(b.corners[bI])) bI++;
        
        Triangle c = new Triangle(a.corners[aI], a.corners[(aI + 1) % 3], b.corners[bI]);
        
        Triangle n = a.neighbors[aI];
        if(n != null)
        {
            c.neighbors[0] = n;
            n.replace(a, c);
        }
        
        n = b.neighbors[(bI + 2) % 3];
        if(n != null)
        {
            c.neighbors[1] = n;
            n.replace(b, c);
        }
        
        
        Triangle d = new Triangle(b.corners[bI], b.corners[(bI + 1) % 3], a.corners[aI]);
        
        n = b.neighbors[bI];
        if(n != null)
        {
            d.neighbors[0] = n;
            n.replace(b, d);
        }
        
        n = a.neighbors[(aI + 2) % 3];
        if(n != null)
        {
            d.neighbors[1] = n;
            n.replace(a, d);
        }
        
        c.neighbors[2] = d;
        d.neighbors[2] = c;
        
        tris.remove(a);
        tris.remove(b);
        tris.add(c);
        tris.add(d);
    }
    
    private ArrayList<Triangle> toTris(ArrayList<Integer> indices, ArrayList<Vector2D> vertices)
    {
        ArrayList<Triangle> tris = new ArrayList<Triangle>();
        Dictionary<Integer, ArrayList<Triangle>> map = new Hashtable<Integer, ArrayList<Triangle>>();
        for (int i = 0; i < indices.size(); i += 3) {
            Triangle tri = new Triangle(indices.get(i), indices.get(i + 1), indices.get(i + 2));
            if(tri.area(vertices) < 0) tri.invert();
            tris.add(tri);
            getFromMap(map, indices.get(i)).add(tri);
            getFromMap(map, indices.get(i + 1)).add(tri);
            getFromMap(map, indices.get(i + 2)).add(tri);
        }
        for (Triangle tri : tris) {
            for (int i = 0; i < 3; i++) {
                int j = (i + 1) % 3;
                for (Triangle neighbor : getFromMap(map, tri.corners[i])) {
                    if(neighbor == tri) continue;
                    if(getFromMap(map, tri.corners[j]).contains(neighbor))
                    {
                        tri.neighbors[i] = neighbor;
                        break;
                    }
                }
            }
        }
        
        assert checkNeighbors(tris);
        
        return tris;
    }
    
    private boolean checkNeighbors(ArrayList<Triangle> tris)
    {
        for (Triangle tri : tris) {
            for (int i = 0; i < 3; i++) {
                Triangle n = tri.neighbors[i];
                if(n != null)
                {
                    if(n.indexOf(tri) == -1) throw new Error("invalid tris");
                }
            }
        }
        return true;
    }
    
    private ArrayList<Triangle> getFromMap(Dictionary<Integer, ArrayList<Triangle>> map, int key)
    {
        if(map.get(key) == null) map.put(key, new ArrayList<Triangle>());
        return map.get(key);
    }
    
    private ArrayList<Integer> FromTris(ArrayList<Triangle> tris)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (Triangle tri : tris)
        {
            for (int i = 0; i < 3; i++) {
                list.add(tri.corners[i]);
            }
        }
        return list;
    }
}