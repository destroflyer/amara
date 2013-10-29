/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersection;

/**
 *
 * @author Philipp
 */
public class SweepPoint<Hitbox extends HasShape> {

    public SweepPoint(Hitbox hitbox, boolean isStart) {
        this.isStart = isStart;
        this.hitbox = hitbox;
    }

    public double getValueX() {
        if(isStart) return hitbox.getShape().getMinX();
        return hitbox.getShape().getMaxX();
    }
    public double getValueY() {
        if(isStart) return hitbox.getShape().getMinY();
        return hitbox.getShape().getMaxY();
    }
    
    public boolean isStart() {
        return isStart;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
    
    protected boolean isStart;
    protected Hitbox hitbox;
}
