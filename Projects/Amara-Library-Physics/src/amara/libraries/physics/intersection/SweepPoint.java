/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.intersection;

/**
 *
 * @author Philipp
 */
public class SweepPoint<Hitbox extends BoundAabb>
{
    private boolean isStart;
    private Hitbox hitbox;

    public SweepPoint(Hitbox hitbox, boolean isStart)
    {
        this.isStart = isStart;
        this.hitbox = hitbox;
    }

    public double getValueX()
    {
        if(isStart) return hitbox.getMinX();
        return hitbox.getMaxX();
    }
    public double getValueY()
    {
        if(isStart) return hitbox.getMinY();
        return hitbox.getMaxY();
    }
    
    public boolean isStart()
    {
        return isStart;
    }

    public Hitbox getHitbox()
    {
        return hitbox;
    }
}
