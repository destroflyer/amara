package amara.applications.ingame.entitysystem.systems.effects.buffs.stacks;

import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.buffs.stacks.CopyStacksComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyCopyStacksSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, CopyStacksComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            CopyStacksComponent copyStacksComponent = entityWorld.getComponent(effectImpactEntity, CopyStacksComponent.class);
            int sourceBuffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, targetEntity, copyStacksComponent.getSourceBuffEntity());
            if (sourceBuffStatusEntity != -1) {
                StacksComponent stacksComponent = entityWorld.getComponent(sourceBuffStatusEntity, StacksComponent.class);
                if (stacksComponent != null) {
                    StackUtil.setStacks(entityWorld, targetEntity, copyStacksComponent.getTargetBuffEntity(), stacksComponent.getStacks());
                }
            }
        }
    }
}
