/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Philipp
 */
public class TriggerRepeatingEffectSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, RepeatingTriggerComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(RepeatingTriggerComponent.class)){
            entityWorld.setComponent(entity, new RepeatingTriggerCounterComponent(0));
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(RepeatingTriggerComponent.class)){
            entityWorld.removeComponent(entity, RepeatingTriggerCounterComponent.class);
        }
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, RepeatingTriggerComponent.class)){
            if(!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)){
                float timeSinceLastRepeatTrigger = deltaSeconds;
                TimeSinceLastRepeatTriggerComponent timeSinceLastRepeatTriggerComponent = entityWorld.getComponent(effectTriggerEntity, TimeSinceLastRepeatTriggerComponent.class);
                if(timeSinceLastRepeatTriggerComponent != null){
                    timeSinceLastRepeatTrigger += timeSinceLastRepeatTriggerComponent.getDuration();
                }
                float intervalDuration = entityWorld.getComponent(effectTriggerEntity, RepeatingTriggerComponent.class).getIntervalDuration();
                if(timeSinceLastRepeatTrigger > intervalDuration){
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                    entityWorld.removeComponent(effectTriggerEntity, TimeSinceLastRepeatTriggerComponent.class);
                    increaseRepeatingTriggerCounter(entityWorld, effectTriggerEntity);
                }
                else{
                    entityWorld.setComponent(effectTriggerEntity, new TimeSinceLastRepeatTriggerComponent(timeSinceLastRepeatTrigger));
                }
            }
        }
    }
    
    private void increaseRepeatingTriggerCounter(EntityWorld entityWorld, int effectTriggerEnttiy){
        int counter = 0;
        RepeatingTriggerCounterComponent repeatingTriggerCounterComponent = entityWorld.getComponent(effectTriggerEnttiy, RepeatingTriggerCounterComponent.class);
        if(repeatingTriggerCounterComponent != null){
            counter = repeatingTriggerCounterComponent.getCounter();
        }
        entityWorld.setComponent(effectTriggerEnttiy, new RepeatingTriggerCounterComponent(counter + 1));
    }
}
