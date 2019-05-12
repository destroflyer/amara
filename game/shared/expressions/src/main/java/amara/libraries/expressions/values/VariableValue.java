/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.values;

import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public class VariableValue extends Value{

    public VariableValue(String name){
        this.name = name;
    }
    private String name;

    @Override
    public <T> T getValue(Values values, Class<T> datatype) throws ExpressionException{
        return getVariableValue(values).getValue(values, datatype);
    }
    
    public Value getVariableValue(Values values){
        return values.getVariable(name);
    }

    @Override
    public String toString(){
        return "[variable " + name + "]";
    }
}
