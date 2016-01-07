/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.expressions;

/**
 *
 * @author Carl
 */
public class GlobalExpressionSpace{

    private static ExpressionSpace instance;
    
    public static ExpressionSpace getInstance(){
        if(instance == null){
            instance = new ExpressionSpace();
        }
        return instance;
    }
}
