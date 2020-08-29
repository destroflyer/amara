package amara.applications.ingame.entitysystem.systems.effects.general;


import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.general.RemoveEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyRemoveEffectTriggersSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveEffectTriggersComponent.class)) {
            int[] effectTriggerEntities = entityWorld.getComponent(effectImpactEntity, RemoveEffectTriggersComponent.class).getEffectTriggerEntities();
            for (int effectTriggerEntity : effectTriggerEntities) {
                entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
            }
        }
    }
}
