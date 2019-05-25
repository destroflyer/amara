/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import java.util.ArrayList;
import com.jme3.network.*;
import amara.libraries.network.debug.*;

/**
 *
 * @author Carl
 */
public abstract class NetworkListener{

    public NetworkListener(){
        
    }
    private ArrayList<MessageBackend> messageBackends = new ArrayList<>();
    private LoadHistory downloadHistory;

    public void onMessageReceived(int sourceID, Message message){
        MessageResponse messageResponse = new MessageResponse(sourceID);
        for(int i=0;i<messageBackends.size();i++){
            MessageBackend messageBackend = messageBackends.get(i);
            messageBackend.onMessageReceived(message, messageResponse);
        }
        sendMessageResponse(messageResponse);
        if(downloadHistory != null){
            downloadHistory.add(MessageSizeCalculator.getMessageSize(message));
        }
    }
    
    public abstract void sendMessageResponse(MessageResponse messageResponse);

    public void addMessageBackend(MessageBackend messageBackend){
        messageBackends.add(messageBackend);
    }

    public void removeMessageBackend(MessageBackend messageBackend){
        messageBackends.remove(messageBackend);
    }
    
    public void enableDownloadHistory(int interval){
        downloadHistory = new LoadHistory(interval);
    }
    
    public void disableDownloadHistory(){
        downloadHistory = null;
    }

    public LoadHistory getDownloadHistory(){
        return downloadHistory;
    }
}
