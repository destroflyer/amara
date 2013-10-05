/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

/**
 *
 * @author Carl
 */
public class InstantTargetBuffComponent{

    public InstantTargetBuffComponent(int buffEntityID, float duration){
        this.buffEntityID = buffEntityID;
        this.duration = duration;
    }
    private int buffEntityID;
    private float duration;

    public int getBuffEntityID(){
        return buffEntityID;
    }

    public float getDuration(){
        return duration;
    }
}
