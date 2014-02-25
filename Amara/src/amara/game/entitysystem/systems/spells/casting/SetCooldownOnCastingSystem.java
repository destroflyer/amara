/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spells.*;

/**
 *
 * @author Carl
 */
public class SetCooldownOnCastingSystem extends SimpleCastingSystem{

    @Override
    public void onCasting(EntityWorld entityWorld, int casterEnttiy, int spellEntity){
        setOnCooldown(entityWorld, spellEntity);
    }
    
    public static void setOnCooldown(EntityWorld entityWorld, int spellEntity){
        CooldownComponent cooldownComponent = entityWorld.getComponent(spellEntity, CooldownComponent.class);
        if(cooldownComponent != null){
            entityWorld.setComponent(spellEntity, new RemainingCooldownComponent(cooldownComponent.getDuration()));
        }
    }
}
