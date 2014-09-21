/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.crowdcontrol.knockup;

import amara.game.entitysystem.components.effects.crowdcontrol.*;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class KnockupHeightComponent{

    public KnockupHeightComponent(){
        
    }

    public KnockupHeightComponent(float height){
        this.height = height;
    }
    private float height;

    public float getHeight(){
        return height;
    }
}
