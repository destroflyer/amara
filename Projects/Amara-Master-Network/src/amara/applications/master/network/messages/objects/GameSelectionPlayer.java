/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class GameSelectionPlayer{

    public GameSelectionPlayer(){
        
    }
    
    public GameSelectionPlayer(LobbyPlayer lobbyPlayer, GameSelectionPlayerData playerData){
        this.lobbyPlayer= lobbyPlayer;
        this.playerData = playerData;
    }
    private LobbyPlayer lobbyPlayer;
    private GameSelectionPlayerData playerData;
    private boolean isLockedIn;

    public LobbyPlayer getLobbyPlayer() {
        return lobbyPlayer;
    }

    public void setPlayerData(GameSelectionPlayerData playerData){
        this.playerData = playerData;
    }

    public GameSelectionPlayerData getPlayerData(){
        return playerData;
    }

    public void lockIn(){
        isLockedIn = true;
    }

    public boolean isLockedIn(){
        return isLockedIn;
    }
}
