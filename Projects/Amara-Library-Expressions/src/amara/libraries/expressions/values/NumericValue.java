/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.values;

import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public class NumericValue extends Value implements CastableNumeric{

    public NumericValue(double value){
        this.value = value;
    }
    private double value;

    @Override
    public double getValue(Values values) throws ExpressionException{
        return value;
    }

    @Override
    public String toString(){
        return "[numeric " + value + "]";
    }
}
