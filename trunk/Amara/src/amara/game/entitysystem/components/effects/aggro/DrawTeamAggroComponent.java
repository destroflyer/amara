/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.aggro;

import com.jme3.network.serializing.Serializable;

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
    private float range;

    public float getRange(){
        return range;
    }
}
