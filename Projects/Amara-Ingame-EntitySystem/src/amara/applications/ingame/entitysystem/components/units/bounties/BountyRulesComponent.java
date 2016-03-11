/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.bounties;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class BountyRulesComponent{

    public BountyRulesComponent(){
        
    }

    public BountyRulesComponent(int targetRulesEntity){
        this.targetRulesEntity = targetRulesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetRulesEntity;

    public int getTargetRulesEntity(){
        return targetRulesEntity;
    }
}
