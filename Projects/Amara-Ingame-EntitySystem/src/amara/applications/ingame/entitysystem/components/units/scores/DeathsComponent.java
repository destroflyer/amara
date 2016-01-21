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
public class DeathsComponent{

    public DeathsComponent(){
        
    }
    
    public DeathsComponent(int deaths){
        this.deaths = deaths;
    }
    private int deaths;

    public int getDeaths(){
        return deaths;
    }
}
