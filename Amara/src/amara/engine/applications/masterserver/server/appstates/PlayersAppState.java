/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import amara.engine.applications.*;
import amara.engine.applications.ingame.server.appstates.NetworkServerAppState;
import amara.engine.applications.masterserver.server.network.backends.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.encoding.*;
import amara.engine.files.FileManager;
import amara.engine.network.NetworkServer;
import amara.game.games.RunningGames;
import amara.game.players.ConnectedPlayers;

/**
 *
 * @author Carl
 */
public class PlayersAppState extends ServerBaseAppState{

    private Encoder passwordEncoder;
    private ConnectedPlayers connectedPlayers = new ConnectedPlayers();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        initializePasswordEncoder();
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        PlayersContentsAppState playersContentsAppState = getAppState(PlayersContentsAppState.class);
        networkServer.addMessageBackend(new ReceiveLoginsBackend(databaseAppState, this));
        networkServer.addMessageBackend(new ReceiveLogoutsBackend(connectedPlayers));
        networkServer.addMessageBackend(new SendPlayerProfilesDataBackend(databaseAppState));
        networkServer.addMessageBackend(new SendPlayerStatusesBackend(this));
        networkServer.addMessageBackend(new EditUserMetaBackend(databaseAppState, connectedPlayers));
        networkServer.addMessageBackend(new SendGameContentsBackend(databaseAppState, connectedPlayers, playersContentsAppState));
        networkServer.addMessageBackend(new EditActiveCharacterSkinsBackend(databaseAppState, connectedPlayers));
    }
    
    private void initializePasswordEncoder(){
        String[] keyPartLines = FileManager.getFileLines("./key_to_the_city.ini");
        long keyPart1 = Long.parseLong(keyPartLines[0]);
        long keyPart2 = Long.parseLong(keyPartLines[1]);
        passwordEncoder = new AES_Encoder(keyPart1, keyPart2);
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

    public Encoder getPasswordEncoder(){
        return passwordEncoder;
    }

    public ConnectedPlayers getConnectedPlayers(){
        return connectedPlayers;
    }
}
