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
public class CountdownInCombatSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(InCombatComponent.class))){
            InCombatComponent inCombatComponent = entityWrapper.getComponent(InCombatComponent.class);
            float duration = (inCombatComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new InCombatComponent(duration));
            }
            else{
                entityWrapper.removeComponent(InCombatComponent.class);
            }
        }
    }
}
