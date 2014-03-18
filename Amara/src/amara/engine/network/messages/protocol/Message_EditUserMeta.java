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
public class Message_EditUserMeta extends AbstractMessage{
    
    public Message_EditUserMeta(){
        
    }
    
    public Message_EditUserMeta(String key, String value){
        this.key = key;
        this.value = value;
    }
    private String key;
    private String value;

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }
}
