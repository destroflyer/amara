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
public class Message_GameCreated extends AbstractMessage{
    
    public Message_GameCreated(){
        
    }
    
    public Message_GameCreated(int authentificationKey){
        this.authentificationKey = authentificationKey;
    }
    private int authentificationKey;

    public int getAuthentificationKey(){
        return authentificationKey;
    }
}
