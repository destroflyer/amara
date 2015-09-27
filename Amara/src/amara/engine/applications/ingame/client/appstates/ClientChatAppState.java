/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.network.Message;
import amara.engine.applications.ingame.client.gui.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.appstates.*;
import amara.engine.input.Event;
import amara.engine.input.events.KeyPressedEvent;
import amara.engine.network.MessageBackend;
import amara.engine.network.MessageResponse;
import amara.engine.network.NetworkClient;
import amara.engine.network.debug.frame.NetworkLoadDisplay;
import amara.engine.network.messages.*;
import amara.launcher.client.MasterserverClientUtil;

/**
 *
 * @author Carl
 */
public class ClientChatAppState extends BaseDisplayAppState{
    
    private static final float HIDE_CHAT_DELAY = 10;
    private float timeSinceLastReceivedMessage;
    private LinkedList<Message_ChatMessage> queuedMessages = new LinkedList<Message_ChatMessage>();
    private boolean isHandlingMessage;
    private HashMap<Integer, String> playerLogins = new HashMap<Integer, String>();
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new MessageBackend(){

            @Override
            public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
                if(receivedMessage instanceof Message_ChatMessage){
                    final Message_ChatMessage message = (Message_ChatMessage) receivedMessage;
                    mainApplication.enqueueTask(new Runnable(){

                        @Override
                        public void run(){
                            queuedMessages.add(message);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        ScreenController_Chat screenController_Chat = getScreenController_Chat();
        Iterator<Event> eventsIterator = getAppState(EventManagerAppState.class).getEventQueue().getIterator();
        while(eventsIterator.hasNext()){
            Event event = eventsIterator.next();
            if(event instanceof KeyPressedEvent){
                KeyPressedEvent keyPressedEvent = (KeyPressedEvent) event;
                switch(keyPressedEvent.getKeyCode()){
                    case KeyInput.KEY_RETURN:
                        screenController_Chat.setChatVisible_Input(true);
                        screenController_Chat.setChatVisible_Output(true);
                        break;
                }
            }
        }
        timeSinceLastReceivedMessage += lastTimePerFrame;
        if(screenController_Chat.isOnlyChatOutputVisible() && (timeSinceLastReceivedMessage >= HIDE_CHAT_DELAY)){
            screenController_Chat.setChatVisible_Output(false);
        }
        if((queuedMessages.size() > 0) && (!isHandlingMessage)){
            displayMessages(queuedMessages.toArray(new Message_ChatMessage[0]));
            queuedMessages.clear();
        }
    }
    
    private void displayMessages(final Message_ChatMessage[] messages){
        new Thread(new Runnable(){

            @Override
            public void run(){
                isHandlingMessage = true;
                for(Message_ChatMessage message : messages){
                    ScreenController_Chat screenController_Chat = getScreenController_Chat();
                    String chatLine = "";
                    if(message.getPlayerID() != 0){
                        String login = playerLogins.get(message.getPlayerID());
                        if(login == null){
                            PlayerProfileData playerProfileData = MasterserverClientUtil.getPlayerProfile(message.getPlayerID());
                            login = playerProfileData.getLogin();
                            playerLogins.put(message.getPlayerID(), login);
                        }
                        chatLine += "\\#" + ((message.getPlayerID() == MasterserverClientUtil.getPlayerID())?"00CE00":"E72D33") + "#" + login + ":\\#FFFFFF# ";
                    }
                    else{
                        chatLine += "\\#FFCC00#";
                    }
                    chatLine += message.getText();
                    screenController_Chat.addChatLine(chatLine);
                    screenController_Chat.setChatVisible_Output(true);
                    timeSinceLastReceivedMessage = 0;
                }
                isHandlingMessage = false;
            }
        }).start();
    }
    
    private ScreenController_Chat getScreenController_Chat(){
        return getAppState(NiftyAppState.class).getScreenController(ScreenController_Chat.class);
    }
    
    public void sendMessage(String text){
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        if(text.startsWith("/networklog down ")){
            try{
                int interval = Integer.parseInt(text.substring(17));
                networkClient.enableDownloadHistory(interval);
                new NetworkLoadDisplay(networkClient.getDownloadHistory(), 100*1024, "Download (Ingame)").setVisible(true);
            }catch(NumberFormatException ex){
            }
        }
        else if(text.startsWith("/networklog up ")){
            try{
                int interval = Integer.parseInt(text.substring(15));
                networkClient.enableUploadHistory(interval);
                new NetworkLoadDisplay(networkClient.getUploadHistory(), 200, "Upload (Ingame)").setVisible(true);
            }catch(NumberFormatException ex){
            }
        }
        else if(text.startsWith("/networklog stop")){
            networkClient.disableDownloadHistory();
            networkClient.disableUploadHistory();
        }
        else{
            networkClient.sendMessage(new Message_SendChatMessage(text));
        }
    }
}
