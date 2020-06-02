/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameContents extends AbstractMessage{
    
    public Message_GameContents(){
        
    }
    
    public Message_GameContents(GameCharacter[] characters){
        this.characters = characters;
    }
    private GameCharacter[] characters;

    public GameCharacter[] getCharacters(){
        return characters;
    }
}
