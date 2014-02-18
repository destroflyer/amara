/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.games;

import amara.game.maps.Map;

/**
 *
 * @author Carl
 */
public class Game{

    public Game(Map map, GamePlayer[] players){
        this.map = map;
        this.players = players;
    }
    private Map map;
    private GamePlayer[] players;
    private int port;

    public Map getMap(){
        return map;
    }

    public GamePlayer[] getPlayers(){
        return players;
    }
    
    public GamePlayer getPlayer(int authentificationKey){
        for(GamePlayer player : players){
            if(player.getAuthentificationKey() == authentificationKey){
                return player;
            }
        }
        return null;
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getPort(){
        return port;
    }
}
