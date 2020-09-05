/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.camera;

import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.core.settings.Settings;
import amara.libraries.applications.display.ingame.appstates.IngameCameraAppState;
import amara.libraries.entitysystem.*;

import java.util.function.Supplier;

/**
 *
 * @author Carl
 */
public class MoveCameraToPlayerSystem implements EntitySystem{

    public MoveCameraToPlayerSystem(Supplier<Integer> playerEntity, IngameCameraAppState ingameCameraAppState){
        this.playerEntity = playerEntity;
        this.ingameCameraAppState = ingameCameraAppState;
    }
    private Supplier<Integer> playerEntity;
    private IngameCameraAppState ingameCameraAppState;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity.get(), PlayerCharacterComponent.class);
        if(playerCharacterComponent != null){
            int characterEntity = playerCharacterComponent.getEntity();
            ComponentMapObserver observer = entityWorld.requestObserver(this, PositionComponent.class);
            PositionComponent positionComponent = entityWorld.getComponent(characterEntity, PositionComponent.class);
            if(observer.getNew().hasEntity(characterEntity)){
                ingameCameraAppState.lookAt(positionComponent.getPosition());
            }
            else if(observer.getChanged().hasEntity(playerCharacterComponent.getEntity())){
                // TODO: Analyze what I did here and fix...
                if(!ingameCameraAppState.isVisible(positionComponent.getPosition(), Settings.getInteger("camera_move_to_player_screen_extension"))){
                    //ingameCameraAppState.lookAt(positionComponent.getPosition());
                }
            }
        }
    }
}
