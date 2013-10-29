/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

/**
 *
 * @author Philipp
 */
public class Bounds {

    public Bounds(double min, double max) {
        if(min < max) {
            this.min = min;
            this.max = max;
        } else {
            this.min = max;
            this.max = min;
        }
    }

    public void translate(double delta) {
        min += delta;
        max += delta;
    }
    
    public void addNormedBounds(Bounds b) {
        min += b.getMin();
        max += b.getMax();
    }
    
    public void add(double delta) {
        if(delta > 0) max += delta;
        else min += delta;
    }
    public void sub(double delta) {
        if(delta > 0) max -= delta;
        else min -= delta;
    }
    
    public boolean intersects(Bounds bounds) {
        return getMin() < bounds.getMax() && bounds.getMin() < getMax();
    }
    
    public double getIntersectionTime(double speed, Bounds bounds) {
        if(speed > 0) return (bounds.getMin() - max) / speed;
        return (bounds.getMax() - min) / speed;
    }
    public Bounds getIntersectionBounds(double speed, Bounds bounds) {
        return new Bounds((bounds.getMin() - max) / speed, (bounds.getMax() - min) / speed);
    }
    
    public double getOverlap(Bounds bounds) {
        double a = bounds.getMax() - getMin();
        double b = bounds.getMin() - getMax();
        if(Math.abs(a) < Math.abs(b)) return a;
        return b;
    }
    
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
    
    double min, max;
}
