/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class IsVisibleForTeamsComponent{

    public IsVisibleForTeamsComponent(){
        
    }

    public IsVisibleForTeamsComponent(boolean[] teamVisions){
        this.isVisibleForTeams = teamVisions;
    }
    private boolean[] isVisibleForTeams;

    public boolean[] isVisibleForTeams(){
        return isVisibleForTeams;
    }
}