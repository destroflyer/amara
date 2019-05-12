/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

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
        for(int entity : observer.getRemoved().getEntitiesWithAll(TriggeredEffectComponent.class)){
            int effectEntity = observer.getRemoved().getComponent(entity, TriggeredEffectComponent.class).getEffectEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
        }
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class)){
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if(!entityWorld.hasEntity(sourceEntity)){
                int effectEntity = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getEffectEntity();
                CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
                entityWorld.removeEntity(effectTriggerEntity);
            }
        }
    }
}
