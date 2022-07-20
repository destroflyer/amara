/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.expressions;

import java.util.Iterator;
import java.util.LinkedList;
import amara.libraries.expressions.exceptions.*;
import amara.libraries.expressions.operators.*;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public class ExpressionPart{

    public ExpressionPart(FunctionsMap functionsMap){
        this.functionsMap = functionsMap;
    }
    private FunctionsMap functionsMap;
    private Value value;
    private char character;
    private String currentValueText = "";
    private Operator lastOperator;
    private boolean isOperatorAllowed = false;
    
    public void parse(Iterator<Character> charactersIterator) throws ExpressionException{
        boolean continueParsing = true;
        while(charactersIterator.hasNext() && continueParsing){
            character = charactersIterator.next();
            Operator operator = null;
            switch(character){
                case '+': operator = new AddOperator(); break;
                case '-': operator = new SubtractOperator(); break;
                case '*': operator = new MultiplyOperator(); break;
                case '/': operator = new DivideOperator(); break;
                case '<': operator = new LessOperator(); break;
                case '>': operator = new GreaterOperator(); break;
                case '=': operator = new EqualsOperator(); break;
                case '&': operator = new AndOperator(); break;
                case '|': operator = new OrOperator(); break;
                case '^': operator = new XorOperator(); break;
            }
            if((operator != null) && isOperatorAllowed){
                if((lastOperator != null) || (value == null)){
                    addLastOperation(getCurrentValue());
                }
                lastOperator = operator;
                currentValueText = "";
                isOperatorAllowed = false;
            }
            else{
                switch(character){
                    case '(':
                        LinkedList<Value> arguments = new LinkedList<Value>();
                        ExpressionPart expressionPart;
                        do{
                            expressionPart = new ExpressionPart(functionsMap);
                            expressionPart.parse(charactersIterator);
                            Value argumentValue = expressionPart.getValue();
                            if((expressionPart.character == ',') || (argumentValue != null)){
                                arguments.add(argumentValue);
                            }
                        }while(expressionPart.character != ')');
                        Function function = functionsMap.createFunction(currentValueText);
                        if(function == null){
                            throw new UnknownFunctionException(currentValueText);
                        }
                        function.setArguments(arguments.toArray(Value[]::new));
                        addLastOperation(function);
                        currentValueText = "";
                        isOperatorAllowed = true;
                        break;

                    case ')':
                    case ',':
                        continueParsing = false;
                        break;

                    case ' ':
                        break;

                    default:
                        currentValueText += character;
                        if(character == '"'){
                            parseString(charactersIterator);
                        }
                        isOperatorAllowed = true;
                        break;
                }
            }
        }
        if(currentValueText.length() > 0){
            addLastOperation(getCurrentValue());
        }
    }
    
    private void parseString(Iterator<Character> charactersIterator){
        while(charactersIterator.hasNext()){
            character = charactersIterator.next();
            boolean isEscapedCharacter = false;
            if(character == '\\'){
                isEscapedCharacter = true;
                character = charactersIterator.next();
            }
            currentValueText += character;
            if((character == '"') && (!isEscapedCharacter)){
                break;
            }
        }
    }
    
    private void addLastOperation(Value parsedValue){
        if(value == null){
            value = parsedValue;
        }
        else{
            value = new Operation(value, parsedValue, lastOperator);
            lastOperator = null;
        }
    }
    
    private Value getCurrentValue(){
        if(currentValueText.equals("false")){
            return new BooleanValue(false);
        }
        else if(currentValueText.equals("true")){
            return new BooleanValue(true);
        }
        try{
            float number = Float.parseFloat(currentValueText);
            return new NumericValue(number);
        }catch(NumberFormatException numberFormatException){
        }
        if(currentValueText.startsWith("\"") && currentValueText.endsWith("\"")){
            String text = currentValueText.substring(1, currentValueText.length() - 1);
            text = text.replaceAll("\\\\\"", "\"");
            return new StringValue(text);
        }
        return new VariableValue(currentValueText);
    }

    public Value getValue(){
        return value;
    }
}
