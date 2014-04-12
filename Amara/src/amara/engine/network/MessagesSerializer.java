/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import com.jme3.network.serializing.Serializer;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.network.messages.*;
import amara.engine.network.messages.entitysystem.*;
import amara.engine.network.messages.protocol.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.input.casts.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.placeholders.*;
import amara.game.entitysystem.components.spells.specials.*;
import amara.game.entitysystem.components.spells.triggers.*;
import amara.game.entitysystem.components.targets.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.synchronizing.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.games.PlayerData;
import amara.launcher.client.protocol.*;

/**
 *
 * @author Carl
 */
public class MessagesSerializer{
    
    public static void registerClasses(){
        Serializer.registerClasses(
            Message_EditUserMeta.class,
            Message_GetPlayerProfileData.class,
            Message_GetPlayerStatus.class,
            Message_Login.class,
                AuthentificationInformation.class,
            Message_LoginResult.class,
            Message_Logout.class,
            Message_PlayerProfileData.class,
                PlayerProfileData.class,
            Message_PlayerProfileDataNotExistant.class,
            Message_PlayerStatus.class,
            
            Message_StartGame.class,
                PlayerData.class,
            Message_GameCreated.class,
            
            Message_Command.class,
                AutoAttackCommand.class,
                Command.class,
                MoveCommand.class,
                StopCommand.class,
                CastLinearSkillshotSpellCommand.class,
                CastPositionalSkillshotSpellCommand.class,
                CastSelfcastSpellCommand.class,
                CastSingleTargetSpellCommand.class,
            Message_ClientConnection.class,
            Message_ClientDisconnection.class,
            Message_ClientInitialized.class,
            Message_EntityChanges.class,
                EntityChange.class,
                    NewComponentChange.class,
                        //attributes
                        AbilityPowerComponent.class,
                        AttackDamageComponent.class,
                        AttackSpeedComponent.class,
                        BaseAbilityPowerComponent.class,
                        BaseAttackDamageComponent.class,
                        BaseAttackSpeedComponent.class,
                        BaseMaximumHealthComponent.class,
                        BonusFlatAbilityPowerComponent.class,
                        BonusFlatAttackDamageComponent.class,
                        BonusFlatMaximumHealthComponent.class,
                        BonusPercentageAttackSpeedComponent.class,
                        HealthComponent.class,
                        MaximumHealthComponent.class,
                        RequestUpdateAttributesComponent.class,
                        //buffs
                        ContinuousEffectComponent.class,
                        RemoveEffectTriggersComponent.class,
                        RepeatingEffectComponent.class,
                        //buffs/status
                        ActiveBuffComponent.class,
                        BuffVisualisationComponent.class,
                        RemainingBuffDurationComponent.class,
                        RemoveFromTargetComponent.class,
                        TimeSinceLastRepeatingEffectComponent.class,
                        //effects
                        AffectedTargetsComponent.class,
                        ApplyEffectImpactComponent.class,
                        PrepareEffectComponent.class,
                        //effects/buffs
                        AddBuffComponent.class,
                        RemoveBuffComponent.class,
                        //effects/casts
                        EffectCastDirectionComponent.class,
                        EffectCastPositionComponent.class,
                        EffectCastSourceComponent.class,
                        EffectCastSourceSpellComponent.class,
                        EffectCastTargetComponent.class,
                        //effects/crowdcontrol
                        AddBindingComponent.class,
                        AddBindingImmuneComponent.class,
                        AddSilenceComponent.class,
                        AddSilenceImmuneComponent.class,
                        AddStunComponent.class,
                        AddStunImmuneComponent.class,
                        RemoveBindingComponent.class,
                        RemoveSilenceComponent.class,
                        RemoveStunComponent.class,
                        //effects/damage
                        AddTargetabilityComponent.class,
                        AddVulnerabilityComponent.class,
                        FlatMagicDamageComponent.class,
                        FlatPhysicalDamageComponent.class,
                        MagicDamageComponent.class,
                        PhysicalDamageComponent.class,
                        RemoveTargetabilityComponent.class,
                        RemoveVulnerabilityComponent.class,
                        ScalingAbilityPowerMagicDamageComponent.class,
                        ScalingAttackDamagePhysicalDamageComponent.class,
                        //effects/general
                        AddComponentsComponent.class,
                        AddEffectTriggersComponent.class,
                        RemoveEntityComponent.class,
                        //effects/heal
                        FlatHealComponent.class,
                        HealComponent.class,
                        //effects/movement,
                        MoveComponent.class,
                        StopComponent.class,
                        //effects/spells
                        AddAutoAttackSpellEffectsComponent.class,
                        RemoveSpellEffectsComponent.class,
                        ReplaceSpellWithExistingSpellComponent.class,
                        ReplaceSpellWithNewSpellComponent.class,
                        TriggerSpellEffectsComponent.class,
                        //general
                        DescriptionComponent.class,
                        NameComponent.class,
                        //input
                        CastSpellComponent.class,
                        //input/casts
                        TargetComponent.class,
                        //items
                        InventoryComponent.class,
                        ItemVisualisationComponent.class,
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
                        MovementSpeedComponent.class,
                        MovementTargetComponent.class,
                        MovementTargetReachedComponent.class,
                        MovementTargetSufficientDistanceComponent.class,
                        //objectives
                        FinishedObjectiveComponent.class,
                        MissingEntitiesComponent.class,
                        OpenObjectiveComponent.class,
                        //physics
                        IntersectionPushComponent.class,
                        CollisionGroupComponent.class,
                        DirectionComponent.class,
                        HitboxActiveComponent.class,
                        HitboxComponent.class,
                            Circle.class,
                            Rectangle.class,
                            RegularCyclic.class,
                            Shape.class,
                            SimpleConvex.class,
                            Transform.class,
                            Vector2D.class,
                        MovementComponent.class,
                        PositionComponent.class,
                        ScaleComponent.class,
                        //players
                        ClientComponent.class,
                        PlayerIndexComponent.class,
                        RespawnComponent.class,
                        SelectedUnitComponent.class,
                        WaitingToRespawnComponent.class,
                        //spawns
                        RelativeSpawnPositionComponent.class,
                        SpawnMovementSpeedComponent.class,
                        SpawnTemplateComponent.class,
                        //spells
                        ApplyCastedSpellComponent.class,
                        CastAnimationComponent.class,
                        CastTypeComponent.class,
                        CooldownComponent.class,
                        InstantSpawnsComponent.class,
                        InstantTargetBuffComponent.class,
                        InstantEffectTriggersComponent.class,
                        RangeComponent.class,
                        RemainingCooldownComponent.class,
                        SpellTargetRulesComponent.class,
                        SpellVisualisationComponent.class,
                        //spells/placeholders
                        TargetedMovementDirectionComponent.class,
                        TargetedMovementTargetComponent.class,
                        TriggerCastedSpellEffectsComponent.class,
                        //spells/specials
                        TeleportCasterToTargetPositionComponent.class,
                        //spells/triggers
                        CastedEffectTriggersComponent.class,
                        CastedSpellComponent.class,
                        //targets
                        AcceptAlliesComponent.class,
                        AcceptEnemiesComponent.class,
                        //units
                        AutoAggroComponent.class,
                        AutoAttackComponent.class,
                        AutoAttackTargetComponent.class,
                        IntersectionRulesComponent.class,
                        IsAliveComponent.class,
                        IsTargetableComponent.class,
                        IsVulnerableComponent.class,
                        LifetimeComponent.class,
                        MovementComponent.class,
                        SpellsComponent.class,
                        TargetsInAggroRangeComponent.class,
                        TeamComponent.class,
                        //units/animations
                        AutoAttackAnimationComponent.class,
                        DeathAnimationComponent.class,
                        IdleAnimationComponent.class,
                        WalkAnimationComponent.class,
                        //units/crowdcontrol
                        IsBindedComponent.class,
                        IsBindedImmuneComponent.class,
                        IsSilencedComponent.class,
                        IsSilencedImmuneComponent.class,
                        IsStunnedComponent.class,
                        IsStunnedImmuneComponent.class,
                        //units/effecttriggers
                        TriggeredEffectComponent.class,
                        TriggerOnceComponent.class,
                        TriggerSourceComponent.class,
                        //units/effecttriggers/targets
                        CasterTargetComponent.class,
                        CustomTargetComponent.class,
                        SourceTargetComponent.class,
                        TargetTargetComponent.class,
                        //units/effecttriggers/triggers
                        CollisionTriggerComponent.class,
                        TargetReachedTriggerComponent.class,
                        //visuals
                        ModelComponent.class,
                        AnimationComponent.class,
                        TitleComponent.class,
                        //visuals/animations
                        FreezeAfterPlayingComponent.class,
                        LoopDurationComponent.class,
                        PassedLoopTimeComponent.class,
                        RemainingLoopsComponent.class,
                    RemovedComponentChange.class,
                    RemovedEntityChange.class,
            Message_GameInfo.class,
            Message_GameStarted.class,
            Message_GameOver.class,
            Message_PlayerAuthentification.class
        );
    }
}
