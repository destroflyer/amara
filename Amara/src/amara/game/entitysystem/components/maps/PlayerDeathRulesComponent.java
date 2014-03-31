/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.maps;

import com.jme3.network.serializing.Serializable;

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
    private int rulesEntity;

    public int getRulesEntity(){
        return rulesEntity;
    }
}
