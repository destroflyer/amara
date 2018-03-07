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
public class Message_GenericInformation extends AbstractMessage{
    
    public Message_GenericInformation(){
        
    }
    
    public Message_GenericInformation(boolean isError, String message){
        this.isError = isError;
        this.message = message;
    }
    private boolean isError;
    private String message;

    public boolean isError(){
        return isError;
    }

    public String getMessage(){
        return message;
    }
}
