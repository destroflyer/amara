/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import java.util.LinkedList;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;

/**
 *
 * @author Carl
 */
public class CampResetSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(ResetCampComponent.class))
        {
            entityWorld.removeComponent(entity, AggroTargetComponent.class);
            int campEntity = entityWorld.getComponent(entity, CampComponent.class).getCampEntity();
            CampTransformComponent campTransformComponent = entityWorld.getComponent(campEntity, CampTransformComponent.class);
            int targetPositionEntity = entityWorld.createEntity();
            entityWorld.setComponent(targetPositionEntity, new PositionComponent(campTransformComponent.getPosition()));
            if(ExecutePlayerCommandsSystem.walk(entityWorld, entity, targetPositionEntity, -1)){
                EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                effectTrigger.setComponent(new TargetReachedTriggerComponent());
                effectTrigger.setComponent(new SourceTargetComponent());
                EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                LinkedList<Object> componentsToAdd = new LinkedList<Object>();
                componentsToAdd.add(new DirectionComponent(campTransformComponent.getDirection()));
                if(entityWorld.hasComponent(campEntity, CampHealthResetComponent.class)){
                    float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
                    componentsToAdd.add(new HealthComponent(maximumHealth));
                }
                effect.setComponent(new AddComponentsComponent(componentsToAdd.toArray(new Object[0])));
                effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                effectTrigger.setComponent(new TriggerSourceComponent(entity));
                effectTrigger.setComponent(new TriggerOnceComponent());
                entityWorld.removeComponent(entity, ResetCampComponent.class);
            }
        }
    }
}
