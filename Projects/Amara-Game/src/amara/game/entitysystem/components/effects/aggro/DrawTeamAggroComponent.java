/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.aggro;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class DrawTeamAggroComponent{

    public DrawTeamAggroComponent(){
        
    }

    public DrawTeamAggroComponent(float range){
        this.range = range;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float range;

    public float getRange(){
        return range;
    }
}
