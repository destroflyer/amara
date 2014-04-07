/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.players;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.games.Game;

/**
 *
 * @author Carl
 */
public class PlayerRespawnSystem implements EntitySystem{

    public PlayerRespawnSystem(Game game){
        this.game = game;
    }
    private Game game;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int playerEntity : entityWorld.getEntitiesWithAll(RespawnComponent.class))
        {
            entityWorld.removeComponent(playerEntity, RespawnComponent.class);
            int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntityID();
            int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
            String unitTemplate = game.getPlayers()[playerIndex].getPlayerData().getUnitTemplate();
            EntityTemplate.loadTemplate(entityWorld, selectedEntity, unitTemplate + "_spawn");
            entityWorld.setComponent(selectedEntity, new RequestUpdateAttributesComponent());
            entityWorld.removeComponent(selectedEntity, AnimationComponent.class);
            game.getMap().spawn(entityWorld, playerEntity);
        }
    }
}
