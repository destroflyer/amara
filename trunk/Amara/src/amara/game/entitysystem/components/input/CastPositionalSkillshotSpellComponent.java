/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.input;

import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class CastPositionalSkillshotSpellComponent{

    public CastPositionalSkillshotSpellComponent(int spellEntityID, Vector2f position){
        this.spellEntityID = spellEntityID;
        this.position = position;
    }
    private int spellEntityID;
    private Vector2f position;

    public int getSpellEntityID(){
        return spellEntityID;
    }

    public Vector2f getPosition(){
        return position;
    }
}
