/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import java.util.ArrayList;
import com.jme3.network.Message;
import amara.core.Util;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.*;
import amara.game.games.*;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class StartGameBackend implements MessageBackend{

    public StartGameBackend(MasterserverServerApplication mainApplication){
        this.mainApplication = mainApplication;
    }
    private MasterserverServerApplication mainApplication;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_StartGame){
            Message_StartGame message = (Message_StartGame) receivedMessage;
            PlayersAppState playersAppState = mainApplication.getStateManager().getState(PlayersAppState.class);
            int ownerID = playersAppState.getConnectedPlayers().getPlayer(messageResponse.getClientID()).getID();
            Lobby lobby = mainApplication.getStateManager().getState(LobbiesAppState.class).getLobby(ownerID);
            if(lobby != null){
                ArrayList<LobbyPlayer> lobbyPlayers = lobby.getPlayers();
                GamePlayer[] players = new GamePlayer[lobbyPlayers.size()];
                int[] authentificationKeys = generateAuthentificationKeys(lobbyPlayers.size());
                for(int i=0;i<players.length;i++){
                    players[i] = new GamePlayer(lobbyPlayers.get(i), authentificationKeys[i]);
                }
                LobbyData lobbyData = lobby.getLobbyData();
                Map map = MapFileHandler.load(lobbyData.getMapName());
                Game game = new Game(map, players);
                GamesAppState gamesAppState = mainApplication.getStateManager().getState(GamesAppState.class);
                gamesAppState.onGameStartRequested(game);
                IngameServerApplication ingameServerApplication = null;
                try{
                    ingameServerApplication = new IngameServerApplication(mainApplication, game);
                    ingameServerApplication.start();
                }catch(Exception ex){
                    gamesAppState.onGameCrashed(ingameServerApplication, ex);
                }
            }
        }
    }
    
    private int[] generateAuthentificationKeys(int count){
        int[] keys = new int[count];
        for(int i=0;i<keys.length;i++){
            int key;
            do{
                key = (int) (Math.random() * 1000000000);
            }while(Util.containsArrayElement(keys, key));
            keys[i] = key;
        }
        return keys;
    }
}
