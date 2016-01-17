/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.operators;

import amara.libraries.expressions.*;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public class SubtractOperator extends NumericOperator{

    @Override
    protected Value getResult(double value1, double value2){
        return new NumericValue(value1 - value2);
    }
}
