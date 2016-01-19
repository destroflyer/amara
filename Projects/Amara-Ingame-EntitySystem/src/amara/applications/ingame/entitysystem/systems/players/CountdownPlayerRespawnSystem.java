/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.players;

import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownPlayerRespawnSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int playerEntity : entityWorld.getEntitiesWithAll(WaitingToRespawnComponent.class))
        {
            WaitingToRespawnComponent respawnComponent = entityWorld.getComponent(playerEntity, WaitingToRespawnComponent.class);
            float duration = (respawnComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWorld.setComponent(playerEntity, new WaitingToRespawnComponent(duration));
            }
            else{
                entityWorld.removeComponent(playerEntity, WaitingToRespawnComponent.class);
                entityWorld.setComponent(playerEntity, new RespawnComponent());
            }
        }
    }
}
