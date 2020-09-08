package amara.applications.ingame.entitysystem.systems.effects;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
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
import amara.applications.ingame.entitysystem.components.effects.popups.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.effects.units.*;
import amara.applications.ingame.entitysystem.components.effects.vision.*;
import amara.applications.ingame.entitysystem.components.effects.visuals.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.specials.erika.*;
import amara.applications.ingame.entitysystem.components.spells.placeholders.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.systems.cleanup.CleanupTemporaryTargetsUtil;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.ExpressionException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateEffectImpactSystem implements EntitySystem {

    private LinkedList<Integer> appliedEffectEntities = new LinkedList<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        appliedEffectEntities.clear();
        ExpressionSpace expressionSpace = GlobalExpressionSpace.getInstance();
        for (int effectCastEntity : getApplicableEffectCastEntities(entityWorld)) {
            EntityWrapper effectCast = entityWorld.getWrapped(effectCastEntity);
            EntityWrapper effect = entityWorld.getWrapped(effectCast.getComponent(PrepareEffectComponent.class).getEffectEntity());
            EffectSourceComponent effectSourceComponent = effectCast.getComponent(EffectSourceComponent.class);
            EffectSourceSpellComponent effectSourceSpellComponent = effectCast.getComponent(EffectSourceSpellComponent.class);
            EffectCastTargetComponent effectCastTargetComponent = effectCast.getComponent(EffectCastTargetComponent.class);
            if (doesNotHaveInvalidSource(entityWorld, effectSourceComponent, effectSourceSpellComponent)) {
                expressionSpace.clearValues();
                int effectSourceEntity = ((effectSourceComponent != null) ? effectSourceComponent.getSourceEntity() : -1);
                if (effectSourceEntity != -1) {
                    ExpressionUtil.setEntityValues(entityWorld, expressionSpace, "source", effectSourceEntity);
                }
                CustomEffectValuesComponent customEffectValuesComponent = effectCast.getComponent(CustomEffectValuesComponent.class);
                if (customEffectValuesComponent != null) {
                    expressionSpace.addValues(customEffectValuesComponent.getValues());
                }
                int[] affectedTargetEntities = effectCast.getComponent(AffectedTargetsComponent.class).getTargetEntities();
                for (int targetEntity : affectedTargetEntities) {
                    ExpressionUtil.setEntityValues(entityWorld, expressionSpace, "target", targetEntity);
                    EntityWrapper effectImpact = entityWorld.getWrapped(entityWorld.createEntity());
                    effectImpact.setComponent(effectCast.getComponent(EffectSourceActionIndexComponent.class));
                    float physicalDamage = 0;
                    float magicDamage = 0;
                    float heal = 0;
                    PhysicalDamageComponent physicalDamageComponent = effect.getComponent(PhysicalDamageComponent.class);
                    if (physicalDamageComponent != null) {
                        try {
                            expressionSpace.parse(physicalDamageComponent.getExpression());
                            physicalDamage += expressionSpace.getResult_Float();
                        } catch (ExpressionException ex) {
                        }
                    }
                    MagicDamageComponent magicDamageComponent = effect.getComponent(MagicDamageComponent.class);
                    if (magicDamageComponent != null) {
                        try {
                            expressionSpace.parse(magicDamageComponent.getExpression());
                            magicDamage += expressionSpace.getResult_Float();
                        } catch (ExpressionException ex) {
                        }
                    }
                    HealComponent healComponent = effect.getComponent(HealComponent.class);
                    if (healComponent != null) {
                        try {
                            expressionSpace.parse(healComponent.getExpression());
                            heal += expressionSpace.getResult_Float();
                        } catch (ExpressionException ex) {
                        }
                    }
                    if (effectSourceEntity != -1) {
                        if (effect.hasComponent(CanCritComponent.class)) {
                            CriticalChanceComponent criticalChanceComponent = entityWorld.getComponent(effectSourceEntity, CriticalChanceComponent.class);
                            if ((criticalChanceComponent != null) && (Math.random() < criticalChanceComponent.getValue())) {
                                physicalDamage *= 2;
                                magicDamage *= 2;
                            }
                        }
                        effectImpact.setComponent(effectSourceComponent);
                    }
                    if (physicalDamage > 0) {
                        ArmorComponent armorComponent = entityWorld.getComponent(targetEntity, ArmorComponent.class);
                        if (armorComponent != null) {
                            physicalDamage *= getResistanceDamageFactor(armorComponent.getValue());
                        }
                        IncomingDamageAmplificationComponent incomingDamageAmplificationComponent = entityWorld.getComponent(targetEntity, IncomingDamageAmplificationComponent.class);
                        if (incomingDamageAmplificationComponent != null) {
                            physicalDamage *= (1 + incomingDamageAmplificationComponent.getValue());
                        }
                        OutgoingDamageAmplificationComponent outgoingDamageAmplificationComponent = entityWorld.getComponent(effectSourceEntity, OutgoingDamageAmplificationComponent.class);
                        if (outgoingDamageAmplificationComponent != null) {
                            physicalDamage *= (1 + outgoingDamageAmplificationComponent.getValue());
                        }
                        if (physicalDamage > 0) {
                            effectImpact.setComponent(new ResultingPhysicalDamageComponent(physicalDamage));
                        }
                    }
                    if (magicDamage > 0) {
                        MagicResistanceComponent magicResistanceComponent = entityWorld.getComponent(targetEntity, MagicResistanceComponent.class);
                        if (magicResistanceComponent != null) {
                            magicDamage *= getResistanceDamageFactor(magicResistanceComponent.getValue());
                        }
                        IncomingDamageAmplificationComponent incomingDamageAmplificationComponent = entityWorld.getComponent(targetEntity, IncomingDamageAmplificationComponent.class);
                        if (incomingDamageAmplificationComponent != null) {
                            magicDamage *= (1 + incomingDamageAmplificationComponent.getValue());
                        }
                        OutgoingDamageAmplificationComponent outgoingDamageAmplificationComponent = entityWorld.getComponent(effectSourceEntity, OutgoingDamageAmplificationComponent.class);
                        if (outgoingDamageAmplificationComponent != null) {
                            magicDamage *= (1 + outgoingDamageAmplificationComponent.getValue());
                        }
                        if (magicDamage > 0) {
                            effectImpact.setComponent(new ResultingMagicDamageComponent(magicDamage));
                        }
                    }
                    if (heal != 0) {
                        effectImpact.setComponent(new ResultingHealComponent(heal));
                    }
                    AddNewBuffComponent addNewBuffComponent = effect.getComponent(AddNewBuffComponent.class);
                    if (addNewBuffComponent != null) {
                        try {
                            expressionSpace.parse(addNewBuffComponent.getTemplateExpression());
                            int buffEntity = entityWorld.createEntity();
                            EntityTemplate.loadTemplates(entityWorld, buffEntity, EntityTemplate.parseToOldTemplate(expressionSpace.getResult_String()));
                            effectImpact.setComponent(new AddBuffComponent(new int[]{buffEntity}, addNewBuffComponent.getDuration()));
                        } catch (ExpressionException ex) {
                        }
                    }
                    MoveComponent moveComponent = effect.getComponent(MoveComponent.class);
                    if (moveComponent != null) {
                        int movementEntity = entityWorld.createEntity();
                        for (Object component : entityWorld.getComponents(moveComponent.getMovementEntity())) {
                            if (component instanceof SourceMovementDirectionComponent) {
                                SourceMovementDirectionComponent sourceMovementDirectionComponent = (SourceMovementDirectionComponent) component;
                                Vector2f direction = entityWorld.getComponent(effectSourceEntity, DirectionComponent.class).getVector().clone();
                                direction.rotateAroundOrigin(sourceMovementDirectionComponent.getAngle_Radian(), true);
                                entityWorld.setComponent(movementEntity, new MovementDirectionComponent(direction));
                            } else if (component instanceof TargetedMovementDirectionComponent) {
                                TargetedMovementDirectionComponent targetedMovementDirectionComponent = (TargetedMovementDirectionComponent) component;
                                Vector2f direction = entityWorld.getComponent(effectCastTargetComponent.getTargetEntity(), DirectionComponent.class).getVector().clone();
                                direction.rotateAroundOrigin(targetedMovementDirectionComponent.getAngle_Radian(), true);
                                entityWorld.setComponent(movementEntity, new MovementDirectionComponent(direction));
                            } else if (component instanceof TowardsSourceMovementDirectionComponent) {
                                TowardsSourceMovementDirectionComponent towardsSourceMovementDirectionComponent = (TowardsSourceMovementDirectionComponent) component;
                                Vector2f sourcePosition = entityWorld.getComponent(effectSourceEntity, PositionComponent.class).getPosition();
                                Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                                Vector2f direction = sourcePosition.subtract(targetPosition).normalizeLocal();
                                direction.rotateAroundOrigin(towardsSourceMovementDirectionComponent.getAngle_Radian(), true);
                                entityWorld.setComponent(movementEntity, new MovementDirectionComponent(direction));
                            } else if (component instanceof SourceMovementTargetComponent) {
                                entityWorld.setComponent(movementEntity, new MovementTargetComponent(effectSourceEntity));
                                entityWorld.setComponent(movementEntity, new MovementTurnInDirectionComponent());
                            } else if (component instanceof TargetedMovementTargetComponent) {
                                entityWorld.setComponent(movementEntity, new MovementTargetComponent(effectCastTargetComponent.getTargetEntity()));
                                entityWorld.setComponent(movementEntity, new MovementTurnInDirectionComponent());
                            } else {
                                entityWorld.setComponent(movementEntity, component);
                            }
                        }
                        effectImpact.setComponent(new MoveComponent(movementEntity));
                    }
                    if (effect.hasComponent(TeleportToTargetPositionComponent.class)) {
                        effectImpact.setComponent(new TeleportComponent(effectCastTargetComponent.getTargetEntity()));
                    }
                    if (effectSourceSpellComponent != null) {
                        if (effect.hasComponent(TriggerCastedSpellEffectsComponent.class)) {
                            effect.setComponent(new TriggerSpellEffectsComponent(effectSourceSpellComponent.getSpellEntity()));
                        }
                        effectImpact.setComponent(effectSourceSpellComponent);
                    }
                    ReplaceSpellWithNewSpellComponent replaceSpellWithNewSpellComponent = effect.getComponent(ReplaceSpellWithNewSpellComponent.class);
                    if (replaceSpellWithNewSpellComponent != null) {
                        effectImpact.setComponent(new ReplaceSpellWithNewSpellComponent(replaceSpellWithNewSpellComponent.getSpellIndex(), replaceSpellWithNewSpellComponent.getNewSpellTemplate() + "," + effectCastTargetComponent.getTargetEntity()));
                    }
                    DisplayPlayerAnnouncementComponent displayPlayerAnnouncementComponent = effect.getComponent(DisplayPlayerAnnouncementComponent.class);
                    if (displayPlayerAnnouncementComponent != null) {
                        try {
                            expressionSpace.parse(displayPlayerAnnouncementComponent.getExpression());
                            effectImpact.setComponent(new ResultingPlayerAnnouncementComponent(expressionSpace.getResult_String(), displayPlayerAnnouncementComponent.getRemainingDuration()));
                        } catch (ExpressionException ex) {
                        }
                    }
                    AddPopupComponent addPopupComponent = effect.getComponent(AddPopupComponent.class);
                    if (addPopupComponent != null) {
                        try {
                            expressionSpace.parse(addPopupComponent.getExpression());
                            effectImpact.setComponent(new ResultingPopupComponent(expressionSpace.getResult_String()));
                        } catch (ExpressionException ex) {
                        }
                    }
                    EntityUtil.transferComponents(entityWorld, effect.getId(), effectImpact.getId(), new Class[]{
                        AddComponentsComponent.class,
                        RemoveComponentsComponent.class,
                        FinishObjectiveComponent.class,
                        AddEffectTriggersComponent.class,
                        RemoveEffectTriggersComponent.class,
                        RemoveEntityComponent.class,
                        DrawTeamAggroComponent.class,
                        SetAggroTargetComponent.class,
                        PlayAudioComponent.class,
                        StopAudioComponent.class,
                        AddBuffComponent.class,
                        RemoveBuffComponent.class,
                        AddBuffAreaComponent.class,
                        RemoveBuffAreaComponent.class,
                        AddStacksComponent.class,
                        ClearStacksComponent.class,
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
                        AddTargetabilityComponent.class,
                        RemoveTargetabilityComponent.class,
                        AddVulnerabilityComponent.class,
                        RemoveVulnerabilityComponent.class,
                        PlayCinematicComponent.class,
                        RelativeTeleportComponent.class,
                        StopComponent.class,
                        TeleportComponent.class,
                        ActivateHitboxComponent.class,
                        AddCollisionGroupsComponent.class,
                        AddIntersectionPushedComponent.class,
                        AddIntersectionPushesComponent.class,
                        DeactivateHitboxComponent.class,
                        RemoveCollisionGroupsComponent.class,
                        RemoveIntersectionPushedComponent.class,
                        RemoveIntersectionPushesComponent.class,
                        SetScaleComponent.class,
                        SwapPositionsComponent.class,
                        RemovePopupComponent.class,
                        SpawnComponent.class,
                        AddAutoAttackSpellEffectsComponent.class,
                        AddSpellSpellEffectsComponent.class,
                        EnqueueSpellCastComponent.class,
                        RemoveSpellEffectsComponent.class,
                        ReplaceSpellWithExistingSpellComponent.class,
                        ResetCooldownComponent.class,
                        TriggerSpellEffectsComponent.class,
                        AddGoldComponent.class,
                        AddShieldComponent.class,
                        CancelActionComponent.class,
                        LevelUpComponent.class,
                        RemoveAutoAggroComponent.class,
                        RespawnComponent.class,
                        SetAsRespawnTransformComponent.class,
                        SetAutoAggroComponent.class,
                        AddStealthComponent.class,
                        RemoveStealthComponent.class,
                        PlayAnimationComponent.class,
                        StopAnimationComponent.class,
                        //specials
                        TriggerErikaPassiveComponent.class
                    });
                    effectImpact.setComponent(new ApplyEffectImpactComponent(targetEntity));
                }
            }
            entityWorld.removeEntity(effectCast.getId());
            if (effectCastTargetComponent != null) {
                CleanupTemporaryTargetsUtil.tryRemoveTemporaryTargetEntity(entityWorld, effectCastTargetComponent.getTargetEntity());
            }
            appliedEffectEntities.add(effect.getId());
        }
        cleanupUnreferencedAppliedEffects(entityWorld);
    }

    public boolean hasApplicableEffectCastEntities(EntityWorld entityWorld) {
        return getApplicableEffectCastEntitiesStream(entityWorld).findAny().isPresent();
    }

    public List<Integer> getApplicableEffectCastEntities(EntityWorld entityWorld) {
        return getApplicableEffectCastEntitiesStream(entityWorld).collect(Collectors.toList());
    }

    private Stream<Integer> getApplicableEffectCastEntitiesStream(EntityWorld entityWorld) {
        return entityWorld.getEntitiesWithAny(PrepareEffectComponent.class).stream()
                .filter(effectCastEntity -> !entityWorld.hasComponent(effectCastEntity, RemainingEffectDelayComponent.class));
    }

    private boolean doesNotHaveInvalidSource(EntityWorld entityWorld, EffectSourceComponent effectSourceComponent, EffectSourceSpellComponent effectSourceSpellComponent) {
        return ((effectSourceComponent == null) || entityWorld.hasEntity(effectSourceComponent.getSourceEntity()))
            && ((effectSourceSpellComponent == null) || entityWorld.hasEntity(effectSourceSpellComponent.getSpellEntity()));
    }

    private static float getResistanceDamageFactor(float resistance) {
        if (resistance >= 0) {
            return (100 / (100 + resistance));
        } else {
            return (2 - (100 / (100 - resistance)));
        }
    }

    private void cleanupUnreferencedAppliedEffects(EntityWorld entityWorld) {
        // Remove effects that are no longer referenced, e.g. when an instant once effecttrigger was already removed, but had a delay for the effect cast
        for (int appliedEffectEntity : appliedEffectEntities) {
            boolean isReferencedInEffectTrigger = entityWorld.getEntitiesWithAny(TriggeredEffectComponent.class).stream()
                    .map(effectTriggerEntity -> entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getEffectEntity())
                    .anyMatch(effectEntity -> effectEntity == appliedEffectEntity);
            if (!isReferencedInEffectTrigger) {
                boolean isReferencedInRepeatingEffect = entityWorld.getEntitiesWithAny(RepeatingEffectComponent.class).stream()
                        .map(buffEntity -> entityWorld.getComponent(buffEntity, RepeatingEffectComponent.class).getEffectEntity())
                        .anyMatch(effectEntity -> effectEntity == appliedEffectEntity);
                if (!isReferencedInRepeatingEffect) {
                    entityWorld.removeEntity(appliedEffectEntity);
                }
            }
        }
    }
}
