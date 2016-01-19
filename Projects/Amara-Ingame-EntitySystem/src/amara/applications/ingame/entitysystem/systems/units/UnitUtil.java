/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.applications.ingame.entitysystem.systems.movement.MovementSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UnitUtil{
    
    public static boolean tryCancelAction(EntityWorld entityWorld, int entity){
        boolean isAllowed = true;
        IsCastingComponent isCastingComponent = entityWorld.getComponent(entity, IsCastingComponent.class);
        if((isCastingComponent != null) && (!isCastingComponent.isCancelable())){
            isAllowed = false;
        }
        if(isAllowed){
            if(MovementSystem.isDisplaced(entityWorld, entity)){
                isAllowed = false;
            }
            else{
                cancelAction(entityWorld, entity);
            }
        }
        return isAllowed;
    }
    
    public static void cancelAction(EntityWorld entityWorld, int entity){
        cancelMovement(entityWorld, entity);
        entityWorld.removeComponent(entity, AggroTargetComponent.class);
        entityWorld.removeComponent(entity, IsWalkingToAggroTargetComponent.class);
        entityWorld.removeComponent(entity, AnimationComponent.class);
        CurrentActionEffectCastsComponent currentActionEffectCastsComponent = entityWorld.getComponent(entity, CurrentActionEffectCastsComponent.class);
        if(currentActionEffectCastsComponent != null){
            for(int effectCastEntity : currentActionEffectCastsComponent.getEffectCastEntities()){
                entityWorld.removeEntity(effectCastEntity);
            }
        }
        entityWorld.removeComponent(entity, IsCastingComponent.class);
        removeTemporaryTriggers(entityWorld, entity, CastingFinishedTriggerComponent.class);
    }
    
    public static void cancelMovement(EntityWorld entityWorld, int entity){
        entityWorld.removeComponent(entity, MovementComponent.class);
        removeTemporaryTriggers(entityWorld, entity, TargetReachedTriggerComponent.class);
        removeTemporaryTriggers(entityWorld, entity, CollisionTriggerComponent.class);
    }
    
    private static void removeTemporaryTriggers(EntityWorld entityWorld, int sourceEntity, Class triggerComponentClass){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, triggerComponentClass)){
            int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if(triggerSourceEntity == sourceEntity){
                if(entityWorld.hasComponent(effectTriggerEntity, TriggerOnCancelComponent.class)){
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
                if(entityWorld.hasComponent(effectTriggerEntity, TriggerOnceComponent.class)){
                    entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
                }
                else if(entityWorld.hasComponent(effectTriggerEntity, TriggerTemporaryComponent.class)){
                    entityWorld.removeEntity(effectTriggerEntity);
                }
            }
        }
    }
    
    public static boolean isPlayerUnit(EntityWorld entityWorld, int entity){
        for(int playerEntity : entityWorld.getEntitiesWithAll(SelectedUnitComponent.class)){
            int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
            if(selectedEntity == entity){
                return true;
            }
        }
        return false;
    }
}
