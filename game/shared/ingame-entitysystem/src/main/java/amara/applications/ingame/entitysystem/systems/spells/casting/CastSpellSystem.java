/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells.casting;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.movement.MovementSystem;
import amara.applications.ingame.entitysystem.systems.movement.TargetedMovementSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CastSpellSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int casterEntity : entityWorld.getEntitiesWithAny(CastSpellComponent.class)){
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
