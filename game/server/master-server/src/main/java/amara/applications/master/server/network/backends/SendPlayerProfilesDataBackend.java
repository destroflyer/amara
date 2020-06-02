/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import java.util.HashMap;

import amara.applications.master.server.appstates.model.DestrostudiosUser;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.applications.master.server.appstates.*;
import amara.libraries.database.QueryResult;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SendPlayerProfilesDataBackend implements MessageBackend {

    public SendPlayerProfilesDataBackend(DatabaseAppState databaseAppState, PlayersAppState playersAppState, DestrostudiosAppState destrostudiosAppState) {
        this.databaseAppState = databaseAppState;
        this.playersAppState = playersAppState;
        this.destrostudiosAppState = destrostudiosAppState;
    }
    private DatabaseAppState databaseAppState;
    private PlayersAppState playersAppState;
    private DestrostudiosAppState destrostudiosAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GetPlayerProfileData_ById) {
            Message_GetPlayerProfileData_ById message = (Message_GetPlayerProfileData_ById) receivedMessage;
            int playerId = message.getPlayerId();
            String login = destrostudiosAppState.getLoginByPlayerId(playerId);
            sendResponse(playerId, login, messageResponse);
        } else if (receivedMessage instanceof Message_GetPlayerProfileData_ByLogin) {
            Message_GetPlayerProfileData_ByLogin message = (Message_GetPlayerProfileData_ByLogin) receivedMessage;
            String login = message.getLogin();
            DestrostudiosUser user = destrostudiosAppState.getUserByLogin(login);
            Integer playerId = ((user != null) ? user.getId() : null);
            sendResponse(playerId, login, messageResponse);
        }
    }

    private void sendResponse(Integer playerId, String login, MessageResponse messageResponse) {
        if (playerId != null) {
            HashMap<String, String> meta = new HashMap<>(playersAppState.getUserDefaultMeta());
            QueryResult results_Meta = databaseAppState.getQueryResult("SELECT name, value FROM users_meta WHERE user_id = " + playerId);
            while (results_Meta.next()) {
                meta.put(results_Meta.getString("name"), results_Meta.getString("value"));
            }
            results_Meta.close();
            PlayerProfileData playerProfileData = new PlayerProfileData(playerId, login, meta);
            messageResponse.addAnswerMessage(new Message_PlayerProfileData(playerProfileData));
        } else {
            messageResponse.addAnswerMessage(new Message_PlayerProfileDataNotExistent(login));
        }
    }
}
