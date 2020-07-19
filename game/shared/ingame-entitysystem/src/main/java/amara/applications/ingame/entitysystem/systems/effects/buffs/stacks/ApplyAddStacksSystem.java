package amara.applications.ingame.entitysystem.systems.effects.buffs.stacks;

import amara.applications.ingame.entitysystem.components.buffs.stacks.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.stacks.*;
import amara.libraries.entitysystem.*;

public class ApplyAddStacksSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStacksComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int oldStacks = entityWorld.getComponent(targetEntity, StacksComponent.class).getStacks();
            int newStacks = (oldStacks + entityWorld.getComponent(effectImpactEntity, AddStacksComponent.class).getStacks());
            entityWorld.setComponent(targetEntity, new StacksComponent(newStacks));
        }
    }
}
