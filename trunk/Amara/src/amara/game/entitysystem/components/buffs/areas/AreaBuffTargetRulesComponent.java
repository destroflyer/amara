/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.areas;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AreaBuffTargetRulesComponent{

    public AreaBuffTargetRulesComponent(){
        
    }

    public AreaBuffTargetRulesComponent(int targetRulesEntity){
        this.targetRulesEntity = targetRulesEntity;
    }
    private int targetRulesEntity;

    public int getTargetRulesEntity(){
        return targetRulesEntity;
    }
}
