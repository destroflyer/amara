/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.spells.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SetCooldownOnCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellComponent.class)){
            int spellEntity = entityWorld.getComponent(casterEntity, CastSpellComponent.class).getSpellEntity();
            setOnCooldown(entityWorld, spellEntity);
        }
    }
    
    public static void setOnCooldown(EntityWorld entityWorld, int spellEntity){
        CooldownComponent cooldownComponent = entityWorld.getComponent(spellEntity, CooldownComponent.class);
        if(cooldownComponent != null){
            entityWorld.setComponent(spellEntity, new RemainingCooldownComponent(cooldownComponent.getDuration()));
        }
    }
}
