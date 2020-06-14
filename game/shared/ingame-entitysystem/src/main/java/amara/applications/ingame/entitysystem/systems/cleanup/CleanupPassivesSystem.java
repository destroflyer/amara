/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.units.passives.PassiveAddedEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.units.passives.PassiveRemovedEffectTriggersComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupPassivesSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            PassiveAddedEffectTriggersComponent.class,
            PassiveRemovedEffectTriggersComponent.class
        );
        for (int entity : observer.getRemoved().getEntitiesWithAny(PassiveAddedEffectTriggersComponent.class)) {
            int[] effectTriggerEntities = observer.getRemoved().getComponent(entity, PassiveAddedEffectTriggersComponent.class).getEffectTriggerEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, effectTriggerEntities);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(PassiveRemovedEffectTriggersComponent.class)) {
            int[] effectTriggerEntities = observer.getRemoved().getComponent(entity, PassiveRemovedEffectTriggersComponent.class).getEffectTriggerEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, effectTriggerEntities);
        }
    }
}
