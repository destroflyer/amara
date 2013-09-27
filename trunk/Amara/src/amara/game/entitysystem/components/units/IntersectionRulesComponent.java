/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

/**
 *
 * @author Carl
 */
public class IntersectionRulesComponent{

    public IntersectionRulesComponent(int rulesEntityID){
        this.rulesEntityID = rulesEntityID;
    }
    private int rulesEntityID;

    public int getRulesEntityID(){
        return rulesEntityID;
    }
}
