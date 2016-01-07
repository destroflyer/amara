/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_SendChatMessage extends AbstractMessage{
    
    public Message_SendChatMessage(){
        
    }
    
    public Message_SendChatMessage(String text){
        this.text = text;
    }
    private String text;

    public String getText(){
        return text;
    }
}
