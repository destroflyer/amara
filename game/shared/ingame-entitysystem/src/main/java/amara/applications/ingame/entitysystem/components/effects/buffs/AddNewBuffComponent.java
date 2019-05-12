/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AddNewBuffComponent{

    public AddNewBuffComponent(){
        
    }

    public AddNewBuffComponent(String templateExpression, float duration){
        this.templateExpression = templateExpression;
        this.duration = duration;
    }
    @ComponentField(type=ComponentField.Type.EXPRESSION)
    private String templateExpression;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public String getTemplateExpression(){
        return templateExpression;
    }

    public float getDuration(){
        return duration;
    }
}
