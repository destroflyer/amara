/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects;

/**
 *
 * @author Carl
 */
public class ApplyEffectComponent{

    public ApplyEffectComponent(int targetEntityID){
        this.targetEntityID = targetEntityID;
    }
    private int targetEntityID;

    public int getTargetID(){
        return targetEntityID;
    }
}
