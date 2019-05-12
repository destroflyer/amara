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
public abstract class Operator{
    
    public abstract Value getResult(Values values, Value value1, Value value2) throws ExpressionException;
}
