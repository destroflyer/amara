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
