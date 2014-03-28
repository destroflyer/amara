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
    
    public static void triggerEffects(EntityWorld entityWorld, int[] effectTriggerEntities, int targetEntity){
        for(int effectTriggerEntity : effectTriggerEntities){
            triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
        }
    }
    
    public static void triggerEffect(EntityWorld entityWorld, int effectTriggerEntity, int targetEntity){
        EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
        TriggeredEffectComponent triggeredEffectComponent = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class);
        int effectEntity = triggeredEffectComponent.getEffectEntity();
        ReplaceSpellWithNewSpellComponent replaceSpellWithNewSpellComponent = entityWorld.getComponent(effectEntity, ReplaceSpellWithNewSpellComponent.class);
        if(replaceSpellWithNewSpellComponent != null){
            entityWorld.setComponent(effectEntity, new ReplaceSpellWithNewSpellComponent(replaceSpellWithNewSpellComponent.getSpellIndex(), replaceSpellWithNewSpellComponent.getNewSpellTemplate() + "," + targetEntity));
        }
        effectCast.setComponent(new PrepareEffectComponent(effectEntity));
        CastSourceComponent castSourceComponent = entityWorld.getComponent(triggeredEffectComponent.getSourceEntity(), CastSourceComponent.class);
        if(castSourceComponent != null){
            effectCast.setComponent(new EffectSourceComponent(castSourceComponent.getSourceEntitiyID()));
        }
        LinkedList<Integer> affectedTargets = new LinkedList<Integer>();
        if(entityWorld.hasComponent(effectTriggerEntity, TargetTargetComponent.class)){
            affectedTargets.add(targetEntity);
        }
        if(entityWorld.hasComponent(effectTriggerEntity, CustomTargetComponent.class)){
            int customTargetEntity = entityWorld.getComponent(effectTriggerEntity, CustomTargetComponent.class).getTargetEntity();
            affectedTargets.add(customTargetEntity);
        }
        effectCast.setComponent(new AffectedTargetsComponent(Util.convertToArray(affectedTargets)));
    }
}
