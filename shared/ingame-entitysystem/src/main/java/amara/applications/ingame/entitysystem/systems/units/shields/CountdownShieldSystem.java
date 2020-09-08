package amara.applications.ingame.entitysystem.systems.units.shields;

import amara.applications.ingame.entitysystem.components.units.shields.ActiveShieldComponent;
import amara.applications.ingame.entitysystem.components.units.shields.RemainingShieldDurationComponent;
import amara.applications.ingame.entitysystem.systems.effects.units.ApplyAddShieldSystem;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class CountdownShieldSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int entity : entityWorld.getEntitiesWithAny(RemainingShieldDurationComponent.class)) {
            RemainingShieldDurationComponent remainingShieldDurationComponent = entityWorld.getComponent(entity, RemainingShieldDurationComponent.class);
            float duration = (remainingShieldDurationComponent.getDuration() - deltaSeconds);
            if (duration > 0) {
                entityWorld.setComponent(entity, new RemainingShieldDurationComponent(duration));
            } else {
                ActiveShieldComponent activeShieldComponent = entityWorld.getComponent(entity, ActiveShieldComponent.class);
                ApplyAddShieldSystem.removeShield(entityWorld, activeShieldComponent.getTargetEntity(), activeShieldComponent.getShieldEntity());
            }
        }
    }
}
