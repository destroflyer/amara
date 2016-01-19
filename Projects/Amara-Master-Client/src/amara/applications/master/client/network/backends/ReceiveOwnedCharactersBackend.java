/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.appstates.CharactersAppState;
import amara.applications.master.network.messages.Message_OwnedCharacters;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceiveOwnedCharactersBackend implements MessageBackend{

    public ReceiveOwnedCharactersBackend(CharactersAppState charactersAppState){
        this.charactersAppState = charactersAppState;
    }
    private CharactersAppState charactersAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_OwnedCharacters){
            Message_OwnedCharacters message = (Message_OwnedCharacters) receivedMessage;
            charactersAppState.setOwnedCharacters(message.getOwnedCharacters());
        }
    }
}
