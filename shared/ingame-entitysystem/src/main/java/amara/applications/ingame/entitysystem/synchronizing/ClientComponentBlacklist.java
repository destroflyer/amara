package amara.applications.ingame.entitysystem.synchronizing;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.aggro.*;
import amara.applications.ingame.entitysystem.components.effects.audio.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.stacks.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
import amara.applications.ingame.entitysystem.components.effects.game.*;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.components.effects.heals.*;
import amara.applications.ingame.entitysystem.components.effects.movement.*;
import amara.applications.ingame.entitysystem.components.effects.physics.*;
import amara.applications.ingame.entitysystem.components.effects.players.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.effects.units.*;
import amara.applications.ingame.entitysystem.components.effects.vision.*;
import amara.applications.ingame.entitysystem.components.effects.visuals.*;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.spells.placeholders.*;
import amara.applications.ingame.entitysystem.components.spells.triggers.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.bounties.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.units.shields.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.libraries.entitysystem.synchronizing.ComponentClassSet;

public class ClientComponentBlacklist {

    public ClientComponentBlacklist() {
        add(new ChangeType[] { ChangeType.NEW, ChangeType.CHANGED, ChangeType.REMOVED },
            // attributes
            RequestUpdateAttributesComponent.class,
            // audio
            // buffs
            ContinuousAttributesComponent.class,
            ContinuousAttributesPerStackComponent.class,
            KeepOnDeathComponent.class,
            OnBuffAddEffectTriggersComponent.class,
            OnBuffRemoveEffectTriggersComponent.class,
            RepeatingEffectComponent.class,
            // buffs/areas
            AreaBuffComponent.class,
            AreaBuffTargetRulesComponent.class,
            AreaSourceComponent.class,
            // buffs/status
            TimeSinceLastRepeatingEffectComponent.class,
            // camps
            CampHealthResetComponent.class,
            CampMaximumAggroDistanceComponent.class,
            CampRemainingRespawnDurationComponent.class,
            CampRespawnDurationComponent.class,
            CampSpawnComponent.class,
            CampSpawnInformationComponent.class,
            CampUnionAggroComponent.class,
            // effects
            AffectedTargetsComponent.class,
            ApplyEffectImpactComponent.class,
            CustomEffectValuesComponent.class,
            EffectSourceComponent.class,
            EffectSourceSpellComponent.class,
            PrepareEffectComponent.class,
            RemainingEffectDelayComponent.class,
            // effects/aggro
            DrawTeamAggroComponent.class,
            SetAggroTargetComponent.class,
            // effects/audio
            PlayAudioComponent.class,
            StopAudioComponent.class,
            // effects/buffs
            AddBuffComponent.class,
            RemoveBuffComponent.class,
            // effects/buffs/areas
            AddBuffAreaComponent.class,
            RemoveBuffAreaComponent.class,
            // effects/buffs/stacks
            AddStacksComponent.class,
            ClearStacksComponent.class,
            // effects/casts
            EffectCastTargetComponent.class,
            // effects/crowdcontrol
            AddBindableComponent.class,
            AddBindingComponent.class,
            AddKnockupableComponent.class,
            AddKnockupComponent.class,
            AddSilencableComponent.class,
            AddSilenceComponent.class,
            AddStunComponent.class,
            AddStunnableComponent.class,
            RemoveBindableComponent.class,
            RemoveBindingComponent.class,
            RemoveKnockupableComponent.class,
            RemoveKnockupComponent.class,
            RemoveSilencableComponent.class,
            RemoveSilenceComponent.class,
            RemoveStunComponent.class,
            RemoveStunnableComponent.class,
            // effects/damage
            AddTargetabilityComponent.class,
            AddVulnerabilityComponent.class,
            MagicDamageComponent.class,
            PhysicalDamageComponent.class,
            RemoveTargetabilityComponent.class,
            RemoveVulnerabilityComponent.class,
            ResultingMagicDamageComponent.class,
            ResultingPhysicalDamageComponent.class,
            // effects/game
            PlayCinematicComponent.class,
            // effects/general
            AddComponentsComponent.class,
            AddEffectTriggersComponent.class,
            AddNewEffectTriggersComponent.class,
            FinishObjectiveComponent.class,
            RemoveComponentsComponent.class,
            RemoveEffectTriggersComponent.class,
            RemoveEntityComponent.class,
            // effects/heals
            HealComponent.class,
            ResultingHealComponent.class,
            // effects/movement
            MoveComponent.class,
            StopComponent.class,
            TeleportComponent.class,
            // effects/physics
            ActivateHitboxComponent.class,
            AddCollisionGroupsComponent.class,
            AddIntersectionPushedComponent.class,
            AddIntersectionPushesComponent.class,
            DeactivateHitboxComponent.class,
            RemoveCollisionGroupsComponent.class,
            RemoveIntersectionPushedComponent.class,
            RemoveIntersectionPushesComponent.class,
            SwapPositionsComponent.class,
            // effects/players
            DisplayPlayerAnnouncementComponent.class,
            ResultingPlayerAnnouncementComponent.class,
            // effects/spawns
            SpawnComponent.class,
            // effects/spells
            AddAutoAttackSpellEffectsComponent.class,
            AddSpellSpellEffectsComponent.class,
            EnqueueSpellCastComponent.class,
            ReduceRemainingCooldownAbsoluteComponent.class,
            RemoveSpellEffectsComponent.class,
            ReplaceSpellWithExistingSpellComponent.class,
            ReplaceSpellWithNewSpellComponent.class,
            ResetCooldownComponent.class,
            TriggerSpellEffectsComponent.class,
            // effects/units
            CancelActionComponent.class,
            AddGoldComponent.class,
            AddShieldComponent.class,
            RespawnComponent.class,
            // effects/vision
            AddStealthComponent.class,
            RemoveStealthComponent.class,
            // effects/visuals
            PlayAnimationComponent.class,
            StopAnimationComponent.class,
            // game
            // general
            CustomCleanupComponent.class,
            TemporaryComponent.class,
            // input
            CastSpellComponent.class,
            // items
            // maps
            MapObjectiveComponent.class,
            PlayerDeathRulesComponent.class,
            // maps/playerdeathrules
            RespawnPlayersComponent.class,
            RespawnTimerComponent.class,
            // movements
            DistanceLimitComponent.class,
            MovedDistanceComponent.class,
            MovementAnimationComponent.class,
            MovementDirectionComponent.class,
            MovementIsCancelableComponent.class,
            MovementLocalAvoidanceComponent.class,
            MovementPathfindingComponent.class,
            MovementTargetReachedComponent.class,
            MovementTargetSufficientDistanceComponent.class,
            WalkMovementComponent.class,
            // objectives
            FinishedObjectiveComponent.class,
            MissingEntitiesComponent.class,
            OpenObjectiveComponent.class,
            OrObjectivesComponent.class,
            // physics
            CollisionGroupComponent.class,
            IntersectionPushesComponent.class,
            IntersectionPushedComponent.class,
            RemoveOnMapLeaveComponent.class,
            TransformOriginComponent.class,
            // players
            ClientComponent.class,
            IsBotComponent.class,
            PlayerAnnouncementRemainingDurationComponent.class,
            RespawnComponent.class,
            // shop
            // spawns
            SpawnAttackMoveComponent.class,
            SpawnMoveToTargetComponent.class,
            SpawnMovementAnimationComponent.class,
            SpawnMovementRelativeDirectionComponent.class,
            SpawnMovementSpeedComponent.class,
            SpawnRedirectReceivedBountiesComponent.class,
            SpawnRelativeDirectionComponent.class,
            SpawnRelativePositionComponent.class,
            SpawnTemplateComponent.class,
            // spells
            ApplyCastedSpellComponent.class,
            CastAnimationComponent.class,
            CastCancelCastComponent.class,
            CastCancelMovementComponent.class,
            CastCancelableComponent.class,
            CastTurnToTargetComponent.class,
            InstantEffectTriggersComponent.class,
            // spells/placeholders
            SourceMovementDirectionComponent.class,
            TargetedMovementDirectionComponent.class,
            TargetedMovementTargetComponent.class,
            TeleportToTargetPositionComponent.class,
            TriggerCastedSpellEffectsComponent.class,
            // spells/triggers
            CastedEffectTriggersComponent.class,
            CastedSpellComponent.class,
            // targets
            // units
            AggroPriorityComponent.class,
            AggroResetTimerComponent.class,
            AutoAggroComponent.class,
            CampResetComponent.class,
            CastSpellOnCooldownWhileAttackingComponent.class,
            CurrentCastEffectCastsComponent.class,
            LifetimeComponent.class,
            LocalAvoidanceWalkComponent.class,
            MaximumAggroRangeComponent.class,
            RedirectReceivedBountiesComponent.class,
            RemainingAggroResetDurationComponent.class,
            SetNewCampCombatSpellsOnCooldownComponent.class,
            WalkStepDistanceComponent.class,
            // units/animations
            AutoAttackAnimationComponent.class,
            DeathAnimationComponent.class,
            IdleAnimationComponent.class,
            WalkAnimationComponent.class,
            // units/bounties
            // units/crowdcontrol
            // units/effecttriggers
            TriggerDelayComponent.class,
            TriggerOnCancelComponent.class,
            TriggerOnceComponent.class,
            TriggerSourceComponent.class,
            TriggerTemporaryComponent.class,
            TriggeredEffectComponent.class,
            // units/effecttriggers/targets
            BuffTargetsTargetComponent.class,
            CustomTargetComponent.class,
            ExcludeTargetTargetComponent.class,
            MaximumTargetsComponent.class,
            RuleTargetComponent.class,
            SourceCasterTargetComponent.class,
            SourceTargetComponent.class,
            TargetCasterTargetComponent.class,
            TargetTargetComponent.class,
            TeamTargetComponent.class,
            // units/effecttriggers/triggers
            CastCancelledTriggerComponent.class,
            BuffTargetsAmountTriggerComponent.class,
            CastingFinishedTriggerComponent.class,
            CollisionTriggerComponent.class,
            DeathTriggerComponent.class,
            HasAggroTargetTriggerComponent.class,
            HasBuffsTriggerComponent.class,
            HasNoAggroTargetTriggerComponent.class,
            InstantTriggerComponent.class,
            MapCollisionTriggerComponent.class,
            MovementTriggerComponent.class,
            MovementTriggerMovedDistanceComponent.class,
            NoBuffTargetsTriggerComponent.class,
            ObjectiveFinishedTriggerComponent.class,
            RemoveTriggerComponent.class,
            RepeatingTriggerComponent.class,
            RepeatingTriggerCounterComponent.class,
            ShopUsageTriggerComponent.class,
            SurroundingDeathTriggerComponent.class,
            TargetReachedTriggerComponent.class,
            TeamDeathTriggerComponent.class,
            TimeSinceLastRepeatTriggerComponent.class,
            // units/shields
            RemainingShieldDurationComponent.class,
            // visuals
            // visuals/animations
            PassedLoopTimeComponent.class,
            RemainingLoopsComponent.class
        );
        add(new ChangeType[] { ChangeType.CHANGED },
            // game
            GameTimeComponent.class,
            // physics
            HitboxComponent.class,
            // units
            IsCastingComponent.class,
            WaitingToRespawnComponent.class
        );
    }
    public enum ChangeType {
        NEW,
        CHANGED,
        REMOVED
    }
    private ComponentClassSet componentClasses_New = new ComponentClassSet();
    private ComponentClassSet componentClasses_Changed = new ComponentClassSet();
    private ComponentClassSet componentClasses_Removed = new ComponentClassSet();

    public void add(ChangeType[] changeTypes, Class... componentClasses) {
        for (ChangeType changeType : changeTypes) {
            add(changeType, componentClasses);
        }
    }

    public void add(ChangeType changeType, Class... componentClasses) {
        ComponentClassSet componentClassSet = getComponentClassSet(changeType);
        componentClassSet.add(componentClasses);
    }

    public boolean contains(ChangeType changeType, Class componentClass) {
        ComponentClassSet componentClassSet = getComponentClassSet(changeType);
        return componentClassSet.contains(componentClass);
    }

    private ComponentClassSet getComponentClassSet(ChangeType changeType) {
        switch (changeType) {
            case NEW:
                return componentClasses_New;
            case CHANGED:
                return componentClasses_Changed;
            case REMOVED:
                return componentClasses_Removed;
        }
        return null;
    }
}
