/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.scores.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.shared.games.GamePlayer;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapSpell;
import amara.applications.ingame.shared.maps.MapSpells;
import amara.applications.master.network.messages.objects.*;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.core.Util;
import amara.libraries.database.QueryResult;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class PlayerEntitiesAppState extends ServerBaseAppState{

    public PlayerEntitiesAppState(){
        
    }
    
    public int createPlayerEntity(EntityWorld entityWorld, Map map, GamePlayer player, int playerIndex){
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
        BotsAppState botsAppState = getAppState(BotsAppState.class);
        int playerEntity = entityWorld.createEntity();
        entityWorld.setComponent(playerEntity, new PlayerIndexComponent(playerIndex));
        LobbyPlayer lobbyPlayer = player.getGameSelectionPlayer().getLobbyPlayer();
        String playerName = null;
        if(lobbyPlayer instanceof LobbyPlayer_Human){
            LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
            playerName = databaseAppState.getQueryResult("SELECT login FROM users WHERE id = " + lobbyPlayer_Human.getPlayerID() + " LIMIT 1").nextString_Close();
        }
        else if(lobbyPlayer instanceof LobbyPlayer_Bot) {
            LobbyPlayer_Bot lobbyPlayer_Bot = (LobbyPlayer_Bot) lobbyPlayer;
            playerName = lobbyPlayer_Bot.getName();
            entityWorld.setComponent(playerEntity, new IsBotComponent());
            botsAppState.createAndRegisterBot(playerEntity, lobbyPlayer_Bot.getBotType());
        }
        entityWorld.setComponent(playerEntity, new NameComponent(playerName));
        int characterEntity = createCharacterEntity(entityWorld, player, playerName);
        entityWorld.setComponent(playerEntity, new PlayerCharacterComponent(characterEntity));
        map.initializePlayer(entityWorld, playerEntity);

        // Has to happen here since player-specific map spells have to be ugly modified by the map currently
        GameSelectionPlayerData gameSelectionPlayerData = player.getGameSelectionPlayer().getPlayerData();
        createMapSpells(entityWorld, characterEntity, map, gameSelectionPlayerData.getMapSpellsIndices());

        map.spawnPlayer(entityWorld, playerEntity);
        player.setEntity(playerEntity);
        return playerEntity;
    }
    
    public int createCharacterEntity(EntityWorld entityWorld, GamePlayer player, String playerName){
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
        GameSelectionPlayerData gameSelectionPlayerData = player.getGameSelectionPlayer().getPlayerData();
        String characterName = databaseAppState.getQueryResult("SELECT name FROM characters WHERE id = " + gameSelectionPlayerData.getCharacterID() + " LIMIT 1").nextString_Close();
        int characterEntity = EntityTemplate.createFromTemplate(entityWorld, "units/" + characterName).getId();
        entityWorld.setComponent(characterEntity, new TitleComponent(playerName));
        createModelAndInventory(entityWorld, characterEntity, player, characterName);
        entityWorld.setComponent(characterEntity, new IsCharacterComponent());
        entityWorld.setComponent(characterEntity, new SightRangeComponent(30));
        entityWorld.setComponent(characterEntity, new GoldComponent(475));
        entityWorld.setComponent(characterEntity, new LevelComponent(1));
        entityWorld.setComponent(characterEntity, new SpellsComponent(new int[0]));
        entityWorld.setComponent(characterEntity, new SpellsUpgradePointsComponent(1));
        int scoreEntity = entityWorld.createEntity();
        entityWorld.setComponent(scoreEntity, new CharacterKillsComponent(0));
        entityWorld.setComponent(scoreEntity, new DeathsComponent(0));
        entityWorld.setComponent(scoreEntity, new CharacterAssistsComponent(0));
        entityWorld.setComponent(scoreEntity, new CreepScoreComponent(0));
        entityWorld.setComponent(characterEntity, new ScoreComponent(scoreEntity));
        return characterEntity;
    }
    
    private void createModelAndInventory(EntityWorld entityWorld, int characterEntity, GamePlayer player, String characterName){
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
        int characterID = player.getGameSelectionPlayer().getPlayerData().getCharacterID();
        try{
            int skinID = 0;
            LinkedList<Integer> inventory = new LinkedList<>();
            LobbyPlayer lobbyPlayer = player.getGameSelectionPlayer().getLobbyPlayer();
            if(lobbyPlayer instanceof LobbyPlayer_Human){
                LobbyPlayer_Human lobbyPlayer_Human = (LobbyPlayer_Human) lobbyPlayer;
                QueryResult results_UserCharacters = databaseAppState.getQueryResult("SELECT skinid, inventory FROM users_characters WHERE (userid = " + lobbyPlayer_Human.getPlayerID() + ") AND (characterid = " + characterID + ") LIMIT 1");
                results_UserCharacters.next();
                skinID = results_UserCharacters.getInteger("skinid");
                ResultSet inventoryResultSet = results_UserCharacters.getArray("inventory").getResultSet();
                while(inventoryResultSet.next()){
                    int itemID = inventoryResultSet.getInt(2);
                    if(itemID != 0){
                        String itemName = databaseAppState.getQueryResult("SELECT name FROM items WHERE id = " + itemID + " LIMIT 1").nextString_Close();
                        EntityWrapper item = EntityTemplate.createFromTemplate(entityWorld, "items/" + itemName);
                        inventory.add(item.getId());
                    }
                }
                results_UserCharacters.close();
                inventoryResultSet.close();
            }
            else {
                QueryResult results_CharactersSkins = databaseAppState.getQueryResult("SELECT id FROM characters_skins WHERE (characterid = " + characterID + ") ORDER BY RANDOM() LIMIT 1");
                if(results_CharactersSkins.next()){
                    skinID = results_CharactersSkins.getInteger("id");
                }
            }
            String skinName = "default";
            if(skinID != 0){
                skinName = databaseAppState.getQueryResult("SELECT name FROM characters_skins WHERE id = " + skinID + " LIMIT 1").nextString_Close();
            }
            entityWorld.setComponent(characterEntity, new ModelComponent("Models/" + characterName + "/skin_" + skinName + ".xml"));
            entityWorld.setComponent(characterEntity, new InventoryComponent(Util.convertToArray_Integer(inventory)));
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void createMapSpells(EntityWorld entityWorld, int characterEntity, Map map, int[][] mapSpellsIndices){
        LinkedList<Integer> mapSpellsEntities = new LinkedList<>();
        for(int i=0;i<map.getSpells().length;i++){
            MapSpells mapSpellsGroup = map.getSpells()[i];
            for(int r=0;r<mapSpellsGroup.getKeys().length;r++){
                int mapSpellIndex = ((mapSpellsIndices != null)?mapSpellsIndices[i][r]:0);
                MapSpell mapSpell = mapSpellsGroup.getMapSpells()[mapSpellIndex];
                int spellEntity = entityWorld.createEntity();
                EntityTemplate.loadTemplate(entityWorld, spellEntity, EntityTemplate.parseToOldTemplate(mapSpell.getEntityTemplate()));
                mapSpellsEntities.add(spellEntity);
            }
        }
        entityWorld.setComponent(characterEntity, new MapSpellsComponent(Util.convertToArray_Integer(mapSpellsEntities)));
    }
}
