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
public class Vector2D {
    public Vector2D() {
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public final double getX() {
        return x;
    }
    public final double getY() {
        return y;
    }

    public final void setX(double x) {
        this.x = x;
    }
    public final void setY(double y) {
        this.y = y;
    }
    
    public final void set(Vector2D vector) {
        set(vector.getX(), vector.getY());
    }
    public final void set(double x, double y) {
        setX(x);
        setY(y);
    }
    
    public final double squareLength() {
        return x * x + y * y;
    }
    public final double length() {
        return Math.sqrt(squareLength());
    }
    
    public final void setLength(double length) {
        if(isZero()) throw new Error("Can not resize zero-vector.");
        scale(length / length());
    }
    
    public final void add(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
    }
    public final void addX(double deltaX) {
        x += deltaX;
    }
    public final void addY(double deltaY) {
        y += deltaY;
    }
    public final void addLength(double deltaLength) {
        if(isZero()) throw new Error("Can not resize zero-vector.");
        double length = length();
        scale((length + deltaLength) / length);
    }
    public final Vector2D getScaled(double factor) {
        Vector2D scaled = clone();
        scaled.scale(factor);
        return scaled;
    }
    public final void scale(double factor) {
        scaleX(factor);
        scaleY(factor);
    }
    public final void scaleX(double factorX) {
        x *= factorX;
    }
    public final void scaleY(double factorY) {
        y *= factorY;
    }
    public final Vector2D getRotatedbyRadian(double radian) {
        Vector2D rotated = clone();
        rotated.rotateByRadian(radian);
        return rotated;
    }
    public final Vector2D getRotatedbyDegree(double degree) {
        return getRotatedbyRadian(Math.toRadians(degree));
    }
    public final void rotateByRadian(double radian) {
        double sin = Math.sin(radian);
        double cos = Math.cos(radian);
        set(x * cos - y * sin, x * sin + y * cos);
    }
    public final void rotateByDegree(double degree) {
        rotateByRadian(Math.toRadians(degree));
    }
    
    public final void add(Vector2D vector) {
        add(vector.getX(), vector.getY());
    }
    public final void sub(Vector2D vector) {
        add(-vector.getX(), -vector.getY());
    }
    
    public final void invert() {
        x = -x;
        y = -y;
    }
    public final Vector2D getInverse() {
        Vector2D vec = clone();
        vec.invert();
        return vec;
    }
    
    static double dotProduct(Vector2D v1, Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }
    static double crossProductZ(Vector2D v1, Vector2D v2) {
        return v1.getX() * v2.getY() - v1.getY() * v2.getX();
    }
    public static Vector2D sum(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() + b.getX(), a.getY() + b.getY());
    }
    public static Vector2D difference(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() - b.getX(), a.getY() - b.getY());
    }
    public static Vector2D average(Vector2D... vecs) {
        Vector2D average = new Vector2D();
        for(Vector2D vec: vecs) {
            average.add(vec);
        }
        average.scale(1 / (double)vecs.length);
        return average;
    }
    
    public final double getScalarProjectOnto(Vector2D vec) {
        return dotProduct(this, vec) / vec.length();
    }
    
    public final void projectOnto(Vector2D vector) {
        projectOntoUnit(vector.getUnit());
    }
    private void projectOntoUnit(Vector2D unitVector) {
        y = dotProduct(this, unitVector);
        x = y * unitVector.getX();
        y *= unitVector.getY();
    }
    public final Vector2D getProjectedOnto(Vector2D vector) {
        Vector2D vec = clone();
        vec.projectOnto(vector);
        return vec;
    }
    
    public final void toLeftHand() {
        double t = x;
        x = -y;
        y = t;
    }
    public final Vector2D getLeftHand() {
        Vector2D vec = clone();
        vec.toLeftHand();
        return vec;
    }
    public final void toRightHand() {
        double t = x;
        x = y;
        y = -t;
    }
    public final Vector2D getRightHand() {
        Vector2D vec = clone();
        vec.toRightHand();
        return vec;
    }
    
    public final void toUnit() {
        scale(1 / length());
    }
    public final Vector2D getUnit() {
        Vector2D vec = clone();
        vec.toUnit();
        return vec;
    }
    
    public final boolean isZero() {
        return x == 0 && y == 0;
    }
    
    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector2D) {
            Vector2D vec = (Vector2D)obj;
            return (x == vec.getX()) && (y == vec.getY());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
        
    }
    
    protected double x, y;
}