/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import amara.game.entitysystem.components.physics.CollisionGroupComponent;
import amara.game.entitysystem.components.effects.buffs.stacks.ClearStacksComponent;
import amara.game.entitysystem.components.units.animations.WalkAnimationComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.DeathTriggerComponent;
import amara.game.entitysystem.components.effects.spells.AddAutoAttackSpellEffectsComponent;
import amara.game.entitysystem.components.units.effecttriggers.TriggerTemporaryComponent;
import amara.game.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.game.entitysystem.components.players.WaitingToRespawnComponent;
import amara.game.entitysystem.components.buffs.ContinuousAttributesPerStackComponent;
import amara.game.entitysystem.components.units.effecttriggers.TriggerOnceComponent;
import amara.game.entitysystem.components.buffs.ContinuousAttributesComponent;
import amara.game.entitysystem.components.effects.audio.PlayAudioComponent;
import amara.game.entitysystem.components.objectives.OpenObjectiveComponent;
import amara.game.entitysystem.components.effects.casts.EffectCastSourceComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupImmuneComponent;
import amara.game.entitysystem.components.effects.physics.DeactivateHitboxComponent;
import amara.game.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent;
import amara.game.entitysystem.components.camps.CampHealthResetComponent;
import amara.game.entitysystem.components.objectives.FinishedObjectiveComponent;
import amara.game.entitysystem.components.units.LocalAvoidanceWalkComponent;
import amara.game.entitysystem.components.spells.StopBeforeCastingComponent;
import amara.game.entitysystem.components.effects.movement.TeleportComponent;
import amara.game.entitysystem.components.effects.damage.AddVulnerabilityComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddBindingImmuneComponent;
import amara.game.entitysystem.components.units.CastSpellOnCooldownWhileAttackingComponent;
import amara.game.entitysystem.components.effects.PrepareEffectComponent;
import amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent;
import amara.game.entitysystem.components.movements.WalkMovementComponent;
import amara.game.entitysystem.components.effects.heals.HealComponent;
import amara.game.entitysystem.components.effects.damage.RemoveTargetabilityComponent;
import amara.game.entitysystem.components.buffs.status.TimeSinceLastRepeatingEffectComponent;
import amara.game.entitysystem.components.units.WalkStepDistanceComponent;
import amara.game.entitysystem.components.buffs.areas.AreaBuffComponent;
import amara.game.entitysystem.components.effects.visuals.StopAnimationComponent;
import amara.game.entitysystem.components.effects.game.PlayCinematicComponent;
import amara.game.entitysystem.components.camps.CampRespawnDurationComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerCounterComponent;
import amara.game.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.game.entitysystem.components.spells.placeholders.TargetedMovementTargetComponent;
import amara.game.entitysystem.components.effects.casts.EffectCastSourceSpellComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.InstantTriggerComponent;
import amara.game.entitysystem.components.effects.spells.TriggerSpellEffectsComponent;
import amara.game.entitysystem.components.spawns.SpawnAttackMoveComponent;
import amara.game.entitysystem.components.units.AutoAggroComponent;
import amara.game.entitysystem.components.effects.visuals.PlayAnimationComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.RemoveSilenceComponent;
import amara.game.entitysystem.components.movements.MovementTargetReachedComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceImmuneComponent;
import amara.game.entitysystem.components.effects.aggro.DrawTeamAggroComponent;
import amara.game.entitysystem.components.spells.placeholders.TargetedMovementDirectionComponent;
import amara.game.entitysystem.components.buffs.KeepOnDeathComponent;
import amara.game.entitysystem.components.spells.placeholders.TriggerCastedSpellEffectsComponent;
import amara.game.entitysystem.components.buffs.OnBuffRemoveEffectTriggersComponent;
import amara.game.entitysystem.components.effects.heals.ResultingHealComponent;
import amara.game.entitysystem.components.camps.CampSpawnInformationComponent;
import amara.game.entitysystem.components.camps.CampRemainingRespawnDurationComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.RemoveBindingComponent;
import amara.game.entitysystem.components.effects.audio.StopAudioComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.CastingFinishedTriggerComponent;
import amara.game.entitysystem.components.buffs.areas.AreaSourceComponent;
import amara.game.entitysystem.components.effects.buffs.AddBuffComponent;
import amara.game.entitysystem.components.physics.RemoveOnMapLeaveComponent;
import amara.game.entitysystem.components.spells.StopAfterCastingComponent;
import amara.game.entitysystem.components.units.effecttriggers.TriggerDelayComponent;
import amara.game.entitysystem.components.effects.AffectedTargetsComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.RemoveKnockupComponent;
import amara.game.entitysystem.components.spells.placeholders.SourceMovementDirectionComponent;
import amara.game.entitysystem.components.units.effecttriggers.targets.SourceTargetComponent;
import amara.game.entitysystem.components.players.ClientComponent;
import amara.game.entitysystem.components.units.effecttriggers.targets.TargetTargetComponent;
import amara.game.entitysystem.components.input.CastSpellComponent;
import amara.game.entitysystem.components.spawns.SpawnMovementAnimationComponent;
import amara.game.entitysystem.components.spawns.SpawnTemplateComponent;
import amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent;
import amara.game.entitysystem.components.units.SetNewTargetSpellsOnCooldownComponent;
import amara.game.entitysystem.components.spells.ApplyCastedSpellComponent;
import amara.game.entitysystem.components.effects.general.RemoveEffectTriggersComponent;
import amara.game.entitysystem.components.effects.RemainingEffectDelayComponent;
import amara.game.entitysystem.components.spawns.SpawnRelativeDirectionComponent;
import amara.game.entitysystem.components.units.CurrentActionEffectCastsComponent;
import amara.game.entitysystem.components.effects.general.RemoveComponentsComponent;
import amara.game.entitysystem.components.units.MaximumAggroRangeComponent;
import amara.game.entitysystem.components.spells.triggers.CastedSpellComponent;
import amara.game.entitysystem.components.spells.InstantEffectTriggersComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.CollisionTriggerComponent;
import amara.game.entitysystem.components.players.PlayerIndexComponent;
import amara.game.entitysystem.components.units.effecttriggers.TriggerOnCancelComponent;
import amara.game.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent;
import amara.game.entitysystem.components.effects.spawns.SpawnComponent;
import amara.game.entitysystem.components.effects.movement.StopComponent;
import amara.game.entitysystem.components.effects.general.AddComponentsComponent;
import amara.game.entitysystem.components.effects.buffs.stacks.AddStacksComponent;
import amara.game.entitysystem.components.movements.MovementDirectionComponent;
import amara.game.entitysystem.components.effects.damage.RemoveVulnerabilityComponent;
import amara.game.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.game.entitysystem.components.effects.damage.MagicDamageComponent;
import amara.game.entitysystem.components.buffs.areas.AreaOriginComponent;
import amara.game.entitysystem.components.effects.spells.ReplaceSpellWithExistingSpellComponent;
import amara.game.entitysystem.components.spells.CastAnimationComponent;
import amara.game.entitysystem.components.spawns.SpawnRelativePositionComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.TimeSinceLastRepeatTriggerComponent;
import amara.game.entitysystem.components.units.IntersectionRulesComponent;
import amara.game.entitysystem.components.movements.MovementPathfindingComponent;
import amara.game.entitysystem.components.units.animations.IdleAnimationComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.TargetReachedTriggerComponent;
import amara.game.entitysystem.components.game.GameTimeComponent;
import amara.game.entitysystem.components.spells.CastCancelableComponent;
import amara.game.entitysystem.components.units.effecttriggers.targets.CasterTargetComponent;
import amara.game.entitysystem.components.visuals.animations.PassedLoopTimeComponent;
import amara.game.entitysystem.components.players.RespawnComponent;
import amara.game.entitysystem.components.effects.general.AddEffectTriggersComponent;
import amara.game.entitysystem.components.camps.CampMaximumAggroDistanceComponent;
import amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddStunImmuneComponent;
import amara.game.entitysystem.components.effects.damage.AddTargetabilityComponent;
import amara.game.entitysystem.components.movements.MovedDistanceComponent;
import amara.game.entitysystem.components.effects.general.RemoveEntityComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddSilenceComponent;
import amara.game.entitysystem.components.physics.HitboxComponent;
import amara.game.entitysystem.components.spells.CastCancelActionComponent;
import amara.game.entitysystem.components.spawns.SpawnMoveToTargetComponent;
import amara.game.entitysystem.components.movements.MovementAnimationComponent;
import amara.game.entitysystem.components.effects.buffs.RemoveBuffComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddBindingComponent;
import amara.game.entitysystem.components.effects.damage.PhysicalDamageComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddKnockupComponent;
import amara.game.entitysystem.components.units.AggroResetTimerComponent;
import amara.game.entitysystem.components.spells.triggers.CastedEffectTriggersComponent;
import amara.game.entitysystem.components.movements.MovementIsCancelableComponent;
import amara.game.entitysystem.components.objectives.OrObjectivesComponent;
import amara.game.entitysystem.components.units.IsCastingComponent;
import amara.game.entitysystem.components.effects.damage.ResultingPhysicalDamageComponent;
import amara.game.entitysystem.components.spawns.SpawnMovementRelativeDirectionComponent;
import amara.game.entitysystem.components.units.IsWalkingToAggroTargetComponent;
import amara.game.entitysystem.components.units.animations.AutoAttackAnimationComponent;
import amara.game.entitysystem.components.spells.placeholders.TeleportToTargetPositionComponent;
import amara.game.entitysystem.components.units.TargetsInAggroRangeComponent;
import amara.game.entitysystem.components.movements.MovementTargetSufficientDistanceComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.AddStunComponent;
import amara.game.entitysystem.components.physics.IntersectionPushComponent;
import amara.game.entitysystem.components.units.effecttriggers.targets.BuffTargetsTargetComponent;
import amara.game.entitysystem.components.movements.DistanceLimitComponent;
import amara.game.entitysystem.components.effects.spells.RemoveSpellEffectsComponent;
import amara.game.entitysystem.components.maps.PlayerDeathRulesComponent;
import amara.game.entitysystem.components.objectives.MissingEntitiesComponent;
import amara.game.entitysystem.components.effects.buffs.stacks.RemoveStacksComponent;
import amara.game.entitysystem.components.units.LifetimeComponent;
import amara.game.entitysystem.components.visuals.animations.RemainingLoopsComponent;
import amara.game.entitysystem.components.buffs.RepeatingEffectComponent;
import amara.game.entitysystem.components.buffs.status.RemoveFromTargetComponent;
import amara.game.entitysystem.components.buffs.areas.AreaBuffTargetRulesComponent;
import amara.game.entitysystem.components.units.animations.DeathAnimationComponent;
import amara.game.entitysystem.components.units.CampResetComponent;
import amara.game.entitysystem.components.movements.MovementLocalAvoidanceComponent;
import amara.game.entitysystem.components.units.effecttriggers.TriggeredEffectComponent;
import amara.game.entitysystem.components.effects.buffs.areas.RemoveBuffAreaComponent;
import amara.game.entitysystem.components.maps.MapObjectiveComponent;
import amara.game.entitysystem.components.effects.physics.ActivateHitboxComponent;
import amara.game.entitysystem.components.camps.CampSpawnComponent;
import amara.game.entitysystem.components.effects.damage.ResultingMagicDamageComponent;
import amara.game.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent;
import amara.game.entitysystem.components.spells.CastTurnToTargetComponent;
import amara.game.entitysystem.components.effects.crowdcontrol.RemoveStunComponent;
import amara.game.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent;
import amara.game.entitysystem.components.camps.CampUnionAggroComponent;
import amara.game.entitysystem.components.effects.CustomEffectValuesComponent;
import amara.game.entitysystem.components.spawns.SpawnMovementSpeedComponent;
import amara.game.entitysystem.components.effects.movement.MoveComponent;
import amara.game.entitysystem.components.effects.buffs.areas.AddBuffAreaComponent;

