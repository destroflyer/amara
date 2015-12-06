/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions;

import java.util.HashMap;
import amara.game.expressions.functions.*;

/**
 *
 * @author Carl
 */
public class FunctionsMap{
    
    public FunctionsMap(){
        //General
        registerFunction("", IdentityFunction.class);
        //Boolean
        registerFunction("not", NotFunction.class);
        //Numeric
        registerFunction("int", IntegerFunction.class);
        registerFunction("rand", RandomFunction.class);
        //String
        registerFunction("substr", SubstringFunction.class);
        registerFunction("length", LengthFunction.class);
        //List
        registerFunction("get", GetFunction.class);
    }
    private HashMap<String, Class<? extends Function>> functions = new HashMap<String, Class<? extends Function>>();
    
    public void registerFunction(String name, Class<? extends Function> functionClass){
        functions.put(name, functionClass);
    }
    
    public Function createFunction(String name){
        try{
            return functions.get(name).newInstance();
        }catch(Exception ex){
        }
        return null;
    }
}
