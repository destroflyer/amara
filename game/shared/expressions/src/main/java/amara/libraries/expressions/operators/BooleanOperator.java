/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.operators;

import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public abstract class BooleanOperator extends Operator{

    @Override
    public Value getResult(Values values, Value value1, Value value2) throws ExpressionException{
        if((value1 instanceof BooleanValue) && (value2 instanceof BooleanValue)){
            boolean boolean1 = ((BooleanValue) value1).getValue(values);
            boolean boolean2 = ((BooleanValue) value2).getValue(values);
            return new BooleanValue(getResult(boolean1, boolean2));
        }
        throw new WrongOperatorException();
    }
    
    protected abstract boolean getResult(boolean value1, boolean value2);
}
