/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions.operators;

import amara.game.expressions.*;
import amara.game.expressions.values.*;

/**
 *
 * @author Carl
 */
public class MultiplyOperator extends NumericOperator{

    @Override
    protected Value getResult(double value1, double value2){
        return new NumericValue(value1 * value2);
    }
}
