package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.movements.MovementTargetComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellQueueSystem;
import amara.libraries.entitysystem.*;

public class PerformAutoAttacksSystem implements EntitySystem {

    public PerformAutoAttacksSystem(CastSpellQueueSystem castSpellQueueSystem) {
        this.castSpellQueueSystem = castSpellQueueSystem;
    }
    private CastSpellQueueSystem castSpellQueueSystem;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(AggroTargetComponent.class)){
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            if (!isWalkingToEntity(entityWorld, entity, targetEntity)) {
                int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
                castSpellQueueSystem.enqueueSpellCast(entityWorld, entity, autoAttackEntity, targetEntity);
            }
        }
    }

    private boolean isWalkingToEntity(EntityWorld entityWorld, int entity, int targetEntity) {
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if (movementComponent != null) {
            MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementComponent.getMovementEntity(), MovementTargetComponent.class);
            if (movementTargetComponent != null) {
                return (movementTargetComponent.getTargetEntity() == targetEntity);
            }
        }
        return false;
    }
}
