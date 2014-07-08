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
            }
            else{
                entityWorld.setComponent(effectTriggerEntity, new TimeSinceLastRepeatTriggerComponent(timeSinceLastRepeatTrigger));
            }
        }
    }
}
