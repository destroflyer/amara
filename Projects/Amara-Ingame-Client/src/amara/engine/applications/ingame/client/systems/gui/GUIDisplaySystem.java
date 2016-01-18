/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.components.players.SelectedUnitComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class GUIDisplaySystem implements EntitySystem{

    public GUIDisplaySystem(int playerEntity, ScreenController_HUD screenController_HUD){
        this.playerEntity = playerEntity;
        this.screenController_HUD = screenController_HUD;
    }
    protected int playerEntity;
    protected ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(screenController_HUD.isVisible()){
            SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
            if(selectedUnitComponent != null){
                update(entityWorld, deltaSeconds, selectedUnitComponent.getEntity());
            }
        }
    }
    
    protected abstract void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity);
}
