/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.items;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ItemActiveComponent{

    public ItemActiveComponent(){
        
    }
    
    public ItemActiveComponent(int spellEntity, boolean consumable){
        this.spellEntity = spellEntity;
        this.consumable = consumable;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int spellEntity;
    private boolean consumable;

    public int getSpellEntity(){
        return spellEntity;
    }

    public boolean isConsumable(){
        return consumable;
    }
}
