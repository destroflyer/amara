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
public class InventoryComponent{

    public InventoryComponent(){
        
    }
    
    public InventoryComponent(int... itemEntities){
        this.itemEntities = itemEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] itemEntities;

    public int[] getItemEntities(){
        return itemEntities;
    }
}
