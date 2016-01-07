/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.functions;

import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;

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
