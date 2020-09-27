package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.ManaComponent;
import amara.applications.ingame.entitysystem.components.attributes.ManaRegenerationComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ManaRegenerationSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(ManaRegenerationComponent.class, ManaComponent.class)){
            float mana = entityWorld.getComponent(entity, ManaComponent.class).getValue();
            float manaRegeneration = entityWorld.getComponent(entity, ManaRegenerationComponent.class).getValue();
            entityWorld.setComponent(entity, new ManaComponent(mana + (manaRegeneration * deltaSeconds)));
        }
    }
}
