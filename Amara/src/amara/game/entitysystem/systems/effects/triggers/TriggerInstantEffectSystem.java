/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;

/**
 *
 * @author Philipp
 */
public class TriggerInstantEffectSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(InstantTriggerComponent.class)){
            EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
        }
    }
}
