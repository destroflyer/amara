/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.UpdateFile;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_UpdateFiles extends AbstractMessage{
    
    public Message_UpdateFiles(){
        
    }
    
    public Message_UpdateFiles(UpdateFile[] updateFiles, boolean isEndReached){
        this.updateFiles = updateFiles;
        this.isEndReached = isEndReached;
    }
    private UpdateFile[] updateFiles;
    private boolean isEndReached;

    public UpdateFile[] getUpdateFiles(){
        return updateFiles;
    }

    public boolean isEndReached(){
        return isEndReached;
    }
}
