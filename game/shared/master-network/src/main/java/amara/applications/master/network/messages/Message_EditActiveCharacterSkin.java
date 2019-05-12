/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_EditActiveCharacterSkin extends AbstractMessage{
    
    public Message_EditActiveCharacterSkin(){
        
    }
    
    public Message_EditActiveCharacterSkin(int characterID, int skinID){
        this.characterID = characterID;
        this.skinID = skinID;
    }
    private int characterID;
    private int skinID;

    public int getCharacterID(){
        return characterID;
    }

    public int getSkinID(){
        return skinID;
    }
}
