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
public class ListValue extends Value implements CastableList{

    public ListValue(List<Value> value){
        this.value = value;
    }
    private List<Value> value;

    @Override
    public List<Value> getValue(Values values) throws ExpressionException{
        return value;
    }

    @Override
    public String toString(){
        return "[list size=" + value.size() + "]";
    }
}
