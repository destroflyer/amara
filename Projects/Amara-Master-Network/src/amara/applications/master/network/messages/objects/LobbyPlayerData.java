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
public class LobbyPlayerData{

    public LobbyPlayerData(){
        
    }
    
    public LobbyPlayerData(int characterID){
        this.characterID = characterID;
    }
    private int characterID;

    public int getCharacterID(){
        return characterID;
    }
}
