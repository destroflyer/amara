/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

/**
 *
 * @author Philipp
 */
public class AutoAggroComponent
{
    private float range;

    public AutoAggroComponent(float range) {
        this.range = range;
    }

    public float getRange() {
        return range;
    }
}
