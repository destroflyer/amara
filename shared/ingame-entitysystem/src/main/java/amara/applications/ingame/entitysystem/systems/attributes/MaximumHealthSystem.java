package amara.applications.ingame.entitysystem.systems.attributes;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class MaximumHealthSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(MaximumHealthComponent.class, HealthComponent.class)) {
            float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
            float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
            if (health > maximumHealth) {
                entityWorld.setComponent(entity, new HealthComponent(maximumHealth));
            }
        }
    }
}
