/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import java.util.HashMap;
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
public class SendPlayerProfilesDataBackend implements MessageBackend{

    public SendPlayerProfilesDataBackend(DatabaseAppState databaseAppState, PlayersAppState playersAppState){
        this.databaseAppState = databaseAppState;
        this.playersAppState = playersAppState;
    }
    private DatabaseAppState databaseAppState;
    private PlayersAppState playersAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetPlayerProfileData){
            Message_GetPlayerProfileData message = (Message_GetPlayerProfileData) receivedMessage;
            int id = message.getPlayerID();
            String login = message.getLogin();
            if(id != 0){
                login = databaseAppState.getQueryResult("SELECT login FROM users WHERE id = " + id + " LIMIT 1").nextString_Close();
            }
            else{
                id = databaseAppState.getQueryResult("SELECT id FROM users WHERE login = '" + databaseAppState.escape(message.getLogin()) + "' LIMIT 1").nextInteger_Close();
            }
            if(id != 0){
                PlayerProfileData playerProfileData = null;
                long lastModificationDate = databaseAppState.getQueryResult("SELECT last_modification_date FROM users WHERE id = " + id + " LIMIT 1").nextLong_Close();
                if(lastModificationDate > message.getCachedTimestamp()){
                    HashMap<String, String> meta = new HashMap<String, String>(playersAppState.getUserDefaultMeta());
                    QueryResult results_Meta = databaseAppState.getQueryResult("SELECT key, value FROM users_meta WHERE userid = " + id);
                    while(results_Meta.next()){
                        meta.put(results_Meta.getString("key"), results_Meta.getString("value"));
                    }
                    results_Meta.close();
                    playerProfileData = new PlayerProfileData(id, login, meta, System.currentTimeMillis());
                }
                messageResponse.addAnswerMessage(new Message_PlayerProfileData(id, login, playerProfileData));
            }
            else{
                messageResponse.addAnswerMessage(new Message_PlayerProfileDataNotExistant(id, login));
            }
        }
    }
}
