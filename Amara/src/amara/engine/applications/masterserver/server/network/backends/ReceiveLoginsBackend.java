/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.DatabaseAppState;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;
import amara.game.players.*;

/**
 *
 * @author Carl
 */
public class ReceiveLoginsBackend implements MessageBackend{

    public ReceiveLoginsBackend(DatabaseAppState databaseAppState, ConnectedPlayers connectedPlayers){
        this.databaseAppState = databaseAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private DatabaseAppState databaseAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Login){
            Message_Login message = (Message_Login) receivedMessage;
            AuthentificationInformation authentificationInformation = message.getAuthentificationInformation();
            boolean wasSuccessful = false;
            int playerID = databaseAppState.getInteger("SELECT id FROM users WHERE login = '" + authentificationInformation.getLogin() + "' LIMIT 1");
            if(playerID != 0){
                Player player = new Player(playerID, authentificationInformation.getLogin());
                connectedPlayers.login(messageResponse.getClientID(), player);
                wasSuccessful = true;
                System.out.println("Login '" + player.getLogin() + "' (#" + player.getID() + ")");
            }
            messageResponse.addAnswerMessage(new Message_LoginResult(wasSuccessful));
        }
    }
}
