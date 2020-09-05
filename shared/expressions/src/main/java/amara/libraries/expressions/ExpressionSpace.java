/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions;

import amara.libraries.expressions.exceptions.*;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public class ExpressionSpace{

    public ExpressionSpace(){
        
    }
    private FunctionsMap functionsMap = new FunctionsMap();
    private Values values = new Values();
    private ExpressionParser expressionParser;
    
    public void parse(String text) throws ExpressionException{
        expressionParser = new ExpressionParser(functionsMap, text);
    }
    
    public void clearValues(){
        values.clear();
    }
    
    public void addValues(Values values){
        this.values.add(values);
    }
    
    public void setValue(String name, Value value){
        values.setVariable(name, value);
    }
    
    public void unsetValue(String name){
        values.unsetVariable(name);
    }
    
    public boolean getResult_Boolean() throws ExpressionException{
        return expressionParser.getValue().getValue(values, CastableBoolean.class).getValue(values);
    }
    
    public float getResult_Float() throws ExpressionException{
        return (float) getResult_Double();
    }
    
    public double getResult_Double() throws ExpressionException{
        return expressionParser.getValue().getValue(values, CastableNumeric.class).getValue(values);
    }
    
    public String getResult_String() throws ExpressionException{
        return expressionParser.getValue().getValue(values, CastableString.class).getValue(values);
    }
}
