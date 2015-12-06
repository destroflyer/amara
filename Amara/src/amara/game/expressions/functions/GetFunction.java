/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.functions;

import java.util.List;
import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;

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
