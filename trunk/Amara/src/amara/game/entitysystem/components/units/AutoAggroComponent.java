/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class AutoAggroComponent
{
    private float range;

    public AutoAggroComponent() {
        
    }
    
    public AutoAggroComponent(float range) {
        this.range = range;
    }

    public float getRange() {
        return range;
    }
}
