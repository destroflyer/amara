/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import java.util.HashMap;
import amara.applications.master.network.messages.objects.PlayerStatus;
import amara.applications.master.server.games.RunningGames;
import amara.applications.master.server.network.backends.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.database.QueryResult;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class PlayersAppState extends ServerBaseAppState{

    private ConnectedPlayers connectedPlayers = new ConnectedPlayers();
    private HashMap<String, String> userDefaultMeta = new HashMap<>();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        loadUserDefaultMeta();
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        PlayersContentsAppState playersContentsAppState = getAppState(PlayersContentsAppState.class);
        networkServer.addMessageBackend(new ReceiveLoginsBackend(databaseAppState, this));
        networkServer.addMessageBackend(new SendPlayerProfilesDataBackend(databaseAppState, this));
        networkServer.addMessageBackend(new SendPlayerStatusesBackend(this));
        networkServer.addMessageBackend(new EditUserMetaBackend(databaseAppState, this));
        networkServer.addMessageBackend(new SendGameContentsBackend(databaseAppState, connectedPlayers, playersContentsAppState));
        networkServer.addMessageBackend(new EditActiveCharacterSkinsBackend(databaseAppState, connectedPlayers));
        networkServer.addMessageBackend(new EditCharacterInventoriesBackend(databaseAppState, connectedPlayers));
    }
    
    private void loadUserDefaultMeta(){
        userDefaultMeta.clear();
        QueryResult results_MetaDefaults = getAppState(DatabaseAppState.class).getQueryResult("SELECT key, value FROM users_meta_defaults");
        while(results_MetaDefaults.next()){
            userDefaultMeta.put(results_MetaDefaults.getString("key"), results_MetaDefaults.getString("value"));
        }
        results_MetaDefaults.close();
    }
    
    public PlayerStatus getPlayerStatus(int playerID){
        if(connectedPlayers.getClientID(playerID) != -1){
            RunningGames runningGames = getAppState(GamesAppState.class).getRunningGames();
            if(runningGames.getGame(playerID) != null){
                return PlayerStatus.INGAME;
            }
            return PlayerStatus.ONLINE;
        }
        return PlayerStatus.OFFLINE;
    }

    public ConnectedPlayers getConnectedPlayers(){
        return connectedPlayers;
    }

    public HashMap<String, String> getUserDefaultMeta(){
        return userDefaultMeta;
    }
}
