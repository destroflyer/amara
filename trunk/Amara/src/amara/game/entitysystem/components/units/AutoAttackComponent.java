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
public class AutoAttackComponent{

    public AutoAttackComponent(){
        
    }
    
    public AutoAttackComponent(int autoAttackEntityID){
        this.autoAttackEntityID = autoAttackEntityID;
    }
    private int autoAttackEntityID;

    public int getAutoAttackEntityID(){
        return autoAttackEntityID;
    }
}
