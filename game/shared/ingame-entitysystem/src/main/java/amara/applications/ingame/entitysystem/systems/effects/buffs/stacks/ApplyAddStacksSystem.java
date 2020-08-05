package amara.applications.ingame.entitysystem.systems.effects.buffs.stacks;

import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyAddStacksSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStacksComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            AddStacksComponent addStacksComponent = entityWorld.getComponent(effectImpactEntity, AddStacksComponent.class);
            int buffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, targetEntity, addStacksComponent.getBuffEntity());
            if (buffStatusEntity != -1) {
                StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
                int oldStacks = ((stacksComponent != null) ? stacksComponent.getStacks() : 0);
                int newStacks = (oldStacks + addStacksComponent.getStacks());
                entityWorld.setComponent(buffStatusEntity, new StacksComponent(newStacks));
            }
        }
    }
}
