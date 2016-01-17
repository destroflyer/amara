/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class PointShape extends ConvexShape {
    private Vector2D localPoint;
    private transient Circle boundCircle = null;
    private transient Vector2D globalPoint = null;

    public PointShape() {
        this(Vector2D.Zero);
    }

    public PointShape(Vector2D localPoint) {
        this.localPoint = localPoint;
    }
    
    @Override
    public Vector2D calcLocalCentroid() {
        return localPoint;
    }

    @Override
    public double calcLocalArea() {
        return 0;
    }

    @Override
    public Circle getBoundCircle() {
        if(boundCircle == null) {
            boundCircle = new Circle(localPoint, 0);
        }
        return boundCircle;
    }

    @Override
    public PointShape clone() {
        PointShape s = new PointShape(localPoint);
        s.setTransform(transform);
        return s;
    }

    @Override
    public void setTransform(Transform2D transform) {
        this.transform = transform;
        getBoundCircle().setTransform(transform);
        globalPoint = null;
    }

    @Override
    public boolean contains(Vector2D point) {
        return false;
    }

    @Override
    public void draw(ShapeGraphics graphics, boolean global) {
        if(global) {
            updateCache();
            graphics.drawPoint(globalPoint);
        }
        graphics.drawPoint(localPoint);
    }

    @Override
    public void fill(ShapeGraphics graphics, boolean global) {
        draw(graphics, global);
    }
    
    private void updateCache() {
        if(globalPoint != null) return;
        globalPoint = transform.transform(localPoint);
    }

    public Vector2D getLocalPoint() {
        return localPoint;
    }

    public Vector2D getGlobalPoint() {
        updateCache();
        return globalPoint;
    }
    
}
