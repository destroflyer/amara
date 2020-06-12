/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

import amara.applications.ingame.shared.maps.*;
import amara.applications.master.network.messages.objects.*;

import java.util.LinkedList;
import java.util.function.Predicate;

/**
 *
 * @author Carl
 */
public class Game {

    public Game(GameSelection gameSelection) {
        this.map = MapFileHandler.load(gameSelection.getGameSelectionData().getMapName());
        this.gameSelection = gameSelection;
        addPlayersFromGameSelection();
    }
    public static int ENTITY = 0;
    private Map map;
    private LinkedList<GamePlayer> players = new LinkedList<>();
    private GameSelection gameSelection;
    private boolean isStarted;

    private void addPlayersFromGameSelection() {
        TeamFormat teamFormat = gameSelection.getGameSelectionData().getTeamFormat();
        for (int i = 0; i < teamFormat.getTeamsCount(); i++) {
            for (int r = 0; r < teamFormat.getTeamSize(i); r++) {
                GameSelectionPlayer gameSelectionPlayer = gameSelection.getTeams()[i][r];
                LobbyPlayer lobbyPlayer = gameSelectionPlayer.getLobbyPlayer();
                GamePlayerInfo gamePlayerInfo = null;
                if (lobbyPlayer instanceof LobbyPlayer_Human) {
                    LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                    gamePlayerInfo = new GamePlayerInfo_Human(lobbyPlayer_Human.getPlayerId());
                } else if (lobbyPlayer instanceof LobbyPlayer_Bot) {
                    LobbyPlayer_Bot lobbyPlayer_Bot = (LobbyPlayer_Bot) lobbyPlayer;
                    gamePlayerInfo = new GamePlayerInfo_Bot(lobbyPlayer_Bot.getBotType(), lobbyPlayer_Bot.getName());
                }
                addPlayer(new GamePlayer(gamePlayerInfo, gameSelectionPlayer.getPlayerData()));
            }
        }
    }

    public void addPlayer(GamePlayer player) {
        players.add(player);
    }

    public void removePlayer(GamePlayer player) {
        players.remove(player);
    }

    public GamePlayer getPlayerByClientId(int clientId) {
        return getHumanPlayerBy(gamePlayerInfo_Human -> gamePlayerInfo_Human.getClientId() == clientId);
    }

    public GamePlayer getPlayerByPlayerId(int playerId) {
        return getHumanPlayerBy(gamePlayerInfo_Human -> gamePlayerInfo_Human.getPlayerId() == playerId);
    }

    private GamePlayer getHumanPlayerBy(Predicate<GamePlayerInfo_Human> predicate) {
        for (GamePlayer player : players) {
            GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
            if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
                if (predicate.test(gamePlayerInfo_Human)) {
                    return player;
                }
            }
        }
        return null;
    }

    public boolean areAllPlayersReady() {
        for (GamePlayer player : players) {
            GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
            if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
                if (!gamePlayerInfo_Human.isReady()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map getMap() {
        return map;
    }

    public GameSelection getGameSelection() {
        return gameSelection;
    }

    public LinkedList<GamePlayer> getPlayers() {
        return players;
    }
    
    public void start() {
        isStarted = true;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
