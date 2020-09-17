package amara.applications.ingame.entitysystem.systems.effects.buffs.stacks;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
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
            addStacks(entityWorld, targetEntity, addStacksComponent.getBuffEntity(), addStacksComponent.getStacks());
        }
    }

    public static void addStacks(EntityWorld entityWorld, int entity, int buffEntity, int stacks) {
        int buffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, entity, buffEntity);
        if (buffStatusEntity != -1) {
            StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
            int oldStacks = ((stacksComponent != null) ? stacksComponent.getStacks() : 0);
            int newStacks = (oldStacks + stacks);
            entityWorld.setComponent(buffStatusEntity, new StacksComponent(newStacks));
            int targetEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getTargetEntity();
            entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
        }
    }
}
