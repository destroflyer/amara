/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import java.sql.ResultSet;
import java.util.LinkedList;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.scores.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.synchronizing.*;
import amara.applications.ingame.entitysystem.systems.aggro.*;
import amara.applications.ingame.entitysystem.systems.attributes.*;
import amara.applications.ingame.entitysystem.systems.buffs.*;
import amara.applications.ingame.entitysystem.systems.buffs.areas.*;
import amara.applications.ingame.entitysystem.systems.buffs.stacks.*;
import amara.applications.ingame.entitysystem.systems.camps.*;
import amara.applications.ingame.entitysystem.systems.commands.*;
import amara.applications.ingame.entitysystem.systems.effects.*;
import amara.applications.ingame.entitysystem.systems.effects.aggro.*;
import amara.applications.ingame.entitysystem.systems.effects.audio.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.stacks.*;
import amara.applications.ingame.entitysystem.systems.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.effects.damage.*;
import amara.applications.ingame.entitysystem.systems.effects.game.*;
import amara.applications.ingame.entitysystem.systems.effects.general.*;
import amara.applications.ingame.entitysystem.systems.effects.heal.*;
import amara.applications.ingame.entitysystem.systems.effects.movement.*;
import amara.applications.ingame.entitysystem.systems.effects.physics.*;
import amara.applications.ingame.entitysystem.systems.effects.spawns.*;
import amara.applications.ingame.entitysystem.systems.effects.spells.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.*;
import amara.applications.ingame.entitysystem.systems.effects.units.*;
import amara.applications.ingame.entitysystem.systems.effects.visuals.*;
import amara.applications.ingame.entitysystem.systems.general.*;
import amara.applications.ingame.entitysystem.systems.movement.*;
import amara.applications.ingame.entitysystem.systems.network.*;
import amara.applications.ingame.entitysystem.systems.objectives.*;
import amara.applications.ingame.entitysystem.systems.physics.*;
import amara.applications.ingame.entitysystem.systems.players.*;
import amara.applications.ingame.entitysystem.systems.shop.*;
import amara.applications.ingame.entitysystem.systems.specials.erika.*;
import amara.applications.ingame.entitysystem.systems.spells.*;
import amara.applications.ingame.entitysystem.systems.spells.casting.*;
import amara.applications.ingame.entitysystem.systems.units.*;
import amara.applications.ingame.entitysystem.systems.units.scores.*;
import amara.applications.ingame.entitysystem.systems.visuals.*;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.server.entitysystem.systems.objectives.CheckMapObjectiveSystem;
import amara.applications.ingame.server.network.backends.*;
import amara.applications.ingame.shared.games.*;
import amara.applications.ingame.shared.maps.*;
import amara.applications.master.network.messages.objects.LobbyPlayerData;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.core.Util;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.network.NetworkServer;
import amara.libraries.physics.intersectionHelper.PolyMapManager;

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
        //networkServer.addMessageBackend(new UpdateNewClientBackend(entityWorld));
        networkServer.addMessageBackend(new InitializeClientBackend(mainApplication.getGame()));
        
        Game game = mainApplication.getGame();
        EntityWrapper gameEntity = entityWorld.getWrapped(entityWorld.createEntity());
        gameEntity.setComponent(new GameSpeedComponent(1));
        Map map = game.getMap();
        EntityWrapper mapEntity = entityWorld.getWrapped(entityWorld.createEntity());
        map.setEntity(mapEntity.getId());
        map.load(entityWorld);
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
        for(int i=0;i<mainApplication.getGame().getPlayers().length;i++){
            GamePlayer player = mainApplication.getGame().getPlayers()[i];
            EntityWrapper playerEntity = entityWorld.getWrapped(entityWorld.createEntity());
            playerEntity.setComponent(new PlayerIndexComponent(i));
            String login = databaseAppState.getString("SELECT login FROM users WHERE id = " + player.getLobbyPlayer().getID() + " LIMIT 1");
            playerEntity.setComponent(new NameComponent(login));
            LobbyPlayerData lobbyPlayerData = player.getLobbyPlayer().getPlayerData();
            String characterName = databaseAppState.getString("SELECT name FROM characters WHERE id = " + lobbyPlayerData.getCharacterID());
            EntityWrapper unit = EntityTemplate.createFromTemplate(entityWorld, "units/" + characterName);
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
                        EntityWrapper item = EntityTemplate.createFromTemplate(entityWorld, "items/" + itemName);
                        inventory.add(item.getId());
                    }
                }
                inventoryResultSet.close();
                ownedCharacterResultSet.close();
                unit.setComponent(new SightRangeComponent(30));
                unit.setComponent(new InventoryComponent(Util.convertToArray(inventory)));
                unit.setComponent(new GoldComponent(475));
                unit.setComponent(new LevelComponent(1));
                unit.setComponent(new SpellsComponent(new int[0]));
                unit.setComponent(new SpellsUpgradePointsComponent(1));
                int scoreEntity = entityWorld.createEntity();
                entityWorld.setComponent(scoreEntity, new CharacterKillsComponent(0));
                entityWorld.setComponent(scoreEntity, new DeathsComponent(0));
                entityWorld.setComponent(scoreEntity, new CharacterAssistsComponent(0));
                entityWorld.setComponent(scoreEntity, new CreepScoreComponent(0));
                unit.setComponent(new ScoreComponent(scoreEntity));
            }catch(Exception ex){
                ex.printStackTrace();
            }
            playerEntity.setComponent(new SelectedUnitComponent(unit.getId()));
            map.initializePlayer(entityWorld, playerEntity.getId());
            //MapSpells
            LinkedList<Integer> mapSpellsEntities = new LinkedList<Integer>();
            for(int r=0;r<map.getSpells().length;r++){
                MapSpells mapSpellsGroup = map.getSpells()[r];
                int[][] mapSpellIndices = lobbyPlayerData.getMapSpellsIndices();
                for(int z=0;z<mapSpellsGroup.getKeys().length;z++){
                    int mapSpellIndex = ((mapSpellIndices != null)?mapSpellIndices[r][z]:0);
                    MapSpell mapSpell = mapSpellsGroup.getMapSpells()[mapSpellIndex];
                    int spellEntity = entityWorld.createEntity();
                    EntityTemplate.loadTemplate(entityWorld, spellEntity, EntityTemplate.parseToOldTemplate(mapSpell.getEntityTemplate()));
                    mapSpellsEntities.add(spellEntity);
                }
            }
            unit.setComponent(new MapSpellsComponent(Util.convertToArray(mapSpellsEntities)));
            map.spawnPlayer(entityWorld, playerEntity.getId());
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
        addEntitySystem(new LinkedCooldownsSystem());
        addEntitySystem(new SetLevelExperienceSystem());
        for(EntitySystem entitySystem : ParallelNetworkSystems.generateSystems()){
            addEntitySystem(entitySystem);
        }
        addEntitySystem(new CountdownLifetimeSystem());
        addEntitySystem(new CountdownBuffsSystem());
        addEntitySystem(new CountdownCooldownSystem());
        addEntitySystem(new CountdownBindingSystem());
        addEntitySystem(new CountdownBindingImmuneSystem());
        addEntitySystem(new CountdownSilenceSystem());
        addEntitySystem(new CountdownSilenceImmuneSystem());
        addEntitySystem(new CountdownStunSystem());
        addEntitySystem(new CountdownStunImmuneSystem());
        addEntitySystem(new CountdownKnockupSystem());
        addEntitySystem(new CountdownKnockupImmuneSystem());
        addEntitySystem(new CountdownReactionsSystem());
        addEntitySystem(new CountdownEffectDelaySystem());
        addEntitySystem(new CountdownCampRespawnSystem());
        addEntitySystem(new CountdownAnimationLoopsSystem());
        addEntitySystem(new CountdownAggroResetTimersSystem());
        addEntitySystem(new CheckOpenObjectivesSystem());
        addEntitySystem(new CheckAggroTargetAttackibilitySystem());
        addEntitySystem(new CheckMaximumAggroRangeSystem());
        addEntitySystem(new CheckBuffStacksUpdateAttributesSystem());
        addEntitySystem(new LevelUpSystem());
        addEntitySystem(new UpdateAttributesSystem());
        addEntitySystem(new TriggerUnitsPassivesSystem());
        addEntitySystem(new TriggerItemPassivesSystem());
        addEntitySystem(new ExecutePlayerCommandsSystem(getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue()));
        addEntitySystem(new AttackMoveSystem());
        addEntitySystem(new AttackAggroedTargetsSystem());
        addEntitySystem(new StartAggroResetTimersSystem());
        addEntitySystem(new CheckCampUnionAggroSystem());
        addEntitySystem(new CheckLostAggroCampsSystem());
        addEntitySystem(new SetNewTargetSpellsOnCooldownSystem());
        addEntitySystem(new CastSpellOnCooldownWhileAttackingSystem());
        addEntitySystem(new PerformAutoAttacksSystem());
        addEntitySystem(new SetCastDurationOnCastingSystem());
        addEntitySystem(new SetCooldownOnCastingSystem());
        addEntitySystem(new ConsumeItemsOnCastingSystem());
        addEntitySystem(new PlayCastAnimationSystem());
        addEntitySystem(new CastSpellSystem());
        addEntitySystem(new UpdateAreaTransformsSystem());
        addEntitySystem(new CheckAreaBuffsSystem(intersectionObserver));
        addEntitySystem(new RemoveBuffsSystem());
        addEntitySystem(new RepeatingBuffEffectsSystem());
        addEntitySystem(new CalculateEffectImpactSystem());
        addEntitySystem(new ApplyPlayCinematicSystem());
        addEntitySystem(new ApplyDrawTeamAggroSystem());
        addEntitySystem(new ApplyPlayAudioSystem());
        addEntitySystem(new ApplyStopAudioSystem());
        addEntitySystem(new ApplyRemoveBuffsSystem());
        addEntitySystem(new ApplyRemoveBuffAreasSystem());
        addEntitySystem(new ApplyAddBuffsSystem());
        addEntitySystem(new ApplyAddBuffAreasSystem());
        addEntitySystem(new ApplyAddStacksSystem());
        addEntitySystem(new ApplyClearStacksSystem());
        addEntitySystem(new ApplyRemoveStacksSystem());
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
        addEntitySystem(new ApplyAddSpellsSpellEffectsSystem());
        addEntitySystem(new ApplyRemoveSpellEffectsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithExistingSpellsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithNewSpellsSystem());
        addEntitySystem(new ApplyTriggerSpellEffectsSystem());
        addEntitySystem(new ApplyAddGoldSystem());
        addEntitySystem(new ApplyCancelActionsSystem());
        addEntitySystem(new ApplyPlayAnimationsSystem());
        addEntitySystem(new ApplyStopAnimationsSystem());
        addEntitySystem(new ApplyAddComponentsSystem());
        addEntitySystem(new ApplyAddEffectTriggersSystem());
        addEntitySystem(new ApplyRemoveComponentsSystem());
        addEntitySystem(new ApplyRemoveEffectTriggersSystem());
        addEntitySystem(new ApplyRemoveEntitySystem());
        addEntitySystem(new ApplyTriggerErikaPassivesSystem());
        addEntitySystem(new DrawAggroOnDamageSystem());
        addEntitySystem(new ResetAggroTimerOnDamageSystem());
        addEntitySystem(new UpdateDamageHistorySystem());
        addEntitySystem(new TriggerDamageTakenSystem());
        addEntitySystem(new LifestealSystem());
        addEntitySystem(new RemoveAppliedEffectsSystem());
        addEntitySystem(new HealthRegenerationSystem());
        addEntitySystem(new DeathSystem());
        addEntitySystem(new TriggerDeathEffectSystem());
        addEntitySystem(new MaximumHealthSystem());
        addEntitySystem(new MaximumStacksSystem());
        addEntitySystem(new GoldGenerationSystem());
        addEntitySystem(new PayOutBountiesSystem());
        addEntitySystem(new UpdateDeathsScoreSystem());
        addEntitySystem(new RemoveDeadUnitsSystem());
        addEntitySystem(new RemoveCancelledMovementsEffectTriggersSystem());
        addEntitySystem(new PlayMovementAnimationsSystem());
        addEntitySystem(new UpdateWalkMovementsSystem());
        addEntitySystem(new TargetedMovementSystem(intersectionObserver, polyMapManager));
        addEntitySystem(new LocalAvoidanceSystem());
        addEntitySystem(new MovementSystem());
        addEntitySystem(new CheckDistanceLimitMovementsSystem());
        addEntitySystem(new TriggerTargetReachedEffectSystem());
        addEntitySystem(new RemoveFinishedMovementsSystem());
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionObserver));
        addEntitySystem(new TriggerCastingFinishedEffectSystem());
        addEntitySystem(new TriggerStacksReachedEffectSystem());
        addEntitySystem(new TriggerRepeatingEffectSystem());
        addEntitySystem(new TriggerInstantEffectSystem());
        addEntitySystem(new AggroSystem());
        addEntitySystem(new CheckCampMaximumAggroDistanceSystem());
        addEntitySystem(new CampResetSystem());
        addEntitySystem(new CheckDeadCampsRespawnSystem());
        addEntitySystem(new CampSpawnSystem());
        addEntitySystem(new SetIdleAnimationsSystem());
        addEntitySystem(new IntersectionPushSystem(intersectionObserver));
        addEntitySystem(new MapIntersectionSystem(polyMapManager));
        addEntitySystem(new PlayerDeathSystem(map));
        addEntitySystem(new PlayerRespawnSystem(game));
        
        addEntitySystem(new SendEntityChangesSystem(networkServer, new ClientComponentBlacklist()));
        addEntitySystem(new CheckMapObjectiveSystem(map, mainApplication));
    }

    @Override
    public void update(float lastTimePerFrame){
        if(mainApplication.getGame().isStarted()){
            float gameSpeed = entityWorld.getComponent(Game.ENTITY, GameSpeedComponent.class).getSpeed();
            super.update(lastTimePerFrame * gameSpeed);
        }
    }
}
