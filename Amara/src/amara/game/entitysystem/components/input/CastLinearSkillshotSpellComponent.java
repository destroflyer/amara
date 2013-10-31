/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.input;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class CastLinearSkillshotSpellComponent{

    public CastLinearSkillshotSpellComponent(){
        
    }
    
    public CastLinearSkillshotSpellComponent(int spellEntityID, Vector2f direction){
        this.spellEntityID = spellEntityID;
        this.direction = direction;
    }
    private int spellEntityID;
    private Vector2f direction;

    public int getSpellEntityID(){
        return spellEntityID;
    }

    public Vector2f getDirection(){
        return direction;
    }
}
