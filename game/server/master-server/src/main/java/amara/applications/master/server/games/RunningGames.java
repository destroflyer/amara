/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import java.util.LinkedList;

import amara.applications.ingame.shared.games.*;

/**
 *
 * @author Carl
 */
public class RunningGames {

    private LinkedList<Game> games = new LinkedList<>();

    public void registerGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public Game getGame(int playerId) {
        for (Game game : games) {
            for (GamePlayer player : game.getPlayers()) {
                GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
                if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                    GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
                    if (gamePlayerInfo_Human.getPlayerId() == playerId) {
                        return game;
                    }
                }
            }
        }
        return null;
    }
}
