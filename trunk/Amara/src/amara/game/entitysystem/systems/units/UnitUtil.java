/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class UnitUtil{
    
    public static boolean cancelAction(EntityWorld entityWorld, int entity){
        boolean isAllowed = true;
        IsCastingComponent isCastingComponent = entityWorld.getComponent(entity, IsCastingComponent.class);
        if((isCastingComponent != null) && (!isCastingComponent.isCancelable())){
            isAllowed = false;
        }
        if(isAllowed){
            if(!entityWorld.hasComponent(entity, MovementComponent.class)){
                entityWorld.removeComponent(entity, AnimationComponent.class);
            }
            CurrentActionEffectCastsComponent currentActionEffectCastsComponent = entityWorld.getComponent(entity, CurrentActionEffectCastsComponent.class);
            if(currentActionEffectCastsComponent != null){
                for(int effectCastEntity : currentActionEffectCastsComponent.getEffectCastEntities()){
                    entityWorld.removeEntity(effectCastEntity);
                }
            }
            entityWorld.removeComponent(entity, IsCastingComponent.class);
            for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, CastingFinishedTriggerComponent.class)){
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                if(sourceEntity == entity){
                    entityWorld.removeEntity(effectTriggerEntity);
                }
            }
        }
        return isAllowed;
    }
}
