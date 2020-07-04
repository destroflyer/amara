package amara.applications.ingame.entitysystem.systems.effects.general;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.libraries.entitysystem.*;

public class ApplyAddEffectTriggersSystem implements EntitySystem {
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddEffectTriggersComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int[] effectTriggerEntities = entityWorld.getComponent(effectImpactEntity, AddEffectTriggersComponent.class).getEffectTriggerEntities();
            for (int effectTriggerEntity : effectTriggerEntities) {
                entityWorld.setComponent(effectTriggerEntity, new TriggerSourceComponent(targetEntity));
            }
        }
    }
}
