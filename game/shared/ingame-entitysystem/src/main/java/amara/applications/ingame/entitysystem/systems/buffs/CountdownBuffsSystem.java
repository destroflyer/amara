package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyRemoveBuffsSystem;
import amara.libraries.entitysystem.*;

public class CountdownBuffsSystem implements EntitySystem {
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int buffStatusEntity : entityWorld.getEntitiesWithAny(RemainingBuffDurationComponent.class)) {
            RemainingBuffDurationComponent remainingBuffDurationComponent = entityWorld.getComponent(buffStatusEntity, RemainingBuffDurationComponent.class);
            float duration = (remainingBuffDurationComponent.getRemainingDuration() - deltaSeconds);
            if (duration > 0){
                entityWorld.setComponent(buffStatusEntity, new RemainingBuffDurationComponent(duration));
            } else {
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                ApplyRemoveBuffsSystem.removeBuff(entityWorld, activeBuffComponent.getTargetEntity(), activeBuffComponent.getBuffEntity());
            }
        }
    }
}
