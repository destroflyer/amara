/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.functions;

import java.util.List;
import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public class GetFunction extends Function{

    @Override
    public Value getResult(Values values) throws ExpressionException{
        List<Value> list = getArgument_List(values, 0);
        int index = (int) getArgument_Numeric(values, 1);
        return list.get(index);
    }
}
