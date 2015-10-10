/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;

/**
 *
 * @author Philipp
 */
public class TriggerRepeatingEffectSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, RepeatingTriggerComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(RepeatingTriggerComponent.class)){
            entityWorld.setComponent(entity, new RepeatingTriggerCounterComponent(0));
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(RepeatingTriggerComponent.class)){
            entityWorld.removeComponent(entity, RepeatingTriggerCounterComponent.class);
        }
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, RepeatingTriggerComponent.class))
        {
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
    
    private void increaseRepeatingTriggerCounter(EntityWorld entityWorld, int effectTriggerEnttiy){
        int counter = 0;
        RepeatingTriggerCounterComponent repeatingTriggerCounterComponent = entityWorld.getComponent(effectTriggerEnttiy, RepeatingTriggerCounterComponent.class);
        if(repeatingTriggerCounterComponent != null){
            counter = repeatingTriggerCounterComponent.getCounter();
        }
        entityWorld.setComponent(effectTriggerEnttiy, new RepeatingTriggerCounterComponent(counter + 1));
    }
}
