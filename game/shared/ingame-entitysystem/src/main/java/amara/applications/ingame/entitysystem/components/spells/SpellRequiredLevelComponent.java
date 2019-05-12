/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellRequiredLevelComponent{

    public SpellRequiredLevelComponent(){
        
    }
    
    public SpellRequiredLevelComponent(int level){
        this.level = level;
    }
    private int level;

    public int getLevel(){
        return level;
    }
}
