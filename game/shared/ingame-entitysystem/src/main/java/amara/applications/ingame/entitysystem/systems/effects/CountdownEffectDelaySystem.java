/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownEffectDelaySystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(RemainingEffectDelayComponent.class))) {
            RemainingEffectDelayComponent remainingEffectDelayComponent = entityWrapper.getComponent(RemainingEffectDelayComponent.class);
            float duration = (remainingEffectDelayComponent.getDuration() - deltaSeconds);
            if (duration > 0) {
                entityWrapper.setComponent(new RemainingEffectDelayComponent(duration));
            } else{
                entityWrapper.removeComponent(RemainingEffectDelayComponent.class);
            }
        }
    }
}
