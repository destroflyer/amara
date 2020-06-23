/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.spawns;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.bounties.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class ApplySpawnsSystems implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, SpawnComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            boolean cleanupTemporaryTarget = true;
            PositionComponent targetPositionComponent = null;
            DirectionComponent targetDirectionComponent = null;
            if(targetEntity != -1){
                targetPositionComponent = entityWorld.getComponent(targetEntity, PositionComponent.class);
                targetDirectionComponent = entityWorld.getComponent(targetEntity, DirectionComponent.class);
            }
            int casterEntity = entityWrapper.getComponent(EffectSourceComponent.class).getSourceEntity();
            TeamComponent teamComponent = entityWorld.getComponent(casterEntity, TeamComponent.class);
            for(int spawnInformationEntity : entityWrapper.getComponent(SpawnComponent.class).getSpawnInformationEntites()){
                EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntity);
                EntityUtil.transferComponents(entityWorld, entityWrapper.getId(), spawnedObject.getId(), new Class[]{
                    EffectSourceComponent.class,
                    EffectSourceSpellComponent.class
                });
                if(teamComponent != null){
                    spawnedObject.setComponent(new TeamComponent(teamComponent.getTeamEntity()));
                }
                boolean moveToTarget = spawnInformation.hasComponent(SpawnMoveToTargetComponent.class);
                Vector2f position;
                Vector2f direction = null;
                if(targetDirectionComponent != null){
                    direction = targetDirectionComponent.getVector().clone();
                }
                PositionComponent casterPositionComponent = entityWorld.getComponent(casterEntity, PositionComponent.class);
                if((targetPositionComponent != null) && (!moveToTarget)){
                    position = targetPositionComponent.getPosition().clone();
                    if((direction == null) && (casterPositionComponent != null)){
                        direction = position.subtract(casterPositionComponent.getPosition()).normalizeLocal();
                    }
                }
                else if(casterPositionComponent != null){
                    position = casterPositionComponent.getPosition().clone();
                }
                else{
                    //Caster does no longer exist or an invalid combination was specified
                    continue;
                }
                SpawnRelativePositionComponent spawnRelativePositionComponent = spawnInformation.getComponent(SpawnRelativePositionComponent.class);
                if(spawnRelativePositionComponent != null){
                    DirectionComponent casterDirectionComponent = entityWorld.getComponent(casterEntity, DirectionComponent.class);
                    if(casterDirectionComponent !=  null){
                        float casterDirection_Radian = casterDirectionComponent.getRadian();
                        Vector2f relativePosition = spawnRelativePositionComponent.getPosition().clone();
                        relativePosition.rotateAroundOrigin(casterDirection_Radian, false);
                        position.addLocal(relativePosition);
                    }
                }
                spawnedObject.setComponent(new PositionComponent(position));
                if(direction != null){
                    SpawnRelativeDirectionComponent spawnRelativeDirectionComponent = spawnInformation.getComponent(SpawnRelativeDirectionComponent.class);
                    if(spawnRelativeDirectionComponent != null){
                        direction.rotateAroundOrigin(spawnRelativeDirectionComponent.getAngle_Radian(), false);
                    }
                    spawnedObject.setComponent(new DirectionComponent(direction));
                }
                SpawnMovementSpeedComponent spawnMovementSpeedComponent = spawnInformation.getComponent(SpawnMovementSpeedComponent.class);
                if(spawnMovementSpeedComponent != null){
                    int movementEntity = entityWorld.createEntity();
                    if(moveToTarget){
                        entityWorld.setComponent(movementEntity, new MovementTargetComponent(targetEntity));
                        entityWorld.setComponent(movementEntity, new MovementTurnInDirectionComponent());
                        cleanupTemporaryTarget = false;
                    }
                    else if(direction != null){
                        Vector2f movementDirection = direction.clone();
                        SpawnMovementRelativeDirectionComponent spawnMovementRelativeDirectionComponent = spawnInformation.getComponent(SpawnMovementRelativeDirectionComponent.class);
                        if(spawnMovementRelativeDirectionComponent != null){
                            movementDirection.rotateAroundOrigin(spawnMovementRelativeDirectionComponent.getAngle_Radian(), false);
                        }
                        entityWorld.setComponent(movementEntity, new MovementDirectionComponent(movementDirection));
                    }
                    entityWorld.setComponent(movementEntity, new MovementSpeedComponent(spawnMovementSpeedComponent.getSpeed()));
                    SpawnMovementAnimationComponent spawnMovementAnimationComponent = spawnInformation.getComponent(SpawnMovementAnimationComponent.class);
                    if(spawnMovementAnimationComponent != null){
                        entityWorld.setComponent(movementEntity, new MovementAnimationComponent(spawnMovementAnimationComponent.getAnimationEntity()));
                    }
                    spawnedObject.setComponent(new MovementComponent(movementEntity));
                }
                if(spawnInformation.hasComponent(SpawnAttackMoveComponent.class)){
                    spawnedObject.setComponent(new AttackMoveComponent(targetEntity));
                    cleanupTemporaryTarget = false;
                }
                if(spawnInformation.hasComponent(SpawnApplyAsRespawnTransformComponent.class)){
                    spawnedObject.setComponent(new RespawnPositionComponent(position));
                    spawnedObject.setComponent(new RespawnDirectionComponent(direction));
                }
                SpawnRedirectReceivedBountiesComponent spawnRedirectReceivedBountiesComponent = spawnInformation.getComponent(SpawnRedirectReceivedBountiesComponent.class);
                if(spawnRedirectReceivedBountiesComponent != null){
                    spawnedObject.setComponent(new RedirectReceivedBountiesComponent(casterEntity));
                }
                EntityTemplate.loadTemplates(entityWorld, spawnedObject.getId(), spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateNames());
            }
            if(cleanupTemporaryTarget && entityWorld.hasComponent(targetEntity, TemporaryComponent.class)){
                entityWorld.removeEntity(targetEntity);
            }
        }
    }
}
