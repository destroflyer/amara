/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class Values{
    
    private HashMap<String, Value> variables = new HashMap<String, Value>();
    
    public void setVariable(String name, Value value){
        variables.put(name, value);
    }
    
    public void unsetVariable(String name){
        variables.remove(name);
    }
    
    public Value getVariable(String name){
        return variables.get(name);
    }
}
