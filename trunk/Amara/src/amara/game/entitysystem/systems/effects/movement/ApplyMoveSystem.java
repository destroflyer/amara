/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.movement;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ApplyMoveSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, MoveComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            entityWorld.setComponent(targetID, new MovementComponent(entityWrapper.getComponent(MoveComponent.class).getMovementEntity()));
        }
    }
}
