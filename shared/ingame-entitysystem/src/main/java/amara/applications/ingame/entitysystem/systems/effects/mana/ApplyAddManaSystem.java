package amara.applications.ingame.entitysystem.systems.effects.mana;

import amara.applications.ingame.entitysystem.components.attributes.ManaComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.mana.ResultingAddManaComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyAddManaSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingAddManaComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            ManaComponent manaComponent = entityWorld.getComponent(targetEntity, ManaComponent.class);
            if (manaComponent != null) {
                float newMana = (manaComponent.getValue() + entityWorld.getComponent(effectImpactEntity, ResultingAddManaComponent.class).getValue());
                entityWorld.setComponent(targetEntity, new ManaComponent(newMana));
            }
        }
    }
}
