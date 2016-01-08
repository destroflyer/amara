/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_EditCharacterInventory extends AbstractMessage{
    
    public Message_EditCharacterInventory(){
        
    }
    
    public Message_EditCharacterInventory(int characterID, int[] inventory){
        this.characterID = characterID;
        this.inventory = inventory;
    }
    private int characterID;
    private int[] inventory;

    public int getCharacterID(){
        return characterID;
    }

    public int[] getInventory(){
        return inventory;
    }
}
