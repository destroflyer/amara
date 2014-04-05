/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CastSourceComponent{

    public CastSourceComponent(){
        
    }
    
    public CastSourceComponent(int sourceEntity){
        this.sourceEntity = sourceEntity;
    }
    private int sourceEntity;

    public int getSourceEntity(){
        return sourceEntity;
    }
}
