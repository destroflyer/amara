package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.*;

public class RepeatingBuffEffectsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int buffStatusEntity : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)) {
            ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
            RepeatingEffectComponent repeatingEffectComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), RepeatingEffectComponent.class);
            if (repeatingEffectComponent != null) {
                TimeSinceLastRepeatingEffectComponent timeSinceLastRepeatingEffectComponent = entityWorld.getComponent(buffStatusEntity, TimeSinceLastRepeatingEffectComponent.class);
                float timeSinceLastRepeatingEffect = (((timeSinceLastRepeatingEffectComponent != null) ? timeSinceLastRepeatingEffectComponent.getDuration() : 0) + deltaSeconds);
                if (timeSinceLastRepeatingEffect >= repeatingEffectComponent.getInterval()) {
                    int effectCastEntity = entityWorld.createEntity();
                    entityWorld.setComponent(effectCastEntity, new PrepareEffectComponent(repeatingEffectComponent.getEffectEntity()));
                    int effectActionIndex = EffectTriggerUtil.getAndIncreaseNextEffectActionIndex(entityWorld);
                    entityWorld.setComponent(effectCastEntity, new EffectSourceActionIndexComponent(effectActionIndex));
                    EntityUtil.transferComponents(entityWorld, buffStatusEntity, effectCastEntity, new Class[] {
                            EffectSourceComponent.class,
                            EffectSourceSpellComponent.class
                    });
                    entityWorld.setComponent(effectCastEntity, new AffectedTargetsComponent(activeBuffComponent.getTargetEntity()));
                    timeSinceLastRepeatingEffect = 0;
                }
                entityWorld.setComponent(buffStatusEntity, new TimeSinceLastRepeatingEffectComponent(timeSinceLastRepeatingEffect));
            }
        }
    }
}
