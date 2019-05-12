/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.game;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CinematicComponent{

    public CinematicComponent(){
        
    }

    public CinematicComponent(String cinematicClassName){
        this.cinematicClassName = cinematicClassName;
    }
    private String cinematicClassName;

    public String getCinematicClassName(){
        return cinematicClassName;
    }
}
