package amara.applications.master.server.players;

import java.util.HashMap;

public class ConnectedPlayers {

    private HashMap<Integer, Player> players = new HashMap<>();

    public void login(int clientID, Player player) {
        players.put(clientID, player);
    }

    public void logout(int clientId){
        players.remove(clientId);
    }

    public Player getPlayer(int clientId) {
        return players.get(clientId);
    }

    public int getClientID(int playerId) {
        for (int clientId : players.keySet()) {
            Player player = players.get(clientId);
            if (player.getID() == playerId) {
                return clientId;
            }
        }
        return -1;
    }
}
