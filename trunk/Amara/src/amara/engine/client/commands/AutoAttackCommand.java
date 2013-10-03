/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.commands;

/**
 *
 * @author Carl
 */
public class AutoAttackCommand extends Command{

    public AutoAttackCommand(int entity, int targetEntityID){
        this.entity = entity;
        this.targetEntityID = targetEntityID;
    }
    private int entity;
    private int targetEntityID;

    public int getEntity(){
        return entity;
    }

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
