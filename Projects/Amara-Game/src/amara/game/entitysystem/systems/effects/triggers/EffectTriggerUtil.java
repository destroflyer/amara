/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import java.util.LinkedList;
import amara.core.Util;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.systems.conditions.ConditionUtil;

/**
 *
 * @author Carl
 */
public class EffectTriggerUtil{
    
    public static LinkedList<EntityWrapper> triggerEffects(EntityWorld entityWorld, int[] effectTriggerEntities, int targetEntity){
        LinkedList<EntityWrapper> effectCasts = new LinkedList<EntityWrapper>();
        for(int effectTriggerEntity : effectTriggerEntities){
            EntityWrapper effectCast = triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
            if(effectCast != null){
                effectCasts.add(effectCast);
            }
        }
        return effectCasts;
    }
    
    public static EntityWrapper triggerEffect(EntityWorld entityWorld, int effectTriggerEntity, int targetEntity){
        if(areTriggerConditionsMet(entityWorld, effectTriggerEntity, targetEntity)){
            EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
            int effectEntity = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getEffectEntity();
            effectCast.setComponent(new PrepareEffectComponent(effectEntity));
            TriggerSourceComponent triggerSourceComponent = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class);
            if(triggerSourceComponent != null){
                EntityUtil.transferComponents(entityWorld, triggerSourceComponent.getSourceEntity(), effectCast.getId(), new Class[]{
                    EffectCastSourceComponent.class,
                    EffectCastSourceSpellComponent.class
                });
            }
            if(targetEntity != -1){
                effectCast.setComponent(new EffectCastTargetComponent(targetEntity));
            }
            int[] targetEntities = getTargetEntities(entityWorld, effectTriggerEntity, targetEntity);
            effectCast.setComponent(new AffectedTargetsComponent(targetEntities));
            if(entityWorld.hasComponent(effectTriggerEntity, TriggerOnceComponent.class)){
                entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
            }
            TriggerDelayComponent triggerDelayComponent = entityWorld.getComponent(effectTriggerEntity, TriggerDelayComponent.class);
            if(triggerDelayComponent != null){
                effectCast.setComponent(new RemainingEffectDelayComponent(triggerDelayComponent.getDuration()));
            }
            return effectCast;
        }
        return null;
    }
    
    public static boolean areTriggerConditionsMet(EntityWorld entityWorld, int effectTriggerEntity, int targetEntity){
        TriggerConditionsComponent triggerConditionsComponent = entityWorld.getComponent(effectTriggerEntity, TriggerConditionsComponent.class);
        if(triggerConditionsComponent != null){
            for(int conditionEntity : triggerConditionsComponent.getConditionEntities()){
                int[] conditionTargetEntities = getTargetEntities(entityWorld, conditionEntity, targetEntity);
                for(int conditionTargetEntity : conditionTargetEntities){
                    if(!ConditionUtil.isConditionMet(entityWorld, conditionEntity, conditionTargetEntity)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private static LinkedList<Integer> tmpTargetEntities = new LinkedList<Integer>();
    private static int[] getTargetEntities(EntityWorld entityWorld, int entity, int targetEntity){
        tmpTargetEntities.clear();
        TriggerSourceComponent triggerSourceComponent = entityWorld.getComponent(entity, TriggerSourceComponent.class);
        if(triggerSourceComponent != null){
            if(entityWorld.hasComponent(entity, SourceTargetComponent.class)){
                tmpTargetEntities.add(triggerSourceComponent.getSourceEntity());
            }
            if(entityWorld.hasComponent(entity, CasterTargetComponent.class)){
                EffectCastSourceComponent castSourceComponent = entityWorld.getComponent(triggerSourceComponent.getSourceEntity(), EffectCastSourceComponent.class);
                if(castSourceComponent != null){
                    tmpTargetEntities.add(castSourceComponent.getSourceEntity());
                }
            }
        }
        if(entityWorld.hasComponent(entity, TargetTargetComponent.class)){
            tmpTargetEntities.add(targetEntity);
        }
        BuffTargetsTargetComponent buffTargetTargetComponent = entityWorld.getComponent(entity, BuffTargetsTargetComponent.class);
        if(buffTargetTargetComponent != null){
            for(int buffStatusEntity : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                if(activeBuffComponent.getBuffEntity() == buffTargetTargetComponent.getBuffEntity()){
                    tmpTargetEntities.add(activeBuffComponent.getTargetEntity());
                }
            }
        }
        CustomTargetComponent customTargetComponent = entityWorld.getComponent(entity, CustomTargetComponent.class);
        if(customTargetComponent != null){
            for(int customTargetEntity : customTargetComponent.getTargetEntities()){
                tmpTargetEntities.add(customTargetEntity);
            }
        }
        return Util.convertToArray(tmpTargetEntities);
    }
}
