/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.UpdatesAppState;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.*;

/**
 *
 * @author Carl
 */
public class SendUpdateFilesBackend implements MessageBackend{

    public SendUpdateFilesBackend(UpdateFile[] updateFiles){
        this.updateFiles = updateFiles;
    }
    private UpdateFile[] updateFiles;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetUpdateFiles){
            Message_GetUpdateFiles message = (Message_GetUpdateFiles) receivedMessage;
            messageResponse.addAnswerMessage(new Message_UpdateFiles(updateFiles));
        }
        else if(receivedMessage instanceof Message_GetUpdateFile){
            Message_GetUpdateFile message = (Message_GetUpdateFile) receivedMessage;
            if((message.getIndex() >= 0) && (message.getIndex() < updateFiles.length)){
                UpdateFile updateFile = updateFiles[message.getIndex()];
                try{
                    String filePath = (UpdatesAppState.UPDATE_FILES_DIRECTORY + updateFile.getFilePath().substring(2));
                    FileInputStream fileInputStream = new FileInputStream(filePath);
                    byte[] buffer = new byte[30000];
                    int readBytes = 0;
                    while((readBytes = fileInputStream.read(buffer)) != -1){
                        byte[] data = Arrays.copyOf(buffer, readBytes);
                        messageResponse.addAnswerMessage(new Message_UpdateFilePart(data));
                    }
                    fileInputStream.close();
                }catch(FileNotFoundException ex){
                    ex.printStackTrace();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
