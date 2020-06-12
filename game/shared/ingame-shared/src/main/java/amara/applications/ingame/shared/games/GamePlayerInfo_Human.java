/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

/**
 *
 * @author Carl
 */
public class GamePlayerInfo_Human extends GamePlayerInfo {

    public GamePlayerInfo_Human(int playerId) {
        this.playerId = playerId;
    }
    private int playerId;
    private Integer clientId;
    private boolean isReady;

    public int getPlayerId() {
        return playerId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isReady() {
        return isReady;
    }
}
