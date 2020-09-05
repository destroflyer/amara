package amara.applications.ingame.entitysystem.systems.effects.aggro;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.EffectSourceComponent;
import amara.applications.ingame.entitysystem.components.effects.aggro.SetAggroTargetComponent;
import amara.applications.ingame.entitysystem.systems.aggro.AggroUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplySetAggroTargetSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, EffectSourceComponent.class, SetAggroTargetComponent.class)) {
            int sourceEntity = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class).getSourceEntity();
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            AggroUtil.tryCancelActionAndSetAggro(entityWorld, targetEntity, sourceEntity);
        }
    }
}
