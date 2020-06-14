/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import amara.applications.master.network.messages.objects.*;

import java.util.function.Function;

/**
 *
 * @author Carl
 */
public class TeamGame extends Game {

    public TeamGame(GameSelection gameSelection, Function<Integer, Integer> playerToClientId) {
        super(gameSelection.getGameSelectionData().getMapName());
        this.gameSelection = gameSelection;
        addPlayersFromGameSelection(playerToClientId);
    }
    private GameSelection gameSelection;

    private void addPlayersFromGameSelection(Function<Integer, Integer> playerToClientId) {
        TeamFormat teamFormat = gameSelection.getGameSelectionData().getTeamFormat();
        for (int i = 0; i < teamFormat.getTeamsCount(); i++) {
            for (int r = 0; r < teamFormat.getTeamSize(i); r++) {
                GameSelectionPlayer gameSelectionPlayer = gameSelection.getTeams()[i][r];
                LobbyPlayer lobbyPlayer = gameSelectionPlayer.getLobbyPlayer();
                GamePlayerInfo gamePlayerInfo = null;
                if (lobbyPlayer instanceof LobbyPlayer_Human) {
                    LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                    int playerId = lobbyPlayer_Human.getPlayerId();
                    int clientId = playerToClientId.apply(playerId);
                    gamePlayerInfo = new GamePlayerInfo_Human(playerId, clientId);
                } else if (lobbyPlayer instanceof LobbyPlayer_Bot) {
                    LobbyPlayer_Bot lobbyPlayer_Bot = (LobbyPlayer_Bot) lobbyPlayer;
                    gamePlayerInfo = new GamePlayerInfo_Bot(lobbyPlayer_Bot.getBotType(), lobbyPlayer_Bot.getName());
                }
                addPlayer(new GamePlayer(gamePlayerInfo, gameSelectionPlayer.getPlayerData()));
            }
        }
    }

    @Override
    public int getTeamsCount() {
        return gameSelection.getTeams().length;
    }

    public GameSelection getGameSelection() {
        return gameSelection;
    }
}
