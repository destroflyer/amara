/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.operators;

/**
 *
 * @author Carl
 */
public class AndOperator extends BooleanOperator{

    @Override
    protected boolean getResult(boolean value1, boolean value2){
        return (value1 && value2);
    }
}
