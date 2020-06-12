/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

import amara.applications.master.network.messages.objects.GameSelectionPlayerData;

/**
 *
 * @author Carl
 */
public class GamePlayer {

    public GamePlayer(GamePlayerInfo gamePlayerInfo, GameSelectionPlayerData gameSelectionPlayerData) {
        this.gamePlayerInfo = gamePlayerInfo;
        this.gameSelectionPlayerData = gameSelectionPlayerData;
    }
    private GamePlayerInfo gamePlayerInfo;
    private GameSelectionPlayerData gameSelectionPlayerData;
    private int entity = -1;

    public GamePlayerInfo getGamePlayerInfo() {
        return gamePlayerInfo;
    }

    public GameSelectionPlayerData getGameSelectionPlayerData() {
        return gameSelectionPlayerData;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    public int getEntity() {
        return entity;
    }
}
