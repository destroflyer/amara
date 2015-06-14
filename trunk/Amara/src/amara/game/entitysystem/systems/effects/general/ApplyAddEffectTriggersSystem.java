/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.general;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.units.effecttriggers.*;

/**
 *
 * @author Carl
 */
public class ApplyAddEffectTriggersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddEffectTriggersComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            for(int effectTriggerEntity : entityWrapper.getComponent(AddEffectTriggersComponent.class).getEffectTriggerEntities()){
                entityWorld.setComponent(effectTriggerEntity, new TriggerSourceComponent(targetID));
            }
        }
    }
}
