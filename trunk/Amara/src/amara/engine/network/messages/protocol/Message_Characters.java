/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.engine.applications.masterserver.server.protocol.GameCharacter;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_Characters extends AbstractMessage{
    
    public Message_Characters(){
        
    }
    
    public Message_Characters(GameCharacter[] characters){
        this.characters = characters;
    }
    private GameCharacter[] characters;

    public GameCharacter[] getCharacters(){
        return characters;
    }
}
