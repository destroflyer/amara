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
public class DisplayScoreboardPlayersNamesSystem extends PlayersDisplaySystem{

    public DisplayScoreboardPlayersNamesSystem(ScreenController_HUD screenController_HUD){
        super(screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int playerEntity){
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        ComponentMapObserver observer = entityWorld.requestObserver(this, NameComponent.class);
        check(playerIndex, observer.getNew().getComponent(playerEntity, NameComponent.class));
        check(playerIndex, observer.getChanged().getComponent(playerEntity, NameComponent.class));
    }
    
    private void check(int playerIndex, NameComponent nameComponent){
        if(nameComponent != null){
            screenController_HUD.setScoreboard_PlayerName(playerIndex, nameComponent.getName());
        }
    }
}
