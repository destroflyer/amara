/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions;

import java.util.List;
import amara.libraries.expressions.exceptions.ExpressionException;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public abstract class Function extends Expression{
    
    private Value[] arguments;

    public void setArguments(Value[] arguments){
        this.arguments = arguments;
    }
    
    protected double getArgument_Numeric(Values values, int index) throws ExpressionException{
        return getArgument(values, index).getValue(values, CastableNumeric.class).getValue(values);
    }
    
    protected boolean getArgument_Boolean(Values values, int index) throws ExpressionException{
        return getArgument(values, index).getValue(values, CastableBoolean.class).getValue(values);
    }
    
    protected String getArgument_String(Values values, int index) throws ExpressionException{
        return getArgument(values, index).getValue(values, CastableString.class).getValue(values);
    }
    
    protected List<Value> getArgument_List(Values values, int index) throws ExpressionException{
        return getArgument(values, index).getValue(values, CastableList.class).getValue(values);
    }
    
    protected Value getArgument(Values values, int index) throws ExpressionException{
        return resolveValue(values, arguments[index]);
    }
}
