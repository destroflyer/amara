/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellTargetRulesComponent{

    public SpellTargetRulesComponent(){
        
    }

    public SpellTargetRulesComponent(int targetRulesEntity){
        this.targetRulesEntity = targetRulesEntity;
    }
    private int targetRulesEntity;

    public int getTargetRulesEntity(){
        return targetRulesEntity;
    }
}
