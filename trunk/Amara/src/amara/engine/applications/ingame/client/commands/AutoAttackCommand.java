/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AutoAttackCommand extends Command{

    public AutoAttackCommand(){
        
    }
    
    public AutoAttackCommand(int targetEntity){
        this.targetEntity = targetEntity;
    }
    private int targetEntity;

    public int getTargetEntity(){
        return targetEntity;
    }
}
