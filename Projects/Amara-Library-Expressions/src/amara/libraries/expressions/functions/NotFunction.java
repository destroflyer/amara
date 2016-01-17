/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.functions;

import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public class NotFunction extends Function{

    @Override
    public Value getResult(Values values) throws ExpressionException{
        return new BooleanValue(!getArgument_Boolean(values, 0));
    }
}
