/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spawns;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.spawns.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ApplySpawnsSystems implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, SpawnComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            PositionComponent targetPositionComponent = null;
            DirectionComponent targetDirectionComponent = null;
            if(targetEntity != -1){
                targetPositionComponent = entityWorld.getComponent(targetEntity, PositionComponent.class);
                targetDirectionComponent = entityWorld.getComponent(targetEntity, DirectionComponent.class);
            }
            int casterEntity = entityWrapper.getComponent(EffectCastSourceComponent.class).getSourceEntity();
            if(entityWorld.hasEntity(casterEntity)){
                TeamComponent teamComponent = entityWorld.getComponent(casterEntity, TeamComponent.class);
                for(int spawnInformationEntity : entityWrapper.getComponent(SpawnComponent.class).getSpawnInformationEntites()){
                    EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                    EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntity);
                    EntityUtil.transferComponents(entityWrapper, spawnedObject, new Class[]{
                        EffectCastSourceComponent.class,
                        EffectCastSourceSpellComponent.class
                    });
                    if(teamComponent != null){
                        spawnedObject.setComponent(new TeamComponent(teamComponent.getTeamEntityID()));
                    }
                    boolean moveToTarget = spawnInformation.hasComponent(SpawnMoveToTargetComponent.class);
                    Vector2f position;
                    if((targetPositionComponent != null) && (!moveToTarget)){
                        position = targetPositionComponent.getPosition().clone();
                        if(targetDirectionComponent != null){
                            spawnedObject.setComponent(new DirectionComponent(targetDirectionComponent.getVector().clone()));
                        }
                    }
                    else{
                        position = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition().clone();
                    }
                    RelativeSpawnPositionComponent relativeSpawnPositionComponent = spawnInformation.getComponent(RelativeSpawnPositionComponent.class);
                    if(relativeSpawnPositionComponent != null){
                        position.addLocal(relativeSpawnPositionComponent.getPosition());
                    }
                    spawnedObject.setComponent(new PositionComponent(position));
                    SpawnMovementSpeedComponent spawnMovementSpeedComponent = spawnInformation.getComponent(SpawnMovementSpeedComponent.class);
                    if(spawnMovementSpeedComponent != null){
                        EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                        if(moveToTarget){
                            movement.setComponent(new MovementTargetComponent(targetEntity));
                        }
                        else if(targetDirectionComponent != null){
                            movement.setComponent(new MovementDirectionComponent(targetDirectionComponent.getVector().clone()));
                        }
                        movement.setComponent(new MovementSpeedComponent(spawnMovementSpeedComponent.getSpeed()));
                        SpawnMovementAnimationComponent spawnMovementAnimationComponent = spawnInformation.getComponent(SpawnMovementAnimationComponent.class);
                        if(spawnMovementAnimationComponent != null){
                            movement.setComponent(new MovementAnimationComponent(spawnMovementAnimationComponent.getAnimationEntity()));
                        }
                        spawnedObject.setComponent(new MovementComponent(movement.getId()));
                    }
                    if(spawnInformation.hasComponent(SpawnAttackMoveComponent.class)){
                        spawnedObject.setComponent(new AttackMoveComponent(targetEntity));
                    }
                    EntityTemplate.loadTemplates(entityWorld, spawnedObject.getId(), spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateNames());
                }
            }
        }
    }
}
