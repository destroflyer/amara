/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AffectedTargetsComponent{

    public AffectedTargetsComponent(){
        
    }
    
    public AffectedTargetsComponent(int... targetEntities){
        this.targetEntities = targetEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] targetEntities;

    public int[] getTargetEntities(){
        return targetEntities;
    }
}
