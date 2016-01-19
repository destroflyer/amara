/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.Message_GameContents;
import amara.engine.applications.masterserver.client.appstates.ItemsAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceiveItemsBackend implements MessageBackend{

    public ReceiveItemsBackend(ItemsAppState itemsAppState){
        this.itemsAppState = itemsAppState;
    }
    private ItemsAppState itemsAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameContents){
            Message_GameContents message = (Message_GameContents) receivedMessage;
            itemsAppState.setItems(message.getItems());
        }
    }
}
