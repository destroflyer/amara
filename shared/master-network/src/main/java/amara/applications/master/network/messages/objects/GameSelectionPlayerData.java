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
public class GameSelectionPlayerData{

    public GameSelectionPlayerData(){
        
    }

    public GameSelectionPlayerData(int characterID, int[][] mapSpellsIndices){
        this.characterID = characterID;
        this.mapSpellsIndices = mapSpellsIndices;
    }
    private int characterID;
    private int[][] mapSpellsIndices;

    public int getCharacterID(){
        return characterID;
    }

    public int[][] getMapSpellsIndices(){
        return mapSpellsIndices;
    }
}
