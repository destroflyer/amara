/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.damage;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class PhysicalDamageComponent{

    public PhysicalDamageComponent(){
        
    }
    
    public PhysicalDamageComponent(String expression){
        this.expression = expression;
    }
    @ComponentField(type=ComponentField.Type.EXPRESSION)
    private String expression;

    public String getExpression(){
        return expression;
    }
}
