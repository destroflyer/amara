package amara.applications.ingame.entitysystem.systems.buffs.stacks;

import amara.applications.ingame.entitysystem.components.buffs.MaximumStacksComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class MaximumStacksSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int buffStatusEntity : entityWorld.getEntitiesWithAny(StacksComponent.class)) {
            int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
            MaximumStacksComponent maximumStacksComponent = entityWorld.getComponent(buffEntity, MaximumStacksComponent.class);
            if (maximumStacksComponent != null) {
                int stacks = entityWorld.getComponent(buffStatusEntity, StacksComponent.class).getStacks();
                int maximumStacks = maximumStacksComponent.getStacks();
                if (stacks > maximumStacks) {
                    entityWorld.setComponent(buffStatusEntity, new StacksComponent(maximumStacks));
                }
            }
        }
    }
}
