/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;
import amara.game.players.*;

/**
 *
 * @author Carl
 */
public class ReceiveLoginsBackend implements MessageBackend{

    public ReceiveLoginsBackend(DatabaseAppState databaseAppState, PlayersAppState playersAppState){
        this.databaseAppState = databaseAppState;
        this.playersAppState = playersAppState;
    }
    private DatabaseAppState databaseAppState;
    private PlayersAppState playersAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Login){
            Message_Login message = (Message_Login) receivedMessage;
            AuthentificationInformation authentificationInformation = message.getAuthentificationInformation();
            int playerID = databaseAppState.getInteger("SELECT id FROM users WHERE login = '" + DatabaseAppState.escape(authentificationInformation.getLogin()) + "' LIMIT 1");
            if(playerID != 0){
                String encodedSentPassword = playersAppState.getPasswordEncoder().encode(authentificationInformation.getPassword());
                String encodedPassword = databaseAppState.getString("SELECT password FROM users WHERE id = " + playerID + " LIMIT 1");
                if(encodedSentPassword.equals(encodedPassword)){
                    Player player = new Player(playerID, authentificationInformation.getLogin());
                    ConnectedPlayers connectedPlayers = playersAppState.getConnectedPlayers();
                    connectedPlayers.login(messageResponse.getClientID(), player);
                    System.out.println("Login '" + player.getLogin() + "' (#" + player.getID() + ")");
                }
            }
            messageResponse.addAnswerMessage(new Message_LoginResult(playerID));
        }
    }
}
