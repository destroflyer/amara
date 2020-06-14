/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Carl
 */
public class RunningGames {

    private HashMap<String, Game> mmoGames = new HashMap<>();
    private LinkedList<Game> teamGames = new LinkedList<>();

    public void addGame(Game game) {
        if (game instanceof MMOGame) {
            mmoGames.put(game.getMap().getName(), game);
        } else {
            teamGames.add(game);
        }
    }

    public void removeGame(Game game) {
        boolean wasTeamGame = teamGames.remove(game);
        if (!wasTeamGame) {
            for (Map.Entry<String, Game> entry : mmoGames.entrySet()) {
                if (entry.getValue() == game) {
                    mmoGames.remove(entry.getKey());
                    break;
                }
            }
        }
    }

    public Game getMMOGame(String mapName) {
        return mmoGames.get(mapName);
    }

    public Game getGame(int playerId) {
        Game teamGame = getTeamGame(playerId);
        if (teamGame != null) {
            return teamGame;
        } else {
            return getMMOGame(playerId);
        }
    }

    private Game getTeamGame(int playerId) {
        return teamGames.stream()
                .filter(game -> isInGame(game, playerId)).findAny()
                .orElse(null);
    }

    private Game getMMOGame(int playerId) {
        return mmoGames.values().stream()
                .filter(game -> isInGame(game, playerId)).findAny()
                .orElse(null);
    }

    private boolean isInGame(Game game, int playerId) {
        for (GamePlayer player : game.getPlayers()) {
            GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
            if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
                if (gamePlayerInfo_Human.getPlayerId() == playerId) {
                    return true;
                }
            }
        }
        return false;
    }
}
