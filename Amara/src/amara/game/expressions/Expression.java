/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions;

import amara.game.expressions.exceptions.*;
import amara.game.expressions.values.*;

/**
 *
 * @author Carl
 */
public abstract class Expression extends Value{

    @Override
    public <T> T getValue(Values values, Class<T> datatype) throws ExpressionException{
        return getResult(values).getValue(values, datatype);
    }
    
    public abstract Value getResult(Values values) throws ExpressionException;
    
    public static Value resolveValue(Values values, Value value) throws ExpressionException{
        Value result = value;
        if(result instanceof VariableValue){
            VariableValue variableValue = (VariableValue) result;
            result = variableValue.getVariableValue(values);
        }
        else if(result instanceof Expression){
            Expression expression = (Expression) result;
            result = expression.getResult(values);
        }
        return result;
    }
}
