/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects;

import com.jme3.network.serializing.Serializable;
import amara.game.expressions.Values;

/**
 *
 * @author Carl
 */
@Serializable
public class CustomEffectValuesComponent{

    public CustomEffectValuesComponent(){
        
    }
    
    public CustomEffectValuesComponent(Values values){
        this.values = values;
    }
    private Values values;

    public Values getValues(){
        return values;
    }
}
