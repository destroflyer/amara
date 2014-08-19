/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.*;

/**
 *
 * @author Philipp
 */
@Serializable
public class SimpleConvexPolygon extends ConvexShape
{
    private Vector2D[] localPoints, globalPoints;
    private Circle boundCircle;
    private boolean cached = false;

    public SimpleConvexPolygon()
    {
    }

    public SimpleConvexPolygon(Vector2D... localPoints)
    {
        assert localPoints.length >= 2;
        if(Vector2DUtil.area(localPoints) < 0) Util.reverse(localPoints);
        assert Vector2DUtil.area(localPoints) >= 0;
        this.localPoints = localPoints;
        globalPoints = new Vector2D[localPoints.length];
        boundCircle = bouncingBubble(localPoints);
        assert boundCircleTest(boundCircle, localPoints);
    }
    
    private static Circle bouncingBubble(Vector2D... points)
    {
        Vector2D center = points[0];
        double radius = Util.Epsilon;
        double len, alphaSquared, alpha;
        Vector2D diff;
        
        for (int i = 0; i < 2; i++)
        {
            for (Vector2D p : points)
            {
                len = center.distance(p);
                if(len > radius)
                {
                    alpha = len / radius;
                    alphaSquared = alpha * alpha;
                    radius = 0.5 * (alpha + 1 / alpha) * radius;
                    center = center.mult(1 + 1 / alphaSquared).add(p.mult(1 - 1 / alphaSquared)).mult(0.5);
                }
            }
        }
        for (Vector2D p : points)
        {
            diff = p.sub(center);
            len = diff.length();
            if(len > radius)
            {
                radius = (radius + len) * 0.5;
                center = center.add(diff.mult((len - radius) / len));
            }
        }
        return new Circle(center, radius);
    }
    
    private static boolean boundCircleTest(Circle circle, Vector2D... localPoints)
    {
        for (Vector2D v : localPoints)
        {
            assert circle.getLocalPosition().distance(v) <= circle.getLocalRadius() + Util.Epsilon;
        }
        return true;
    }
    private static boolean optimalBoundCircleTest(Circle circle, Vector2D... localPoints)
    {
        Circle c = computeBoundCircle(localPoints);
        assert c.getLocalPosition().withinEpsilon(circle.getLocalPosition()): c.getLocalPosition() + " / " + circle.getLocalPosition() + "\n\r" + c.getLocalRadius()+ " / " + circle.getLocalRadius();
        assert Util.withinEpsilon(c.getLocalRadius() - circle.getLocalRadius()): c.getLocalRadius()+ " / " + circle.getLocalRadius();
        return true;
    }
    private static Circle computeBoundCircle(Vector2D... localPoints)
    {
        Vector2D center = null;
        double squaredRadius = Double.POSITIVE_INFINITY;
        for (Vector2D a : localPoints)
        {
            for (Vector2D b : localPoints)
            {
                if(a == b) continue;
                for (Vector2D c : localPoints)
                {
                    if(a == c) continue;
                    Vector2D tmpCenter;
                    double tmpDistSquared;
                    if(b == c) tmpCenter = Vector2DUtil.avg(a, b);
                    else tmpCenter = Vector2DUtil.circumCircleCenter(a, b, c);
                    tmpDistSquared = tmpCenter.squaredDistance(a);
                    
                    boolean containsAll = true;
                    for (Vector2D v : localPoints)
                    {
                        if(!(tmpCenter.squaredDistance(v) <= tmpDistSquared + Util.Epsilon))
                        {
                            containsAll = false;
                            break;
                        }
                    }
                    
                    if(containsAll && tmpDistSquared < squaredRadius)
                    {
                        center = tmpCenter;
                        squaredRadius = tmpDistSquared;
                    }
                }
            }
        }
        
        Circle circle = new Circle(center, Math.sqrt(squaredRadius));
        return circle;
    }
    public Circle getBoundCircle()
    {
        return boundCircle;
    }

    public Vector2D[] getLocalPoints()
    {
        return localPoints;
    }

    public Vector2D[] getGlobalPoints()
    {
        updateCache();
        return globalPoints;
    }

    @Override
    public void setTransform(Transform2D transform)
    {
        this.transform = transform;
        boundCircle.setTransform(transform);
        cached = false;
    }
    
    private void updateCache()
    {
        if(cached) return;
        for (int i = 0; i < localPoints.length; i++)
        {
            globalPoints[i] = transform.transform(localPoints[i]);
        }
        cached = true;
    }

    @Override
    public boolean contains(Vector2D point)
    {
        updateCache();
        boolean inside = false;
        int i, j;
        for (i = globalPoints.length - 1, j = 0; j < globalPoints.length; i = j++)
        {
            if (((globalPoints[j].getY() > point.getY()) != (globalPoints[i].getY() > point.getY()))
                && (point.getX() < (globalPoints[i].getX() - globalPoints[j].getX()) * (point.getY() - globalPoints[j].getY()) / (globalPoints[i].getY() - globalPoints[j].getY()) + globalPoints[j].getX()))
            {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    public void draw(ShapeGraphics graphics, boolean global)
    {
        if(global)
        {
            updateCache();
            graphics.drawPolygon(globalPoints);
        }
        else graphics.drawPolygon(localPoints);
    }

    @Override
    public void fill(ShapeGraphics graphics, boolean global)
    {
        if(global)
        {
            updateCache();
            graphics.fillPolygon(globalPoints);
        }
        else graphics.fillPolygon(localPoints);
    }

    @Override
    public SimpleConvexPolygon clone()
    {
        SimpleConvexPolygon c = new SimpleConvexPolygon(localPoints);
        c.setTransform(transform);
        return c;
    }
    
}