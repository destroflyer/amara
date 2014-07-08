/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
public class TriangleNode
{
    private Point2D[] points;
    private TriangleNode[] neighbors = new TriangleNode[3];
    private double[] cornerWidth = new double[3];
    
    TriangleNode(Point2D a, Point2D b, Point2D c)
    {
        assert !a.equals(b);
        assert !a.equals(c);
        assert !b.equals(c);
        points = new Point2D[]{a, b, c};
    }

    public Point2D[] getPoints()
    {
        return points;
    }
    
    public Point2D point(int i)
    {
        return points[i];
    }
    public TriangleNode neighbor(int i)
    {
        return neighbors[i];
    }

    public double getCornerWidth(int i) {
        return cornerWidth[i];
    }

    public void setCornerWidth(int i, double edgeWidth) {
        this.cornerWidth[i] = edgeWidth;
    }
    
    public int indexOf(Point2D p)
    {
        for (int i = 0; i < 3; i++)
        {
            if(p.equals(points[i])) return i;
        }
        return -1;
    }

    public int indexOf(TriangleNode neighbor)
    {
        for (int i = 0; i < 3; i++)
        {
            if (neighbor.equals(neighbors[i])) return i;
        }
        return -1;
    }
    
    public boolean tryNeighbor(TriangleNode tri)
    {
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            int k = tri.indexOf(points[j]);
            if(k == -1 || tri.indexOf(points[i]) == -1) continue;
            assert neighbors[i] == null;
            assert tri.neighbors[k] == null;
            neighbors[i] = tri;
            tri.neighbors[k] = this;
            return true;
        }
        return false;
    }
    
    public boolean areaContains(Point2D p)
    {
//        int c = 0;
//        int i, j;
//        for (i = 2, j = 0; j < 3; i = j++)
//        {
//            if (((points[j].getY() > p.getY()) != (points[i].getY() > p.getY()))
//                && (p.getX() < (points[i].getX() - points[j].getX()) * (p.getY() - points[j].getY()) / (points[i].getY() - points[j].getY()) + points[j].getX()))
//            {
//                c++;
//            }
//        }
//        return (c & 1) != 0;
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            if (Point2DUtil.lineSide(p, points[i], points[j]) > Util.Epsilon) return false;
        }
        return true;
    }
    
    public Point2D center()
    {
        return Point2DUtil.avg(points);
    }
    
    public int numNeighbors()
    {
        int num = 0;
        for (int i = 0; i < 3; i++)
        {
            if(neighbors[i] != null) num++;
        }
        return num;
    }
}
