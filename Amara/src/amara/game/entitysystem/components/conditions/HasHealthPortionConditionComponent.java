/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.conditions;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class HasHealthPortionConditionComponent{

    public HasHealthPortionConditionComponent(){
        
    }

    public HasHealthPortionConditionComponent(float portion, boolean lessOrMore, boolean allowEqual){
        this.lessOrMore = lessOrMore;
        this.portion = portion;
        this.allowEqual = allowEqual;
    }
    private float portion;
    private boolean lessOrMore;
    private boolean allowEqual;

    public float getPortion(){
        return portion;
    }

    public boolean isLessOrMore(){
        return lessOrMore;
    }

    public boolean isAllowEqual(){
        return allowEqual;
    }
}
