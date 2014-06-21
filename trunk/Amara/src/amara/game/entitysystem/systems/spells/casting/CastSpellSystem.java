/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
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
            int targetEntity = castSpellComponent.getTargetEntity();
            if(targetEntity != -1){
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
                for(EntityWrapper effectCast : effectCasts){
                    effectCast.setComponent(new EffectCastSourceComponent(casterEntity));
                    effectCast.setComponent(new EffectCastSourceSpellComponent(spellEntity));
                }
            }
            //Specials
            if(targetEntity != -1){
                if(entityWorld.hasComponent(spellEntity, TeleportCasterToTargetPositionComponent.class)){
                    entityWorld.removeComponent(casterEntity, MovementComponent.class);
                    Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                    entityWorld.setComponent(casterEntity, new PositionComponent(targetPosition.clone()));
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