/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.heals;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class HealComponent{

    public HealComponent(){
        
    }
    
    public HealComponent(String expression){
        this.expression = expression;
    }
    @ComponentField(type=ComponentField.Type.EXPRESSION)
    private String expression;

    public String getExpression(){
        return expression;
    }
}
