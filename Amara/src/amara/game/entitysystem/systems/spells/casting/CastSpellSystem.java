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
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.input.casts.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.specials.*;
import amara.game.entitysystem.components.units.*;
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
            InstantEffectTriggersComponent instantEffectTriggersComponent = entityWorld.getComponent(spellEntity, InstantEffectTriggersComponent.class);
            if(instantEffectTriggersComponent != null){
                int targetEntity = ((castTargetComponent != null)?castTargetComponent.getTargetEntity():-1);
                LinkedList<EntityWrapper> effectCasts = EffectTriggerUtil.triggerEffects(entityWorld, instantEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
                for(EntityWrapper effectCast : effectCasts){
                    if(castTargetComponent != null){
                        effectCast.setComponent(new EffectCastTargetComponent(castTargetComponent.getTargetEntity()));
                    }
                    if(castPositionComponent != null){
                        effectCast.setComponent(new EffectCastPositionComponent(castPositionComponent.getPosition().clone()));
                    }
                    if(castDirectionComponent != null){
                        effectCast.setComponent(new EffectCastDirectionComponent(castDirectionComponent.getVector().clone()));
                    }
                }
            }
            if(castTargetComponent != null){
                InstantTargetBuffComponent instantTargetBuffComponent = entityWorld.getComponent(spellEntity, InstantTargetBuffComponent.class);
                if(instantTargetBuffComponent != null){
                    EntityWrapper buffStatus = entityWorld.getWrapped(entityWorld.createEntity());
                    buffStatus.setComponent(new ActiveBuffComponent(castTargetComponent.getTargetEntity(), instantTargetBuffComponent.getBuffEntityID()));
                    buffStatus.setComponent(new CastSourceComponent(casterEntity));
                    buffStatus.setComponent(new RemainingBuffDurationComponent(instantTargetBuffComponent.getDuration()));
                    entityWorld.setComponent(castTargetComponent.getTargetEntity(), new RequestUpdateAttributesComponent());
                }
            }
            if(castPositionComponent != null){
                if(entityWorld.hasComponent(spellEntity, TeleportCasterToTargetPositionComponent.class)){
                    entityWorld.removeComponent(casterEntity, MovementComponent.class);
                    entityWorld.setComponent(casterEntity, new PositionComponent(castPositionComponent.getPosition().clone()));
                }
            }
            InstantSpawnsComponent instantSpawnsComponent = entityWorld.getComponent(spellEntity, InstantSpawnsComponent.class);
            if(instantSpawnsComponent != null){
                int[] spawnInformationEntitiesIDs = instantSpawnsComponent.getSpawnInformationEntitiesIDs();
                TeamComponent teamComponent = entityWorld.getComponent(casterEntity, TeamComponent.class);
                for(int i=0;i<spawnInformationEntitiesIDs.length;i++){
                    EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                    EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntitiesIDs[i]);
                    spawnedObject.setComponent(new CastSourceComponent(casterEntity));
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
            entityWorld.removeComponent(casterEntity, CastSpellComponent.class);
        }
    }
}
