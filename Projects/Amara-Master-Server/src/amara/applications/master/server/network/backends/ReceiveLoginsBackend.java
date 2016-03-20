/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.AuthentificationInformation;
import amara.applications.master.server.appstates.*;
import amara.applications.master.server.players.*;
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
            Integer playerID = databaseAppState.getQueryResult("SELECT id FROM users WHERE LOWER(login) = '" + databaseAppState.escape(authentificationInformation.getLogin().toLowerCase()) + "' LIMIT 1").nextInteger_Close();
            if(playerID != null){
                String encodedSentPassword = playersAppState.getPasswordEncoder().encode(authentificationInformation.getPassword());
                String encodedPassword = databaseAppState.getQueryResult("SELECT password FROM users WHERE id = " + playerID + " LIMIT 1").nextString_Close();
                if(encodedSentPassword.equals(encodedPassword)){
                    String login = databaseAppState.getQueryResult("SELECT login FROM users WHERE id = " + playerID + " LIMIT 1").nextString_Close();
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
