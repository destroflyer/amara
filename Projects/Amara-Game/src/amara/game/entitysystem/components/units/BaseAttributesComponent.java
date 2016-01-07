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
public class BaseAttributesComponent{

    public BaseAttributesComponent(){
        
    }
    
    public BaseAttributesComponent(int bonusAttributesEntity){
        this.bonusAttributesEntity = bonusAttributesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int bonusAttributesEntity;

    public int getBonusAttributesEntity(){
        return bonusAttributesEntity;
    }
}
