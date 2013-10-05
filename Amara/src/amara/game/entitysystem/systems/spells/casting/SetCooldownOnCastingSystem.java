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
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(CastSingleTargetSpellComponent.class))
        {
            setOnCooldown(entityWorld, entityWorld.getCurrent().getComponent(entity, CastSingleTargetSpellComponent.class).getSpellEntityID());
        }
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(CastLinearSkillshotSpellComponent.class))
        {
            setOnCooldown(entityWorld, entityWorld.getCurrent().getComponent(entity, CastLinearSkillshotSpellComponent.class).getSpellEntityID());
        }
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(CastPositionalSkillshotSpellComponent.class))
        {
            setOnCooldown(entityWorld, entityWorld.getCurrent().getComponent(entity, CastPositionalSkillshotSpellComponent.class).getSpellEntityID());
        }
    }
    
    public static void setOnCooldown(EntityWorld entityWorld, int spellEntityID){
        CooldownComponent cooldownComponent = entityWorld.getCurrent().getComponent(spellEntityID, CooldownComponent.class);
        if(cooldownComponent != null){
            entityWorld.getCurrent().setComponent(spellEntityID, new RemainingCooldownComponent(cooldownComponent.getDuration()));
        }
    }
}
