package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.units.MovementComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

import java.util.HashMap;

public class RemoveFinishedMovementsSystem implements EntitySystem {

    private HashMap<Integer, Integer> movementEntities = new HashMap<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, MovementComponent.class, TriggerSourceComponent.class);
        for (int entity : observer.getNew().getEntitiesWithAny(MovementComponent.class)) {
            addNewMovement(entityWorld, entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(MovementComponent.class)) {
            removeOldMovement(entityWorld, entity);
            removeMovementEffectTriggers(entityWorld, observer, entity);
            addNewMovement(entityWorld, entity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(MovementComponent.class)) {
            removeOldMovement(entityWorld, entity);
            removeMovementEffectTriggers(entityWorld, observer, entity);
            movementEntities.remove(entity);
        }
    }

    private void addNewMovement(EntityWorld entityWorld, int entity) {
        int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
        movementEntities.put(entity, movementEntity);
    }

    private void removeOldMovement(EntityWorld entityWorld, int entity) {
        int movementEntity = movementEntities.get(entity);
        entityWorld.removeEntity(movementEntity);
    }

    private void removeMovementEffectTriggers(EntityWorld entityWorld, ComponentMapObserver observer, int entity) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, TargetReachedTriggerComponent.class, TriggerTemporaryComponent.class)) {
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if ((sourceEntity == entity) && (!observer.getNew().hasEntity(effectTriggerEntity))) {
                entityWorld.removeEntity(effectTriggerEntity);
            }
        }
    }
}
