/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

import amara.applications.ingame.shared.maps.Map;

/**
 *
 * @author Carl
 */
public class Game{

    public Game(Map map, GamePlayer[] players){
        this.map = map;
        this.players = players;
    }
    public static int ENTITY = 0;
    private Map map;
    private GamePlayer[] players;
    private int port;
    private boolean isStarted;

    public Map getMap(){
        return map;
    }

    public GamePlayer[] getPlayers(){
        return players;
    }
    
    public GamePlayer onClientConnected(int clientID, int authentificationKey){
        for(GamePlayer player : players){
            if(player.getAuthentificationKey() == authentificationKey){
                player.setClientID(clientID);
                return player;
            }
        }
        return null;
    }
    
    public GamePlayer getPlayer(int clientID){
        for(GamePlayer player : players){
            if(player.getClientID() == clientID){
                return player;
            }
        }
        return null;
    }
    
    public boolean areAllPlayersInitialized(){
        for(GamePlayer player : players){
            if(!player.isInitialized()){
                return false;
            }
        }
        return true;
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getPort(){
        return port;
    }
    
    public void start(){
        isStarted = true;
    }

    public boolean isStarted(){
        return isStarted;
    }
}
