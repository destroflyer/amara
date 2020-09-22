package amara.applications.ingame.server.entitysystem;

import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.entitysystem.synchronizing.ParallelNetworkSystems;
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
import amara.applications.ingame.entitysystem.systems.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.*;
import amara.applications.ingame.entitysystem.systems.general.*;
import amara.applications.ingame.entitysystem.systems.movement.*;
import amara.applications.ingame.entitysystem.systems.objectives.*;
import amara.applications.ingame.entitysystem.systems.physics.*;
import amara.applications.ingame.entitysystem.systems.players.*;
import amara.applications.ingame.entitysystem.systems.shop.*;
import amara.applications.ingame.entitysystem.systems.spells.*;
import amara.applications.ingame.entitysystem.systems.spells.casting.*;
import amara.applications.ingame.entitysystem.systems.units.*;
import amara.applications.ingame.entitysystem.systems.units.scores.*;
import amara.applications.ingame.entitysystem.systems.units.shields.*;
import amara.applications.ingame.entitysystem.systems.visuals.*;
import amara.applications.ingame.network.messages.objects.commands.PlayerCommand;
import amara.applications.ingame.server.entitysystem.systems.effects.ApplyEffectsLoopSystem;
import amara.applications.ingame.server.maps.PolyMapLoader;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.master.server.games.Game;
import amara.core.Queue;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.physics.intersectionHelper.PolyMapManager;

import java.util.Collections;
import java.util.LinkedList;

public class GameLogic {

    public GameLogic(EntityWorld entityWorld, Game game, Queue<PlayerCommand> playerCommandsQueue, ExecuteAIActionsSystem.EntityBotsMap entityBotsMap) {
        this.entityWorld = entityWorld;
        this.game = game;
        this.playerCommandsQueue = playerCommandsQueue;
        this.entityBotsMap = entityBotsMap;
    }
    private EntityWorld entityWorld;
    private Game game;
    private Queue<PlayerCommand> playerCommandsQueue;
    private ExecuteAIActionsSystem.EntityBotsMap entityBotsMap;
    private LinkedList<EntitySystem> entitySystems;

