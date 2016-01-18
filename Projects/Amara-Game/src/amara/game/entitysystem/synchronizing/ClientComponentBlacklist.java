/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.areas.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.aggro.*;
import amara.game.entitysystem.components.effects.audio.*;
import amara.game.entitysystem.components.effects.buffs.*;
import amara.game.entitysystem.components.effects.buffs.areas.*;
import amara.game.entitysystem.components.effects.buffs.stacks.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.game.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.effects.physics.*;
import amara.game.entitysystem.components.effects.spawns.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.effects.visuals.*;
import amara.game.entitysystem.components.game.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.placeholders.*;
import amara.game.entitysystem.components.spells.triggers.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.libraries.entitysystem.synchronizing.ComponentClassSet;

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
