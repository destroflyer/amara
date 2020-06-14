/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapFileHandler;

import java.util.LinkedList;

/**
 *
 * @author Carl
 */
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
        for (GamePlayer<?> player : players) {
            GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
            if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
                if (gamePlayerInfo_Human.getClientId() == clientId) {
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

    public IngameServerApplication getIngameServerApplication() {
        return ingameServerApplication;
    }

    public void start() {
        isStarted = true;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public abstract int getTeamsCount();
}
