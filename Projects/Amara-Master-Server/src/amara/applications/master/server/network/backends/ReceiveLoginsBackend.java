/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.AuthentificationInformation;
import amara.applications.master.server.appstates.PlayersAppState;
import amara.applications.master.server.players.*;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.libraries.network.*;

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
            int resultPlayerID = 0;
            int playerID = databaseAppState.getInteger("SELECT id FROM users WHERE LCASE(login) = '" + DatabaseAppState.escape(authentificationInformation.getLogin().toLowerCase()) + "' LIMIT 1");
            if(playerID != 0){
                String encodedSentPassword = playersAppState.getPasswordEncoder().encode(authentificationInformation.getPassword());
                String encodedPassword = databaseAppState.getString("SELECT password FROM users WHERE id = " + playerID + " LIMIT 1");
                if(encodedSentPassword.equals(encodedPassword)){
                    String login = databaseAppState.getString("SELECT login FROM users WHERE id = " + playerID + " LIMIT 1");
                    Player player = new Player(playerID, login);
                    ConnectedPlayers connectedPlayers = playersAppState.getConnectedPlayers();
                    connectedPlayers.login(messageResponse.getClientID(), player);
                    System.out.println("Login '" + player.getLogin() + "' (#" + player.getID() + ")");
                    resultPlayerID = playerID;
                }
            }
            messageResponse.addAnswerMessage(new Message_LoginResult(resultPlayerID));
        }
    }
}
