/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.network.Message;
import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.gui.ScreenController_Chat;
import amara.applications.ingame.client.network.debug.frame.NetworkLoadDisplay;
import amara.applications.ingame.network.messages.*;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.core.input.Event;
import amara.core.input.events.KeyEvent;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ClientChatAppState extends BaseDisplayAppState<IngameClientApplication>{
    
    private static final float HIDE_CHAT_DELAY = 10;
    private float timeSinceLastReceivedMessage;
    private LinkedList<Message_ChatMessage> queuedMessages = new LinkedList<>();
    private boolean isHandlingMessage;
    private HashMap<Integer, String> playerLogins = new HashMap<>();
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        IngameNetworkAppState ingameNetworkAppState = getAppState(IngameNetworkAppState.class);
        ingameNetworkAppState.addMessageBackend((Message receivedMessage, MessageResponse messageResponse) -> {
            if(receivedMessage instanceof Message_ChatMessage){
                final Message_ChatMessage message = (Message_ChatMessage) receivedMessage;
                mainApplication.enqueueTask(() -> {
                    queuedMessages.add(message);
                });
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
            if(event instanceof KeyEvent){
                KeyEvent keyEvent = (KeyEvent) event;
                if ((keyEvent.getKeyCode() == KeyInput.KEY_RETURN) && keyEvent.isPressed()) {
                    screenController_Chat.setChatVisible_Input(true);
                    screenController_Chat.setChatVisible_Output(true);
                }
            }
        }
        timeSinceLastReceivedMessage += lastTimePerFrame;
        if(screenController_Chat.isOnlyChatOutputVisible() && (timeSinceLastReceivedMessage >= HIDE_CHAT_DELAY)){
            screenController_Chat.setChatVisible_Output(false);
        }
        if((queuedMessages.size() > 0) && (!isHandlingMessage)){
            displayMessages(queuedMessages.toArray(new Message_ChatMessage[queuedMessages.size()]));
            queuedMessages.clear();
        }
    }
    
    private void displayMessages(final Message_ChatMessage[] messages){
        new Thread(() -> {
            isHandlingMessage = true;
            for(Message_ChatMessage message : messages){
                ScreenController_Chat screenController_Chat = getScreenController_Chat();
                String chatLine = "";
                String name = message.getSender();
                if((name == null) && (message.getPlayerID() != 0)){
                    PlayerProfileData playerProfileData = mainApplication.getMasterserverClient().getPlayerProfile(message.getPlayerID());
                    String login = playerProfileData.getLogin();
                    playerLogins.put(message.getPlayerID(), login);
                    // TODO: Add " (characterName)"
                    name = login;
                }
                if(name != null){
                    boolean isOwnMessage = (message.getPlayerID() == mainApplication.getMasterserverClient().getPlayerId());
                    chatLine += "\\#" + (isOwnMessage?"00CE00":"E72D33") + "#" + name + ":\\#FFFFFF# ";
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
        }).start();
    }
    
    private ScreenController_Chat getScreenController_Chat(){
        return getAppState(NiftyAppState.class).getScreenController(ScreenController_Chat.class);
    }
    
    public void sendMessage(String text){
        NetworkClient networkClient = mainApplication.getMasterserverClient().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
        if(text.startsWith("/networklog down ")){
            try{
                int interval = Integer.parseInt(text.substring(17));
                networkClient.enableDownloadHistory(interval);
                new NetworkLoadDisplay(networkClient.getDownloadHistory(), 20*1024, "Download (Ingame)").setVisible(true);
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
