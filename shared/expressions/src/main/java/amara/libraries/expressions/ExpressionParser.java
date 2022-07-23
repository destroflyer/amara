package amara.libraries.expressions;

import java.util.LinkedList;

import amara.libraries.expressions.exceptions.ExpressionException;

public class ExpressionParser {

    public ExpressionParser(FunctionsMap functionsMap, String text) throws ExpressionException {
        this.functionsMap = functionsMap;
        parse(text);
    }
    private FunctionsMap functionsMap;
    private ExpressionPart expressionPart;

    private void parse(String text) throws ExpressionException {
        LinkedList<Character> charactersQueue = new LinkedList<>();
        for (char character : text.toCharArray()) {
            charactersQueue.add(character);
        }
        expressionPart = new ExpressionPart(functionsMap);
        expressionPart.parse(charactersQueue.iterator());
    }

    public Value getValue(){
        return expressionPart.getValue();
    }
}
