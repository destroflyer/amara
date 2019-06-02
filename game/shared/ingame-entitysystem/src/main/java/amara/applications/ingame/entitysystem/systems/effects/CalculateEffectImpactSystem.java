/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import amara.applications.ingame.entitysystem.components.effects.popups.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.effects.units.*;
import amara.applications.ingame.entitysystem.components.effects.vision.*;
import amara.applications.ingame.entitysystem.components.effects.visuals.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.specials.erika.*;
import amara.applications.ingame.entitysystem.components.spells.placeholders.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.expressions.*;
import amara.libraries.expressions.exceptions.ExpressionException;

import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class CalculateEffectImpactSystem implements EntitySystem{

    private LinkedList<Integer> appliedEffectEntities = new LinkedList<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        appliedEffectEntities.clear();
        ExpressionSpace expressionSpace = GlobalExpressionSpace.getInstance();
        for(EntityWrapper effectCast : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(PrepareEffectComponent.class))){
            if(!effectCast.hasComponent(RemainingEffectDelayComponent.class)){
                EntityWrapper effect = entityWorld.getWrapped(effectCast.getComponent(PrepareEffectComponent.class).getEffectEntity());
                EffectCastSourceComponent effectCastSourceComponent = effectCast.getComponent(EffectCastSourceComponent.class);
                EffectCastSourceSpellComponent effectCastSourceSpellComponent = effectCast.getComponent(EffectCastSourceSpellComponent.class);
                EffectCastTargetComponent effectCastTargetComponent = effectCast.getComponent(EffectCastTargetComponent.class);
                boolean removeTemporaryEffectCastTargets = false;
                expressionSpace.clearValues();
                int effectSourceEntity = ((effectCastSourceComponent != null) ? effectCastSourceComponent.getSourceEntity() : -1);
                if(effectSourceEntity != -1){
                    ExpressionUtil.setEntityValues(entityWorld, expressionSpace, "source", effectSourceEntity);
                }
                CustomEffectValuesComponent customEffectValuesComponent = effectCast.getComponent(CustomEffectValuesComponent.class);
                if(customEffectValuesComponent != null){
                    expressionSpace.addValues(customEffectValuesComponent.getValues());
                }
                int[] affectedTargetEntities = effectCast.getComponent(AffectedTargetsComponent.class).getTargetEntities();
                for(int i=0;i<affectedTargetEntities.length;i++){
                    int targetEntity = affectedTargetEntities[i];
                    ExpressionUtil.setEntityValues(entityWorld, expressionSpace, "target", targetEntity);
                    EntityWrapper effectImpact = entityWorld.getWrapped(entityWorld.createEntity());
                    float physicalDamage = 0;
                    float magicDamage = 0;
                    float heal = 0;
                    PhysicalDamageComponent physicalDamageComponent = effect.getComponent(PhysicalDamageComponent.class);
                    if(physicalDamageComponent != null){
                        try{
                            expressionSpace.parse(physicalDamageComponent.getExpression());
                            physicalDamage += expressionSpace.getResult_Float();
                        }catch(ExpressionException ex){
                        }
                    }
                    MagicDamageComponent magicDamageComponent = effect.getComponent(MagicDamageComponent.class);
                    if(magicDamageComponent != null){
                        try{
                            expressionSpace.parse(magicDamageComponent.getExpression());
                            magicDamage += expressionSpace.getResult_Float();
                        }catch(ExpressionException ex){
                        }
                    }
                    HealComponent healComponent = effect.getComponent(HealComponent.class);
                    if(healComponent != null){
                        try{
                            expressionSpace.parse(healComponent.getExpression());
                            heal += expressionSpace.getResult_Float();
                        }catch(ExpressionException ex){
                        }
                    }
                    if(effectSourceEntity != -1){
                        if(effect.hasComponent(CanCritComponent.class)){
                            CriticalChanceComponent criticalChanceComponent = entityWorld.getComponent(effectSourceEntity, CriticalChanceComponent.class);
                            if((criticalChanceComponent != null) && (Math.random() < criticalChanceComponent.getValue())){
                                physicalDamage *= 2;
                                magicDamage *= 2;
                            }
                        }
                        effectImpact.setComponent(effectCastSourceComponent);
                    }
                    if(physicalDamage != 0){
                        ArmorComponent armorComponent = entityWorld.getComponent(targetEntity, ArmorComponent.class);
                        if(armorComponent != null){
                            physicalDamage *= getResistanceDamageFactor(armorComponent.getValue());
                        }
                        IncomingDamageAmplificationComponent incomingDamageAmplificationComponent = entityWorld.getComponent(targetEntity, IncomingDamageAmplificationComponent.class);
                        if(incomingDamageAmplificationComponent != null){
                            physicalDamage *= (1 + incomingDamageAmplificationComponent.getValue());
                        }
                        OutgoingDamageAmplificationComponent outgoingDamageAmplificationComponent = entityWorld.getComponent(effectSourceEntity, OutgoingDamageAmplificationComponent.class);
                        if(outgoingDamageAmplificationComponent != null){
                            physicalDamage *= (1 + outgoingDamageAmplificationComponent.getValue());
                        }
                        effectImpact.setComponent(new ResultingPhysicalDamageComponent(physicalDamage));
                    }
                    if(magicDamage != 0){
                        MagicResistanceComponent magicResistanceComponent = entityWorld.getComponent(targetEntity, MagicResistanceComponent.class);
                        if(magicResistanceComponent != null){
                            magicDamage *= getResistanceDamageFactor(magicResistanceComponent.getValue());
                        }
                        IncomingDamageAmplificationComponent incomingDamageAmplificationComponent = entityWorld.getComponent(targetEntity, IncomingDamageAmplificationComponent.class);
                        if(incomingDamageAmplificationComponent != null){
                            magicDamage *= (1 + incomingDamageAmplificationComponent.getValue());
                        }
                        OutgoingDamageAmplificationComponent outgoingDamageAmplificationComponent = entityWorld.getComponent(effectSourceEntity, OutgoingDamageAmplificationComponent.class);
                        if(outgoingDamageAmplificationComponent != null){
                            magicDamage *= (1 + outgoingDamageAmplificationComponent.getValue());
                        }
                        effectImpact.setComponent(new ResultingMagicDamageComponent(magicDamage));
                    }
                    if(heal != 0){
                        effectImpact.setComponent(new ResultingHealComponent(heal));
                    }
                    AddNewBuffComponent addNewBuffComponent = effect.getComponent(AddNewBuffComponent.class);
                    if(addNewBuffComponent != null){
                        try{
                            expressionSpace.parse(addNewBuffComponent.getTemplateExpression());
                            int buffEntity = entityWorld.createEntity();
                            EntityTemplate.loadTemplates(entityWorld, buffEntity, EntityTemplate.parseToOldTemplate(expressionSpace.getResult_String()));
                            effectImpact.setComponent(new AddBuffComponent(buffEntity, addNewBuffComponent.getDuration()));
                        }catch(ExpressionException ex){
                        }
                    }
                    MoveComponent moveComponent = effect.getComponent(MoveComponent.class);
                    if(moveComponent != null){
                        int movementEntity = entityWorld.createEntity();
                        for(Object component : entityWorld.getComponents(moveComponent.getMovementEntity())){
                            if(component instanceof SourceMovementDirectionComponent){
                                SourceMovementDirectionComponent sourceMovementDirectionComponent = (SourceMovementDirectionComponent) component;
                                Vector2f direction = entityWorld.getComponent(effectCastSourceComponent.getSourceEntity(), DirectionComponent.class).getVector().clone();
                                direction.rotateAroundOrigin(sourceMovementDirectionComponent.getAngle_Radian(), true);
                                entityWorld.setComponent(movementEntity, new MovementDirectionComponent(direction));
                            }
                            else if(component instanceof TargetedMovementDirectionComponent){
                                TargetedMovementDirectionComponent targetedMovementDirectionComponent = (TargetedMovementDirectionComponent) component;
                                Vector2f direction = entityWorld.getComponent(effectCastTargetComponent.getTargetEntity(), DirectionComponent.class).getVector().clone();
                                direction.rotateAroundOrigin(targetedMovementDirectionComponent.getAngle_Radian(), true);
                                entityWorld.setComponent(movementEntity, new MovementDirectionComponent(direction));
                                removeTemporaryEffectCastTargets = true;
                            }
                            else if(component instanceof TargetedMovementTargetComponent){
                                entityWorld.setComponent(movementEntity, new MovementTargetComponent(effectCastTargetComponent.getTargetEntity()));
                            }
                            else{
                                entityWorld.setComponent(movementEntity, component);
                            }
                        }
                        effectImpact.setComponent(new MoveComponent(movementEntity));
                    }
                    if(effect.hasComponent(TeleportToTargetPositionComponent.class)){
                        effectImpact.setComponent(new TeleportComponent(effectCastTargetComponent.getTargetEntity()));
                    }
                    if(effectCastSourceSpellComponent != null){
                        if(effect.hasComponent(TriggerCastedSpellEffectsComponent.class)){
                            effect.setComponent(new TriggerSpellEffectsComponent(effectCastSourceSpellComponent.getSpellEntity()));
                        }
                        effectImpact.setComponent(effectCastSourceSpellComponent);
                    }
                    ReplaceSpellWithNewSpellComponent replaceSpellWithNewSpellComponent = effect.getComponent(ReplaceSpellWithNewSpellComponent.class);
                    if(replaceSpellWithNewSpellComponent != null){
                        effectImpact.setComponent(new ReplaceSpellWithNewSpellComponent(replaceSpellWithNewSpellComponent.getSpellIndex(), replaceSpellWithNewSpellComponent.getNewSpellTemplate() + "," + effectCastTargetComponent.getTargetEntity()));
                    }
                    EntityUtil.transferComponents(effect, effectImpact, new Class[] {
                        AddComponentsComponent.class,
                        RemoveComponentsComponent.class,
                        FinishObjectiveComponent.class,
                        AddEffectTriggersComponent.class,
                        RemoveEffectTriggersComponent.class,
                        RemoveEntityComponent.class,
                        DrawTeamAggroComponent.class,
                        PlayAudioComponent.class,
                        StopAudioComponent.class,
                        AddBuffComponent.class,
                        RemoveBuffComponent.class,
                        AddBuffAreaComponent.class,
                        RemoveBuffAreaComponent.class,
                        AddStacksComponent.class,
                        ClearStacksComponent.class,
                        RemoveStacksComponent.class,
                        AddBindingComponent.class,
                        RemoveBindingComponent.class,
                        AddBindingImmuneComponent.class,
                        AddSilenceComponent.class,
                        RemoveSilenceComponent.class,
                        AddSilenceImmuneComponent.class,
                        AddStunComponent.class,
                        RemoveStunComponent.class,
                        AddStunImmuneComponent.class,
                        AddKnockupComponent.class,
                        RemoveKnockupComponent.class,
                        AddKnockupImmuneComponent.class,
                        AddTargetabilityComponent.class,
                        RemoveTargetabilityComponent.class,
                        AddVulnerabilityComponent.class,
                        RemoveVulnerabilityComponent.class,
                        PlayCinematicComponent.class,
                        StopComponent.class,
                        TeleportComponent.class,
                        ActivateHitboxComponent.class,
                        AddCollisionGroupsComponent.class,
                        DeactivateHitboxComponent.class,
                        RemoveCollisionGroupsComponent.class,
                        AddPopupComponent.class,
                        RemovePopupComponent.class,
                        SpawnComponent.class,
                        AddAutoAttackSpellEffectsComponent.class,
                        AddSpellsSpellEffectsComponent.class,
                        RemoveSpellEffectsComponent.class,
                        ReplaceSpellWithExistingSpellComponent.class,
                        TriggerSpellEffectsComponent.class,
                        AddGoldComponent.class,
                        CancelActionComponent.class,
                        RespawnComponent.class,
                        AddStealthComponent.class,
                        RemoveStealthComponent.class,
                        PlayAnimationComponent.class,
                        StopAnimationComponent.class,
                        //specials
                        TriggerErikaPassiveComponent.class
                    });
                    effectImpact.setComponent(new ApplyEffectImpactComponent(targetEntity));
                }
                entityWorld.removeEntity(effectCast.getId());
                if(removeTemporaryEffectCastTargets){
                    if(entityWorld.hasComponent(effectCastTargetComponent.getTargetEntity(), TemporaryComponent.class)){
                        entityWorld.removeEntity(effectCastTargetComponent.getTargetEntity());
                    }
                }
                appliedEffectEntities.add(effect.getId());
            }
        }
        cleanupUnreferencedAppliedEffects(entityWorld);
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
            boolean isReferencedInEffectTrigger = entityWorld.getEntitiesWithAll(TriggeredEffectComponent.class).stream()
                    .map(effectTriggerEntity -> entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getEffectEntity())
                    .anyMatch(effectEntity -> effectEntity == appliedEffectEntity);
            if (!isReferencedInEffectTrigger) {
                boolean isReferencedInRepeatingEffect = entityWorld.getEntitiesWithAll(RepeatingEffectComponent.class).stream()
                        .map(buffEntity -> entityWorld.getComponent(buffEntity, RepeatingEffectComponent.class).getEffectEntity())
                        .anyMatch(effectEntity -> effectEntity == appliedEffectEntity);
                if (!isReferencedInRepeatingEffect) {
                    entityWorld.removeEntity(appliedEffectEntity);
                }
            }
        }
    }
}
