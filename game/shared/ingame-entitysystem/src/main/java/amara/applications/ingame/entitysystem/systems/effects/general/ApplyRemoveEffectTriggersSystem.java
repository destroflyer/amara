/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.general;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyRemoveEffectTriggersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveEffectTriggersComponent.class)))
        {
            for(int effectTriggerEntity : entityWrapper.getComponent(RemoveEffectTriggersComponent.class).getEffectTriggerEntities()){
                entityWorld.removeComponent(effectTriggerEntity, TriggerSourceComponent.class);
            }
        }
    }
}
