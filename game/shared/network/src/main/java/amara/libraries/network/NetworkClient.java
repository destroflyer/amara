/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import java.util.LinkedList;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.ErrorListener;
import com.jme3.network.Message;
import com.jme3.network.MessageConnection;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.base.DefaultClient;
import amara.core.Util;
import amara.libraries.network.debug.*;
import amara.libraries.network.exceptions.*;

/**
 *
 * @author Carl
 */
public class NetworkClient extends NetworkListener{
    
    public NetworkClient(){
        
    }
    public static final int MAXIMUM_CONNECTION_TIME = 5000;
    private Client client;
    private LoadHistory uploadHistory;
    private boolean isConnected;
    
    public void connectToServer(String host, int port) throws ServerConnectionException, ServerConnectionTimeoutException{
        try{
            client = Network.connectToServer(host, port);
            client.addClientStateListener(new ClientStateListener(){

                @Override
                public void clientConnected(Client client){
                    isConnected = true;
                    System.out.println("Connected to server.");
                }

                @Override
                public void clientDisconnected(Client client, ClientStateListener.DisconnectInfo info){
                    System.err.println(info);
                    onDisconnect();
                }
            });
            client.addErrorListener(new ErrorListener<Client>(){

                @Override
                public void handleError(Client source, Throwable throwable){
                    throwable.printStackTrace();
                    onDisconnect();
                }
            });
            client.addMessageListener(new MessageListener<MessageConnection>(){

                @Override
                public void messageReceived(MessageConnection source, Message message){
                    if(source instanceof DefaultClient){
                        onMessageReceived(((DefaultClient) source).getId(), message);
                    }
                }
            });
            client.start();
            long connectionStart = System.currentTimeMillis();
            while(!isConnected){
                if(Util.isTimeElapsed(connectionStart, MAXIMUM_CONNECTION_TIME)){
                    throw new ServerConnectionTimeoutException(host, port);
                }
                try{
                    Thread.sleep(100);
                }catch(Exception ex){
                }
            }
        }catch(Exception ex){
            throw new ServerConnectionException(host, port);
        }
    }

    @Override
    public void sendMessageResponse(MessageResponse messageResponse){
        LinkedList<MessageResponse_Entry> messageResponseEntries = messageResponse.getEntries();
        for(MessageResponse_Entry messageResponseEntry : messageResponseEntries){
            sendMessage(messageResponseEntry.getMessage());
        }
    }

    public void sendMessage(Message message){
        if(isConnected){
            client.send(message);
            if(uploadHistory != null){
                uploadHistory.add(MessageSizeCalculator.getMessageSize(message));
            }
        }
    }
    
    public void disconnect(){
        if(isConnected){
            client.close();
        }
    }
    
    private void onDisconnect(){
        isConnected = false;
        System.out.println("Disconnected from server.");
    }

    public boolean isConnected(){
        return isConnected;
    }

    public int getID(){
        return client.getId();
    }
    
    public void enableUploadHistory(int interval){
        uploadHistory = new LoadHistory(interval);
    }
    
    public void disableUploadHistory(){
        uploadHistory = null;
    }

    public LoadHistory getUploadHistory(){
        return uploadHistory;
    }
}
