/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupEffectTriggersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, TriggeredEffectComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(TriggeredEffectComponent.class)) {
            int effectEntity = observer.getRemoved().getComponent(entity, TriggeredEffectComponent.class).getEffectEntity();
            // Only remove the effect entity, if it's not referenced in an ongoing effect cast
            // If it is, it will be cleanuped when the effect impact is calculated
            boolean canEffectBeCleanuped = entityWorld.getEntitiesWithAny(PrepareEffectComponent.class).stream()
                    .map(effectCastEntity -> entityWorld.getComponent(effectCastEntity, PrepareEffectComponent.class).getEffectEntity())
                    .noneMatch(castingEffectEntity -> castingEffectEntity == effectEntity);
            if (canEffectBeCleanuped) {
                CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
            }
        }
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAny(TriggerSourceComponent.class)) {
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if(!entityWorld.hasEntity(sourceEntity)){
                int effectEntity = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getEffectEntity();
                CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
                entityWorld.removeEntity(effectTriggerEntity);
            }
        }
    }
}
