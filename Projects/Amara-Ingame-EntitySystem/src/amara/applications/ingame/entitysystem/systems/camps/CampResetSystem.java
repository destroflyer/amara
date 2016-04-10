/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.camps;

import java.util.LinkedList;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
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
            int targetEntity = entityWorld.createEntity();
            entityWorld.setComponent(targetEntity, new TemporaryComponent());
            entityWorld.setComponent(targetEntity, new PositionComponent(campComponent.getPosition()));
            if(ExecutePlayerCommandsSystem.tryWalk(entityWorld, entity, targetEntity, -1)){
                int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
                entityWorld.removeComponent(movementEntity, MovementIsCancelableComponent.class);
                int effectTriggerEntity = entityWorld.createEntity();
                entityWorld.setComponent(effectTriggerEntity, new TriggerTemporaryComponent());
                entityWorld.setComponent(effectTriggerEntity, new TargetReachedTriggerComponent());
                entityWorld.setComponent(effectTriggerEntity, new SourceTargetComponent());
                int effectEntity = entityWorld.createEntity();
                LinkedList<Object> componentsToAdd = new LinkedList<Object>();
                componentsToAdd.add(new DirectionComponent(campComponent.getDirection()));
                if(entityWorld.hasComponent(campComponent.getCampEntity(), CampHealthResetComponent.class)){
                    float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
                    componentsToAdd.add(new HealthComponent(maximumHealth));
                }
                entityWorld.setComponent(effectEntity, new AddComponentsComponent(componentsToAdd.toArray(new Object[componentsToAdd.size()])));
                entityWorld.setComponent(effectEntity, new RemoveComponentsComponent(CampResetComponent.class));
                entityWorld.setComponent(effectTriggerEntity, new TriggeredEffectComponent(effectEntity));
                entityWorld.setComponent(effectTriggerEntity, new TriggerSourceComponent(entity));
            }
            else{
                entityWorld.removeEntity(targetEntity);
            }
        }
    }
}
