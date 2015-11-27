/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.items;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ItemPassivesComponent{

    public ItemPassivesComponent(){
        
    }
    
    public ItemPassivesComponent(int... passiveEntities){
        this.passiveEntities = passiveEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] passiveEntities;

    public int[] getPassiveEntities(){
        return passiveEntities;
    }
}