    public void initialize() {
        int gameEntity = entityWorld.createEntity();
        entityWorld.setComponent(gameEntity, new GameSpeedComponent(1));
        entityWorld.setComponent(gameEntity, new NextEffectActionIndexComponent(0));
        Map map = game.getMap();
        int mapEntity = entityWorld.createEntity();
        map.setEntity(mapEntity);

        IntersectionObserver intersectionObserver = new IntersectionObserver();
        PolyMapManager polyMapManager = PolyMapLoader.createPolyMapManager(game.getMap());

        entitySystems = new LinkedList<>();
        entitySystems.add(new SetAutoAttacksCastAnimationsSystem());
        entitySystems.add(new SetSpellsCastersSystem());
        entitySystems.add(new SetBaseCooldownSystem());
        entitySystems.add(new LinkedCooldownsSystem());
        entitySystems.add(new SetLevelExperienceSystem());
        entitySystems.add(new RemoveAudiosAfterPlayingSystem());
        Collections.addAll(entitySystems, ParallelNetworkSystems.generateSystems());
        entitySystems.add(new CountdownLifetimeSystem());
        entitySystems.add(new CountdownBuffsSystem());
        entitySystems.add(new CountdownCooldownSystem());
        entitySystems.add(new CountdownBindingSystem());
        entitySystems.add(new CountdownKnockupSystem());
        entitySystems.add(new CountdownSilenceSystem());
        entitySystems.add(new CountdownStunSystem());
        entitySystems.add(new CountdownShieldSystem());
        entitySystems.add(new CountdownReactionsSystem());
        entitySystems.add(new CountdownEffectDelaySystem());
        entitySystems.add(new CountdownCampRespawnSystem());
        entitySystems.add(new CountdownAnimationLoopsSystem());
        entitySystems.add(new CountdownAggroResetTimersSystem());
        entitySystems.add(new CountdownInCombatSystem());
        entitySystems.add(new CheckOpenObjectivesSystem());
        entitySystems.add(new CheckWaitingToRespawnSystem());
        entitySystems.add(new CheckAggroTargetAttackabilitySystem());
        entitySystems.add(new CheckAggroTargetSightSystem());
        entitySystems.add(new CheckMaximumAggroRangeSystem());
        entitySystems.add(new CheckBuffStacksUpdateAttributesSystem());
        entitySystems.add(new LevelUpSystem());
        entitySystems.add(new UpdateAttributesSystem());
        entitySystems.add(new TriggerUnitsPassivesSystem());
        entitySystems.add(new TriggerSpellsPassivesSystem());
        entitySystems.add(new TriggerItemPassivesSystem());
        CastSpellQueueSystem castSpellQueueSystem = new CastSpellQueueSystem();
        entitySystems.add(new ExecutePlayerCommandsSystem(playerCommandsQueue, castSpellQueueSystem, game.getMap()));
        entitySystems.add(new ExecuteAIActionsSystem(entityBotsMap, castSpellQueueSystem, game.getMap()));
        entitySystems.add(new AggroResetTimersSystem());
        entitySystems.add(new AutoAggroSystem());
        entitySystems.add(new AttackMoveSystem());
        entitySystems.add(new CheckCampUnionAggroSystem());
        entitySystems.add(new CheckCampInCombatSystem());
        entitySystems.add(new ResetOutOfCombatCampsSystem());
        entitySystems.add(new SetNewCampCombatSpellsOnCooldownSystem());
        entitySystems.add(new CastSpellOnCooldownWhileAttackingSystem(castSpellQueueSystem));
        entitySystems.add(new PerformAutoAttacksSystem(castSpellQueueSystem));
        entitySystems.add(castSpellQueueSystem);
        entitySystems.add(new TriggerCastingStartedEffectSystem());
        entitySystems.add(new SetCastDurationOnCastingSystem());
        entitySystems.add(new SetCooldownOnCastingSystem());
        entitySystems.add(new ConsumeItemsOnCastingSystem());
        entitySystems.add(new PlayCastAnimationSystem());
        entitySystems.add(new CastSpellSystem());
        entitySystems.add(new CheckAreaBuffsSystem(intersectionObserver));
        entitySystems.add(new RepeatingBuffEffectsSystem());
        // Cleanup effects here, so effect impact entities aren't observed
        entitySystems.add(new CleanupEffectsSystem());
        entitySystems.add(new ApplyEffectsLoopSystem(game.getMap(), castSpellQueueSystem));
        // Cleanup effect triggers here, so they have been triggered before being removed
        entitySystems.add(new CleanupEffectTriggersSystem());
        entitySystems.add(new HealthRegenerationSystem());
        entitySystems.add(new DeathSystem());
        entitySystems.add(new TriggerDeathEffectSystem());
        entitySystems.add(new TriggerSurroundingDeathEffectSystem());
        entitySystems.add(new TriggerTeamDeathEffectSystem());
        entitySystems.add(new MaximumHealthSystem());
        entitySystems.add(new MaximumStacksSystem());
        entitySystems.add(new GoldGenerationSystem());
        entitySystems.add(new PayOutBountiesSystem());
        entitySystems.add(new TriggerKillEffectsSystem());
        entitySystems.add(new UpdateDeathsScoreSystem());
        entitySystems.add(new CompleteDeathSystem());
        entitySystems.add(new CampResetSystem());
        entitySystems.add(new CheckDeadCampsRespawnSystem());
        entitySystems.add(new CampSpawnSystem());
        entitySystems.add(new InnateWalkSystem());
        entitySystems.add(new PlayMovementAnimationsSystem());
        entitySystems.add(new UpdateWalkMovementsSystem());
        entitySystems.add(new TargetedMovementSystem(intersectionObserver, polyMapManager));
        entitySystems.add(new LocalAvoidanceSystem());
        entitySystems.add(new TurnInMovementDirectionSystem());
        entitySystems.add(new MovementSystem());
        entitySystems.add(new CheckDistanceLimitMovementsSystem());
        entitySystems.add(new TransformOriginsSystem());
        entitySystems.add(new TriggerTargetReachedEffectSystem());
        entitySystems.add(new FinishTargetedMovementsSystem());
        entitySystems.add(new CheckHiddenAreasSystem(intersectionObserver));
        // Add 1 for the neutral team
        entitySystems.add(new TeamVisionSystem(game.getTeamsCount() + 1, game.getMap().getPhysicsInformation().generateVisionObstacles()));
        entitySystems.add(new TriggerAggroTargetEffectSystem());
        entitySystems.add(new TriggerBuffTargetsAmountEffectSystem());
        entitySystems.add(new TriggerCollisionEffectSystem(intersectionObserver));
        entitySystems.add(new TriggerCastingFinishedEffectSystem());
        entitySystems.add(new TriggerFinishedObjectivesEffctSystem());
        entitySystems.add(new TriggerNoBuffTargetsEffectSystem());
        entitySystems.add(new TriggerHasBuffsEffectSystem());
        entitySystems.add(new TriggerStacksReachedEffectSystem());
        entitySystems.add(new TriggerRepeatingEffectSystem());
        entitySystems.add(new TriggerInstantEffectSystem());
        entitySystems.add(new CheckCampMaximumAggroDistanceSystem());
        entitySystems.add(new ResetAnimationsSystem());
        entitySystems.add(new IntersectionPushSystem(intersectionObserver));
        entitySystems.add(new MapIntersectionSystem(polyMapManager));
        entitySystems.add(new RemoveOnMapLeaveSystem(polyMapManager));
        entitySystems.add(new RespawnableDeathSystem());
        entitySystems.add(new RespawnPlayersSystem(game.getMap()));
        entitySystems.add(new CleanupPlayersSystem());
        entitySystems.add(new CleanupUnitsSystem());
        entitySystems.add(new CleanupSpellsSystem());
        entitySystems.add(new CleanupPassivesSystem());
        entitySystems.add(new CleanupBuffAreasSystem());
        entitySystems.add(new CleanupBuffsSystem());
        entitySystems.add(new CleanupTemporaryEntitiesSystem());
    }

    public LinkedList<EntitySystem> getEntitySystems() {
        return entitySystems;
    }
}
