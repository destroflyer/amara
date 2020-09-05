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
public class Message_LoginResult extends AbstractMessage{
    
    public Message_LoginResult(){
        
    }
    
    public Message_LoginResult(int playerId){
        this.playerId = playerId;
    }
    private int playerId;

    public int getPlayerId(){
        return playerId;
    }
}
