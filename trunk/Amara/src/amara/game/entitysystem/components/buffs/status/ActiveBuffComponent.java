/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.status;

/**
 *
 * @author Carl
 */
public class ActiveBuffComponent{

    public ActiveBuffComponent(int targetEntityID, int buffEntityID){
        this.targetEntityID = targetEntityID;
        this.buffEntityID = buffEntityID;
    }
    private int targetEntityID;
    private int buffEntityID;

    public int getTargetEntityID(){
        return targetEntityID;
    }

    public int getBuffEntityID(){
        return buffEntityID;
    }
}
