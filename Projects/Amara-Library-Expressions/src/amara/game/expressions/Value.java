/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions;

import amara.game.expressions.exceptions.ExpressionException;

/**
 *
 * @author Carl
 */
public abstract class Value{
    
    public <T> T getValue(Values values, Class<T> datatype) throws ExpressionException{
        return (T) this;
    }
}
