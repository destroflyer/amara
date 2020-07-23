package amara.applications.ingame.entitysystem.systems.aggro;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
import amara.libraries.entitysystem.*;

public class DrawAggroOnDamageSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingPhysicalDamageComponent.class)) {
            drawAggro(entityWorld, effectImpactEntity);
        }
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ResultingMagicDamageComponent.class)) {
            drawAggro(entityWorld, effectImpactEntity);
        }
    }

    private void drawAggro(EntityWorld entityWorld, int effectImpactEntity) {
        int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
        EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
        if (effectSourceComponent != null) {
            AggroUtil.trySetAggroIfStill(entityWorld, targetEntity, effectSourceComponent.getSourceEntity());
        }
    }
}
