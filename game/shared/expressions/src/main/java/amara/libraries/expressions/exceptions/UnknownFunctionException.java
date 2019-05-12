/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions.exceptions;

/**
 *
 * @author Carl
 */
public class UnknownFunctionException extends ExpressionException{

    public UnknownFunctionException(String functionName){
        this.functionName = functionName;
    }
    private String functionName;

    public String getFunctionName(){
        return functionName;
    }
}
