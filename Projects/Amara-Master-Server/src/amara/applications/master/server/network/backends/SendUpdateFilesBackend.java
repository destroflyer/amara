/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.UpdateFile;
import amara.applications.master.server.appstates.UpdatesAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SendUpdateFilesBackend implements MessageBackend{

    public SendUpdateFilesBackend(UpdatesAppState updatesAppState){
        this.updatesAppState = updatesAppState;
        generateSplittedUpdateFiles();
    }
    private static final int FILES_PER_MESSAGE = 50;
    private UpdatesAppState updatesAppState;
    private UpdateFile[][] splittedUpdateFiles;
    
    private void generateSplittedUpdateFiles(){
        UpdateFile[] updateFiles = updatesAppState.getUpdateFiles();
        splittedUpdateFiles = new UpdateFile[(int) Math.ceil(((float) updateFiles.length) / FILES_PER_MESSAGE)][];
        int remainingFiles = updateFiles.length;
        for(int i=0;i<splittedUpdateFiles.length;i++){
            int partSize = Math.min(remainingFiles, FILES_PER_MESSAGE);
            splittedUpdateFiles[i] = new UpdateFile[partSize];
            for(int r=0;r<partSize;r++){
                splittedUpdateFiles[i][r] = updateFiles[(i * FILES_PER_MESSAGE) + r];
            }
            remainingFiles -= partSize;
        }
    }
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetUpdateFiles){
            Message_GetUpdateFiles message = (Message_GetUpdateFiles) receivedMessage;
            for(int i=0;i<splittedUpdateFiles.length;i++){
                boolean isEndReached = (i == (splittedUpdateFiles.length - 1));
                messageResponse.addAnswerMessage(new Message_UpdateFiles(splittedUpdateFiles[i], isEndReached));
            }
        }
        else if(receivedMessage instanceof Message_GetUpdateFile){
            Message_GetUpdateFile message = (Message_GetUpdateFile) receivedMessage;
            UpdateFile[] updateFiles = updatesAppState.getUpdateFiles();
            if((message.getIndex() >= 0) && (message.getIndex() < updateFiles.length)){
                UpdateFile updateFile = updateFiles[message.getIndex()];
                try{
                    String filePath = (updatesAppState.getUpdateFilesDirectory() + updateFile.getFilePath().substring(2));
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
