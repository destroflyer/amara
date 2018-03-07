/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_ChatMessage extends AbstractMessage{
    
    public Message_ChatMessage(){
        
    }
    
    public Message_ChatMessage(String text){
        this(0, null, text);
    }
    
    public Message_ChatMessage(int playerID, String sender, String text){
        this.playerID = playerID;
        this.sender = sender;
        this.text = text;
    }
    private int playerID;
    private String sender;
    private String text;

    public int getPlayerID(){
        return playerID;
    }

    public String getSender(){
        return sender;
    }

    public String getText(){
        return text;
    }
}
