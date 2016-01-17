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
