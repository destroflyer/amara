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
public class DisplayPlayerSystem implements EntitySystem{

    public DisplayPlayerSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        this.playerEntity = playerEntity;
        this.screenController_HUD = screenController_HUD;
    }
    private int playerEntity;
    private ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
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
