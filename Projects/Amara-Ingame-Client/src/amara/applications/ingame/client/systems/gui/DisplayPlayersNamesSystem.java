/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayPlayersNamesSystem extends GUIDisplaySystem{

    public DisplayPlayersNamesSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, NameComponent.class);
        for(int playerEntity : entityWorld.getEntitiesWithAll(PlayerIndexComponent.class)){
            int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
            check(playerIndex, observer.getNew().getComponent(playerEntity, NameComponent.class));
            check(playerIndex, observer.getChanged().getComponent(playerEntity, NameComponent.class));
        }
    }
    
    private void check(int playerIndex, NameComponent nameComponent){
        if(nameComponent != null){
            screenController_HUD.setPlayerName(playerIndex, nameComponent.getName());
        }
    }
}
