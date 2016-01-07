/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class IntersectionRulesComponent{

    public IntersectionRulesComponent(){
        
    }

    public IntersectionRulesComponent(int targetRulesEntity){
        this.targetRulesEntity = targetRulesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetRulesEntity;

    public int getTargetRulesEntity(){
        return targetRulesEntity;
    }
}
