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
public class Message_GetPlayerProfileData_ByLogin extends AbstractMessage{

    public Message_GetPlayerProfileData_ByLogin(){

    }

    public Message_GetPlayerProfileData_ByLogin(String login){
        this.login = login;
    }
    private String login;

    public String getLogin(){
        return login;
    }
}
