/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import java.util.LinkedList;
import amara.Util;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;

/**
 *
 * @author Carl
 */
public class EffectTriggerUtil{
    
    public static LinkedList<EntityWrapper> triggerEffects(EntityWorld entityWorld, int[] effectTriggerEntities, int targetEntity){
        LinkedList<EntityWrapper> effectCasts = new LinkedList<EntityWrapper>();
        for(int effectTriggerEntity : effectTriggerEntities){
            effectCasts.add(triggerEffect(entityWorld, effectTriggerEntity, targetEntity));
        }
        return effectCasts;
    }
    
    public static EntityWrapper triggerEffect(EntityWorld entityWorld, int effectTriggerEntity, int targetEntity){
        EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
        TriggeredEffectComponent triggeredEffectComponent = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class);
        TriggerSourceComponent triggerSourceComponent = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class);
        int effectEntity = triggeredEffectComponent.getEffectEntity();
        ReplaceSpellWithNewSpellComponent replaceSpellWithNewSpellComponent = entityWorld.getComponent(effectEntity, ReplaceSpellWithNewSpellComponent.class);
        if(replaceSpellWithNewSpellComponent != null){
            entityWorld.setComponent(effectEntity, new ReplaceSpellWithNewSpellComponent(replaceSpellWithNewSpellComponent.getSpellIndex(), replaceSpellWithNewSpellComponent.getNewSpellTemplate() + "," + targetEntity));
        }
        effectCast.setComponent(new PrepareEffectComponent(effectEntity));
        LinkedList<Integer> affectedTargets = new LinkedList<Integer>();
        if(triggerSourceComponent != null){
            CastSourceComponent castSourceComponent = entityWorld.getComponent(triggerSourceComponent.getSourceEntity(), CastSourceComponent.class);
            if(castSourceComponent != null){
                effectCast.setComponent(new EffectSourceComponent(castSourceComponent.getSourceEntity()));
                if(entityWorld.hasComponent(effectTriggerEntity, CasterTargetComponent.class)){
                    affectedTargets.add(castSourceComponent.getSourceEntity());
                }
            }
            if(entityWorld.hasComponent(effectTriggerEntity, SourceTargetComponent.class)){
                affectedTargets.add(triggerSourceComponent.getSourceEntity());
            }
        }
        if(entityWorld.hasComponent(effectTriggerEntity, TargetTargetComponent.class) && (targetEntity != -1)){
            affectedTargets.add(targetEntity);
        }
        if(entityWorld.hasComponent(effectTriggerEntity, CustomTargetComponent.class)){
            int customTargetEntity = entityWorld.getComponent(effectTriggerEntity, CustomTargetComponent.class).getTargetEntity();
            affectedTargets.add(customTargetEntity);
        }
        effectCast.setComponent(new AffectedTargetsComponent(Util.convertToArray(affectedTargets)));
        if(entityWorld.hasComponent(effectTriggerEntity, TriggerOnceComponent.class)){
            entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
        }
        return effectCast;
    }
}
