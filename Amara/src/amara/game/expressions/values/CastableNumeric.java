/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.values;

import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public interface CastableNumeric{

    public abstract double getValue(Values values) throws ExpressionException;
}
