/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions;

import amara.game.expressions.exceptions.*;
import amara.game.expressions.values.*;

/**
 *
 * @author Carl
 */
public class ExpressionTest{
    
    public static void main(String[] args){
        ExpressionSpace expressionSpace = new ExpressionSpace();
        try{
            //Test1
            expressionSpace.parse("(10+4)*3");
            System.out.println(expressionSpace.getResult_Float());
            //Test2
            expressionSpace.parse("person + \" is cool\"");
            expressionSpace.setValue("person", new StringValue("destro"));
            System.out.println(expressionSpace.getResult_String());
        }catch(ExpressionException ex){
            ex.printStackTrace();
        }
    }
}
