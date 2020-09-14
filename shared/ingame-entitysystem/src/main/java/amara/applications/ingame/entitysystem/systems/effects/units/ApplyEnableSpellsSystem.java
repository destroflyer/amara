package amara.applications.ingame.entitysystem.systems.effects.units;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.units.EnableSpellsComponent;
import amara.applications.ingame.entitysystem.components.units.IsSpellsEnabledComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyEnableSpellsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, EnableSpellsComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.setComponent(targetEntity, new IsSpellsEnabledComponent());
        }
    }
}
