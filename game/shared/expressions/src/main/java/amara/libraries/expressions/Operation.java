/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions;

import amara.libraries.expressions.exceptions.ExpressionException;

/**
 *
 * @author Carl
 */
public class Operation extends Expression{

    public Operation(Value value1, Value value2, Operator operator){
        this.value1 = value1;
        this.value2 = value2;
        this.operator = operator;
    }
    private Value value1;
    private Value value2;
    private Operator operator;
    
    @Override
    public Value getResult(Values values) throws ExpressionException{
        Value result1 = resolveValue(values, value1);
        Value result2 = resolveValue(values, value2);
        return operator.getResult(values, result1, result2);
    }
}
