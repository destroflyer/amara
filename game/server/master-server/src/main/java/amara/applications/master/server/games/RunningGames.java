/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import java.util.ArrayList;
import amara.applications.ingame.shared.games.*;
import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
public class RunningGames{

    private ArrayList<Game> games = new ArrayList<>();
    
    public void registerGame(Game game){
        games.add(game);
    }
    
    public void removeGame(Game game){
        games.remove(game);
    }
    
    public Game getGame(int playerID){
        for(Game game : games){
            for(GamePlayer[] team : game.getTeams()){
                for(GamePlayer player : team){
                    LobbyPlayer lobbyPlayer = player.getGameSelectionPlayer().getLobbyPlayer();
                    if(lobbyPlayer instanceof LobbyPlayer_Human){
                        LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                        if(((LobbyPlayer_Human) lobbyPlayer).getPlayerID() == playerID){
                            return game;
                        }
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Game> getGames(){
        return games;
    }
}
