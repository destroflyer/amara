/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
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
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_UpdateFiles){
            Message_UpdateFiles message = (Message_UpdateFiles) receivedMessage;
            final UpdateFile[] updateFiles = message.getUpdateFiles();
            clientLauncher.update(updateFiles);
        }
    }
}
