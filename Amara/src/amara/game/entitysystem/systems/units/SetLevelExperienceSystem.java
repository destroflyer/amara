/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class SetLevelExperienceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, LevelComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(LevelComponent.class)){
            entityWorld.setComponent(entity, new ExperienceComponent(0));
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(LevelComponent.class)){
            entityWorld.removeComponent(entity, ExperienceComponent.class);
        }
    }
}
