/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.shared.games.*;
import amara.libraries.network.*;

import java.util.function.Function;

/**
 *
 * @author Carl
 */
public class SendGameInfoBackend implements MessageBackend {

    public SendGameInfoBackend(Game game, Function<Integer, Integer> clientToPlayerId) {
        this.game = game;
        this.clientToPlayerId = clientToPlayerId;
    }
    private Game game;
    private Function<Integer, Integer> clientToPlayerId;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_JoinGame) {
            int clientId = messageResponse.getClientId();
            Integer playerId = clientToPlayerId.apply(clientId);
            if (playerId != null) {
                GamePlayer player = game.getPlayerByPlayerId(playerId);
                if (player != null) {
                    GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) player.getGamePlayerInfo();
                    gamePlayerInfo_Human.setClientId(clientId);
                    messageResponse.addAnswerMessage(new Message_GameInfo(game.getGameSelection()));
                }
            }
        }
    }
}
