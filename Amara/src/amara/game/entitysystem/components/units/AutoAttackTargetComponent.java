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
public class AutoAttackTargetComponent{

    public AutoAttackTargetComponent(){
        
    }
    
    public AutoAttackTargetComponent(int targetEntityID){
        this.targetEntityID = targetEntityID;
    }
    private int targetEntityID;

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
