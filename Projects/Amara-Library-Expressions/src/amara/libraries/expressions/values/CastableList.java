/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.values;

import java.util.List;
import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public interface CastableList{

    public abstract List<Value> getValue(Values values) throws ExpressionException;
}
