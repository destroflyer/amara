/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.Serializable;
import java.awt.Graphics;

/**
 *
 * @author Philipp
 */
@Serializable
public class Circle extends Shape {

    public Circle() {
        
    }
    
    public Circle(double x, double y, double r) {
        transform.setPosition(x, y);
        baseBoundRadius = r;
    }
    public Circle(double r) {
        baseBoundRadius = r;
    }

    @Override
    public void draw(Graphics graphics) {
        drawBoundCircle(graphics);
    }

    @Override
    public void fill(Graphics graphics) {
        fillBoundCircle(graphics);
    }

    @Override
    public boolean contains(Vector2D point) {
        return circlesIntersect(getX(), getY(), getBoundRadius(), point.getX(), point.getY(), 0);
    }
    
    @Override
    public boolean intersects(Shape s) {
        return s.intersects(this);
    }

    @Override
    public boolean intersects(SimpleConvex c) {
        return c.intersects(this);
    }

    @Override
    public boolean intersects(Circle c) {
        return circlesIntersect(getX(), getY(), getBoundRadius(), c.getX(), c.getY(), c.getBoundRadius());
    }

    @Override
    public Vector2D getResolveVector(Shape s) {
        Vector2D overlap = s.getResolveVector(this);
        overlap.invert();
        return overlap;
    }

    @Override
    public Vector2D getResolveVector(SimpleConvex c) {
        Vector2D overlap = c.getResolveVector(this);
        overlap.invert();
        return overlap;
    }

    @Override
    public Vector2D getResolveVector(Circle c) {
        Vector2D overlap = new Vector2D(c.getX() - getX(), c.getY() - getY());
        if(overlap.isZero())
        {
            overlap.addX(-getBoundRadius() - c.getBoundRadius());
        }
        else overlap.addLength(-getBoundRadius() - c.getBoundRadius());
        return overlap;
    }

    @Override
    public Bounds getScalarProjectOnto(Vector2D vec) {
        double center = new Vector2D(getX(), getY()).getScalarProjectOnto(vec);
        double fac = getBoundRadius() / vec.length();
        return new Bounds(center - fac, center + fac);
    }

    @Override
    public Circle clone() {
        Circle clone = new Circle(baseBoundRadius);
        clone.transform = transform.clone();
        return clone;
    }
    
    @Override
    public void updateTransform() {
        
    }
    
}
