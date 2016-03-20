/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

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

    public Lobby(int ownerID){
        setOwnerID(ownerID);
        addPlayer(ownerID);
    }
    private int ownerID;
    private LobbyData lobbyData = new LobbyData("arama", new TeamFormat(1, 1));
    private ArrayList<Integer> players = new ArrayList<Integer>();

    public void setOwnerID(int ownerID){
        this.ownerID = ownerID;
    }

    public int getOwnerID(){
        return ownerID;
    }
    
    public void setLobbyData(LobbyData lobbyData){
        this.lobbyData = lobbyData;
    }    

    public LobbyData getLobbyData(){
        return lobbyData;
    }

    public ArrayList<Integer> getPlayers(){
        return players;
    }
    
    public void addPlayer(int playerID){
        players.add(playerID);
    }
    
    public void removePlayer(Integer playerID){
        players.remove(playerID);
    }
    
    public boolean containsPlayer(int playerID){
        return players.contains(playerID);
    }
}
