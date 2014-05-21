/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.input.casts.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.specials.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.systems.effects.triggers.EffectTriggerUtil;

/**
 *
 * @author Carl
 */
public class CastSpellSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAll(CastSpellComponent.class)){
            CastSpellComponent castSpellComponent = entityWorld.getComponent(casterEntity, CastSpellComponent.class);
            int spellEntity = castSpellComponent.getSpellEntity();
            int castInformationEntity = castSpellComponent.getCastInformationEntity();
            TargetComponent castTargetComponent = entityWorld.getComponent(castInformationEntity, TargetComponent.class);
            PositionComponent castPositionComponent = entityWorld.getComponent(castInformationEntity, PositionComponent.class);
            DirectionComponent castDirectionComponent = entityWorld.getComponent(castInformationEntity, DirectionComponent.class);
            //Turn into cast direction
            Vector2f turnDirection = entityWorld.getComponent(casterEntity, DirectionComponent.class).getVector().clone();
            Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
            if((castTargetComponent != null) && (castTargetComponent.getTargetEntity() != casterEntity)){
                Vector2f targetPosition = entityWorld.getComponent(castTargetComponent.getTargetEntity(), PositionComponent.class).getPosition();
                turnDirection = targetPosition.subtract(casterPosition);
            }
            else if(castPositionComponent != null){
                turnDirection = castPositionComponent.getPosition().subtract(casterPosition);
            }
            else if(castDirectionComponent != null){
                turnDirection = castDirectionComponent.getVector().clone();
            }
            entityWorld.setComponent(casterEntity, new DirectionComponent(turnDirection));
            //Instant
            InstantEffectTriggersComponent instantEffectTriggersComponent = entityWorld.getComponent(spellEntity, InstantEffectTriggersComponent.class);
            if(instantEffectTriggersComponent != null){
                int targetEntity = ((castTargetComponent != null)?castTargetComponent.getTargetEntity():-1);
                LinkedList<EntityWrapper> effectCasts = EffectTriggerUtil.triggerEffects(entityWorld, instantEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
                for(EntityWrapper effectCast : effectCasts){
                    effectCast.setComponent(new EffectCastSourceComponent(casterEntity));
                    effectCast.setComponent(new EffectCastSourceSpellComponent(spellEntity));
                    if(castPositionComponent != null){
                        effectCast.setComponent(new EffectCastPositionComponent(castPositionComponent.getPosition().clone()));
                    }
                    if(castDirectionComponent != null){
                        effectCast.setComponent(new EffectCastDirectionComponent(castDirectionComponent.getVector().clone()));
                    }
                }
            }
            //Buffs
            if(castTargetComponent != null){
                InstantTargetBuffComponent instantTargetBuffComponent = entityWorld.getComponent(spellEntity, InstantTargetBuffComponent.class);
                if(instantTargetBuffComponent != null){
                    EntityWrapper buffStatus = entityWorld.getWrapped(entityWorld.createEntity());
                    buffStatus.setComponent(new ActiveBuffComponent(castTargetComponent.getTargetEntity(), instantTargetBuffComponent.getBuffEntityID()));
                    buffStatus.setComponent(new EffectCastSourceComponent(casterEntity));
                    buffStatus.setComponent(new EffectCastSourceSpellComponent(spellEntity));
                    buffStatus.setComponent(new RemainingBuffDurationComponent(instantTargetBuffComponent.getDuration()));
                    entityWorld.setComponent(castTargetComponent.getTargetEntity(), new RequestUpdateAttributesComponent());
                }
            }
            //Specials
            if(castPositionComponent != null){
                if(entityWorld.hasComponent(spellEntity, TeleportCasterToTargetPositionComponent.class)){
                    entityWorld.removeComponent(casterEntity, MovementComponent.class);
                    entityWorld.setComponent(casterEntity, new PositionComponent(castPositionComponent.getPosition().clone()));
                }
            }
            //Spawns
            InstantSpawnsComponent instantSpawnsComponent = entityWorld.getComponent(spellEntity, InstantSpawnsComponent.class);
            if(instantSpawnsComponent != null){
                int[] spawnInformationEntitiesIDs = instantSpawnsComponent.getSpawnInformationEntitiesIDs();
                TeamComponent teamComponent = entityWorld.getComponent(casterEntity, TeamComponent.class);
                for(int i=0;i<spawnInformationEntitiesIDs.length;i++){
                    EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                    EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                    spawnedObject.setComponent(new EffectCastSourceComponent(casterEntity));
                    spawnedObject.setComponent(new EffectCastSourceSpellComponent(spellEntity));
                    if(teamComponent != null){
                        spawnedObject.setComponent(new TeamComponent(teamComponent.getTeamEntityID()));
                    }
                    Vector2f position;
                    if(castPositionComponent != null){
                        position = castPositionComponent.getPosition().clone();
                    }
                    else{
                        position = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition().clone();
                    }
                    RelativeSpawnPositionComponent relativeSpawnPositionComponent = spawnInformation.getComponent(RelativeSpawnPositionComponent.class);
                    if(relativeSpawnPositionComponent != null){
                        position.addLocal(relativeSpawnPositionComponent.getPosition());
                    }
                    spawnedObject.setComponent(new PositionComponent(position));
                    if(castDirectionComponent != null){
                        spawnedObject.setComponent(new DirectionComponent(castDirectionComponent.getVector().clone()));
                    }
                    SpawnMovementSpeedComponent spawnMovementSpeedComponent = spawnInformation.getComponent(SpawnMovementSpeedComponent.class);
                    if(spawnMovementSpeedComponent != null){
                        EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                        if(castTargetComponent != null){
                            movement.setComponent(new MovementTargetComponent(castTargetComponent.getTargetEntity()));
                        }
                        if(castDirectionComponent != null){
                            movement.setComponent(new MovementDirectionComponent(castDirectionComponent.getVector().clone()));
                        }
                        movement.setComponent(new MovementSpeedComponent(spawnMovementSpeedComponent.getSpeed()));
                        spawnedObject.setComponent(new MovementComponent(movement.getId()));
                    }
                    EntityTemplate.loadTemplates(entityWorld, spawnedObject.getId(), spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateNames());
                }
            }
            MovementComponent movementComponent = entityWorld.getComponent(casterEntity, MovementComponent.class);
            if((movementComponent != null) && entityWorld.hasComponent(spellEntity, StopBeforeCastingComponent.class)){
                int movementEntity = movementComponent.getMovementEntity();
                entityWorld.removeComponent(casterEntity, MovementComponent.class);
                if(entityWorld.hasComponent(spellEntity, StopAfterCastingComponent.class)){
                    entityWorld.removeEntity(movementEntity);
                }
                else{
                    EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                    effectTrigger.setComponent(new CastingFinishedTriggerComponent());
                    effectTrigger.setComponent(new SourceTargetComponent());
                    EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                    effect.setComponent(new MoveComponent(movementEntity));
                    effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                    effectTrigger.setComponent(new TriggerSourceComponent(casterEntity));
                    effectTrigger.setComponent(new TriggerOnceComponent());
                }
            }
            entityWorld.removeComponent(casterEntity, CastSpellComponent.class);
        }
    }
    
    public static boolean canCast(EntityWorld entityWorld, int casterEntity, int spellEntity){
        if(!entityWorld.hasComponent(casterEntity, IsStunnedComponent.class)){
            AutoAttackComponent autoAttackComponent = entityWorld.getComponent(casterEntity, AutoAttackComponent.class);
            if((autoAttackComponent != null) && (spellEntity == autoAttackComponent.getAutoAttackEntity())){
                return true;
            }
            return (!entityWorld.hasComponent(casterEntity, IsSilencedComponent.class));
        }
        return false;
    }
}
