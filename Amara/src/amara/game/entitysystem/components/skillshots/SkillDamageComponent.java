/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.skillshots;

/**
 *
 * @author Philipp
 */
public class SkillDamageComponent
{
    private float damageValue;

    public SkillDamageComponent(float damageValue) {
        this.damageValue = damageValue;
    }

    public float getDamageValue() {
        return damageValue;
    }
    
}
