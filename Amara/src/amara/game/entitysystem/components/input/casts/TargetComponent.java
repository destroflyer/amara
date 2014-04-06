/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.input.casts;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TargetComponent{

    public TargetComponent(){
        
    }

    public TargetComponent(int targetEntity){
        this.targetEntity = targetEntity;
    }
    private int targetEntity;

    public int getTargetEntity(){
        return targetEntity;
    }
}
