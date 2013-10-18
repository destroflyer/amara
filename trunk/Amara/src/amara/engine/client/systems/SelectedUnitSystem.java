/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.selection.IsSelectedComponent;

/**
 *
 * @author Carl
 */
public class SelectedUnitSystem implements EntitySystem{

    
    private int selectedEntity = -1;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, IsSelectedComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(IsSelectedComponent.class))
        {
            selectedEntity = -1;
        }
        for(int entity : observer.getNew().getEntitiesWithAll(IsSelectedComponent.class))
        {
            selectedEntity = entity;
        }
        observer.reset();
    }

    public int getSelectedEntity(){
        return selectedEntity;
    }
}
