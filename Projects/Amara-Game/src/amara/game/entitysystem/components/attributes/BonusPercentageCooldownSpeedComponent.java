/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.attributes;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class BonusPercentageCooldownSpeedComponent{

    public BonusPercentageCooldownSpeedComponent(){
        
    }
    
    public BonusPercentageCooldownSpeedComponent(float value){
        this.value = value;
    }
    @ComponentField(type=ComponentField.Type.ATTRIBUTE)
    private float value;

    public float getValue(){
        return value;
    }
}
