/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.game.entitysystem.components.spawns.CastSourceComponent;
import amara.game.entitysystem.components.units.effects.TargetReachedTriggerEffectComponent;
import amara.game.entitysystem.components.units.movement.TargetedMovementComponent;

/**
 *
 * @author Philipp
 */
public class TriggerTargetReachedEffectSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(TargetedMovementComponent.class, TargetReachedTriggerEffectComponent.class))
        {
            TargetedMovementComponent targetedMovementComponent = entityWorld.getComponent(entity, TargetedMovementComponent.class);
            PositionComponent targetPositionComponent = entityWorld.getComponent(targetedMovementComponent.getTargetEntityID(), PositionComponent.class);
            if(targetPositionComponent != null){
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                Vector2f targetPosition = targetPositionComponent.getPosition();
                if(position.equals(targetPosition)){
                    EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
                    int effectID = entityWorld.getComponent(entity, TargetReachedTriggerEffectComponent.class).getEffectEntityID();
                    effectCast.setComponent(new PrepareEffectComponent(effectID));
                    CastSourceComponent castSourceComponent = entityWorld.getComponent(entity, CastSourceComponent.class);
                    if(castSourceComponent != null){
                        effectCast.setComponent(new EffectSourceComponent(castSourceComponent.getSourceEntitiyID()));
                    }
                    effectCast.setComponent(new AffectedTargetsComponent(new int[]{targetedMovementComponent.getTargetEntityID()}));
                    entityWorld.removeEntity(entity);
                }
            }
        }
    }
}
