/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.shared.games.*;
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
            GamePlayer player = game.getPlayerByClientId(messageResponse.getClientId());
            if (player != null) {
                GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) player.getGamePlayerInfo();
                gamePlayerInfo_Human.setReady(true);
                System.out.println("Player #" + gamePlayerInfo_Human.getPlayerId() + " (Client #" + gamePlayerInfo_Human.getClientId() + ") is ready.");
                if (game.isStarted()) {
                    System.out.println("Game is already started, sending info to player #" + gamePlayerInfo_Human.getPlayerId() + " (Client #" + gamePlayerInfo_Human.getClientId() + ").");
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
