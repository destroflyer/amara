/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells.casting;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.core.Util;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.components.effects.movement.*;
import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.movement.MovementSystem;
import amara.applications.ingame.entitysystem.systems.movement.TargetedMovementSystem;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;

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
            int targetEntity = castSpellComponent.getTargetEntity();
            if((targetEntity != -1) && entityWorld.hasComponent(spellEntity, CastTurnToTargetComponent.class)){
                PositionComponent targetPositionComponent = entityWorld.getComponent(targetEntity, PositionComponent.class);
                DirectionComponent targetDirectionComponent = entityWorld.getComponent(targetEntity, DirectionComponent.class);
                //Turn into cast direction
                Vector2f turnDirection = null;
                if(targetPositionComponent != null){
                    Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
                    turnDirection = targetPositionComponent.getPosition().subtract(casterPosition);
                }
                else if(targetDirectionComponent != null){
                    turnDirection = targetDirectionComponent.getVector().clone();
                }
                if(turnDirection != null){
                    entityWorld.setComponent(casterEntity, new DirectionComponent(turnDirection));
                }
            }
            //Instant
            InstantEffectTriggersComponent instantEffectTriggersComponent = entityWorld.getComponent(spellEntity, InstantEffectTriggersComponent.class);
            if(instantEffectTriggersComponent != null){
                LinkedList<EntityWrapper> effectCasts = EffectTriggerUtil.triggerEffects(entityWorld, instantEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
                if(entityWorld.hasComponent(spellEntity, CastDurationComponent.class)){
                    int[] currentActionEffectCastEntities = new int[effectCasts.size()];
                    for(int i=0;i<currentActionEffectCastEntities.length;i++){
                        currentActionEffectCastEntities[i] = effectCasts.get(i).getId();
                    }
                    entityWorld.setComponent(casterEntity, new CurrentActionEffectCastsComponent(currentActionEffectCastEntities));
                }
            }
            MovementComponent movementComponent = entityWorld.getComponent(casterEntity, MovementComponent.class);
            if((movementComponent != null) && entityWorld.hasComponent(spellEntity, StopBeforeCastingComponent.class)){
                int movementEntity = movementComponent.getMovementEntity();
                entityWorld.removeComponent(casterEntity, MovementComponent.class);
                if(entityWorld.hasComponent(spellEntity, StopAfterCastingComponent.class)){
                    UnitUtil.cancelMovement(entityWorld, casterEntity);
                    entityWorld.removeEntity(movementEntity);
                }
                else{
                    //Deactivate movement triggers temporarily
                    LinkedList<Integer> deactivatedTriggers = new LinkedList<Integer>();
                    for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, TargetReachedTriggerComponent.class)){
                        int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                        if(triggerSourceEntity == casterEntity){
                            entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
                            deactivatedTriggers.add(effectTriggerEntity);
                        }
                    }
                    EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                    effectTrigger.setComponent(new TriggerTemporaryComponent());
                    effectTrigger.setComponent(new CastingFinishedTriggerComponent());
                    effectTrigger.setComponent(new SourceTargetComponent());
                    EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                    effect.setComponent(new MoveComponent(movementEntity));
                    effect.setComponent(new AddEffectTriggersComponent(Util.convertToArray(deactivatedTriggers)));
                    effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                    effectTrigger.setComponent(new TriggerSourceComponent(casterEntity));
                    effectTrigger.setComponent(new TriggerOnceComponent());
                }
            }
            entityWorld.removeComponent(casterEntity, CastSpellComponent.class);
        }
    }
    
    public static boolean canCast(EntityWorld entityWorld, int casterEntity, int spellEntity){
        if(isAbleToPerformAction(entityWorld, casterEntity)){
            AutoAttackComponent autoAttackComponent = entityWorld.getComponent(casterEntity, AutoAttackComponent.class);
            if((autoAttackComponent != null) && (spellEntity == autoAttackComponent.getAutoAttackEntity())){
                return true;
            }
            return (!entityWorld.hasComponent(casterEntity, IsSilencedComponent.class));
        }
        return false;
    }
    
    public static boolean isAbleToPerformAction(EntityWorld entityWorld, int casterEntity){
        return ((!entityWorld.hasAnyComponent(casterEntity, IsStunnedComponent.class, IsKnockupedComponent.class)) && (!MovementSystem.isDisplaced(entityWorld, casterEntity)));
    }
    
    public static float getMinimumCastRange(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity){
        float minimumCastRange = entityWorld.getComponent(spellEntity, RangeComponent.class).getDistance();
        AutoAttackComponent autoAttackComponent = entityWorld.getComponent(casterEntity, AutoAttackComponent.class);
        if((autoAttackComponent != null) && (spellEntity == autoAttackComponent.getAutoAttackEntity())){
            float casterHitboxRadius = TargetedMovementSystem.getHitboxRadius(entityWorld, casterEntity);
            float targetHitboxRadius = TargetedMovementSystem.getHitboxRadius(entityWorld, targetEntity);
            minimumCastRange += (casterHitboxRadius + targetHitboxRadius);
        }
        return minimumCastRange;
    }
}
