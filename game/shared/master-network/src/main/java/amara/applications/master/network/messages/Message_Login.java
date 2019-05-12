/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.AuthentificationInformation;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_Login extends AbstractMessage{
    
    public Message_Login(){
        
    }
    
    public Message_Login(AuthentificationInformation authentificationInformation){
        this.authentificationInformation = authentificationInformation;
    }
    private AuthentificationInformation authentificationInformation;

    public AuthentificationInformation getAuthentificationInformation(){
        return authentificationInformation;
    }
}
