/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.GamePlayer;
import amara.applications.master.server.games.GamePlayerInfo_Human;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class StartGameBackend implements MessageBackend {

    public StartGameBackend(Game game) {
        this.game = game;
    }
    private Game game;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_ClientReady) {
            GamePlayer<GamePlayerInfo_Human> player = game.getPlayerByClientId(messageResponse.getClientId());
            if (player != null) {
                GamePlayerInfo_Human gamePlayerInfo = player.getGamePlayerInfo();
                gamePlayerInfo.setReady(true);
                System.out.println("Player #" + gamePlayerInfo.getPlayerId() + " (Client #" + gamePlayerInfo.getClientId() + ") is ready.");
                if (game.isStarted()) {
                    System.out.println("Game is already started, sending info to player #" + gamePlayerInfo.getPlayerId() + " (Client #" + gamePlayerInfo.getClientId() + ").");
                    messageResponse.addBroadcastMessage(new Message_GameStarted(player.getEntity()));
                } else if (game.areAllPlayersReady()) {
                    System.out.println("All players ready, start game.");
                    game.start();
                    messageResponse.addBroadcastMessage(new Message_GameStarted(player.getEntity()));
                }
            }
        }
    }
}
