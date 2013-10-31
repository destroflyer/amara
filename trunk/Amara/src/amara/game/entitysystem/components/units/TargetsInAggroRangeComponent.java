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
public class TargetsInAggroRangeComponent
{
    private int[] targets;

    public TargetsInAggroRangeComponent() {
        
    }
    
    public TargetsInAggroRangeComponent(int[] targets) {
        this.targets = targets;
    }

    public int[] getTargets() {
        return targets;
    }
}
