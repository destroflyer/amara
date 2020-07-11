package amara.applications.ingame.server.entitysystem.systems.mmo;

import amara.applications.ingame.server.entitysystem.systems.mmo.state.MMOPlayerState;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.GamePlayer;
import amara.applications.master.server.games.GamePlayerInfo;
import amara.applications.master.server.games.GamePlayerInfo_Human;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MMOPersistenceSystem implements EntitySystem {

    public MMOPersistenceSystem(DatabaseAppState databaseAppState, Game game) {
        this.databaseAppState = databaseAppState;
        this.game = game;

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    private static final float UPDATE_INTERVAL = 10;
    private DatabaseAppState databaseAppState;
    private Game game;
    private ObjectMapper objectMapper;
    private float timeSinceLastUpdate;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        timeSinceLastUpdate += deltaSeconds;
        if (timeSinceLastUpdate >= UPDATE_INTERVAL) {
            updateDatabasePlayers(entityWorld);
            timeSinceLastUpdate = 0;
        }
    }

    private void updateDatabasePlayers(EntityWorld entityWorld) {
        for (GamePlayer<?> gamePlayer : game.getPlayers()) {
            GamePlayerInfo gamePlayerInfo = gamePlayer.getGamePlayerInfo();
            if (gamePlayerInfo instanceof GamePlayerInfo_Human) {
                updateDatabasePlayer(entityWorld, (GamePlayer<GamePlayerInfo_Human>) gamePlayer);
            }
        }
    }

    public void updateDatabasePlayer(EntityWorld entityWorld, GamePlayer<GamePlayerInfo_Human> gamePlayer) {
        int playerEntity = gamePlayer.getEntity();
        // Player entity might not have been initialized yet as the gamePlayer itself is already added in the parallel message backend thread
        if (playerEntity != -1) {
            GamePlayerInfo_Human gamePlayerInfo_Human = gamePlayer.getGamePlayerInfo();
            MMOPlayerState mmoPlayerState = MMOPersistenceUtil.getPlayerState(entityWorld, playerEntity);
            try {
                String newData = objectMapper.writeValueAsString(mmoPlayerState);
                String whereLimitClause = "(user_id = " + gamePlayerInfo_Human.getPlayerId() + ") AND (map_name = '" + databaseAppState.escape(game.getMap().getName()) + "') LIMIT 1";
                String oldData = databaseAppState.getQueryResult("SELECT data FROM mmo_players WHERE " + whereLimitClause).nextString_Close();
                if (oldData == null) {
                    databaseAppState.executeQuery("INSERT INTO mmo_players (user_id, map_name, data) VALUES (" + gamePlayerInfo_Human.getPlayerId() + ", '" + databaseAppState.escape(game.getMap().getName()) + "', '" + databaseAppState.escape(newData) + "')");
                } else {
                    databaseAppState.executeQuery("UPDATE mmo_players SET data = '" + databaseAppState.escape(newData) + "' WHERE " + whereLimitClause);
                }
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
