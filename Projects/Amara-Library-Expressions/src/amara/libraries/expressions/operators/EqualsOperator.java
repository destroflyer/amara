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
public class EqualsOperator extends Operator{

    @Override
    public Value getResult(Values values, Value value1, Value value2) throws ExpressionException{
        if((value1 instanceof NumericValue) && (value2 instanceof NumericValue)){
            double numeric1 = ((NumericValue) value1).getValue(values);
            double numeric2 = ((NumericValue) value2).getValue(values);
            return new BooleanValue(numeric1 == numeric2);
        }
        if((value1 instanceof BooleanValue) && (value2 instanceof BooleanValue)){
            boolean boolean1 = ((BooleanValue) value1).getValue(values);
            boolean boolean2 = ((BooleanValue) value2).getValue(values);
            return new BooleanValue(boolean1 == boolean2);
        }
        if((value1 instanceof StringValue) && (value2 instanceof StringValue)){
            String string1 = ((StringValue) value1).getValue(values);
            String string2 = ((StringValue) value2).getValue(values);
            return new BooleanValue(string1.equals(string2));
        }
        throw new WrongOperatorException();
    }
}
