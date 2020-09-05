/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.physics;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RemoveCollisionGroupsComponent{

    public RemoveCollisionGroupsComponent(){
        
    }

    public RemoveCollisionGroupsComponent(long targetOf, long targets){
        this.targetOf = targetOf;
        this.targets = targets;
    }
    private long targetOf;
    private long targets;

    public long getTargetOf(){
        return targetOf;
    }

    public long getTargets(){
        return targets;
    }
}
