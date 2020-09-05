/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.functions;

import java.util.List;
import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public class LengthFunction extends Function{

    @Override
    public Value getResult(Values values) throws ExpressionException{
        Value value = getArgument(values, 0);
        if(value instanceof StringValue){
            String text = ((StringValue) value).getValue(values);
            return new NumericValue(text.length());
        }
        else if(value instanceof ListValue){
            List<Value> list = ((ListValue) value).getValue(values);
            return new NumericValue(list.size());
        }
        throw new WrongOperatorException();
    }
}
