/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units.bounties;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BountyExperienceComponent{

    public BountyExperienceComponent(){
        
    }
    
    public BountyExperienceComponent(int experience){
        this.experience = experience;
    }
    private int experience;

    public int getExperience(){
        return experience;
    }
}
