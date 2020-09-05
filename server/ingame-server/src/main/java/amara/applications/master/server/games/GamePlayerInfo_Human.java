/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

/**
 *
 * @author Carl
 */
public class GamePlayerInfo_Human extends GamePlayerInfo {

    public GamePlayerInfo_Human(int playerId, int clientId) {
        this.playerId = playerId;
        this.clientId = clientId;
    }
    private int playerId;
    private int clientId;
    private boolean isReady;

    public int getPlayerId() {
        return playerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isReady() {
        return isReady;
    }
}
