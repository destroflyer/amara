package amara.applications.master.server.games;

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

    public void setClientId(int clientId) {
        this.clientId = clientId;
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
