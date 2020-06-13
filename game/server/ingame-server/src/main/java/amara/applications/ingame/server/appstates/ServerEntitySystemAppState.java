/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import amara.applications.ingame.entitysystem.GameLogic;
import amara.applications.ingame.entitysystem.components.players.ClientComponent;
import amara.applications.ingame.entitysystem.synchronizing.ClientComponentBlacklist;
import amara.applications.ingame.entitysystem.synchronizing.GameSynchronizingUtil;
import amara.applications.ingame.entitysystem.systems.network.SendEntityChangesSystem;
import amara.applications.ingame.network.messages.Message_EntityChanges;
import amara.applications.ingame.network.messages.Message_InitialEntityWorldSent;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.server.entitysystem.systems.objectives.CheckMapObjectiveSystem;
import amara.applications.ingame.server.network.backends.SendGameInfoBackend;
import amara.applications.ingame.server.network.backends.AddNewClientsBackend;
import amara.applications.ingame.server.network.backends.RemoveLeavingClientsBackend;
import amara.applications.ingame.server.network.backends.StartGameBackend;
import amara.applications.ingame.shared.games.Game;
import amara.applications.ingame.shared.games.GamePlayer;
import amara.applications.ingame.shared.games.GamePlayerInfo_Human;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.EntitySystemHeadlessAppState;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.synchronizing.EntityChange;
import amara.libraries.entitysystem.synchronizing.NewComponentChange;
import amara.libraries.network.SubNetworkServer;

import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class ServerEntitySystemAppState extends EntitySystemHeadlessAppState<IngameServerApplication> {

    private LinkedList<Integer> newClientIds = new LinkedList<>();
    private LinkedList<Integer> initializedClientIds = new LinkedList<>();
    private ClientComponentBlacklist clientComponentBlacklist = new ClientComponentBlacklist();

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        Game game = mainApplication.getGame();
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.addMessageBackend(new SendGameInfoBackend(game, clientId -> mainApplication.getMasterServer().getPlayerId(clientId)));
        subNetworkServer.addMessageBackend(new AddNewClientsBackend(this));
        subNetworkServer.addMessageBackend(new RemoveLeavingClientsBackend(this));
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
        for (GamePlayer player : game.getPlayers()) {
            playerEntitiesAppState.createPlayerEntity(entityWorld, map, player, playerIndex);
            playerIndex++;
        }
        // Register the entity systems
        for (EntitySystem entitySystem : gameLogic.getEntitySystems()) {
            addEntitySystem(entitySystem);
        }
        addEntitySystem(new SendEntityChangesSystem(subNetworkServer, initializedClientIds, clientComponentBlacklist));
        addEntitySystem(new CheckMapObjectiveSystem(map, mainApplication));
        // Precalculate first frame, so automatic entity processes will be done for the initial world
        super.update(0);
    }

    @Override
    public void update(float lastTimePerFrame) {
        if (mainApplication.getGame().isStarted()) {
            GameSynchronizingUtil.simulateSecondFrames(entityWorld, lastTimePerFrame, super::update);
        }
        initializeNewClients();
    }

    private void initializeNewClients() {
        for (int newClientId : newClientIds) {
            sendInitialEntityWorldAndCreatePlayerEntityIfNotExisting(newClientId);
        }
        initializedClientIds.addAll(newClientIds);
        newClientIds.clear();
    }

    private void sendInitialEntityWorldAndCreatePlayerEntityIfNotExisting(int clientId) {
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        LinkedList<EntityChange> entityChanges = new LinkedList<>();
        for (Integer entity : entityWorld.getEntitiesWithAll()) {
            for (Object component : entityWorld.getComponents(entity)) {
                if (!clientComponentBlacklist.contains(ClientComponentBlacklist.ChangeType.NEW, component.getClass())) {
                    entityChanges.add(new NewComponentChange(entity, component));
                }
            }
        }
        Message_EntityChanges[] entityChangeMessages = SendEntityChangesSystem.getEntityChangesMessages(entityChanges);
        for (Message_EntityChanges entityChangeMessage : entityChangeMessages) {
            subNetworkServer.sendMessageToClient(clientId, entityChangeMessage);
        }
        // Create the player entity AFTER the initial world collection, because it will be included in the entity changes of the next frame
        int playerEntity = getOrCreatePlayerEntity(clientId);
        entityWorld.setComponent(playerEntity, new ClientComponent(clientId));
        subNetworkServer.sendMessageToClient(clientId, new Message_InitialEntityWorldSent(playerEntity));
    }

    private int getOrCreatePlayerEntity(int clientId) {
        Game game = mainApplication.getGame();
        GamePlayer gamePlayer = game.getPlayerByClientId(clientId);
        if (gamePlayer.getEntity() == -1) {
            PlayerEntitiesAppState playerEntitiesAppState = getAppState(PlayerEntitiesAppState.class);
            playerEntitiesAppState.createPlayerEntity(entityWorld, game.getMap(), gamePlayer, null);
        }
        return gamePlayer.getEntity();
    }

    public void addNewClient(int clientId) {
        newClientIds.add(clientId);
        // TODO: If MMO Map
        if (false) {
            // TODO: Add player correctly
            mainApplication.getGame().addPlayer(null);
        }
    }

    public void removeInitializedClient(int clientId) {
        initializedClientIds.remove((Integer) clientId);
        Game game = mainApplication.getGame();
        GamePlayer player = game.getPlayerByClientId(clientId);
        // TODO: If MMO Map
        if (false) {
            game.removePlayer(player);
        } else {
            GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) player.getGamePlayerInfo();
            gamePlayerInfo_Human.setClientId(null);
            gamePlayerInfo_Human.setReady(false);
        }
    }
}
