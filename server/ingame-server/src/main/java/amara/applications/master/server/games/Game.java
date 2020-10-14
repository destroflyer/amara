package amara.applications.master.server.games;

import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapFileHandler;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.network.SubNetworkServer;

import java.util.LinkedList;
import java.util.function.Predicate;

public abstract class Game {

    public Game(String mapName) {
        this.map = MapFileHandler.load(mapName);
    }
    private Map map;
    private LinkedList<GamePlayer<?>> players = new LinkedList<>();
    private IngameServerApplication ingameServerApplication;
    private boolean isStarted;

    public void addPlayer(GamePlayer<?> player) {
        players.add(player);
    }

    public void removePlayer(GamePlayer<?> player) {
        players.remove(player);
    }

    public GamePlayer<GamePlayerInfo_Human> getPlayerByClientId(int clientId) {
        return getHumanPlayer(gamePlayerInfo_Human -> gamePlayerInfo_Human.getClientId() == clientId);
    }

    public GamePlayer<GamePlayerInfo_Human> getPlayerByPlayerId(int playerId) {
        return getHumanPlayer(gamePlayerInfo_Human -> gamePlayerInfo_Human.getPlayerId() == playerId);
    }

    private GamePlayer<GamePlayerInfo_Human> getHumanPlayer(Predicate<GamePlayerInfo_Human> humanPlayerInfoPredicate) {
        for (GamePlayer<?> player : players) {
            GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
            if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
                if (humanPlayerInfoPredicate.test(gamePlayerInfo_Human)) {
                    return (GamePlayer<GamePlayerInfo_Human>) player;
                }
            }
        }
        return null;
    }

    public boolean areAllPlayersReady() {
        for (GamePlayer<?> player : players) {
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

    public LinkedList<GamePlayer<?>> getPlayers() {
        return players;
    }

    public void setIngameServerApplication(IngameServerApplication ingameServerApplication) {
        this.ingameServerApplication = ingameServerApplication;
    }

    public SubNetworkServer getSubNetworkServer() {
        return ingameServerApplication.getStateManager().getState(SubNetworkServerAppState.class).getSubNetworkServer();
    }

    public void start() {
        isStarted = true;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public abstract int getTeamsCount();
}
