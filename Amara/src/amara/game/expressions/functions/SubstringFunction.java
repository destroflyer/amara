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
public class SubstringFunction extends Function{

    @Override
    public Value getResult(Values values) throws ExpressionException{
        String text = getArgument_String(values, 0);
        int startIndex = (int) getArgument_Numeric(values, 1);
        int endIndex = (int) getArgument_Numeric(values, 2);
        return new StringValue(text.substring(startIndex, endIndex));
    }
}
