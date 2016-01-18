/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AutoAttackComponent{

    public AutoAttackComponent(){
        
    }
    
    public AutoAttackComponent(int autoAttackEntity){
        this.autoAttackEntity = autoAttackEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int autoAttackEntity;

    public int getAutoAttackEntity(){
        return autoAttackEntity;
    }
}
