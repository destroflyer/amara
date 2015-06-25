/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class PointShape extends ConvexShape {
    private Vector2D localPoint, globalPoint;
    private Circle boundCircle;
    private boolean cached = false;

    public PointShape() {
        this(Vector2D.Zero);
    }

    public PointShape(Vector2D localPoint) {
        this.localPoint = localPoint;
        boundCircle = new Circle(localPoint, 0);
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
        boundCircle.setTransform(transform);
        cached = false;
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
        if(cached) return;
        globalPoint = transform.transform(localPoint);
        cached = true;
    }

    public Vector2D getLocalPoint() {
        return localPoint;
    }

    public Vector2D getGlobalPoint() {
        updateCache();
        return globalPoint;
    }
    
}
