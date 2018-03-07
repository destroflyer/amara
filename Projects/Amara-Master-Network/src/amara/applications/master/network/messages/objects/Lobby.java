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

    public Lobby(LobbyPlayer_Human owner){
        setOwner(owner);
        addPlayer(owner);
    }
    private int nextLobbyPlayerID;
    private LobbyPlayer_Human owner;
    private LobbyData lobbyData = new LobbyData("arama", new TeamFormat(1, 1));
    private ArrayList<LobbyPlayer> players = new ArrayList<>();

    public void setOwner(LobbyPlayer_Human owner){
        this.owner = owner;
    }

    public LobbyPlayer_Human getOwner(){
        return owner;
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
    
    public void addPlayer(LobbyPlayer player){
        player.setLobbyPlayerID(nextLobbyPlayerID++);
        players.add(player);
    }
    
    public LobbyPlayer_Human removeHumanPlayer(int playerID){
        LobbyPlayer_Human lobbyPlayer_Human = getHumanPlayerByPlayerID(playerID);
        if(lobbyPlayer_Human != null){
            removePlayer(lobbyPlayer_Human);
            return lobbyPlayer_Human;
        }
        return null;
    }
    
    public LobbyPlayer removePlayerByLobbyPlayerID(int lobbyPlayerID){
        LobbyPlayer lobbyPlayer = getPlayerByLobbyPlayerID(lobbyPlayerID);
        if(lobbyPlayer != null){
            removePlayer(lobbyPlayer);
            return lobbyPlayer;
        }
        return null;
    }
    
    public boolean removePlayer(LobbyPlayer lobbyPlayer){
        return players.remove(lobbyPlayer);
    }
    
    public boolean containsHumanPlayer(int playerID){
        return (getHumanPlayerByPlayerID(playerID) != null);
    }
    
    private LobbyPlayer_Human getHumanPlayerByPlayerID(int playerID){
        for (LobbyPlayer lobbyPlayer : players) {
            if (lobbyPlayer instanceof LobbyPlayer_Human) {
                LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                if (lobbyPlayer_Human.getPlayerID() == playerID) {
                    return lobbyPlayer_Human;
                }
            }
        }
        return null;
    }
    
    private LobbyPlayer getPlayerByLobbyPlayerID(int lobbyPlayerID){
        return players.stream().filter(lobbyPlayer -> (lobbyPlayer.getLobbyPlayerID() == lobbyPlayerID)).findAny().orElse(null);
    }
}
