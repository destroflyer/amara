/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.engine.applications.masterserver.server.protocol.UpdateFile;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_UpdateFiles extends AbstractMessage{
    
    public Message_UpdateFiles(){
        
    }
    
    public Message_UpdateFiles(UpdateFile[] updateFiles){
        this.updateFiles = updateFiles;
    }
    private UpdateFile[] updateFiles;

    public UpdateFile[] getUpdateFiles(){
        return updateFiles;
    }
}
