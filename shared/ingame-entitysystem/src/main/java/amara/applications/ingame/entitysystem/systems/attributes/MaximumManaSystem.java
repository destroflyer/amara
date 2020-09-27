package amara.applications.ingame.entitysystem.systems.attributes;

import amara.applications.ingame.entitysystem.components.attributes.ManaComponent;
import amara.applications.ingame.entitysystem.components.attributes.MaximumManaComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class MaximumManaSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(MaximumManaComponent.class, ManaComponent.class)) {
            float mana = entityWorld.getComponent(entity, ManaComponent.class).getValue();
            float maximumMana = entityWorld.getComponent(entity, MaximumManaComponent.class).getValue();
            if (mana > maximumMana) {
                entityWorld.setComponent(entity, new ManaComponent(maximumMana));
            }
        }
    }
}
