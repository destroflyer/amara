/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

/**
 *
 * @author Carl
 */
public class CastSourceComponent{

    public CastSourceComponent(int sourceEntitiyID){
        this.sourceEntitiyID = sourceEntitiyID;
    }
    private int sourceEntitiyID;

    public int getSourceEntitiyID(){
        return sourceEntitiyID;
    }
}
