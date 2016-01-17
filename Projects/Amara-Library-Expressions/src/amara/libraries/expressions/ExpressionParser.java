/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions;

import java.util.Iterator;
import amara.Queue;
import amara.libraries.expressions.exceptions.ExpressionException;

/**
 *
 * @author Carl
 */
public class ExpressionParser{

    public ExpressionParser(FunctionsMap functionsMap, String text) throws ExpressionException{
        this.functionsMap = functionsMap;
        parse(text);
    }
    private FunctionsMap functionsMap;
    private ExpressionPart expressionPart;
    
    private void parse(String text) throws ExpressionException{
        Queue<Character> charactersQueue = new Queue<Character>();
        for(char character : text.toCharArray()){
            charactersQueue.add(character);
        }
        Iterator<Character> charactersIterator = charactersQueue.getIterator();
        expressionPart = new ExpressionPart(functionsMap);
        expressionPart.parse(charactersIterator);
    }

    public Value getValue(){
        return expressionPart.getValue();
    }
}
