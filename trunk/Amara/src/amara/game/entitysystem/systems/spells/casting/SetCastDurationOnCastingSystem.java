/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;

/**
 *
 * @author Carl
 */
public class SetCastDurationOnCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellComponent.class)){
            int spellEntity = entityWorld.getComponent(casterEntity, CastSpellComponent.class).getSpellEntity();
            CastDurationComponent castDurationComponent = entityWorld.getComponent(spellEntity, CastDurationComponent.class);
            if(castDurationComponent != null){
                entityWorld.setComponent(casterEntity, new IsCastingComponent(castDurationComponent.getDuration()));
            }
        }
    }
    
    public static void cancelCasting(EntityWorld entityWorld, int entity){
        entityWorld.removeComponent(entity, IsCastingComponent.class);
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, CastingFinishedTriggerComponent.class)){
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if(sourceEntity == entity){
                entityWorld.removeEntity(effectTriggerEntity);
            }
        }
    }
}
