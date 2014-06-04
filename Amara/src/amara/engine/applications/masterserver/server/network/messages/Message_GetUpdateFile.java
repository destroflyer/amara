/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GetUpdateFile extends AbstractMessage{
    
    public Message_GetUpdateFile(){
        
    }

    public Message_GetUpdateFile(int index){
        this.index = index;
    }
    private int index;

    public int getIndex(){
        return index;
    }
}
