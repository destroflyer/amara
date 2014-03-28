/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.general.NameComponent;

/**
 *
 * @author Carl
 */
public class DisplayPlayerSystem extends GUIDisplaySystem{

    public DisplayPlayerSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, NameComponent.class);
        check(observer.getNew().getComponent(playerEntity, NameComponent.class));
        check(observer.getChanged().getComponent(playerEntity, NameComponent.class));
        observer.reset();
    }
    
    private void check(NameComponent nameComponent){
        if(nameComponent != null){
            screenController_HUD.setPlayerName(nameComponent.getName());
        }
    }
}
