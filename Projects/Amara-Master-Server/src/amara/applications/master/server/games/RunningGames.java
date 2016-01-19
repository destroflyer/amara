/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import java.util.ArrayList;
import amara.applications.ingame.shared.games.*;
import amara.applications.master.server.network.PortProvider;

/**
 *
 * @author Carl
 */
public class RunningGames{

    public RunningGames(PortProvider portProvider){
        this.portProvider = portProvider;
    }
    private PortProvider portProvider;
    private ArrayList<Game> games = new ArrayList<Game>();
    
    public void registerGame(Game game){
        game.setPort(portProvider.requestPort());
        games.add(game);
    }
    
    public void removeGame(Game game){
        games.remove(game);
    }
    
    public Game getGame(int playerID){
        for(Game game : games){
            for(GamePlayer player : game.getPlayers()){
                if(player.getLobbyPlayer().getID() == playerID){
                    return game;
                }
            }
        }
        return null;
    }

    public ArrayList<Game> getGames(){
        return games;
    }
}
