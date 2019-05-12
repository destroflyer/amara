/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.players;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.shared.games.Game;
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
            int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
            entityWorld.removeComponent(characterEntity, AnimationComponent.class);
            entityWorld.removeComponent(characterEntity, DamageHistoryComponent.class);
            entityWorld.setComponent(characterEntity, new HitboxActiveComponent());
            entityWorld.setComponent(characterEntity, new IsAliveComponent());
            entityWorld.setComponent(characterEntity, new IsTargetableComponent());
            entityWorld.setComponent(characterEntity, new IsVulnerableComponent());
            entityWorld.setComponent(characterEntity, new RequestUpdateAttributesComponent());
            game.getMap().spawnPlayer(entityWorld, playerEntity);
        }
    }
}
