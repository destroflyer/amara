/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;
import amara.game.players.*;

/**
 *
 * @author Carl
 */
public class ReceiveLoginsBackend implements MessageBackend{

    public ReceiveLoginsBackend(ConnectedPlayers connectedPlayers){
        this.connectedPlayers = connectedPlayers;
    }
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Login){
            Message_Login message = (Message_Login) receivedMessage;
            AuthentificationInformation authentificationInformation = message.getAuthentificationInformation();
            //For testing purposes
            int playerID = Integer.parseInt(authentificationInformation.getLogin());
            Player player = new Player(playerID, authentificationInformation.getLogin());
            connectedPlayers.login(messageResponse.getClientID(), player);
            messageResponse.addAnswerMessage(new Message_LoginResult(true));
        }
    }
}
