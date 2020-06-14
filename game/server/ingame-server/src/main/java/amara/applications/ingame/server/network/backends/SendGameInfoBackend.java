/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.applications.master.network.messages.objects.GameSelectionPlayer;
import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.TeamGame;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SendGameInfoBackend implements MessageBackend {

    public SendGameInfoBackend(Game game) {
        this.game = game;
    }
    private Game game;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_JoinGame) {
            String mapName = game.getMap().getName();
            GameSelectionPlayer[][] teams = null;
            if (game instanceof TeamGame) {
                TeamGame teamGame = (TeamGame) game;
                teams = teamGame.getGameSelection().getTeams();
            }
            messageResponse.addAnswerMessage(new Message_GameInfo(mapName, teams));
        }
    }
}
