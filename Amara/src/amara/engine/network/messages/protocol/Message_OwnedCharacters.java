/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.engine.applications.masterserver.server.protocol.OwnedGameCharacter;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_OwnedCharacters extends AbstractMessage{
    
    public Message_OwnedCharacters(){
        
    }
    
    public Message_OwnedCharacters(OwnedGameCharacter[] ownedCharacters){
        this.ownedCharacters = ownedCharacters;
    }
    private OwnedGameCharacter[] ownedCharacters;

    public OwnedGameCharacter[] getOwnedCharacters(){
        return ownedCharacters;
    }
}
