package amara.applications.ingame.server.appstates;

import java.util.LinkedList;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.scores.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.server.entitysystem.systems.mmo.MMOPersistenceUtil;
import amara.applications.ingame.server.entitysystem.systems.mmo.state.MMOPlayerState;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapSpell;
import amara.applications.ingame.shared.maps.MapSpells;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.applications.master.server.appstates.DestrostudiosAppState;
import amara.applications.master.server.games.*;
import amara.core.Util;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.network.SubNetworkServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IngamePlayersAppState extends ServerBaseAppState {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        addInitialPlayersToSubNetworkServer();
    }

    private void addInitialPlayersToSubNetworkServer() {
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        for (GamePlayer<?> player : mainApplication.getGame().getPlayers()) {
            GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
            if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
                subNetworkServer.add(gamePlayerInfo_Human.getClientId());
            }
        }
    }

    public void createPlayerEntity(EntityWorld entityWorld, Map map, GamePlayer<?> player, Integer playerIndex) {
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);

        int playerEntity = entityWorld.createEntity();
        if (playerIndex != null) {
            entityWorld.setComponent(playerEntity, new PlayerIndexComponent(playerIndex));
        }

        String playerName = null;
        int characterId = player.getGameSelectionPlayerData().getCharacterID();
        int skinId = 0;

        GamePlayerInfo gamePlayerInfo = player.getGamePlayerInfo();
        if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
            GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
            // Login
            DestrostudiosAppState destrostudiosAppState = mainApplication.getMasterServer().getState(DestrostudiosAppState.class);
            playerName = destrostudiosAppState.getLoginByPlayerId(gamePlayerInfo_Human.getPlayerId());
            // Skin
            Integer skinIdResult = databaseAppState.getQueryResult("SELECT skin_id FROM users_characters_active_skins WHERE (user_id = " + gamePlayerInfo_Human.getPlayerId() + ") AND (character_id = " + characterId + ") LIMIT 1").nextInteger_Close();
            if (skinIdResult != null) {
                skinId = skinIdResult;
            }
        } else if (gamePlayerInfo instanceof GamePlayerInfo_Bot) {
            GamePlayerInfo_Bot gamePlayerInfo_Bot = (GamePlayerInfo_Bot) gamePlayerInfo;
            BotsAppState botsAppState = getAppState(BotsAppState.class);
            botsAppState.createAndRegisterBot(playerEntity, gamePlayerInfo_Bot.getBotType());
            // Name
            playerName = gamePlayerInfo_Bot.getName();
            entityWorld.setComponent(playerEntity, new IsBotComponent());
            // Skin
            Integer skinIdResult = databaseAppState.getQueryResult("SELECT id FROM characters_skins WHERE (character_id = " + characterId + ") ORDER BY RAND() LIMIT 1").nextInteger_Close();
            if (skinIdResult != null) {
                skinId = skinIdResult;
            }
        }

        entityWorld.setComponent(playerEntity, new NameComponent(playerName));
        int characterEntity = createCharacterEntity(entityWorld, playerName, characterId, skinId);
        entityWorld.setComponent(playerEntity, new PlayerCharacterComponent(characterEntity));
        map.initializePlayer(entityWorld, playerEntity);

        // Has to happen after initializePlayer as player-specific map spells have to be ugly modified there
        int[][] mapSpellsIndices = player.getGameSelectionPlayerData().getMapSpellsIndices();
        entityWorld.setComponent(characterEntity, createMapSpells(entityWorld, map, mapSpellsIndices));

        map.spawnPlayer(entityWorld, playerEntity);

        if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
            GamePlayerInfo_Human gamePlayerInfo_Human = (GamePlayerInfo_Human) gamePlayerInfo;
            loadMMOPlayerData(entityWorld, playerEntity, gamePlayerInfo_Human.getPlayerId(), map);
        }

        player.setEntity(playerEntity);
    }

    private int createCharacterEntity(EntityWorld entityWorld, String playerName, int characterId, int skinId) {
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
        String characterName = databaseAppState.getQueryResult("SELECT name FROM characters WHERE id = " + characterId + " LIMIT 1").nextString_Close();
        int characterEntity = EntityTemplate.createFromTemplate(entityWorld, "units/" + characterName).getId();
        String skinName = ((skinId == 0) ? "default" : databaseAppState.getQueryResult("SELECT name FROM characters_skins WHERE id = " + skinId + " LIMIT 1").nextString_Close());
        entityWorld.setComponent(characterEntity, new TitleComponent(playerName));
        entityWorld.setComponent(characterEntity, new ModelComponent("Models/" + characterName + "/skin_" + skinName + ".xml"));
        entityWorld.setComponent(characterEntity, new IsCharacterComponent());
        entityWorld.setComponent(characterEntity, new IsRespawnableComponent());
        entityWorld.setComponent(characterEntity, new SightRangeComponent(30));
        entityWorld.setComponent(characterEntity, new GoldComponent(475));
        entityWorld.setComponent(characterEntity, new LevelComponent(1));
        entityWorld.setComponent(characterEntity, new SpellsComponent(new int[0]));
        entityWorld.setComponent(characterEntity, new SpellsUpgradePointsComponent(1));
        entityWorld.setComponent(characterEntity, new InventoryComponent(new int[0]));
        entityWorld.setComponent(characterEntity, new BagComponent(new int[0]));
        int scoreEntity = entityWorld.createEntity();
        entityWorld.setComponent(scoreEntity, new CharacterKillsComponent(0));
        entityWorld.setComponent(scoreEntity, new DeathsComponent(0));
        entityWorld.setComponent(scoreEntity, new CharacterAssistsComponent(0));
        entityWorld.setComponent(scoreEntity, new CreepScoreComponent(0));
        entityWorld.setComponent(characterEntity, new ScoreComponent(scoreEntity));
        return characterEntity;
    }

    private void loadMMOPlayerData(EntityWorld entityWorld, int playerEntity, int playerId, Map map) {
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
        String data = databaseAppState.getQueryResult("SELECT data FROM mmo_players WHERE (user_id = " + playerId + ") AND (map_name = '" + databaseAppState.escape(map.getName()) + "')").nextString_Close();
        if (data != null) {
            try {
                MMOPlayerState mmoPlayerState = objectMapper.readValue(data, MMOPlayerState.class);
                MMOPersistenceUtil.loadPlayerState(entityWorld, playerEntity, mmoPlayerState);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
    }

    private MapSpellsComponent createMapSpells(EntityWorld entityWorld, Map map, int[][] mapSpellsIndices) {
        LinkedList<Integer> mapSpellsEntities = new LinkedList<>();
        for (int i = 0; i < map.getSpells().length; i++) {
            MapSpells mapSpellsGroup = map.getSpells()[i];
            for (int r = 0; r < mapSpellsGroup.getKeys().length; r++) {
                int mapSpellIndex = ((mapSpellsIndices != null) ? mapSpellsIndices[i][r] : 0);
                MapSpell mapSpell = mapSpellsGroup.getMapSpells()[mapSpellIndex];
                int spellEntity = entityWorld.createEntity();
                EntityTemplate.loadTemplate(entityWorld, spellEntity, EntityTemplate.parseToOldTemplate(mapSpell.getEntityTemplate()));
                mapSpellsEntities.add(spellEntity);
            }
        }
        return new MapSpellsComponent(Util.convertToArray_Integer(mapSpellsEntities));
    }
}
