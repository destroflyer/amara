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
public class ExperienceComponent{

    public ExperienceComponent(){
        
    }
    
    public ExperienceComponent(int experience){
        this.experience = experience;
    }
    private int experience;

    public int getExperience(){
        return experience;
    }
}
