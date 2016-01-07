/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

/**
 *
 * @author Carl
 */
public class RemovedEntityChange extends EntityChange{

    public RemovedEntityChange(){
        
    }
    
    public RemovedEntityChange(int entity){
        super(entity);
    }
}
