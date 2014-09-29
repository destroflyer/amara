/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import amara.game.entitysystem.systems.physics.shapes.Vector2D;
import com.jme3.network.serializing.Serializer;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.messages.*;
import amara.engine.network.messages.entitysystem.*;
import amara.engine.network.messages.protocol.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.audio.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.areas.*;
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
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.effects.audio.*;
import amara.game.entitysystem.components.effects.buffs.areas.*;
import amara.game.entitysystem.components.effects.crowdcontrol.knockup.*;
import amara.game.entitysystem.components.effects.physics.*;
import amara.game.entitysystem.components.effects.spawns.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.synchronizing.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;

/**
 *
 * @author Carl
 */
public class MessagesSerializer{
    
    public static void registerClasses(){
        Serializer.registerClasses(
            Message_Ping.class,
            Message_Pong.class,
            
            Message_GetUpdateFiles.class,
            Message_GetUpdateFile.class,
            Message_UpdateFilePart.class,
            Message_UpdateFiles.class,
                UpdateFile.class,
            
            Message_EditActiveCharacterSkin.class,
            Message_EditCharacterInventory.class,
            Message_EditUserMeta.class,
            Message_GameContents.class,
                GameCharacter.class,
                    GameCharacterSkin.class,
                Item.class,
            Message_GetGameContents.class,
            Message_GetPlayerProfileData.class,
            Message_GetPlayerStatus.class,
            Message_Login.class,
                AuthentificationInformation.class,
            Message_LoginResult.class,
            Message_Logout.class,
            Message_OwnedCharacters.class,
                OwnedGameCharacter.class,
            Message_OwnedItems.class,
                OwnedItem.class,
            Message_PlayerProfileData.class,
                PlayerProfileData.class,
            Message_PlayerProfileDataNotExistant.class,
            Message_PlayerStatus.class,
            
            Message_CreateLobby.class,
            Message_SetLobbyData.class,
                Lobby.class,
                LobbyPlayer.class,
                LobbyData.class,
            Message_SetLobbyPlayerData.class,
                LobbyPlayerData.class,
            Message_InviteLobbyPlayer.class,
            Message_LeaveLobby.class,
            Message_LobbyClosed.class,
            Message_KickLobbyPlayer.class,
            Message_LobbyUpdate.class,
            Message_StartGame.class,
            Message_GameCreated.class,
            
            Message_SendChatMessage.class,
            Message_ChatMessage.class,
            Message_Command.class,
                AutoAttackCommand.class,
                Command.class,
                MoveCommand.class,
                StopCommand.class,
                CastLinearSkillshotSpellCommand.class,
                CastPositionalSkillshotSpellCommand.class,
                CastSelfcastSpellCommand.class,
                CastSingleTargetSpellCommand.class,
                    SpellIndex.class,
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
                        BaseHealthRegenerationComponent.class,
                        BaseMaximumHealthComponent.class,
                        BaseWalkSpeedComponent.class,
                        BonusFlatAbilityPowerComponent.class,
                        BonusFlatAttackDamageComponent.class,
                        BonusFlatHealthRegenerationComponent.class,
                        BonusFlatMaximumHealthComponent.class,
                        BonusFlatWalkSpeedComponent.class,
                        BonusPercentageAttackSpeedComponent.class,
                        BonusPercentageWalkSpeedComponent.class,
                        HealthComponent.class,
                        HealthRegenerationComponent.class,
                        MaximumHealthComponent.class,
                        RequestUpdateAttributesComponent.class,
                        WalkSpeedComponent.class,
                        //audio
                        AudioComponent.class,
                        AudioLoopComponent.class,
                        AudioSourceComponent.class,
                        AudioSuccessorComponent.class,
                        AudioVolumeComponent.class,
                        IsAudioPausedComponent.class,
                        IsAudioPlayingComponent.class,
                        //buffs
                        ContinuousEffectComponent.class,
                        OnBuffRemoveEffectTriggersComponent.class,
                        RepeatingEffectComponent.class,
                        //buffs/areas
                        AreaBuffComponent.class,
                        AreaBuffTargetRulesComponent.class,
                        AreaOriginComponent.class,
                        AreaSourceComponent.class,
                        //buffs/status
                        ActiveBuffComponent.class,
                        BuffVisualisationComponent.class,
                        RemainingBuffDurationComponent.class,
                        RemoveFromTargetComponent.class,
                        TimeSinceLastRepeatingEffectComponent.class,
                        //camps
                        CampHealthResetComponent.class,
                        CampMaximumAggroDistanceComponent.class,
                        CampTransformComponent.class,
                        //effects
                        AffectedTargetsComponent.class,
                        ApplyEffectImpactComponent.class,
                        PrepareEffectComponent.class,
                        RemainingEffectDelayComponent.class,
                        //effects/audio
                        PauseAudioComponent.class,
                        PlayAudioComponent.class,
                        StopAudioComponent.class,
                        //effects/buffs
                        AddBuffComponent.class,
                        RemoveBuffComponent.class,
                        //effects/buffs/areas
                        AddBuffAreaComponent.class,
                        RemoveBuffAreaComponent.class,
                        //effects/casts
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
                        //effects/crowdcontrol/knockup
                        KnockupDurationComponent.class,
                        KnockupHeightComponent.class,
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
                        RemoveComponentsComponent.class,
                        RemoveEffectTriggersComponent.class,
                        RemoveEntityComponent.class,
                        //effects/heal
                        FlatHealComponent.class,
                        HealComponent.class,
                        //effects/movement,
                        MoveComponent.class,
                        StopComponent.class,
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
                        //general
                        DescriptionComponent.class,
                        NameComponent.class,
                        //input
                        CastSpellComponent.class,
                        //input/casts
                        TargetComponent.class,
                        //items
                        InventoryComponent.class,
                        ItemActiveComponent.class,
                        ItemVisualisationComponent.class,
                        //maps
                        MapObjectiveComponent.class,
                        PlayerDeathRulesComponent.class,
                        //maps/playerdeathrules
                        RespawnPlayersComponent.class,
                        RespawnTimerComponent.class,
                        //movements
                        DisplacementComponent.class,
                        DistanceLimitComponent.class,
                        MovedDistanceComponent.class,
                        MovementAnimationComponent.class,
                        MovementDirectionComponent.class,
                        MovementIsCancelableComponent.class,
                        MovementSpeedComponent.class,
                        MovementTargetComponent.class,
                        MovementTargetReachedComponent.class,
                        MovementTargetSufficientDistanceComponent.class,
                        WalkMovementComponent.class,
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
                            ConvexShape.class,
                            SimpleConvexPolygon.class,
                            Transform2D.class,
                            Vector2D.class,
                            PolygonShape.class,
                                BoundRectangle.class,
                                Polygon.class,
                                    SetPolygon.class,
                                    HolePolygon.class,
                                    SimplePolygon.class,
                        MovementComponent.class,
                        PositionComponent.class,
                        ScaleComponent.class,
                        RemoveOnMapLeaveComponent.class,
                        //players
                        ClientComponent.class,
                        PlayerIndexComponent.class,
                        RespawnComponent.class,
                        SelectedUnitComponent.class,
                        WaitingToRespawnComponent.class,
                        //spawns
                        RelativeSpawnPositionComponent.class,
                        SpawnAttackMoveComponent.class,
                        SpawnMovementAnimationComponent.class,
                        SpawnMovementSpeedComponent.class,
                        SpawnMoveToTargetComponent.class,
                        SpawnTemplateComponent.class,
                        //spells
                        ApplyCastedSpellComponent.class,
                        CastAnimationComponent.class,
                        CastCancelableComponent.class,
                        CastCancelActionComponent.class,
                        CastDurationComponent.class,
                        CastTypeComponent.class,
                        CooldownComponent.class,
                        InstantEffectTriggersComponent.class,
                        RangeComponent.class,
                        RemainingCooldownComponent.class,
                        SpellTargetRulesComponent.class,
                        SpellVisualisationComponent.class,
                        StopAfterCastingComponent.class,
                        StopBeforeCastingComponent.class,
                        //spells/placeholders
                        SourceMovementDirectionComponent.class,
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
                        AggroTargetComponent.class,
                        AttackMoveComponent.class,
                        AutoAggroComponent.class,
                        AutoAttackComponent.class,
                        CampComponent.class,
                        CastSpellOnCooldownWhileAttackingComponent.class,
                        CurrentActionEffectCastsComponent.class,
                        IntersectionRulesComponent.class,
                        IsAliveComponent.class,
                        IsCastingComponent.class,
                        IsTargetableComponent.class,
                        IsVulnerableComponent.class,
                        LifetimeComponent.class,
                        MovementComponent.class,
                        ResetCampComponent.class,
                        SpellsComponent.class,
                        TargetsInAggroRangeComponent.class,
                        TeamComponent.class,
                        WalkStepDistanceComponent.class,
                        //units/animations
                        AutoAttackAnimationComponent.class,
                        DeathAnimationComponent.class,
                        IdleAnimationComponent.class,
                        WalkAnimationComponent.class,
                        //units/crowdcontrol
                        IsBindedComponent.class,
                        IsBindedImmuneComponent.class,
                        IsKnockupedComponent.class,
                        IsKnockupedImmuneComponent.class,
                        IsSilencedComponent.class,
                        IsSilencedImmuneComponent.class,
                        IsStunnedComponent.class,
                        IsStunnedImmuneComponent.class,
                        //units/effecttriggers
                        TriggerDelayComponent.class,
                        TriggeredEffectComponent.class,
                        TriggerOnCancelComponent.class,
                        TriggerOnceComponent.class,
                        TriggerSourceComponent.class,
                        TriggerTemporaryComponent.class,
                        //units/effecttriggers/targets
                        CasterTargetComponent.class,
                        CustomTargetComponent.class,
                        SourceTargetComponent.class,
                        TargetTargetComponent.class,
                        //units/effecttriggers/triggers
                        CastingFinishedTriggerComponent.class,
                        CollisionTriggerComponent.class,
                        RepeatingTriggerComponent.class,
                        RepeatingTriggerCounterComponent.class,
                        TargetReachedTriggerComponent.class,
                        TimeSinceLastRepeatTriggerComponent.class,
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
