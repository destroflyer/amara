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
public class BountyComponent{

    public BountyComponent(){
        
    }
    
    public BountyComponent(int bountyEntity){
        this.bountyEntity = bountyEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int bountyEntity;

    public int getBountyEntity(){
        return bountyEntity;
    }
}
