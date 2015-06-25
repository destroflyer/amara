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
public class ReactionComponent{

    public ReactionComponent(){
        
    }
    
    public ReactionComponent(String reaction, float remainingDuration){
        this.reaction = reaction;
        this.remainingDuration = remainingDuration;
    }
    private String reaction;
    private float remainingDuration;

    public String getReaction(){
        return reaction;
    }

    public float getRemainingDuration(){
        return remainingDuration;
    }
}