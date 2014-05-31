/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.protocol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class LobbyPlayer{

    public LobbyPlayer(){
        
    }
    
    public LobbyPlayer(int id, LobbyPlayerData playerData){
        this.id = id;
        this.playerData = playerData;
    }
    private int id;
    private LobbyPlayerData playerData;

    public int getID(){
        return id;
    }

    public void setPlayerData(LobbyPlayerData playerData){
        this.playerData = playerData;
    }

    public LobbyPlayerData getPlayerData(){
        return playerData;
    }
}
