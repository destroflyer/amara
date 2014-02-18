/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.protocol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AuthentificationInformation{

    public AuthentificationInformation(){
        
    }
    
    public AuthentificationInformation(String login, String password){
        this.login = login;
        this.password = password;
    }
    private String login;
    private String password;

    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }
}
