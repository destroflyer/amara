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
    
    public GameSelectionPlayer(int id, GameSelectionPlayerData playerData){
        this.id = id;
        this.playerData = playerData;
    }
    private int id;
    private GameSelectionPlayerData playerData;
    private boolean isLockedIn;

    public int getID(){
        return id;
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
