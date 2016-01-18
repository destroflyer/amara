/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.heals;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ResultingHealComponent{

    public ResultingHealComponent(){
        
    }

    public ResultingHealComponent(float value){
        this.value = value;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private float value;

    public float getValue(){
        return value;
    }
}
