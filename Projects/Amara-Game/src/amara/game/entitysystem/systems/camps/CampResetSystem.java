/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import java.util.LinkedList;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CampResetSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(CampComponent.class, CampResetComponent.class)){
            CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
            int targetPositionEntity = entityWorld.createEntity();
            entityWorld.setComponent(targetPositionEntity, new PositionComponent(campComponent.getPosition()));
            if(ExecutePlayerCommandsSystem.tryWalk(entityWorld, entity, targetPositionEntity, -1)){
                int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
                entityWorld.removeComponent(movementEntity, MovementIsCancelableComponent.class);
                EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                effectTrigger.setComponent(new TriggerTemporaryComponent());
                effectTrigger.setComponent(new TargetReachedTriggerComponent());
                effectTrigger.setComponent(new SourceTargetComponent());
                EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                LinkedList<Object> componentsToAdd = new LinkedList<Object>();
                componentsToAdd.add(new DirectionComponent(campComponent.getDirection()));
                if(entityWorld.hasComponent(campComponent.getCampEntity(), CampHealthResetComponent.class)){
                    float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
                    componentsToAdd.add(new HealthComponent(maximumHealth));
                }
                effect.setComponent(new AddComponentsComponent(componentsToAdd.toArray(new Object[componentsToAdd.size()])));
                effect.setComponent(new RemoveComponentsComponent(CampResetComponent.class));
                effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                effectTrigger.setComponent(new TriggerSourceComponent(entity));
                effectTrigger.setComponent(new TriggerOnceComponent());
            }
            else{
                entityWorld.removeEntity(targetPositionEntity);
            }
        }
    }
}
