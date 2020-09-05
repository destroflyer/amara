/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class LevelComponent{

    public LevelComponent(){
        
    }
    
    public LevelComponent(int level){
        this.level = level;
    }
    private int level;

    public int getLevel(){
        return level;
    }
}
