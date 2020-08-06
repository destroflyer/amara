
package amara.applications.ingame.entitysystem.systems.effects.buffs.stacks;

import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.buffs.stacks.ClearStacksComponent;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyClearStacksSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ClearStacksComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int buffEntity = entityWorld.getComponent(effectImpactEntity, ClearStacksComponent.class).getBuffEntity();
            int buffStatusEntity = BuffUtil.getBuffStatusEntity(entityWorld, targetEntity, buffEntity);
            entityWorld.setComponent(buffStatusEntity, new StacksComponent(0));
        }
    }
}
