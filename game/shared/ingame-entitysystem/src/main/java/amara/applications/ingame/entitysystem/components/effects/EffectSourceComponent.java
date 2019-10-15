/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class EffectSourceComponent {

    public EffectSourceComponent(){

    }

    public EffectSourceComponent(int sourceEntity){
        this.sourceEntity = sourceEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int sourceEntity;

    public int getSourceEntity(){
        return sourceEntity;
    }
}
