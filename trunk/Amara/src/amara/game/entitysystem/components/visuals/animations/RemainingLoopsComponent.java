/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.visuals.animations;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RemainingLoopsComponent{

    public RemainingLoopsComponent(){
        
    }
    
    public RemainingLoopsComponent(int loopsCount){
        this.loopsCount = loopsCount;
    }
    private int loopsCount;
    

    public int getLoopsCount(){
        return loopsCount;
    }
}
