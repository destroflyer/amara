/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AutoAttackCommand extends Command{

    public AutoAttackCommand(){
        
    }
    
    public AutoAttackCommand(int targetEntityID){
        this.targetEntityID = targetEntityID;
    }
    private int targetEntityID;

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
