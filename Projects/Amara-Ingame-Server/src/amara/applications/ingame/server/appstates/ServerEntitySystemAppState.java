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
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.synchronizing.*;
import amara.applications.ingame.entitysystem.systems.aggro.*;
import amara.applications.ingame.entitysystem.systems.attributes.*;
import amara.applications.ingame.entitysystem.systems.audio.*;
import amara.applications.ingame.entitysystem.systems.buffs.*;
import amara.applications.ingame.entitysystem.systems.buffs.areas.*;
import amara.applications.ingame.entitysystem.systems.buffs.stacks.*;
import amara.applications.ingame.entitysystem.systems.camps.*;
import amara.applications.ingame.entitysystem.systems.cleanup.*;
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
import amara.applications.master.network.messages.objects.GameSelectionPlayerData;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.core.Util;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.*;
import amara.libraries.database.QueryResult;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.network.SubNetworkServer;
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
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.addMessageBackend(new AuthentificateClientsBackend(mainApplication.getGame(), entityWorld));
        subNetworkServer.addMessageBackend(new SendInitialEntityWorldBackend(entityWorld));
        subNetworkServer.addMessageBackend(new StartGameBackend(mainApplication.getGame()));
        
        Game game = mainApplication.getGame();
        EntityWrapper gameEntity = entityWorld.getWrapped(entityWorld.createEntity());
        gameEntity.setComponent(new GameSpeedComponent(1));
        Map map = game.getMap();
        EntityWrapper mapEntity = entityWorld.getWrapped(entityWorld.createEntity());
        map.setEntity(mapEntity.getId());
        map.load(entityWorld);
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getState(DatabaseAppState.class);
        int playerIndex = 0;
        for(GamePlayer[] team : mainApplication.getGame().getTeams()){
            for(GamePlayer player : team){
                EntityWrapper playerEntity = entityWorld.getWrapped(entityWorld.createEntity());
                playerEntity.setComponent(new PlayerIndexComponent(playerIndex));
                String login = databaseAppState.getQueryResult("SELECT login FROM users WHERE id = " + player.getGameSelectionPlayer().getID() + " LIMIT 1").nextString_Close();
                playerEntity.setComponent(new NameComponent(login));
                GameSelectionPlayerData gameSelectionPlayerData = player.getGameSelectionPlayer().getPlayerData();
                String characterName = databaseAppState.getQueryResult("SELECT name FROM characters WHERE id = " + gameSelectionPlayerData.getCharacterID()).nextString_Close();
                EntityWrapper character = EntityTemplate.createFromTemplate(entityWorld, "units/" + characterName);
                character.setComponent(new TitleComponent(login));
                try{
                    QueryResult results_UserCharacters = databaseAppState.getQueryResult("SELECT skinid, inventory FROM users_characters WHERE (userid = " + player.getGameSelectionPlayer().getID() + ") AND (characterid = " + gameSelectionPlayerData.getCharacterID() + ")");
                    results_UserCharacters.next();
                    String skinName = "default";
                    int skinID = results_UserCharacters.getInteger("skinid");
                    if(skinID != 0){
                        skinName = databaseAppState.getQueryResult("SELECT name FROM characters_skins WHERE id = " + skinID).nextString_Close();
                    }
                    character.setComponent(new ModelComponent("Models/" + characterName + "/skin_" + skinName + ".xml"));
                    ResultSet inventoryResultSet = results_UserCharacters.getArray("inventory").getResultSet();
                    LinkedList<Integer> inventory = new LinkedList<Integer>();
                    while(inventoryResultSet.next()){
                        int itemID = inventoryResultSet.getInt(2);
                        if(itemID != 0){
                            String itemName = databaseAppState.getQueryResult("SELECT name FROM items WHERE id = " + itemID).nextString_Close();
                            EntityWrapper item = EntityTemplate.createFromTemplate(entityWorld, "items/" + itemName);
                            inventory.add(item.getId());
                        }
                    }
                    inventoryResultSet.close();
                    results_UserCharacters.close();
                    character.setComponent(new IsCharacterComponent());
                    character.setComponent(new SightRangeComponent(30));
                    character.setComponent(new InventoryComponent(Util.convertToArray(inventory)));
                    character.setComponent(new GoldComponent(475));
                    character.setComponent(new LevelComponent(1));
                    character.setComponent(new SpellsComponent(new int[0]));
                    character.setComponent(new SpellsUpgradePointsComponent(1));
                    int scoreEntity = entityWorld.createEntity();
                    entityWorld.setComponent(scoreEntity, new CharacterKillsComponent(0));
                    entityWorld.setComponent(scoreEntity, new DeathsComponent(0));
                    entityWorld.setComponent(scoreEntity, new CharacterAssistsComponent(0));
                    entityWorld.setComponent(scoreEntity, new CreepScoreComponent(0));
                    character.setComponent(new ScoreComponent(scoreEntity));
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                playerEntity.setComponent(new PlayerCharacterComponent(character.getId()));
                map.initializePlayer(entityWorld, playerEntity.getId());
                //MapSpells
                LinkedList<Integer> mapSpellsEntities = new LinkedList<Integer>();
                int[][] mapSpellIndices = gameSelectionPlayerData.getMapSpellsIndices();
                for(int r=0;r<map.getSpells().length;r++){
                    MapSpells mapSpellsGroup = map.getSpells()[r];
                    for(int z=0;z<mapSpellsGroup.getKeys().length;z++){
                        int mapSpellIndex = ((mapSpellIndices != null)?mapSpellIndices[r][z]:0);
                        MapSpell mapSpell = mapSpellsGroup.getMapSpells()[mapSpellIndex];
                        int spellEntity = entityWorld.createEntity();
                        EntityTemplate.loadTemplate(entityWorld, spellEntity, EntityTemplate.parseToOldTemplate(mapSpell.getEntityTemplate()));
                        mapSpellsEntities.add(spellEntity);
                    }
                }
                character.setComponent(new MapSpellsComponent(Util.convertToArray(mapSpellsEntities)));
                map.spawnPlayer(entityWorld, playerEntity.getId());
                player.setEntity(playerEntity.getId());
                playerIndex++;
            }
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
        addEntitySystem(new RemoveAudiosAfterPlayingSystem());
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
        CastSpellQueueSystem castSpellQueueSystem = new CastSpellQueueSystem();
        addEntitySystem(new ExecutePlayerCommandsSystem(getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue(), castSpellQueueSystem));
        addEntitySystem(new AttackMoveSystem());
        addEntitySystem(new AttackAggroedTargetsSystem());
        addEntitySystem(new StartAggroResetTimersSystem());
        addEntitySystem(new CheckCampUnionAggroSystem());
        addEntitySystem(new CheckLostAggroCampsSystem());
        addEntitySystem(new SetNewTargetSpellsOnCooldownSystem());
        addEntitySystem(new CastSpellOnCooldownWhileAttackingSystem(castSpellQueueSystem));
        addEntitySystem(new PerformAutoAttacksSystem(castSpellQueueSystem));
        addEntitySystem(castSpellQueueSystem);
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
        addEntitySystem(new ApplyAddCollisiongGroupsSystem());
        addEntitySystem(new ApplyDeactivateHitboxSystem());
        addEntitySystem(new ApplyRemoveCollisiongGroupsSystem());
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
        addEntitySystem(new CleanupEffectTriggersSystem());
        addEntitySystem(new CleanupEffectsSystem());
        addEntitySystem(new HealthRegenerationSystem());
        addEntitySystem(new DeathSystem());
        addEntitySystem(new TriggerDeathEffectSystem());
        addEntitySystem(new MaximumHealthSystem());
        addEntitySystem(new MaximumStacksSystem());
        addEntitySystem(new GoldGenerationSystem());
        addEntitySystem(new PayOutBountiesSystem());
        addEntitySystem(new UpdateDeathsScoreSystem());
        addEntitySystem(new RemoveDeadUnitsSystem());
        addEntitySystem(new CampResetSystem());
        addEntitySystem(new CheckDeadCampsRespawnSystem());
        addEntitySystem(new CampSpawnSystem());
        addEntitySystem(new RemoveFinishedMovementsSystem());
        addEntitySystem(new PlayMovementAnimationsSystem());
        addEntitySystem(new UpdateWalkMovementsSystem());
        addEntitySystem(new TargetedMovementSystem(intersectionObserver, polyMapManager));
        addEntitySystem(new LocalAvoidanceSystem());
        addEntitySystem(new MovementSystem());
        addEntitySystem(new CheckDistanceLimitMovementsSystem());
        addEntitySystem(new TriggerTargetReachedEffectSystem());
        addEntitySystem(new FinishTargetedMovementsSystem());
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionObserver));
        addEntitySystem(new TriggerCastingFinishedEffectSystem());
        addEntitySystem(new TriggerStacksReachedEffectSystem());
        addEntitySystem(new TriggerRepeatingEffectSystem());
        addEntitySystem(new TriggerInstantEffectSystem());
        addEntitySystem(new AggroSystem());
        addEntitySystem(new CheckCampMaximumAggroDistanceSystem());
        addEntitySystem(new SetIdleAnimationsSystem());
        addEntitySystem(new IntersectionPushSystem(intersectionObserver));
        addEntitySystem(new MapIntersectionSystem(polyMapManager));
        addEntitySystem(new PlayerDeathSystem(map));
        addEntitySystem(new PlayerRespawnSystem(game));
        addEntitySystem(new CleanupUnitsSystem());
        addEntitySystem(new CleanupSpellsSystem());
        addEntitySystem(new CleanupMovementsSystem());
        addEntitySystem(new CleanupBuffAreasSystem());
        addEntitySystem(new CleanupBuffsSystem());
        
        addEntitySystem(new SendEntityChangesSystem(subNetworkServer, new ClientComponentBlacklist()));
        addEntitySystem(new CheckMapObjectiveSystem(map, mainApplication));
        //Precalculate first frame, so automatic entity processes will be done for the initial world
        super.update(0);
    }

    @Override
    public void update(float lastTimePerFrame){
        if(mainApplication.getGame().isStarted()){
            float gameSpeed = entityWorld.getComponent(Game.ENTITY, GameSpeedComponent.class).getSpeed();
            super.update(lastTimePerFrame * gameSpeed);
        }
    }
}
