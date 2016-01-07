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
public class ItemActiveComponent{

    public ItemActiveComponent(){
        
    }
    
    public ItemActiveComponent(int spellEntity){
        this.spellEntity = spellEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int spellEntity;

    public int getSpellEntity(){
        return spellEntity;
    }
}
