/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.camera;

import amara.engine.appstates.IngameCameraAppState;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;

/**
 *
 * @author Carl
 */
public class LockedCameraSystem implements EntitySystem{

    public LockedCameraSystem(int playerEntity, IngameCameraAppState ingameCameraAppState){
        this.playerEntity = playerEntity;
        this.ingameCameraAppState = ingameCameraAppState;
    }
    private int playerEntity;
    private IngameCameraAppState ingameCameraAppState;
    private boolean isEnabled;
    private boolean wasZoomEnabled;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(isEnabled){
            SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
            if(selectedUnitComponent != null){
                PositionComponent positionComponent = entityWorld.getComponent(selectedUnitComponent.getEntity(), PositionComponent.class);
                if(positionComponent != null){
                    ingameCameraAppState.lookAt(positionComponent.getPosition());
                }
            }
        }
    }
    
    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
        ingameCameraAppState.setMovementEnabled(!isEnabled);
        if(isEnabled){
            ingameCameraAppState.saveState();
            wasZoomEnabled = ingameCameraAppState.isZoomEnabled();
            ingameCameraAppState.setZoomEnabled(false);
        }
        else{
            ingameCameraAppState.restoreState();
            ingameCameraAppState.setZoomEnabled(wasZoomEnabled);
        }
    }

    public boolean isEnabled(){
        return isEnabled;
    }
}
