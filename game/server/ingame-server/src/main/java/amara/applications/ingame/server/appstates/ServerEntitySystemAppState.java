package amara.applications.ingame.server.appstates;

import amara.applications.ingame.entitysystem.components.players.ClientComponent;
import amara.applications.ingame.entitysystem.synchronizing.ClientComponentBlacklist;
import amara.applications.ingame.entitysystem.synchronizing.GameSynchronizingUtil;
import amara.applications.ingame.entitysystem.systems.network.SendEntityChangesSystem;
import amara.applications.ingame.network.messages.Message_EntityChanges;
import amara.applications.ingame.network.messages.Message_GameOver;
import amara.applications.ingame.network.messages.Message_InitialEntityWorldSent;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.server.entitysystem.GameLogic;
import amara.applications.ingame.server.entitysystem.systems.mmo.MMOPersistenceSystem;
import amara.applications.ingame.server.entitysystem.systems.objectives.CheckMapObjectiveSystem;
import amara.applications.ingame.server.network.backends.SendGameInfoBackend;
import amara.applications.ingame.server.network.backends.AddNewClientsBackend;
import amara.applications.ingame.server.network.backends.RemoveLeavingClientsBackend;
import amara.applications.ingame.server.network.backends.StartGameBackend;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.GamePlayer;
import amara.applications.master.server.games.GamePlayerInfo_Human;
import amara.applications.master.server.games.MMOGame;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.EntitySystemHeadlessAppState;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.synchronizing.EntityChange;
import amara.libraries.entitysystem.synchronizing.NewComponentChange;
import amara.libraries.network.SubNetworkServer;

import java.util.LinkedList;

public class ServerEntitySystemAppState extends EntitySystemHeadlessAppState<IngameServerApplication> {

    private LinkedList<Integer> newClientIds = new LinkedList<>();
    private LinkedList<Integer> initializedClientIds = new LinkedList<>();
    private ClientComponentBlacklist clientComponentBlacklist = new ClientComponentBlacklist();
    private MMOPersistenceSystem mmoPersistenceSystem;

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        Game game = mainApplication.getGame();
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.addMessageBackend(new SendGameInfoBackend(game));
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
        map.load(entityWorld, game.getPlayers().size());
        IngamePlayersAppState ingamePlayersAppState = getAppState(IngamePlayersAppState.class);
        int playerIndex = 0;
        for (GamePlayer<?> player : game.getPlayers()) {
            ingamePlayersAppState.createPlayerEntity(entityWorld, map, player, playerIndex);
            playerIndex++;
        }
        // Register the entity systems
        for (EntitySystem entitySystem : gameLogic.getEntitySystems()) {
            addEntitySystem(entitySystem);
        }
        addEntitySystem(new SendEntityChangesSystem(subNetworkServer, initializedClientIds, clientComponentBlacklist));
        addEntitySystem(new CheckMapObjectiveSystem(map, mainApplication));
        if (game instanceof MMOGame) {
            DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
            mmoPersistenceSystem = new MMOPersistenceSystem(databaseAppState, game);
            addEntitySystem(mmoPersistenceSystem);
        }
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
        GamePlayer<GamePlayerInfo_Human> gamePlayer = game.getPlayerByClientId(clientId);
        if (gamePlayer.getEntity() == -1) {
            IngamePlayersAppState ingamePlayersAppState = getAppState(IngamePlayersAppState.class);
            ingamePlayersAppState.createPlayerEntity(entityWorld, game.getMap(), gamePlayer, null);
        }
        return gamePlayer.getEntity();
    }

    public void addNewClient(int clientId) {
        newClientIds.add(clientId);
    }

    public void removeInitializedClient(int clientId) {
        initializedClientIds.remove((Integer) clientId);
        Game game = mainApplication.getGame();
        GamePlayer<GamePlayerInfo_Human> player = game.getPlayerByClientId(clientId);
        GamePlayerInfo_Human gamePlayerInfo = player.getGamePlayerInfo();
        System.out.println("Player #" + gamePlayerInfo.getPlayerId() + " (Client #" + clientId + ") left the game.");
        if (game instanceof MMOGame) {
            mmoPersistenceSystem.updateDatabasePlayer(entityWorld, player);
            game.removePlayer(player);
            entityWorld.removeEntity(player.getEntity());
            SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
            subNetworkServer.sendMessageToClient(clientId, new Message_GameOver());
            subNetworkServer.remove(clientId);
        } else {
            gamePlayerInfo.setReady(false);
        }
    }
}
