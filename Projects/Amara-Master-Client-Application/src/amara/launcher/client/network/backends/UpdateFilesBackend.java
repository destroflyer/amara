/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.network.backends;

import java.util.LinkedList;
import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_UpdateFiles;
import amara.applications.master.network.messages.objects.UpdateFile;
import amara.engine.network.*;
import amara.launcher.client.ClientLauncher;

/**
 *
 * @author Carl
 */
public class UpdateFilesBackend implements MessageBackend{

    public UpdateFilesBackend(ClientLauncher clientLauncher){
        this.clientLauncher = clientLauncher;
    }
    private ClientLauncher clientLauncher;
    private LinkedList<UpdateFile> updateFiles = new LinkedList<UpdateFile>();
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_UpdateFiles){
            Message_UpdateFiles message = (Message_UpdateFiles) receivedMessage;
            for(UpdateFile updateFile : message.getUpdateFiles()){
                updateFiles.add(updateFile);
            }
            if(message.isEndReached()){
                clientLauncher.update(updateFiles);
            }
        }
    }
}
