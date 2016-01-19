/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.network.backends;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_UpdateFilePart;
import amara.applications.master.network.messages.objects.UpdateFile;
import amara.engine.network.*;

/**
 *
 * @author Carl
 */
public class WriteUpdateFileBackend implements MessageBackend{

    public WriteUpdateFileBackend(UpdateFile updateFile){
        this.updateFile = updateFile;
        try{
            fileOutputStream = new FileOutputStream(updateFile.getFilePath());
        }catch(FileNotFoundException ex){
            System.err.println("Error while initializing download '" + updateFile.getFilePath() + "'.");
        }
    }
    private UpdateFile updateFile;
    private FileOutputStream fileOutputStream;
    private int writtenBytes;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_UpdateFilePart){
            Message_UpdateFilePart message = (Message_UpdateFilePart) receivedMessage;
            try{
                fileOutputStream.write(message.getData());
                writtenBytes += message.getData().length;
                if(isFinished()){
                    fileOutputStream.close();
                }
            }catch(IOException ex){
                System.err.println("Error while downloading file '" + updateFile.getFilePath() + "'.");
            }
        }
    }
    
    public boolean isFinished(){
        return (writtenBytes == updateFile.getSize());
    }
}
