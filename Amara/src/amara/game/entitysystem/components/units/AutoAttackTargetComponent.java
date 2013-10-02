/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

/**
 *
 * @author Carl
 */
public class AutoAttackTargetComponent{

    public AutoAttackTargetComponent(int targetEntityID){
        this.targetEntityID = targetEntityID;
    }
    private int targetEntityID;

    public int getTargetEntityID(){
        return targetEntityID;
    }
}
