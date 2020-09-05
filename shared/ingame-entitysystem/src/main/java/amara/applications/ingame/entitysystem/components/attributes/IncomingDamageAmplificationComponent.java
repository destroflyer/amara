/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.attributes;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class IncomingDamageAmplificationComponent{

    public IncomingDamageAmplificationComponent(){
        
    }
    
    public IncomingDamageAmplificationComponent(float value){
        this.value = value;
    }
    @ComponentField(type=ComponentField.Type.ATTRIBUTE)
    private float value;

    public float getValue(){
        return value;
    }
}
