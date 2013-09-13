/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spells;

/**
 *
 * @author Carl
 */
public class InstantSpellEffectComponent{

    public InstantSpellEffectComponent(int effectEntityID){
        this.effectEntityID = effectEntityID;
    }
    private int effectEntityID;

    public int getEffectEntityID(){
        return effectEntityID;
    }
}
