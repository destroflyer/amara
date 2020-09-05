/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.functions;

import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public class IdentityFunction extends Function{

    @Override
    public Value getResult(Values values) throws ExpressionException{
        return getArgument(values, 0);
    }
}
