/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Carl
 */
public class Values{
    
    private HashMap<String, Value> variables = new HashMap<String, Value>();
    
    public void clear(){
        variables.clear();
    }
    
    public void add(Values values){
        for(Entry<String, Value> entry : values.variables.entrySet()){
            setVariable(entry.getKey(), entry.getValue());
        }
    }
    
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
