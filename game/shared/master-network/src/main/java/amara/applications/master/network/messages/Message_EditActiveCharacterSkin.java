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
public class Message_EditActiveCharacterSkin extends AbstractMessage {

    public Message_EditActiveCharacterSkin() {
        
    }

    public Message_EditActiveCharacterSkin(int characterId, int skinId) {
        this.characterId = characterId;
        this.skinId = skinId;
    }
    private int characterId;
    private int skinId;

    public int getCharacterId() {
        return characterId;
    }

    public int getSkinId() {
        return skinId;
    }
}
