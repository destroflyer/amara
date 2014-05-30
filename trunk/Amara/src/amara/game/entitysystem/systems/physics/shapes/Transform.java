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
public class Transform {

    public Transform(){
        
    }
    
    public double getRadian() {
        return radian;
    }
    public void setRadian(double radian) {
        this.radian = radian;
        changed = true;
    }
    public double getDegree() {
        return Math.toDegrees(getRadian());
    }
    public void setDegree(double degree) {
        setRadian(Math.toRadians(degree));
    }
    
    public double getScale() {
        return scale;
    }
    public void setScale(double scale) {
        this.scale = scale;
        changed = true;
    }
    public double scaleValue(double value) {
        return value * scale;
    }
    
    protected void apply(Vector2D vector) {
        vector.scale(scale);
        vector.rotateByRadian(radian);
        vector.add(x, y);
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
        changed = true;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
        changed = true;
    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        changed = true;
    }
    
    public void translate(double x, double y) {
        setPosition(this.x + x, this.y + y);
    }
    public void translate(Vector2D vector) {
        translate(vector.getX(), vector.getY());
    }
    public void rotateRadian(double radian) {
        setRadian(this.radian + radian);
    }
    public void rotateDegree(double degree) {
        setDegree(getDegree() + degree);
    }

    boolean hasChanged() {
        return changed;
    }
    void resetChanged() {
        changed = false;
    }
    
    @Override
    public Transform clone() {
        Transform c = new Transform();
        c.setX(x);
        c.setY(y);
        c.setScale(scale);
        c.setRadian(radian);
        return c;
    }
    
    protected boolean changed = true;
    protected double x, y, radian, scale = 1;
}
