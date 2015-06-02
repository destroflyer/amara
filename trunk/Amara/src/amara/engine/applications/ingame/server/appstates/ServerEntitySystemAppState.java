/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import java.sql.ResultSet;
import java.util.LinkedList;
import amara.Util;
import amara.engine.applications.*;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.engine.applications.ingame.server.network.backends.*;
import amara.engine.applications.masterserver.server.appstates.DatabaseAppState;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.appstates.*;
import amara.engine.network.NetworkServer;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.game.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.aggro.*;
import amara.game.entitysystem.systems.attributes.*;
import amara.game.entitysystem.systems.buffs.*;
import amara.game.entitysystem.systems.buffs.areas.*;
import amara.game.entitysystem.systems.camps.*;
import amara.game.entitysystem.systems.commands.*;
import amara.game.entitysystem.systems.effects.*;
import amara.game.entitysystem.systems.effects.aggro.*;
import amara.game.entitysystem.systems.effects.audio.*;
import amara.game.entitysystem.systems.effects.buffs.*;
import amara.game.entitysystem.systems.effects.buffs.areas.*;
import amara.game.entitysystem.systems.effects.crowdcontrol.*;
import amara.game.entitysystem.systems.effects.damage.*;
import amara.game.entitysystem.systems.effects.game.*;
import amara.game.entitysystem.systems.effects.general.*;
import amara.game.entitysystem.systems.effects.heal.*;
import amara.game.entitysystem.systems.effects.movement.*;
import amara.game.entitysystem.systems.effects.physics.*;
import amara.game.entitysystem.systems.effects.spawns.*;
import amara.game.entitysystem.systems.effects.spells.*;
import amara.game.entitysystem.systems.effects.triggers.*;
import amara.game.entitysystem.systems.effects.visuals.*;
import amara.game.entitysystem.systems.game.*;
import amara.game.entitysystem.systems.general.*;
import amara.game.entitysystem.systems.movement.*;
import amara.game.entitysystem.systems.network.*;
import amara.game.entitysystem.systems.objectives.*;
import amara.game.entitysystem.systems.physics.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.PolyMapManager;
import amara.game.entitysystem.systems.players.*;
import amara.game.entitysystem.systems.spells.*;
import amara.game.entitysystem.systems.spells.casting.*;
import amara.game.entitysystem.systems.units.*;
import amara.game.entitysystem.systems.visuals.*;
import amara.game.games.*;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class ServerEntitySystemAppState extends EntitySystemHeadlessAppState<IngameServerApplication>{

    public ServerEntitySystemAppState(){
        
    }

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new AuthentificateClientsBackend(mainApplication.getGame(), entityWorld));
        networkServer.addMessageBackend(new UpdateNewClientBackend(entityWorld));
        networkServer.addMessageBackend(new InitializeClientBackend(mainApplication.getGame(), getAppState(GameRunningAppState.class)));
        
        Game game = mainApplication.getGame();
        EntityWrapper gameEntity = entityWorld.getWrapped(entityWorld.createEntity());
        gameEntity.setComponent(new GameSpeedComponent(1));
        Map map = game.getMap();
        EntityWrapper mapEntity = entityWorld.getWrapped(entityWorld.createEntity());
        map.setEntity(mapEntity.getId());
        map.load(entityWorld);
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getStateManager().getState(DatabaseAppState.class);
        for(int i=0;i<mainApplication.getGame().getPlayers().length;i++){
            GamePlayer player = mainApplication.getGame().getPlayers()[i];
            EntityWrapper playerEntity = entityWorld.getWrapped(entityWorld.createEntity());
            playerEntity.setComponent(new PlayerIndexComponent(i));
            String login = databaseAppState.getString("SELECT login FROM users WHERE id = " + player.getLobbyPlayer().getID() + " LIMIT 1");
            playerEntity.setComponent(new NameComponent(login));
            LobbyPlayerData lobbyPlayerData = player.getLobbyPlayer().getPlayerData();
            String characterName = databaseAppState.getString("SELECT name FROM characters WHERE id = " + lobbyPlayerData.getCharacterID());
            EntityWrapper unit = EntityTemplate.createFromTemplate(entityWorld, characterName);
            unit.setComponent(new TitleComponent(login));
            try{
                ResultSet ownedCharacterResultSet = databaseAppState.getResultSet("SELECT skinid, inventory FROM users_characters WHERE (userid = " + player.getLobbyPlayer().getID() + ") AND (characterid = " + lobbyPlayerData.getCharacterID() + ")");
                ownedCharacterResultSet.next();
                String skinName = "default";
                int skinID = ownedCharacterResultSet.getInt(1);
                if(skinID != 0){
                    skinName = databaseAppState.getString("SELECT name FROM characters_skins WHERE id = " + skinID);
                }
                unit.setComponent(new ModelComponent("Models/" + characterName + "/skin_" + skinName + ".xml"));
                ResultSet inventoryResultSet = ownedCharacterResultSet.getArray(2).getResultSet();
                LinkedList<Integer> inventory = new LinkedList<Integer>();
                while(inventoryResultSet.next()){
                    int itemID = inventoryResultSet.getInt(2);
                    if(itemID != 0){
                        String itemName = databaseAppState.getString("SELECT name FROM items WHERE id = " + itemID);
                        EntityWrapper item = EntityTemplate.createFromTemplate(entityWorld, itemName);
                        inventory.add(item.getId());
                    }
                }
                inventoryResultSet.close();
                ownedCharacterResultSet.close();
                unit.setComponent(new InventoryComponent(Util.convertToArray(inventory)));
                unit.setComponent(new GoldComponent(475));
            }catch(Exception ex){
                ex.printStackTrace();
            }
            playerEntity.setComponent(new SelectedUnitComponent(unit.getId()));
            map.spawn(entityWorld, playerEntity.getId());
            player.setEntityID(playerEntity.getId());
        }
        System.out.println("Calculating navigation meshes...");
        MapPhysicsInformation mapPhysicsInformation = map.getPhysicsInformation();
        PolyMapManager polyMapManager = mapPhysicsInformation.getPolyMapManager();
        //polyMapManager.calcNavigationMap(0.5);
        //polyMapManager.calcNavigationMap(0.75);
        //polyMapManager.calcNavigationMap(1);
        //polyMapManager.calcNavigationMap(1.25); //error, do not enable
        polyMapManager.calcNavigationMap(1.5);
        //polyMapManager.calcNavigationMap(2);
        //polyMapManager.calcNavigationMap(3);
        System.out.println("Finished calculating navigation meshes.");
        
        IntersectionObserver intersectionObserver = new IntersectionObserver();
        addEntitySystem(new SetAutoAttacksCastAnimationsSystem());
        addEntitySystem(new SetSpellsCastersSystem());
        addEntitySystem(new SetBaseCooldownSystem());
        addEntitySystem(new UpdateGameTimeSystem());
        addEntitySystem(new CountdownPlayerRespawnSystem());
        addEntitySystem(new CountdownLifetimeSystem());
        addEntitySystem(new CountdownBuffsSystem());
        addEntitySystem(new CountdownCastingSystem());
        addEntitySystem(new CountdownCooldownSystem());
        addEntitySystem(new CountdownEffectDelaySystem());
        addEntitySystem(new CountdownBindingSystem());
        addEntitySystem(new CountdownBindingImmuneSystem());
        addEntitySystem(new CountdownSilenceSystem());
        addEntitySystem(new CountdownSilenceImmuneSystem());
        addEntitySystem(new CountdownStunSystem());
        addEntitySystem(new CountdownStunImmuneSystem());
        addEntitySystem(new CountdownKnockupSystem());
        addEntitySystem(new CountdownKnockupImmuneSystem());
        addEntitySystem(new CountdownAnimationLoopsSystem());
        addEntitySystem(new CheckOpenObjectivesSystem());
        addEntitySystem(new CheckAggroTargetAttackibilitySystem());
        addEntitySystem(new CheckMaximumAggroRangeSystem());
        addEntitySystem(new UpdateAttributesSystem());
        addEntitySystem(new ExecutePlayerCommandsSystem(getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue()));
        addEntitySystem(new AttackMoveSystem());
        addEntitySystem(new AttackAggroedTargetsSystem());
        addEntitySystem(new CheckLostAggroCampsSystem());
        addEntitySystem(new SetNewTargetSpellsOnCooldownSystem());
        addEntitySystem(new CastSpellOnCooldownWhileAttackingSystem());
        addEntitySystem(new PerformAutoAttacksSystem());
        addEntitySystem(new SetCastDurationOnCastingSystem());
        addEntitySystem(new SetCooldownOnCastingSystem());
        addEntitySystem(new PlayCastAnimationSystem());
        addEntitySystem(new CastSpellSystem());
        addEntitySystem(new UpdateAreaPositionsSystem());
        addEntitySystem(new CheckAreaBuffsSystem(intersectionObserver));
        addEntitySystem(new RemoveBuffsSystem());
        addEntitySystem(new RepeatingBuffEffectsSystem());
        addEntitySystem(new CalculateEffectImpactSystem());
        addEntitySystem(new ApplyPlayCinematicSystem());
        addEntitySystem(new ApplyDrawTeamAggroSystem());
        addEntitySystem(new ApplyPauseAudioSystem());
        addEntitySystem(new ApplyPlayAudioSystem());
        addEntitySystem(new ApplyStopAudioSystem());
        addEntitySystem(new ApplyAddBuffsSystem());
        addEntitySystem(new ApplyRemoveBuffsSystem());
        addEntitySystem(new ApplyAddBuffAreasSystem());
        addEntitySystem(new ApplyRemoveBuffAreasSystem());
        addEntitySystem(new ApplyAddBindingImmuneSystem());
        addEntitySystem(new ApplyAddBindingSystem());
        addEntitySystem(new ApplyRemoveBindingSystem());
        addEntitySystem(new ApplyAddSilenceImmuneSystem());
        addEntitySystem(new ApplyAddSilenceSystem());
        addEntitySystem(new ApplyRemoveSilenceSystem());
        addEntitySystem(new ApplyAddStunImmuneSystem());
        addEntitySystem(new ApplyAddStunSystem());
        addEntitySystem(new ApplyRemoveStunSystem());
        addEntitySystem(new ApplyAddKnockupImmuneSystem());
        addEntitySystem(new ApplyAddKnockupSystem());
        addEntitySystem(new ApplyRemoveKnockupSystem());
        addEntitySystem(new ApplyAddTargetabilitySystem());
        addEntitySystem(new ApplyRemoveTargetabilitySystem());
        addEntitySystem(new ApplyAddVulnerabilitySystem());
        addEntitySystem(new ApplyRemoveVulnerabilitySystem());
        addEntitySystem(new ApplyPhysicalDamageSystem());
        addEntitySystem(new ApplyMagicDamageSystem());
        addEntitySystem(new ApplyHealSystem());
        addEntitySystem(new ApplyStopSystem());
        addEntitySystem(new ApplyMoveSystem());
        addEntitySystem(new ApplyTeleportSystem());
        addEntitySystem(new ApplyActivateHitboxSystem());
        addEntitySystem(new ApplyDeactivateHitboxSystem());
        addEntitySystem(new ApplySpawnsSystems());
        addEntitySystem(new ApplyAddAutoAttackSpellEffectsSystem());
        addEntitySystem(new ApplyRemoveSpellEffectsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithExistingSpellsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithNewSpellsSystem());
        addEntitySystem(new ApplyTriggerSpellEffectsSystem());
        addEntitySystem(new ApplyPlayAnimationsSystem());
        addEntitySystem(new ApplyStopAnimationsSystem());
        addEntitySystem(new ApplyAddComponentsSystem());
        addEntitySystem(new ApplyAddEffectTriggersSystem());
        addEntitySystem(new ApplyRemoveComponentsSystem());
        addEntitySystem(new ApplyRemoveEffectTriggersSystem());
        addEntitySystem(new ApplyRemoveEntitySystem());
        addEntitySystem(new DrawAggroOnDamageSystem());
        addEntitySystem(new UpdateDamageHistorySystem());
        addEntitySystem(new RemoveAppliedEffectsSystem());
        addEntitySystem(new HealthRegenerationSystem());
        addEntitySystem(new DeathSystem());
        addEntitySystem(new TriggerDeathEffectSystem());
        addEntitySystem(new MaximumHealthSystem());
        addEntitySystem(new PayOutBountiesSystem());
        addEntitySystem(new RemoveDeadUnitsSystem());
        addEntitySystem(new RemoveUnusedTriggersSystem());
        addEntitySystem(new RemoveCancelledMovementsEffectTriggersSystem());
        addEntitySystem(new PlayMovementAnimationsSystem());
        addEntitySystem(new UpdateWalkMovementsSystem());
        addEntitySystem(new MovementSystem());
        addEntitySystem(new TargetedMovementSystem(intersectionObserver, polyMapManager));
        addEntitySystem(new CheckDistanceLimitMovementsSystem());
        addEntitySystem(new TriggerTargetReachedEffectSystem());
        addEntitySystem(new RemoveFinishedMovementsSystem());
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionObserver));
        addEntitySystem(new TriggerCastingFinishedEffectSystem());
        addEntitySystem(new TriggerRepeatingEffectSystem());
        addEntitySystem(new TriggerInstantEffectSystem());
        addEntitySystem(new AggroSystem());
        addEntitySystem(new CheckCampMaximumAggroDistanceSystem());
        addEntitySystem(new CampResetSystem());
        addEntitySystem(new SetIdleAnimationsSystem());
        addEntitySystem(new IntersectionPushSystem(intersectionObserver));
        addEntitySystem(new MapIntersectionSystem(polyMapManager));
        addEntitySystem(new CheckMapObjectiveSystem(map, getAppState(GameRunningAppState.class)));
        addEntitySystem(new PlayerDeathSystem(map));
        addEntitySystem(new PlayerRespawnSystem(game));
        
        addEntitySystem(new SendEntityChangesSystem(networkServer));
    }

    @Override
    public void update(float lastTimePerFrame){
        if(mainApplication.getGame().isStarted()){
            float gameSpeed = entityWorld.getComponent(Game.ENTITY, GameSpeedComponent.class).getSpeed();
            super.update(lastTimePerFrame * gameSpeed);
        }
    }
}
