/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spells.*;

/**
 *
 * @author Carl
 */
public class SetBaseCooldownSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, BaseCooldownComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(BaseCooldownComponent.class)){
            float cooldownDuration = entityWorld.getComponent(entity, BaseCooldownComponent.class).getDuration();
            entityWorld.setComponent(entity, new CooldownComponent(cooldownDuration));
        }
        observer.reset();
    }
}
