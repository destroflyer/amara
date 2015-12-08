/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.operators;

import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;
import amara.game.expressions.values.*;

/**
 *
 * @author Carl
 */
public class AddOperator extends Operator{

    @Override
    public Value getResult(Values values, Value value1, Value value2) throws ExpressionException{
        if((value1 instanceof NumericValue) && (value2 instanceof NumericValue)){
            double numeric1 = ((NumericValue) value1).getValue(values);
            double numeric2 = ((NumericValue) value2).getValue(values);
            return new NumericValue(numeric1 + numeric2);
        }
        else if((value1 instanceof StringValue) && (value2 instanceof StringValue)){
            String string1 = ((StringValue) value1).getValue(values);
            String string2 = ((StringValue) value2).getValue(values);
            return new StringValue(string1 + string2);
        }
        else if((value1 instanceof StringValue) && (value2 instanceof NumericValue)){
            String string1 = ((StringValue) value1).getValue(values);
            double numeric2 = ((NumericValue) value2).getValue(values);
            return new StringValue(string1 + numeric2);
        }
        else if((value1 instanceof NumericValue) && (value2 instanceof StringValue)){
            double numeric1 = ((NumericValue) value1).getValue(values);
            String string2 = ((StringValue) value2).getValue(values);
            return new StringValue(numeric1 + string2);
        }
        throw new WrongOperatorException();
    }
}
