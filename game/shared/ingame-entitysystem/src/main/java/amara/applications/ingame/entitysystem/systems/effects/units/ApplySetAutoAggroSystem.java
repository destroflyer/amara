package amara.applications.ingame.entitysystem.systems.effects.units;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.units.SetAutoAggroComponent;
import amara.applications.ingame.entitysystem.components.units.AutoAggroComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplySetAutoAggroSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, SetAutoAggroComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            float range = entityWorld.getComponent(effectImpactEntity, SetAutoAggroComponent.class).getRange();
            entityWorld.setComponent(targetEntity, new AutoAggroComponent(range));
        }
    }
}
