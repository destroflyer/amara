package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.HealthRegenerationComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class HealthRegenerationSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int entity : entityWorld.getEntitiesWithAll(HealthRegenerationComponent.class, HealthComponent.class)) {
            float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
            float healthRegeneration = entityWorld.getComponent(entity, HealthRegenerationComponent.class).getValue();
            entityWorld.setComponent(entity, new HealthComponent(health + (healthRegeneration * deltaSeconds)));
        }
    }
}
