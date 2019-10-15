/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.players;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class DisplayPlayerAnnouncementComponent {

    public DisplayPlayerAnnouncementComponent(){

    }

    public DisplayPlayerAnnouncementComponent(String expression, float remainingDuration){
        this.expression = expression;
        this.remainingDuration = remainingDuration;
    }
    @ComponentField(type=ComponentField.Type.EXPRESSION)
    private String expression;
    @ComponentField(type= ComponentField.Type.TIMER)
    private float remainingDuration;

    public String getExpression(){
        return expression;
    }

    public float getRemainingDuration(){
        return remainingDuration;
    }
}
