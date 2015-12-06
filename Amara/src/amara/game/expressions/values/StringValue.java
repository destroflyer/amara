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
public class StringValue extends Value implements CastableString{

    public StringValue(String value){
        this.value = value;
    }
    private String value;

    @Override
    public String getValue(Values values) throws ExpressionException{
        return value;
    }

    @Override
    public String toString(){
        return "[string \"" + value + "\"]";
    }
}
