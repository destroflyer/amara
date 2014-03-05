/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;

/**
 *
 * @author Carl
 */
public class ApplyAddBindingImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBindingImmuneComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            AddBindingImmuneComponent addBindingImmuneComponent = entityWrapper.getComponent(AddBindingImmuneComponent.class);
            IsBindedImmuneComponent isBindedImmuneComponent = entityWorld.getComponent(targetID, IsBindedImmuneComponent.class);
            if((isBindedImmuneComponent == null) || (addBindingImmuneComponent.getDuration() > isBindedImmuneComponent.getRemainingDuration())){
                entityWorld.setComponent(targetID, new IsBindedImmuneComponent(addBindingImmuneComponent.getDuration()));
            }
        }
    }
}
