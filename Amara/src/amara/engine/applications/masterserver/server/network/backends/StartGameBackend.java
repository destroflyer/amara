/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import com.jme3.system.JmeContext;
import amara.Util;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.engine.applications.ingame.server.appstates.NetworkServerAppState;
import amara.engine.applications.masterserver.server.MasterserverServerApplication;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.network.*;
import amara.game.games.*;
import amara.game.maps.TestMap;
import amara.game.players.ConnectedPlayers;

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
            int[] authentificationKeys = generateAuthentificationKeys(message.getPlayerIDs().length);
            GamePlayer[] players = new GamePlayer[message.getPlayerIDs().length];
            for(int i=0;i<players.length;i++){
                players[i] = new GamePlayer(message.getPlayerIDs()[i], authentificationKeys[i]);
            }
            Game game = new Game(new TestMap(), players);
            RunningGames runningGames = mainApplication.getStateManager().getState(GamesAppState.class).getRunningGames();
            runningGames.registerGame(game);
            IngameServerApplication ingameServerApplication = new IngameServerApplication(mainApplication, game);
            ingameServerApplication.start(JmeContext.Type.Headless);
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
