/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import java.util.LinkedList;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.passives.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.core.Util;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class PassiveUtil{
    
    private static LinkedList<Integer> tmpPassiveEntities_Current = new LinkedList<Integer>();
    private static LinkedList<Integer> tmpPassiveEntities_Changed = new LinkedList<Integer>();
    
    public static void addPassives(EntityWorld entityWorld, int targetEntity, int... passiveEntities){
        triggerPassives(entityWorld, targetEntity, passiveEntities, true);
    }
    
    public static void removePassives(EntityWorld entityWorld, int targetEntity, int... passiveEntities){
        triggerPassives(entityWorld, targetEntity, passiveEntities, false);
    }
    
    private static void triggerPassives(EntityWorld entityWorld, int targetEntity, int[] passiveEntities, boolean addedOrRemoved){
        tmpPassiveEntities_Current.clear();
        tmpPassiveEntities_Changed.clear();
        CurrentPassivesComponent currentPassivesComponent = entityWorld.getComponent(targetEntity, CurrentPassivesComponent.class);
        if(currentPassivesComponent != null){
            for(int passiveEntity : currentPassivesComponent.getPassiveEntities()){
                tmpPassiveEntities_Current.add(passiveEntity);
            }
        }
        for(int passiveEntity : passiveEntities){
            boolean hasChanged = true;
            int uniquePassiveEntity = passiveEntity;
            if(entityWorld.hasComponent(passiveEntity, UniqueComponent.class)){
                NameComponent nameComponent = entityWorld.getComponent(passiveEntity, NameComponent.class);
                if(nameComponent != null){
                    int uniquePassivesCount = 0;
                    for(int i=(tmpPassiveEntities_Current.size() - 1);i>=0;i--){
                        int tmpPassiveEntity = tmpPassiveEntities_Current.get(i);
                        if(entityWorld.hasComponent(tmpPassiveEntity, UniqueComponent.class)){
                            NameComponent tmpNameComponent = entityWorld.getComponent(tmpPassiveEntity, NameComponent.class);
                            if((tmpNameComponent != null) && nameComponent.getName().equals(tmpNameComponent.getName())){
                                if(uniquePassivesCount == 0){
                                    uniquePassiveEntity = tmpPassiveEntity;
                                }
                                uniquePassivesCount++;
                            }
                        }
                    }
                    if((addedOrRemoved && (uniquePassivesCount > 0))
                    || ((!addedOrRemoved) && (uniquePassivesCount != 1))){
                        hasChanged = false;
                    }
                }
            }
            if(addedOrRemoved){
                tmpPassiveEntities_Current.add(passiveEntity);
            }
            else{
                tmpPassiveEntities_Current.remove((Integer) uniquePassiveEntity);
            }
            if(hasChanged){
                tmpPassiveEntities_Changed.add(uniquePassiveEntity);
            }
        }
        entityWorld.setComponent(targetEntity, new CurrentPassivesComponent(Util.convertToArray(tmpPassiveEntities_Current)));
        for(int passiveEntity : tmpPassiveEntities_Changed){
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
