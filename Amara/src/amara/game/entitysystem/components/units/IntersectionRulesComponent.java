/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class IntersectionRulesComponent{

    public IntersectionRulesComponent(){
        
    }
    
    public IntersectionRulesComponent(int rulesEntityID){
        this.rulesEntityID = rulesEntityID;
    }
    private int rulesEntityID;

    public int getRulesEntityID(){
        return rulesEntityID;
    }
}
