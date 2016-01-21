/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.scores;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CharacterAssistsComponent{

    public CharacterAssistsComponent(){
        
    }
    
    public CharacterAssistsComponent(int assists){
        this.assists = assists;
    }
    private int assists;

    public int getAssists(){
        return assists;
    }
}
