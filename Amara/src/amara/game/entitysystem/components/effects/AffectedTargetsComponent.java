/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AffectedTargetsComponent{

    public AffectedTargetsComponent(){
        
    }
    
    public AffectedTargetsComponent(int[] targetEntitiesIDs){
        this.targetEntitiesIDs = targetEntitiesIDs;
    }
    private int[] targetEntitiesIDs;

    public int[] getTargetEntitiesIDs(){
        return targetEntitiesIDs;
    }
}
