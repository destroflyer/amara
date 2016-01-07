/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.values;

import java.util.List;
import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public interface CastableList{

    public abstract List<Value> getValue(Values values) throws ExpressionException;
}
