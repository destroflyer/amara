/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.engine.applications.masterserver.server.protocol.*;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameContents extends AbstractMessage{
    
    public Message_GameContents(){
        
    }
    
    public Message_GameContents(GameCharacter[] characters, Item[] items){
        this.characters = characters;
        this.items = items;
    }
    private GameCharacter[] characters;
    private Item[] items;

    public GameCharacter[] getCharacters(){
        return characters;
    }

    public Item[] getItems(){
        return items;
    }
}
