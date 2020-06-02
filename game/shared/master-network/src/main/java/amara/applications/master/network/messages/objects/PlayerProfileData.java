/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

import java.util.HashMap;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayerProfileData{

    public PlayerProfileData(){
        
    }

    public PlayerProfileData(int id, String login, HashMap<String, String> meta){
        this.id = id;
        this.login = login;
        this.meta = meta;
    }
    private int id;
    private String login;
    private HashMap<String, String> meta;

    public int getId(){
        return id;
    }

    public String getLogin(){
        return login;
    }

    public String getMeta(String key){
        return meta.get(key);
    }
}
