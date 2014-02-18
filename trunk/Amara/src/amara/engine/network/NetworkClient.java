/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import java.util.LinkedList;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageConnection;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import amara.Util;
import amara.engine.network.exceptions.*;

/**
 *
 * @author Carl
 */
public class NetworkClient extends NetworkListener{
    
    public NetworkClient(){
        
    }
    public static final int MAXIMUM_CONNECTION_TIME = 5000;
    private Client client;
    
    public void connectToServer(String host, int port) throws ServerConnectionException, ServerConnectionTimeoutException{
        try{
            client = Network.connectToServer(host, port);
            client.start();
            long connectionStart = System.currentTimeMillis();
            while(!isConnected()){
                if(Util.isTimeElapsed(connectionStart, MAXIMUM_CONNECTION_TIME)){
                    throw new ServerConnectionTimeoutException(host, port);
                }
            }
            client.addMessageListener(new MessageListener<MessageConnection>(){
                
                @Override
                public void messageReceived(MessageConnection source, Message message){
                    onMessageReceived(source, message);
                }
            });
        }catch(Exception ex){
            throw new ServerConnectionException(host, port);
        }
    }

    @Override
    protected void sendMessageResponse(MessageResponse messageResponse){
        LinkedList<Message> answerMessages = messageResponse.getAnswerMessages();
        for(int i=0;i<answerMessages.size();i++){
            sendMessage(answerMessages.get(i));
        }
    }

    public void sendMessage(Message message){
        client.send(message);
    }
    
    public void disconnect(){
        if(isConnected()){
            client.close();
        }
    }

    public boolean isConnected(){
        if(client != null){
            return client.isConnected();
        }
        return false;
    }

    public int getID(){
        return client.getId();
    }
}
