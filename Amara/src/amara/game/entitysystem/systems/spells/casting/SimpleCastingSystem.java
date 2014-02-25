/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;

/**
 *
 * @author Carl
 */
public abstract class SimpleCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(CastSelfcastSpellComponent.class))
        {
            onCasting(entityWorld, entity, entityWorld.getComponent(entity, CastSelfcastSpellComponent.class).getSpellEntityID());
        }
        for(int entity : entityWorld.getEntitiesWithAll(CastSingleTargetSpellComponent.class))
        {
            onCasting(entityWorld, entity, entityWorld.getComponent(entity, CastSingleTargetSpellComponent.class).getSpellEntityID());
        }
        for(int entity : entityWorld.getEntitiesWithAll(CastLinearSkillshotSpellComponent.class))
        {
            onCasting(entityWorld, entity, entityWorld.getComponent(entity, CastLinearSkillshotSpellComponent.class).getSpellEntityID());
        }
        for(int entity : entityWorld.getEntitiesWithAll(CastPositionalSkillshotSpellComponent.class))
        {
            onCasting(entityWorld, entity, entityWorld.getComponent(entity, CastPositionalSkillshotSpellComponent.class).getSpellEntityID());
        }
    }
    
    public abstract void onCasting(EntityWorld entityWorld, int casterEntity, int spellEntity);
}
