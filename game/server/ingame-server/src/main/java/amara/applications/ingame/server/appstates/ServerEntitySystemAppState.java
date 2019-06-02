/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.entitysystem.synchronizing.*;
import amara.applications.ingame.entitysystem.systems.aggro.*;
import amara.applications.ingame.entitysystem.systems.ai.*;
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
import amara.applications.ingame.entitysystem.systems.effects.popups.*;
import amara.applications.ingame.entitysystem.systems.effects.spawns.*;
import amara.applications.ingame.entitysystem.systems.effects.spells.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.*;
import amara.applications.ingame.entitysystem.systems.effects.units.*;
import amara.applications.ingame.entitysystem.systems.effects.vision.*;
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
import amara.applications.ingame.server.entitysystem.systems.objectives.*;
import amara.applications.ingame.server.network.backends.*;
import amara.applications.ingame.shared.games.*;
import amara.applications.ingame.shared.maps.*;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.*;
import amara.libraries.entitysystem.*;
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
        Game game = mainApplication.getGame();
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.addMessageBackend(new AuthentificateClientsBackend(game, entityWorld));
        subNetworkServer.addMessageBackend(new SendInitialEntityWorldBackend(entityWorld));
        subNetworkServer.addMessageBackend(new StartGameBackend(game));

        EntityWrapper gameEntity = entityWorld.getWrapped(entityWorld.createEntity());
        gameEntity.setComponent(new GameSpeedComponent(1));
        Map map = game.getMap();
        EntityWrapper mapEntity = entityWorld.getWrapped(entityWorld.createEntity());
        map.setEntity(mapEntity.getId());
        map.load(entityWorld);
        PlayerEntitiesAppState playerEntitiesAppState = getAppState(PlayerEntitiesAppState.class);
        int playerIndex = 0;
        for(GamePlayer[] team : game.getTeams()){
            for(GamePlayer player : team){
                playerEntitiesAppState.createPlayerEntity(entityWorld, map, player, playerIndex);
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
        addEntitySystem(new CountdownInCombatSystem());
        addEntitySystem(new CheckOpenObjectivesSystem());
        addEntitySystem(new CheckAggroTargetAttackibilitySystem());
        addEntitySystem(new CheckAggroTargetSightSystem());
        addEntitySystem(new CheckMaximumAggroRangeSystem());
        addEntitySystem(new CheckBuffStacksUpdateAttributesSystem());
        addEntitySystem(new LevelUpSystem());
        addEntitySystem(new UpdateAttributesSystem());
        addEntitySystem(new TriggerUnitsPassivesSystem());
        addEntitySystem(new TriggerItemPassivesSystem());
        CastSpellQueueSystem castSpellQueueSystem = new CastSpellQueueSystem();
        addEntitySystem(new ExecutePlayerCommandsSystem(getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue(), castSpellQueueSystem));
        addEntitySystem(new ExecuteAIActionsSystem((playerEntity) -> getAppState(BotsAppState.class).getBot(playerEntity)));
        addEntitySystem(new AggroResetTimersSystem());
        addEntitySystem(new AggroSystem());
        addEntitySystem(new AttackMoveSystem());
        addEntitySystem(new CheckCampUnionAggroSystem());
        addEntitySystem(new CheckCampInCombatSystem());
        addEntitySystem(new ResetOutOfCombatCampsSystem());
        addEntitySystem(new SetNewCampCombatSpellsOnCooldownSystem());
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
        //Cleanup effects here, so effect impact entities aren't observed
        addEntitySystem(new CleanupEffectsSystem());
        addEntitySystem(new CalculateEffectImpactSystem());
        //Cleanup effect triggers here, so they have been triggered before being removed
        addEntitySystem(new CleanupEffectTriggersSystem());
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
        addEntitySystem(new ApplyAddPopupsSystem());
        addEntitySystem(new ApplyRemovePopupsSystem());
        addEntitySystem(new ApplySpawnsSystems());
        addEntitySystem(new ApplyAddAutoAttackSpellEffectsSystem());
        addEntitySystem(new ApplyAddSpellsSpellEffectsSystem());
        addEntitySystem(new ApplyRemoveSpellEffectsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithExistingSpellsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithNewSpellsSystem());
        addEntitySystem(new ApplyTriggerSpellEffectsSystem());
        addEntitySystem(new ApplyAddGoldSystem());
        addEntitySystem(new ApplyCancelActionsSystem());
        addEntitySystem(new ApplyRespawnSystem(map));
        addEntitySystem(new ApplyAddStealthSystem());
        addEntitySystem(new ApplyRemoveStealthSystem());
        addEntitySystem(new ApplyPlayAnimationsSystem());
        addEntitySystem(new ApplyStopAnimationsSystem());
        addEntitySystem(new ApplyAddComponentsSystem());
        addEntitySystem(new ApplyAddEffectTriggersSystem());
        addEntitySystem(new ApplyFinishObjectivesSystem());
        addEntitySystem(new ApplyRemoveComponentsSystem());
        addEntitySystem(new ApplyRemoveEffectTriggersSystem());
        addEntitySystem(new ApplyRemoveEntitySystem());
        addEntitySystem(new ApplyTriggerErikaPassivesSystem());
        addEntitySystem(new SetInCombatSystem());
        addEntitySystem(new DrawAggroOnDamageSystem());
        addEntitySystem(new ResetAggroTimerOnDamageSystem());
        addEntitySystem(new UpdateDamageHistorySystem());
        addEntitySystem(new TriggerDamageTakenSystem());
        addEntitySystem(new LifestealSystem());
        addEntitySystem(new RemoveAppliedEffectImpactsSystem());
        addEntitySystem(new HealthRegenerationSystem());
        addEntitySystem(new DeathSystem());
        addEntitySystem(new TriggerDeathEffectSystem());
        addEntitySystem(new TriggerTeamDeathEffectSystem());
        addEntitySystem(new MaximumHealthSystem());
        addEntitySystem(new MaximumStacksSystem());
        addEntitySystem(new GoldGenerationSystem());
        addEntitySystem(new PayOutBountiesSystem());
        addEntitySystem(new UpdateDeathsScoreSystem());
        addEntitySystem(new CompleteDeathSystem());
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
        addEntitySystem(new CheckHiddenAreasSystem(intersectionObserver));
        //Add 1 for the neutral team
        addEntitySystem(new TeamVisionSystem(game.getTeams().length + 1, mapPhysicsInformation.getObstacles()));
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionObserver));
        addEntitySystem(new TriggerCastingFinishedEffectSystem());
        addEntitySystem(new TriggerFinishedObjectivesEffctSystem());
        addEntitySystem(new TriggerStacksReachedEffectSystem());
        addEntitySystem(new TriggerRepeatingEffectSystem());
        addEntitySystem(new TriggerInstantEffectSystem());
        addEntitySystem(new CheckCampMaximumAggroDistanceSystem());
        addEntitySystem(new SetIdleAnimationsSystem());
        addEntitySystem(new IntersectionPushSystem(intersectionObserver));
        addEntitySystem(new MapIntersectionSystem(polyMapManager));
        addEntitySystem(new RespawnableDeathSystem());
        addEntitySystem(new RespawnPlayersSystem(map));
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
            for (float i = gameSpeed; i > 0; i--) {
                float simulatedTimePerFrame = Math.min(i, 1) * lastTimePerFrame;
                super.update(simulatedTimePerFrame);
            }
        }
    }
}
