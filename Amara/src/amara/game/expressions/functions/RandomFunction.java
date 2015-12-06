/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.functions;

import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;
import amara.game.expressions.values.*;

/**
 *
 * @author Carl
 */
public class RandomFunction extends Function{

    @Override
    public Value getResult(Values values) throws ExpressionException{
        return new NumericValue((float) Math.random());
    }
}
