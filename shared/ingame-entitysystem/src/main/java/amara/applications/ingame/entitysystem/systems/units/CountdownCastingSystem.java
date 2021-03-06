/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownCastingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAny(IsCastingComponent.class))){
            IsCastingComponent isCastingComponent = entityWrapper.getComponent(IsCastingComponent.class);
            float duration = (isCastingComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsCastingComponent(duration, isCastingComponent.isCancelable()));
            }
            else{
                entityWrapper.removeComponent(IsCastingComponent.class);
                entityWrapper.removeComponent(CurrentCastEffectCastsComponent.class);
            }
        }
    }
}
