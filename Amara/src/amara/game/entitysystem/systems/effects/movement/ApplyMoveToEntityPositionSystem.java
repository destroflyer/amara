/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.movement;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ApplyMoveToEntityPositionSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, MoveToEntityPositionComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            MoveToEntityPositionComponent moveToEntityPositionComponent = entityWrapper.getComponent(MoveToEntityPositionComponent.class);
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new MovementTargetComponent(moveToEntityPositionComponent.getTargetPositionEntity()));
            movement.setComponent(new MovementSpeedComponent(moveToEntityPositionComponent.getSpeed()));
            entityWorld.setComponent(targetID, new MovementComponent(movement.getId()));
        }
    }
}
