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
public class ApplyBindingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(ApplyEffectComponent.class, BindingComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectComponent.class).getTargetID();
            BindingComponent bindingComponent = entityWrapper.getComponent(BindingComponent.class);
            IsBindedComponent isBindedComponent = entityWorld.getCurrent().getComponent(targetID, IsBindedComponent.class);
            if((isBindedComponent == null) || (bindingComponent.getDuration() > isBindedComponent.getRemainingDuration())){
                entityWorld.getCurrent().setComponent(targetID, new IsBindedComponent(bindingComponent.getDuration()));
            }
        }
    }
}
