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
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAny(CastSingleTargetSpellComponent.class, CastLinearSkillshotSpellComponent.class)))
        {
            CastSingleTargetSpellComponent castSingleTargetSpellComponent = entityWrapper.getComponent(CastSingleTargetSpellComponent.class);
            if(castSingleTargetSpellComponent != null){
                setOnCooldown(entityWorld, castSingleTargetSpellComponent.getSpellEntityID());
            }
            CastLinearSkillshotSpellComponent castLinearSkillshotSpellComponent = entityWrapper.getComponent(CastLinearSkillshotSpellComponent.class);
            if(castLinearSkillshotSpellComponent != null){
                setOnCooldown(entityWorld, castLinearSkillshotSpellComponent.getSpellEntityID());
            }
        }
    }
    
    public static void setOnCooldown(EntityWorld entityWorld, int spellEntityID){
        CooldownComponent cooldownComponent = entityWorld.getCurrent().getComponent(spellEntityID, CooldownComponent.class);
        if(cooldownComponent != null){
            entityWorld.getCurrent().setComponent(spellEntityID, new RemainingCooldownComponent(cooldownComponent.getDuration()));
        }
    }
}
