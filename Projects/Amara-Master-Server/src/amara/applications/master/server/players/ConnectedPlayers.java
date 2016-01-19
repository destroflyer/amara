/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.players;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Carl
 */
public class ConnectedPlayers{
    
    private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
    
    public void login(int clientID, Player player){
        players.put(clientID, player);
    }
    
    public void logout(int clientID){
        players.remove(clientID);
    }
    
    public Player getPlayer(int clientID){
        return players.get(clientID);
    }
    
    public int getClientID(int playerID){
        Iterator<Integer> clientIDIterator = players.keySet().iterator();
        while(clientIDIterator.hasNext()){
            int clientID = clientIDIterator.next();
            Player player = players.get(clientID);
            if(player.getID() == playerID){
                return clientID;
            }
        }
        return -1;
    }
}
