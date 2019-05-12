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
public class Message_PlayerAuthentification extends AbstractMessage{
    
    public Message_PlayerAuthentification(){
        
    }
    
    public Message_PlayerAuthentification(int authentificationKey){
        this.authentificationKey = authentificationKey;
    }
    private int authentificationKey;

    public int getAuthentificationKey(){
        return authentificationKey;
    }
}
