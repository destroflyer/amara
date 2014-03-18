/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages.protocol;

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
    
    public Message_LoginResult(boolean wasSuccessfull){
        this.wasSuccessfull = wasSuccessfull;
    }
    private boolean wasSuccessfull;

    public boolean wasSuccessfull(){
        return wasSuccessfull;
    }
}
