/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.units.passives.*;
import amara.game.entitysystem.systems.effects.triggers.EffectTriggerUtil;

/**
 *
 * @author Carl
 */
public class PassiveUtil{
    
    public static void addPassives(EntityWorld entityWorld, int... passiveEntities){
        triggerPassives(entityWorld, passiveEntities, true);
    }
    
    public static void removePassives(EntityWorld entityWorld, int... passiveEntities){
        triggerPassives(entityWorld, passiveEntities, false);
    }
    
    private static void triggerPassives(EntityWorld entityWorld, int[] passiveEntities, boolean addedOrRemoved){
        for(int passiveEntity : passiveEntities){
            int[] effectTriggersEntities;
            if(addedOrRemoved){
                effectTriggersEntities = entityWorld.getComponent(passiveEntity, PassiveAddedEffectTriggersComponent.class).getEffectTriggerEntities();
            }
            else{
                effectTriggersEntities = entityWorld.getComponent(passiveEntity, PassiveRemovedEffectTriggersComponent.class).getEffectTriggerEntities();
            }
            for(int effectTriggerEntity : effectTriggersEntities){
                EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
            }
        }
    }
}
