/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.players;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.players.*;

/**
 *
 * @author Carl
 */
public class PlayerDeathSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper player : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(SelectedUnitComponent.class)))
        {
            int selectedEntity = player.getComponent(SelectedUnitComponent.class).getEntityID();
            if(entityWorld.getComponents(selectedEntity).isEmpty()){
                player.removeComponent(IsAliveComponent.class);
            }
        }
    }
}
