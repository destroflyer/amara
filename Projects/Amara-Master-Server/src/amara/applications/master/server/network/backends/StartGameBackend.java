/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import java.util.ArrayList;
import com.jme3.network.Message;
import amara.applications.ingame.shared.games.*;
import amara.applications.ingame.shared.maps.*;
import amara.applications.master.network.messages.Message_StartGame;
import amara.applications.master.network.messages.objects.*;
import amara.applications.master.server.MasterserverServerApplication;
import amara.applications.master.server.appstates.*;
import amara.core.Util;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.libraries.network.*;

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
