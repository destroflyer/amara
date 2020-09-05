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
public class SetLevelExperienceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, LevelComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(LevelComponent.class)){
            entityWorld.setComponent(entity, new ExperienceComponent(0));
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(LevelComponent.class)){
            entityWorld.removeComponent(entity, ExperienceComponent.class);
        }
    }
}
