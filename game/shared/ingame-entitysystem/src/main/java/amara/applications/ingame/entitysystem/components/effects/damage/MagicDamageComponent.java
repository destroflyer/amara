/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.damage;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class MagicDamageComponent{

    public MagicDamageComponent(){
        
    }

    public MagicDamageComponent(String expression){
        this.expression = expression;
    }
    @ComponentField(type=ComponentField.Type.EXPRESSION)
    private String expression;

    public String getExpression(){
        return expression;
    }
}
