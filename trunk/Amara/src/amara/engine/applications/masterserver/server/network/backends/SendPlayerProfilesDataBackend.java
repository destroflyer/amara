/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;
import amara.launcher.client.protocol.PlayerProfileData;

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
            String login = message.getLogin();
            int id = databaseAppState.getInteger("SELECT id FROM users WHERE login = '" + DatabaseAppState.escape(login) + "' LIMIT 1");
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
                messageResponse.addAnswerMessage(new Message_PlayerProfileData(login, playerProfileData));
            }
            else{
                messageResponse.addAnswerMessage(new Message_PlayerProfileDataNotExistant(login));
            }
        }
    }
}
