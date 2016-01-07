/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.maps;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayerDeathRulesComponent{

    public PlayerDeathRulesComponent(){
        
    }

    public PlayerDeathRulesComponent(int rulesEntity){
        this.rulesEntity = rulesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int rulesEntity;

    public int getRulesEntity(){
        return rulesEntity;
    }
}
