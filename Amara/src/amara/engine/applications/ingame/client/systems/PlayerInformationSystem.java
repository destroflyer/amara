/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.players.*;

/**
 *
 * @author Carl
 */
public class PlayerInformationSystem implements EntitySystem{

    public PlayerInformationSystem(int clientID){
        this.clientID = clientID;
    }
    private int clientID = -1;
    private int playerEntityID = -1;
    private int selectedEntity = -1;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(playerEntityID == -1){
            for(int entity : entityWorld.getEntitiesWithAll(ClientComponent.class)){
                if(entityWorld.getComponent(entity, ClientComponent.class).getClientID() == clientID){
                    playerEntityID = entity;
                    break;
                }
            }
        }
        else{
            selectedEntity = entityWorld.getComponent(playerEntityID, SelectedUnitComponent.class).getEntityID();
        }
    }

    public int getSelectedEntity(){
        return selectedEntity;
    }
}
