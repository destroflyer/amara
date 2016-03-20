/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

import amara.applications.ingame.shared.maps.*;
import amara.applications.master.network.messages.objects.*;
import amara.core.Util;

/**
 *
 * @author Carl
 */
public class Game{

    public Game(GameSelection gameSelection){
        this.map = MapFileHandler.load(gameSelection.getGameSelectionData().getMapName());
        this.gameSelection = gameSelection;
        TeamFormat teamFormat = gameSelection.getGameSelectionData().getTeamFormat();
        teams = new GamePlayer[teamFormat.getTeamsCount()][];
        int[][] authentificationKeys = generateAuthentificationKeys(teamFormat.getTeamSizes());
        for(int i=0;i<teams.length;i++){
            teams[i] = new GamePlayer[teamFormat.getTeamSize(i)];
            for(int r=0;r<teams[i].length;r++){
                teams[i][r] = new GamePlayer(gameSelection.getTeams()[i][r], authentificationKeys[i][r]);
            }
        }
    }
    public static int ENTITY = 0;
    private Map map;
    private GameSelection gameSelection;
    private GamePlayer[][] teams;
    private int port;
    private boolean isStarted;
    
    public GamePlayer onClientConnected(int clientID, int authentificationKey){
        for(GamePlayer[] team : teams){
            for(GamePlayer player : team){
                if(player.getAuthentificationKey() == authentificationKey){
                    player.setClientID(clientID);
                    return player;
                }
            }
        }
        return null;
    }
    
    public GamePlayer getPlayer(int clientID){
        for(GamePlayer[] team : teams){
            for(GamePlayer player : team){
                if(player.getClientID() == clientID){
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean areAllPlayersInitialized(){
        for(GamePlayer[] team : teams){
            for(GamePlayer player : team){
                if(!player.isInitialized()){
                    return false;
                }
            }
        }
        return true;
    }

    public Map getMap(){
        return map;
    }

    public GameSelection getGameSelection(){
        return gameSelection;
    }

    public GamePlayer[][] getTeams(){
        return teams;
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
    
    private static int[][] generateAuthentificationKeys(int[] counts){
        int[][] keys = new int[counts.length][];
        for(int i=0;i<keys.length;i++){
            keys[i] = new int[counts[i]];
        }
        for(int i=0;i<keys.length;i++){
            for(int r=0;r<keys[i].length;r++){
                int key;
                do{
                    key = (int) (Math.random() * 1000000000);
                }while(Util.containsArrayElement(keys, key));
                keys[i][r] = key;
            }
        }
        return keys;
    }
}
