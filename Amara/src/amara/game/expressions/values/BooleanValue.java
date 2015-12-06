/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.values;

import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public class BooleanValue extends Value implements CastableBoolean{

    public BooleanValue(boolean value){
        this.value = value;
    }
    private boolean value;

    @Override
    public boolean getValue(Values values) throws ExpressionException{
        return value;
    }

    @Override
    public String toString(){
        return "[boolean " + value + "]";
    }
}
