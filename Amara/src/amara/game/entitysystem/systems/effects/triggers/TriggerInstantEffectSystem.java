/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import java.util.LinkedList;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;

/**
 *
 * @author Philipp
 */
public class TriggerInstantEffectSystem implements EntitySystem{

    private LinkedList<Integer> effectTriggersToRemove = new LinkedList<Integer>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectTriggerEntity : effectTriggersToRemove){
            EffectTriggerUtil.removeTriggerEntity(entityWorld, effectTriggerEntity);
        }
        effectTriggersToRemove.clear();
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(InstantTriggerComponent.class))
        {
            EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
            effectTriggersToRemove.add(effectTriggerEntity);
        }
    }
}
