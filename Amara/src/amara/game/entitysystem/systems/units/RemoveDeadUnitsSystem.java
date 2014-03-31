/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class RemoveDeadUnitsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, IsAliveComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsAliveComponent.class))
        {
            if(!isPlayerUnit(entityWorld, entity)){
                entityWorld.removeEntity(entity);
            }
        }
        observer.reset();
    }
    
    private boolean isPlayerUnit(EntityWorld entityWorld, int entity){
        for(int playerEntity : entityWorld.getEntitiesWithAll(SelectedUnitComponent.class)){
            int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntityID();
            if(selectedEntity == entity){
                return true;
            }
        }
        return false;
    }
}
