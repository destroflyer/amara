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
public class Message_EditUserMeta extends AbstractMessage{
    
    public Message_EditUserMeta(){
        
    }
    
    public Message_EditUserMeta(String name, String value){
        this.name = name;
        this.value = value;
    }
    private String name;
    private String value;

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }
}
