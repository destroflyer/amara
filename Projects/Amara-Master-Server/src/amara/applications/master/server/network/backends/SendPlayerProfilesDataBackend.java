/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SendPlayerProfilesDataBackend implements MessageBackend{

    public SendPlayerProfilesDataBackend(DatabaseAppState databaseAppState){
        this.databaseAppState = databaseAppState;
    }
    private DatabaseAppState databaseAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetPlayerProfileData){
            Message_GetPlayerProfileData message = (Message_GetPlayerProfileData) receivedMessage;
            int id = message.getPlayerID();
            String login = message.getLogin();
            if(id != 0){
                login = databaseAppState.getString("SELECT login FROM users WHERE id = " + id + " LIMIT 1");
            }
            else{
                id = databaseAppState.getInteger("SELECT id FROM users WHERE login = '" + DatabaseAppState.escape(message.getLogin()) + "' LIMIT 1");
            }
            if(id != 0){
                PlayerProfileData playerProfileData = null;
                long lastModificationDate = databaseAppState.getLong("SELECT last_modification_date FROM users WHERE id = " + id + " LIMIT 1");
                if(lastModificationDate > message.getCachedTimestamp()){
                    ResultSet metaResultSet = databaseAppState.getResultSet("SELECT key, value FROM users_meta WHERE userid = " + id);
                    HashMap<String, String> meta = new HashMap<String, String>();
                    try{
                        while(metaResultSet.next()){
                            meta.put(metaResultSet.getString(1), metaResultSet.getString(2));
                        }
                        metaResultSet.close();
                    }catch(SQLException ex){
                        ex.printStackTrace();
                    }
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
