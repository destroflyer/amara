/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Philipp
 */
public class TriggerInstantEffectSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(InstantTriggerComponent.class, TriggerSourceComponent.class)){
            EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
            entityWorld.removeComponent(effectTriggerEntity, InstantTriggerComponent.class);
        }
    }
}
