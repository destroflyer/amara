/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ContinuousAttributesPerStackComponent{

    public ContinuousAttributesPerStackComponent(){
        
    }
    
    public ContinuousAttributesPerStackComponent(int bonusAttributesEntity){
        this.bonusAttributesEntity = bonusAttributesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int bonusAttributesEntity;

    public int getBonusAttributesEntity(){
        return bonusAttributesEntity;
    }
}
