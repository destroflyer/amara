/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.protocol;

import java.util.ArrayList;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Lobby{

    public Lobby(){
        
    }

    public Lobby(int ownerID, LobbyData lobbyData){
        this.ownerID = ownerID;
        this.lobbyData = lobbyData;
        addPlayer(ownerID);
    }
    private int ownerID;
    private LobbyData lobbyData;
    private ArrayList<LobbyPlayer> players = new ArrayList<LobbyPlayer>();

    public int getOwnerID(){
        return ownerID;
    }

    public void setLobbyData(LobbyData lobbyData){
        this.lobbyData = lobbyData;
    }

    public LobbyData getLobbyData(){
        return lobbyData;
    }

    public ArrayList<LobbyPlayer> getPlayers(){
        return players;
    }
    
    public void addPlayer(int playerID){
        players.add(new LobbyPlayer(playerID, new LobbyPlayerData("minion")));
    }
    
    public void removePlayer(int playerID){
        LobbyPlayer lobbyPlayer = getPlayer(playerID);
        if(lobbyPlayer != null){
            players.remove(lobbyPlayer);
        }
    }
    
    public void setPlayerData(int playerID, LobbyPlayerData lobbyPlayerData){
        LobbyPlayer lobbyPlayer = getPlayer(playerID);
        if(lobbyPlayer != null){
            lobbyPlayer.setPlayerData(lobbyPlayerData);
        }
    }
    
    public boolean containsPlayer(int playerID){
        return (getPlayer(playerID) != null);
    }
    
    public LobbyPlayer getPlayer(int playerID){
        for(LobbyPlayer player : players){
            if(player.getID() == playerID){
                return player;
            }
        }
        return null;
    }
}
