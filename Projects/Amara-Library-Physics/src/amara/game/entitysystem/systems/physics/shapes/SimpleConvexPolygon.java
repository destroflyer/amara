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
public class SimpleConvexPolygon extends ConvexShape {

    private Vector2D[] localPoints;
    private transient Vector2D[] globalPoints = null;
    private transient Circle boundCircle = null;
    private transient boolean cached = false;

    public SimpleConvexPolygon() {
    }

    public SimpleConvexPolygon(Vector2D... localPoints) {
        assert localPoints.length >= 2;
        if (Vector2DUtil.area(localPoints) < 0) {
            Util.reverse(localPoints);
        }
        assert Vector2DUtil.area(localPoints) >= 0;
        this.localPoints = localPoints;
    }

    private static Circle bouncingBubble(Vector2D... points) {
        Vector2D center = points[0];
        double radius = Util.Epsilon;
        double len, alphaSquared, alpha;
        Vector2D diff;

        for (int i = 0; i < 2; i++) {
            for (Vector2D p : points) {
                len = center.distance(p);
                if (len > radius) {
                    alpha = len / radius;
                    alphaSquared = alpha * alpha;
                    radius = 0.5 * (alpha + 1 / alpha) * radius;
                    center = center.mult(1 + 1 / alphaSquared).add(p.mult(1 - 1 / alphaSquared)).mult(0.5);
                }
            }
        }
        for (Vector2D p : points) {
            diff = p.sub(center);
            len = diff.length();
            if (len > radius) {
                radius = (radius + len) * 0.5;
                center = center.add(diff.mult((len - radius) / len));
            }
        }
        return new Circle(center, radius);
    }

    private static boolean boundCircleTest(Circle circle, Vector2D... localPoints) {
        for (Vector2D v : localPoints) {
            assert circle.getLocalPosition().distance(v) <= circle.getLocalRadius() + Util.Epsilon;
        }
        return true;
    }

    private static boolean optimalBoundCircleTest(Circle circle, Vector2D... localPoints) {
        Circle c = computeBoundCircle(localPoints);
        assert c.getLocalPosition().withinEpsilon(circle.getLocalPosition()) : c.getLocalPosition() + " / " + circle.getLocalPosition() + "\n\r" + c.getLocalRadius() + " / " + circle.getLocalRadius();
        assert Util.withinEpsilon(c.getLocalRadius() - circle.getLocalRadius()) : c.getLocalRadius() + " / " + circle.getLocalRadius();
        return true;
    }

    private static Circle computeBoundCircle(Vector2D... localPoints) {
        Vector2D center = null;
        double squaredRadius = Double.POSITIVE_INFINITY;
        for (Vector2D a : localPoints) {
            for (Vector2D b : localPoints) {
                if (a == b) {
                    continue;
                }
                for (Vector2D c : localPoints) {
                    if (a == c) {
                        continue;
                    }
                    Vector2D tmpCenter;
                    double tmpDistSquared;
                    if (b == c) {
                        tmpCenter = Vector2DUtil.avg(a, b);
                    } else {
                        tmpCenter = Vector2DUtil.circumCircleCenter(a, b, c);
                    }
                    tmpDistSquared = tmpCenter.squaredDistance(a);

                    boolean containsAll = true;
                    for (Vector2D v : localPoints) {
                        if (!(tmpCenter.squaredDistance(v) <= tmpDistSquared + Util.Epsilon)) {
                            containsAll = false;
                            break;
                        }
                    }

                    if (containsAll && tmpDistSquared < squaredRadius) {
                        center = tmpCenter;
                        squaredRadius = tmpDistSquared;
                    }
                }
            }
        }

        Circle circle = new Circle(center, Math.sqrt(squaredRadius));
        return circle;
    }

    public Circle getBoundCircle() {
        if (boundCircle == null) {
            boundCircle = bouncingBubble(localPoints);
            assert boundCircleTest(boundCircle, localPoints);
        }
        return boundCircle;
    }

    public Vector2D[] getGlobalPoints() {
        if (globalPoints == null) {
            globalPoints = new Vector2D[localPoints.length];
        }
        updateCache();
        return globalPoints;
    }

    public Vector2D[] getLocalPoints() {
        return localPoints;
    }

    @Override
    public void setTransform(Transform2D transform) {
        this.transform = transform;
        getBoundCircle().setTransform(transform);
        cached = false;
    }

    private void updateCache() {
        if (cached) {
            return;
        }
        cached = true;
        for (int i = 0; i < localPoints.length; i++) {
            getGlobalPoints()[i] = transform.transform(localPoints[i]);
        }
    }

    @Override
    public boolean contains(Vector2D point) {
        updateCache();
        boolean inside = false;
        int i, j;
        for (i = getGlobalPoints().length - 1, j = 0; j < getGlobalPoints().length; i = j++) {
            if (((getGlobalPoints()[j].getY() > point.getY()) != (getGlobalPoints()[i].getY() > point.getY()))
                    && (point.getX() < (getGlobalPoints()[i].getX() - getGlobalPoints()[j].getX()) * (point.getY() - getGlobalPoints()[j].getY()) / (getGlobalPoints()[i].getY() - getGlobalPoints()[j].getY()) + getGlobalPoints()[j].getX())) {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    public void draw(ShapeGraphics graphics, boolean global) {
        if (global) {
            updateCache();
            graphics.drawPolygon(getGlobalPoints());
        } else {
            graphics.drawPolygon(localPoints);
        }
    }

    @Override
    public void fill(ShapeGraphics graphics, boolean global) {
        if (global) {
            updateCache();
            graphics.fillPolygon(getGlobalPoints());
        } else {
            graphics.fillPolygon(localPoints);
        }
    }

    @Override
    public SimpleConvexPolygon clone() {
        SimpleConvexPolygon c = new SimpleConvexPolygon(localPoints);
        c.setTransform(transform);
        return c;
    }

    @Override
    public Vector2D calcLocalCentroid() {
        if (localPoints.length <= 2) {
            return Vector2DUtil.avg(localPoints);
        }
        double area = calcLocalArea();
        assert 0 < area;
        double x = 0;
        double y = 0;
        for (int i = 0; i < localPoints.length; i++) {
            int j = (i + 1) % localPoints.length;
            Vector2D a = localPoints[i];
            Vector2D b = localPoints[j];
            double f = (a.getX() * b.getY() - b.getX() * a.getY());
            x += (a.getX() + b.getX()) * f;
            y += (a.getY() + b.getY()) * f;
        }
        area *= 6;
        return new Vector2D(x / area, y / area);
    }

    @Override
    public double calcLocalArea() {
        double area = 0d;
        for (int i = 0; i < localPoints.length; i++) {
            int j = (i + 1) % localPoints.length;

            area += (localPoints[i].getX() - localPoints[j].getX()) * (localPoints[i].getY() + localPoints[j].getY());
        }
        area /= 2;
        assert 0 <= area;
        return area;
    }
}