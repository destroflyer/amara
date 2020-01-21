/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import amara.applications.ingame.entitysystem.GameLogic;
import amara.applications.ingame.entitysystem.synchronizing.ClientComponentBlacklist;
import amara.applications.ingame.entitysystem.synchronizing.GameSynchronizingUtil;
import amara.applications.ingame.entitysystem.systems.network.SendEntityChangesSystem;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.server.entitysystem.systems.objectives.CheckMapObjectiveSystem;
import amara.applications.ingame.server.network.backends.AuthentificateClientsBackend;
import amara.applications.ingame.server.network.backends.SendInitialEntityWorldBackend;
import amara.applications.ingame.server.network.backends.StartGameBackend;
import amara.applications.ingame.shared.games.Game;
import amara.applications.ingame.shared.games.GamePlayer;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.EntitySystemHeadlessAppState;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.network.SubNetworkServer;

/**
 *
 * @author Carl
 */
public class ServerEntitySystemAppState extends EntitySystemHeadlessAppState<IngameServerApplication> {

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        Game game = mainApplication.getGame();
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.addMessageBackend(new AuthentificateClientsBackend(game, entityWorld));
        subNetworkServer.addMessageBackend(new SendInitialEntityWorldBackend(entityWorld));
        subNetworkServer.addMessageBackend(new StartGameBackend(game));
        // Initialize game logic
        GameLogic gameLogic = new GameLogic(
            entityWorld,
            game,
            getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue(),
            playerEntity -> getAppState(BotsAppState.class).getBot(playerEntity)
        );
        gameLogic.initialize();
        // Initialize game content
        Map map = game.getMap();
        map.load(entityWorld);
        PlayerEntitiesAppState playerEntitiesAppState = getAppState(PlayerEntitiesAppState.class);
        int playerIndex = 0;
        for (GamePlayer[] team : game.getTeams()) {
            for (GamePlayer player : team) {
                playerEntitiesAppState.createPlayerEntity(entityWorld, map, player, playerIndex);
                playerIndex++;
            }
        }
        // Register the entity systems
        for (EntitySystem entitySystem : gameLogic.getEntitySystems()) {
            addEntitySystem(entitySystem);
        }
        addEntitySystem(new SendEntityChangesSystem(subNetworkServer, new ClientComponentBlacklist()));
        addEntitySystem(new CheckMapObjectiveSystem(map, mainApplication));
        // Precalculate first frame, so automatic entity processes will be done for the initial world
        super.update(0);
    }

    @Override
    public void update(float lastTimePerFrame) {
        if (mainApplication.getGame().isStarted()) {
            GameSynchronizingUtil.simulateSecondFrames(entityWorld, lastTimePerFrame, simulatedTimePerFrame -> {
                super.update(simulatedTimePerFrame);
            });
        }
    }
}
