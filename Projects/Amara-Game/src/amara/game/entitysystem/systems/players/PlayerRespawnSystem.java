/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.players;

import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.games.Game;
import amara.libraries.entitysystem.*;

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
        for(int playerEntity : entityWorld.getEntitiesWithAll(RespawnComponent.class)){
            entityWorld.removeComponent(playerEntity, RespawnComponent.class);
            int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
            entityWorld.removeComponent(selectedEntity, AnimationComponent.class);
            entityWorld.removeComponent(selectedEntity, DamageHistoryComponent.class);
            entityWorld.setComponent(selectedEntity, new HitboxActiveComponent());
            entityWorld.setComponent(selectedEntity, new IsAliveComponent());
            entityWorld.setComponent(selectedEntity, new IsTargetableComponent());
            entityWorld.setComponent(selectedEntity, new IsVulnerableComponent());
            entityWorld.setComponent(selectedEntity, new RequestUpdateAttributesComponent());
            game.getMap().spawnPlayer(entityWorld, playerEntity);
        }
    }
}