/**
 *
 * @author Carl
 */
public class ClientComponentBlacklist{

    public ClientComponentBlacklist(){
        add(new ChangeType[]{ChangeType.NEW, ChangeType.CHANGED, ChangeType.REMOVED},
            //attributes
            RequestUpdateAttributesComponent.class,
            //audio
            //buffs
            ContinuousAttributesComponent.class,
            ContinuousAttributesPerStackComponent.class,
            KeepOnDeathComponent.class,
            OnBuffRemoveEffectTriggersComponent.class,
            RepeatingEffectComponent.class,
            //buffs/areas
            AreaBuffComponent.class,
            AreaBuffTargetRulesComponent.class,
            AreaOriginComponent.class,
            AreaSourceComponent.class,
            //buffs/status
            RemoveFromTargetComponent.class,
            TimeSinceLastRepeatingEffectComponent.class,
            //camps
            CampHealthResetComponent.class,
            CampMaximumAggroDistanceComponent.class,
            CampRemainingRespawnDurationComponent.class,
            CampRespawnDurationComponent.class,
            CampSpawnComponent.class,
            CampSpawnInformationComponent.class,
            CampUnionAggroComponent.class,
            //effects
            AffectedTargetsComponent.class,
            ApplyEffectImpactComponent.class,
            CustomEffectValuesComponent.class,
            PrepareEffectComponent.class,
            RemainingEffectDelayComponent.class,
            //effects/aggro
            DrawTeamAggroComponent.class,
            //effects/audio
            PlayAudioComponent.class,
            StopAudioComponent.class,
            //effects/buffs
            AddBuffComponent.class,
            RemoveBuffComponent.class,
            //effects/buffs/areas
            AddBuffAreaComponent.class,
            RemoveBuffAreaComponent.class,
            //effects/buffs/stacks
            AddStacksComponent.class,
            ClearStacksComponent.class,
            RemoveStacksComponent.class,
            //effects/ccasts
            EffectCastSourceComponent.class,
            EffectCastSourceSpellComponent.class,
            EffectCastTargetComponent.class,
            //effects/crowdcontrol
            AddBindingComponent.class,
            AddBindingImmuneComponent.class,
            AddKnockupComponent.class,
            AddKnockupImmuneComponent.class,
            AddSilenceComponent.class,
            AddSilenceImmuneComponent.class,
            AddStunComponent.class,
            AddStunImmuneComponent.class,
            RemoveBindingComponent.class,
            RemoveKnockupComponent.class,
            RemoveSilenceComponent.class,
            RemoveStunComponent.class,
            //effects/damage
            AddTargetabilityComponent.class,
            AddVulnerabilityComponent.class,
            MagicDamageComponent.class,
            PhysicalDamageComponent.class,
            RemoveTargetabilityComponent.class,
            RemoveVulnerabilityComponent.class,
            ResultingMagicDamageComponent.class,
            ResultingPhysicalDamageComponent.class,
            //effects/game
            PlayCinematicComponent.class,
            //effects/general
            AddComponentsComponent.class,
            AddEffectTriggersComponent.class,
            RemoveComponentsComponent.class,
            RemoveEffectTriggersComponent.class,
            RemoveEntityComponent.class,
            //effects/heals
            HealComponent.class,
            ResultingHealComponent.class,
            //effects/movement
            MoveComponent.class,
            StopComponent.class,
            TeleportComponent.class,
            //effects/physics
            ActivateHitboxComponent.class,
            DeactivateHitboxComponent.class,
            //effects/spawns
            SpawnComponent.class,
            //effects/spells
            AddAutoAttackSpellEffectsComponent.class,
            RemoveSpellEffectsComponent.class,
            ReplaceSpellWithExistingSpellComponent.class,
            ReplaceSpellWithNewSpellComponent.class,
            TriggerSpellEffectsComponent.class,
            //effects/visuals
            PlayAnimationComponent.class,
            StopAnimationComponent.class,
            //game
            //general
            //input
            CastSpellComponent.class,
            //items
            //maps
            MapObjectiveComponent.class,
            PlayerDeathRulesComponent.class,
            //maps/playerdeathrules
            RespawnPlayersComponent.class,
            RespawnTimerComponent.class,
            //movements
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
            //objectives
            FinishedObjectiveComponent.class,
            MissingEntitiesComponent.class,
            OpenObjectiveComponent.class,
            OrObjectivesComponent.class,
            //physics
            CollisionGroupComponent.class,
            IntersectionPushComponent.class,
            RemoveOnMapLeaveComponent.class,
            //players
            ClientComponent.class,
            PlayerIndexComponent.class,
            RespawnComponent.class,
            //shop
            //spawns
            SpawnAttackMoveComponent.class,
            SpawnMoveToTargetComponent.class,
            SpawnMovementAnimationComponent.class,
            SpawnMovementRelativeDirectionComponent.class,
            SpawnMovementSpeedComponent.class,
            SpawnRelativeDirectionComponent.class,
            SpawnRelativePositionComponent.class,
            SpawnTemplateComponent.class,
            //spells
            ApplyCastedSpellComponent.class,
            CastAnimationComponent.class,
            CastCancelActionComponent.class,
            CastCancelableComponent.class,
            CastTurnToTargetComponent.class,
            InstantEffectTriggersComponent.class,
            StopAfterCastingComponent.class,
            StopBeforeCastingComponent.class,
            //spells/placeholders
            SourceMovementDirectionComponent.class,
            TargetedMovementDirectionComponent.class,
            TargetedMovementTargetComponent.class,
            TeleportToTargetPositionComponent.class,
            TriggerCastedSpellEffectsComponent.class,
            //spells/triggers
            CastedEffectTriggersComponent.class,
            CastedSpellComponent.class,
            //targets
            //units
            AggroResetTimerComponent.class,
            AutoAggroComponent.class,
            CampResetComponent.class,
            CastSpellOnCooldownWhileAttackingComponent.class,
            CurrentActionEffectCastsComponent.class,
            IntersectionRulesComponent.class,
            IsWalkingToAggroTargetComponent.class,
            LifetimeComponent.class,
            LocalAvoidanceWalkComponent.class,
            MaximumAggroRangeComponent.class,
            RemainingAggroResetDurationComponent.class,
            SetNewTargetSpellsOnCooldownComponent.class,
            TargetsInAggroRangeComponent.class,
            WalkStepDistanceComponent.class,
            //units/animations
            AutoAttackAnimationComponent.class,
            DeathAnimationComponent.class,
            IdleAnimationComponent.class,
            WalkAnimationComponent.class,
            //units/bounties
            //units/crowdcontrol
            //units/effecttriggers
            TriggerDelayComponent.class,
            TriggerOnCancelComponent.class,
            TriggerOnceComponent.class,
            TriggerSourceComponent.class,
            TriggerTemporaryComponent.class,
            TriggeredEffectComponent.class,
            //units/effecttriggers/targets
            BuffTargetsTargetComponent.class,
            CasterTargetComponent.class,
            CustomTargetComponent.class,
            SourceTargetComponent.class,
            TargetTargetComponent.class,
            //units/effecttriggers/triggers
            CastingFinishedTriggerComponent.class,
            CollisionTriggerComponent.class,
            DeathTriggerComponent.class,
            InstantTriggerComponent.class,
            RepeatingTriggerComponent.class,
            RepeatingTriggerCounterComponent.class,
            TargetReachedTriggerComponent.class,
            TimeSinceLastRepeatTriggerComponent.class,
            //visuals
            //visuals/animations
            PassedLoopTimeComponent.class,
            RemainingLoopsComponent.class
        );
        add(new ChangeType[]{ChangeType.CHANGED},
            //game
            GameTimeComponent.class,
            //physics
            HitboxComponent.class,
            //players
            WaitingToRespawnComponent.class,
            //units
            IsCastingComponent.class
        );
    }
    public enum ChangeType{
        NEW,
        CHANGED,
        REMOVED
    }
    private ComponentClassSet componentClasses_New = new ComponentClassSet();
    private ComponentClassSet componentClasses_Changed = new ComponentClassSet();
    private ComponentClassSet componentClasses_Removed = new ComponentClassSet();
    
    public void add(ChangeType[] changeTypes, Class... componentClasses){
        for(ChangeType changeType : changeTypes){
            add(changeType, componentClasses);
        }
    }
    
    public void add(ChangeType changeType, Class... componentClasses){
        ComponentClassSet componentClassSet = getComponentClassSet(changeType);
        componentClassSet.add(componentClasses);
    }
    
    public boolean contains(ChangeType changeType, Class componentClass){
        ComponentClassSet componentClassSet = getComponentClassSet(changeType);
        return componentClassSet.contains(componentClass);
    }
    
    private ComponentClassSet getComponentClassSet(ChangeType changeType){
        switch(changeType){
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
