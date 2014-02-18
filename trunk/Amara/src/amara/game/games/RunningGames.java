/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.games;

import java.util.ArrayList;
import amara.engine.applications.masterserver.server.network.PortProvider;

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

    public ArrayList<Game> getGames(){
        return games;
    }
}
