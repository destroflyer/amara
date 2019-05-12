/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

import java.util.LinkedList;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class GameSelection{

    public GameSelection(){
        
    }

    public GameSelection(GameSelectionData gameSelectionData){
        this.gameSelectionData = gameSelectionData;
        teams = new GameSelectionPlayer[gameSelectionData.getTeamFormat().getTeamsCount()][];
        remainingTeamSpots = new int[teams.length];
        for(int i=0;i<teams.length;i++){
            int teamSize = gameSelectionData.getTeamFormat().getTeamSize(i);
            teams[i] = new GameSelectionPlayer[teamSize];
            remainingTeamSpots[i] = teamSize;
        }
    }
    private GameSelectionData gameSelectionData;
    private GameSelectionPlayer[][] teams;
    private LinkedList<Lobby> lobbies = new LinkedList<Lobby>();
    private int[] remainingTeamSpots;
    
    public boolean isMatchingLobby(Lobby lobby){
        LobbyData lobbyData = lobby.getLobbyData();
        return (gameSelectionData.getMapName().equals(lobbyData.getMapName())
             && gameSelectionData.getTeamFormat().equals(lobbyData.getTeamFormat()));
    }
    
    public void addLobby(int teamIndex, Lobby lobby){
        lobbies.add(lobby);
        int lobbyPlayerIndex = 0;
        for(int i=0;i<teams[teamIndex].length;i++){
            GameSelectionPlayer player = teams[teamIndex][i];
            if(player == null){
                LobbyPlayer lobbyPlayer = lobby.getPlayers().get(lobbyPlayerIndex);
                teams[teamIndex][i] = new GameSelectionPlayer(lobbyPlayer, new GameSelectionPlayerData(-1, null));
                lobbyPlayerIndex++;
                if(lobbyPlayerIndex >= lobby.getPlayers().size()){
                    break;
                }
            }
        }
        remainingTeamSpots[teamIndex] -= lobby.getPlayers().size();
    }
    
    public void setPlayerData(int playerID, GameSelectionPlayerData gameSelectionPlayerData){
        GameSelectionPlayer gameSelectionPlayer = getPlayer(playerID);
        if(gameSelectionPlayer != null){
            gameSelectionPlayer.setPlayerData(gameSelectionPlayerData);
        }
    }
    
    public GameSelectionPlayer getPlayer(int playerID){
        for(GameSelectionPlayer[] team : teams){
            for(GameSelectionPlayer player : team){
                LobbyPlayer lobbyPlayer = player.getLobbyPlayer();
                if(lobbyPlayer instanceof LobbyPlayer_Human){
                    LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                    if(lobbyPlayer_Human.getPlayerID() == playerID){
                        return player;
                    }
                }
            }
        }
        return null;
    }

    public GameSelectionData getGameSelectionData(){
        return gameSelectionData;
    }

    public GameSelectionPlayer[][] getTeams(){
        return teams;
    }

    public LinkedList<Lobby> getLobbies(){
        return lobbies;
    }

    public int[] getRemainingTeamSpots(){
        return remainingTeamSpots;
    }
    
    public boolean areTeamsFilled(){
        for(int i=0;i<remainingTeamSpots.length;i++){
            if(remainingTeamSpots[i] != 0){
                return false;
            }
        }
        return true;
    }
    
    public boolean areTeamsLockedIn(){
        for(GameSelectionPlayer[] team : teams){
            for(GameSelectionPlayer player : team){
                if(!player.isLockedIn()){
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getPlayersCount(){
        int playersCount = 0;
        for(GameSelectionPlayer[] team : teams){
            playersCount += team.length;
        }
        return playersCount;
    }
    
    //[jME 3.1 SNAPSHOT] A nested array with length 0 isn't sent correctly
    public void repairOnUnserialize(){
        for(int i=0;i<teams.length;i++){
            GameSelectionPlayer[] team = teams[i];
            if((team.length > 0) && (team[0] == null)){
                teams[i] = new GameSelectionPlayer[0];
            }
        }
    }
}
