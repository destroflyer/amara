/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.stacks.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;

/**
 *
 * @author Philipp
 */
public class TriggerStacksReachedEffectSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, StacksReachedTriggerComponent.class)){
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            StacksComponent stacksComponent = entityWorld.getComponent(sourceEntity, StacksComponent.class);
            if(stacksComponent != null){
                int minimumStacks = entityWorld.getComponent(effectTriggerEntity, StacksReachedTriggerComponent.class).getStacks();
                if(stacksComponent.getStacks() >= minimumStacks){
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }
}
