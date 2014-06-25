/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
public class Triangle
{
    private Point2D[] points;
    private Triangle[] neighbors = new Triangle[3];
    
    public Triangle(Point2D a, Point2D b, Point2D c)
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
    public Triangle[] getNeighbors()
    {
        return neighbors;
    }

    public boolean tryNeighbor(Triangle tri)
    {
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            
            for (int k = 0; k < 3; k++)
            {
                int l = (k + 1) % 3;
                
                if(points[i].equals(tri.points[l]) && points[j].equals(tri.points[k]))
                {
                    assert neighbors[i] == null;
                    assert tri.neighbors[k] == null;
                    neighbors[i] = tri;
                    tri.neighbors[k] = this;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCorner(Point2D p)
    {
        return indexOf(p) != -1;
    }

    public int indexOf(Point2D p)
    {
        for (int i = 0; i < 3; i++)
        {
            if(p.equals(points[i])) return i;
        }
        return -1;
    }

    public int indexOf(Triangle neighbor)
    {
        for (int i = 0; i < 3; i++)
        {
            if (neighbors[i] == neighbor) return i;
        }
        return -1;
    }

    public void replace(Triangle oldNeighbor, Triangle newNeighbor)
    {
        neighbors[indexOf(oldNeighbor)] = newNeighbor;
    }

    public boolean areaContains(Point2D p)
    {
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            if (Point2DUtil.lineSide(p, points[i], points[j]) >= 0) return false;
        }
        return true;
    }
    public boolean areaContainsOrBorder(Point2D p)
    {
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            if (Point2DUtil.lineSide(p, points[i], points[j]) > 0) return false;
        }
        return true;
    }

    public boolean intersectsLineSegment(Point2D a, Point2D b)
    {
        boolean seperated;
        double side = Point2DUtil.lineSide(points[0], a, b);
        if (side == 0)
        {
            seperated = Point2DUtil.lineSide(points[1], a, b) * Point2DUtil.lineSide(points[2], a, b) < 0;
        }
        else
        {
            seperated = side * Point2DUtil.lineSide(points[1], a, b) < 0 ||
                        side * Point2DUtil.lineSide(points[2], a, b) < 0;
        }
        if (seperated) return false;

        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            int k = (j + 1) % 3;
            side = Point2DUtil.lineSide(points[k], points[i], points[j]);
            seperated = side * Point2DUtil.lineSide(a, points[i], points[j]) < 0 ||
                        side * Point2DUtil.lineSide(b, points[i], points[j]) < 0;
            if (seperated) return false;
        }
        return true;
    }

    public double area()
    {
        double area = 0d;
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;

            area += (points[i].getX() - points[j].getX()) * (points[i].getY() + points[j].getY());
        }
        area /= 2;
        return area;
    }

    public Point2D center()
    {
        return Point2DUtil.avg(points);
    }
}
