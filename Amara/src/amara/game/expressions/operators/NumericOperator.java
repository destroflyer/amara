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
public abstract class NumericOperator extends Operator{

    @Override
    public Value getResult(Values values, Value value1, Value value2) throws ExpressionException{
        if((value1 instanceof NumericValue) && (value2 instanceof NumericValue)){
            double numeric1 = ((NumericValue) value1).getValue(values);
            double numeric2 = ((NumericValue) value2).getValue(values);
            return getResult(numeric1, numeric2);
        }
        throw new WrongOperatorException();
    }
    
    protected abstract Value getResult(double value1, double value2);
}
