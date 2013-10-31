/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TeamComponent{

    public TeamComponent(){
        
    }
    
    public TeamComponent(int teamEntityID){
        this.teamEntityID = teamEntityID;
    }
    private int teamEntityID;

    public int getTeamEntityID(){
        return teamEntityID;
    }
}
