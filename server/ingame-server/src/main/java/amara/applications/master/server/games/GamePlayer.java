/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import amara.applications.master.network.messages.objects.GameSelectionPlayerData;

/**
 *
 * @author Carl
 */
public class GamePlayer<T extends GamePlayerInfo> {

    public GamePlayer(T gamePlayerInfo, GameSelectionPlayerData gameSelectionPlayerData) {
        this.gamePlayerInfo = gamePlayerInfo;
        this.gameSelectionPlayerData = gameSelectionPlayerData;
    }
    private T gamePlayerInfo;
    private GameSelectionPlayerData gameSelectionPlayerData;
    private int entity = -1;

    public T getGamePlayerInfo() {
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
