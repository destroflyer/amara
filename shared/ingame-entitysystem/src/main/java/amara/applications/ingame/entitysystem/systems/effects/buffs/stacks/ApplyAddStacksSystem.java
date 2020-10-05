package amara.applications.ingame.entitysystem.systems.effects.buffs.stacks;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyAddStacksSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStacksComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            AddStacksComponent addStacksComponent = entityWorld.getComponent(effectImpactEntity, AddStacksComponent.class);
            StackUtil.addStacks(entityWorld, targetEntity, addStacksComponent.getBuffEntity(), addStacksComponent.getStacks());
        }
    }
}
