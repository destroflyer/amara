/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import java.awt.Graphics;

/**
 * basic shape class
 * all shapes need to extend this class
 * @author Philipp
 */
public abstract class Shape {

    /**
     *
     * @param x1 x of the first circles center
     * @param y1 y of the first circles center
     * @param r1 radius of the first circles center
     * @param x2 x of the second circles center
     * @param y2 y of the second circles center
     * @param r2 radius of the second circles center
     * @return
     */
    protected static boolean circlesIntersect(double x1, double y1, double r1, double x2, double y2, double r2) {
        x1 -= x2;
        y1 -= y2;
        r1 += r2;
        return r1 * r1 > x1 * x1 + y1 * y1;
    }
    protected final boolean boundCirclesOverlap(Shape s) {
        return circlesIntersect(getX(), getY(), getBoundRadius(), s.getX(), s.getY(), s.getBoundRadius());
    }
    
    public abstract boolean contains(Vector2D point);
    
    public abstract boolean intersects(Shape s);
    public abstract boolean intersects(SimpleConvex c);
    public abstract boolean intersects(Circle c);
    /** Returns the Vector2D this shape needs to be pushed to solve intersection
     *
     * @param shape
     * @return
     */
    public abstract Vector2D getResolveVector(Shape shape);
    /** Returns the Vector2D this shape needs to be pushed to solve intersection
     *
     * @param convex
     * @return
     */
    public abstract Vector2D getResolveVector(SimpleConvex convex);
    /** Returns the Vector2D this shape needs to be pushed to solve intersection
     *
     * @param circle
     * @return
     */
    public abstract Vector2D getResolveVector(Circle circle);
    
    public double getPenetration(Shape s, Vector2D unitAxis) {
//        axis = axis.getUnit();
        return getScalarProjectOnto(unitAxis).getOverlap(s.getScalarProjectOnto(unitAxis));
    }
    
    public boolean intersectOnAxis(Shape s, Vector2D speed, Vector2D axis) {
        double speedProjection = speed.getScalarProjectOnto(axis);
        Bounds bounds = getScalarProjectOnto(axis);
        bounds.add(speedProjection);
        return bounds.intersects(s.getScalarProjectOnto(axis));
    }
    
    public double getIntersectionTime(Shape s, Vector2D speed, Vector2D unitAxis) {
        return getScalarProjectOnto(unitAxis).getIntersectionTime(speed.getScalarProjectOnto(unitAxis), s.getScalarProjectOnto(unitAxis));
    }
    
    public Bounds getIntersectionBounds(Shape s, Vector2D speed, Vector2D unitAxis) {
        return getScalarProjectOnto(unitAxis).getIntersectionBounds(speed.getScalarProjectOnto(unitAxis), s.getScalarProjectOnto(unitAxis));
    }
    
//    public double getBaseBoundRadius() {
//        return baseBoundRadius;
//    }
    
    
    protected final void updateShape() {
        if(transform.hasChanged()) {
            updateTransform();
            transform.setUnChanged();
        }
    }
    protected abstract void updateTransform();
    
    public final double getBoundRadius() {
        return transform.scaleValue(baseBoundRadius);
    }

    public final double getX() {
        return transform.getX();
    }
    public final double getY() {
        return transform.getY();
    }
    public final void setX(double x) {
        transform.setX(x);
    }
    public final void setY(double y) {
        transform.setY(y);
    }

    public double getMinX() {
        return getX() - getBoundRadius();
    }
    public double getMaxX() {
        return getX() + getBoundRadius();
    }
    public double getMinY() {
        return getY() - getBoundRadius();
    }
    public double getMaxY() {
        return getY() + getBoundRadius();
    }
    
    /** returns a Vector2D, where x is the minimum and y is the maximum factor
     * to multiply vec with, the Shape is projected to the Vector
     * from minFactor * vec to maxFactor * vec
     *
     * @param axis
     * @return
     */
    public abstract Bounds getScalarProjectOnto(Vector2D axis);
    
    public final Transform getTransform() {
        return transform;
    }
    
    public void draw(Graphics graphics) {
        
    }
    public void fill(Graphics graphics) {
        
    }
    
    public void drawBoundCircle(Graphics graphics) {
        int radius = Math.abs((int)getBoundRadius());
        graphics.drawOval((int)transform.getX() - radius, (int)transform.getY() - radius, 2 * radius, 2 * radius);
    }
    public void fillBoundCircle(Graphics graphics) {
        int radius = Math.abs((int)getBoundRadius());
        graphics.fillOval((int)transform.getX() - radius, (int)transform.getY() - radius, 2 * radius, 2 * radius);
    }
    public void drawBoundAABB(Graphics graphics) {
        graphics.drawRect((int)getMinX(), (int)getMinY(), (int)(getMaxX() - getMinX()), (int)(getMaxY() - getMinY()));
    }
    public void fillBoundAABB(Graphics graphics) {
        graphics.fillRect((int)getMinX(), (int)getMinY(), (int)(getMaxX() - getMinX()), (int)(getMaxY() - getMinY()));
    }
    
    @Override
    public abstract Shape clone();
    
    protected double baseBoundRadius;
    protected Transform transform = new Transform();
}
