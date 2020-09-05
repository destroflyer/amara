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
public abstract class Value{
    
    public <T> T getValue(Values values, Class<T> datatype) throws ExpressionException{
        return (T) this;
    }
}
