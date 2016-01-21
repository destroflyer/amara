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
public class CharacterKillsComponent{

    public CharacterKillsComponent(){
        
    }
    
    public CharacterKillsComponent(int kills){
        this.kills = kills;
    }
    private int kills;

    public int getKills(){
        return kills;
    }
}
