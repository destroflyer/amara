/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.spells.*;

/**
 *
 * @author Carl
 */
public class SetCooldownOnCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(CastSingleTargetSpellComponent.class))
        {
            setOnCooldown(entityWorld, entityWorld.getComponent(entity, CastSingleTargetSpellComponent.class).getSpellEntityID());
        }
        for(int entity : entityWorld.getEntitiesWithAll(CastLinearSkillshotSpellComponent.class))
        {
            setOnCooldown(entityWorld, entityWorld.getComponent(entity, CastLinearSkillshotSpellComponent.class).getSpellEntityID());
        }
        for(int entity : entityWorld.getEntitiesWithAll(CastPositionalSkillshotSpellComponent.class))
        {
            setOnCooldown(entityWorld, entityWorld.getComponent(entity, CastPositionalSkillshotSpellComponent.class).getSpellEntityID());
        }
    }
    
    public static void setOnCooldown(EntityWorld entityWorld, int spellEntityID){
        CooldownComponent cooldownComponent = entityWorld.getComponent(spellEntityID, CooldownComponent.class);
        if(cooldownComponent != null){
            entityWorld.setComponent(spellEntityID, new RemainingCooldownComponent(cooldownComponent.getDuration()));
        }
    }
}
