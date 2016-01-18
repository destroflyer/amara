/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.buffs.RemoveBuffsSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RemoveDeadUnitsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsAliveComponent.class))
        {
            if(!UnitUtil.isPlayerUnit(entityWorld, entity)){
                UnitUtil.cancelAction(entityWorld, entity);
                RemoveBuffsSystem.removeAllBuffs(entityWorld, entity);
                entityWorld.removeEntity(entity);
            }
        }
    }
}
