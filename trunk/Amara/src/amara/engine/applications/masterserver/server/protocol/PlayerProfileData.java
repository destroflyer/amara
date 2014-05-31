/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.protocol;

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

    public PlayerProfileData(int id, String login, HashMap<String, String> meta, long timestamp){
        this.id = id;
        this.login = login;
        this.meta = meta;
        this.timestamp = timestamp;
    }
    private int id;
    private String login;
    private HashMap<String, String> meta;
    private long timestamp;

    public int getID(){
        return id;
    }

    public String getLogin(){
        return login;
    }

    public String getMeta(String key){
        return meta.get(key);
    }

    public long getTimestamp(){
        return timestamp;
    }
}
