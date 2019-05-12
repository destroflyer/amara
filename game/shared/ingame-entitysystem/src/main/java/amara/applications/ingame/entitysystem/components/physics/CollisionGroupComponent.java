/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.physics;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class CollisionGroupComponent{
    
    public CollisionGroupComponent(){
        
    }

    public CollisionGroupComponent(long targetOf, long targets){
        this.targetOf = targetOf;
        this.targets = targets;
    }
    public static final long NONE = 0x0;
    public static final long MAP = 0x1;
    public static final long UNITS = 0x2;
    public static final long SPELLS = 0x4;
    public static final long SPELL_TARGETS = 0x8;
    private long targetOf;
    private long targets;

    public long getTargetOf(){
        return targetOf;
    }

    public long getTargets(){
        return targets;
    }
    
    public static boolean isColliding(long targetOf, long targets){
        return ((targetOf & targets) != 0);
    }
}
