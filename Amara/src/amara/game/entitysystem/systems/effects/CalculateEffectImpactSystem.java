/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
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
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.specials.erika.*;
import amara.game.entitysystem.components.spells.placeholders.*;
import amara.game.expressions.*;
import amara.game.expressions.exceptions.*;

/**
 *
 * @author Carl
 */
public class CalculateEffectImpactSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ExpressionSpace expressionSpace = GlobalExpressionSpace.getInstance();
        for(EntityWrapper effectCast : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(PrepareEffectComponent.class))){
            if(!effectCast.hasComponent(RemainingEffectDelayComponent.class)){
                EntityWrapper effect = entityWorld.getWrapped(effectCast.getComponent(PrepareEffectComponent.class).getEffectEntity());
                EffectCastSourceComponent effectCastSourceComponent = effectCast.getComponent(EffectCastSourceComponent.class);
                EffectCastSourceSpellComponent effectCastSourceSpellComponent = effectCast.getComponent(EffectCastSourceSpellComponent.class);
                EffectCastTargetComponent effectCastTargetComponent = effectCast.getComponent(EffectCastTargetComponent.class);
                expressionSpace.clearValues();
                int effectSourceEntity = ((effectCastSourceComponent != null)?effectCastSourceComponent.getSourceEntity():-1);
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
                        float armor = 0;
                        ArmorComponent armorComponent = entityWorld.getComponent(targetEntity, ArmorComponent.class);
                        if(armorComponent != null){
                            armor = armorComponent.getValue();
                        }
                        physicalDamage *= getResistanceDamageFactor(armor);
                        effectImpact.setComponent(new ResultingPhysicalDamageComponent(physicalDamage));
                    }
                    if(magicDamage != 0){
                        float magicResistance = 0;
                        MagicResistanceComponent magicResistanceComponent = entityWorld.getComponent(targetEntity, MagicResistanceComponent.class);
                        if(magicResistanceComponent != null){
                            magicResistance = magicResistanceComponent.getValue();
                        }
                        magicDamage *= getResistanceDamageFactor(magicResistance);
                        effectImpact.setComponent(new ResultingMagicDamageComponent(magicDamage));
                    }
                    if(heal != 0){
                        effectImpact.setComponent(new ResultingHealComponent(heal));
                    }
                    AddNewBuffComponent addNewBuffComponent = effect.getComponent(AddNewBuffComponent.class);
                    if(addNewBuffComponent != null){
                        try{
                            expressionSpace.parse(addNewBuffComponent.getTemplateExpression());
                            EntityWrapper buff = EntityTemplate.createFromTemplate(entityWorld, EntityTemplate.parseToOldTemplate(expressionSpace.getResult_String()));
                            effectImpact.setComponent(new AddBuffComponent(buff.getId(), addNewBuffComponent.getDuration()));
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
                    EntityUtil.transferComponents(effect, effectImpact, new Class[]{
                        AddComponentsComponent.class,
                        RemoveComponentsComponent.class,
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
                        ActivateHitboxComponent.class,
                        DeactivateHitboxComponent.class,
                        SpawnComponent.class,
                        AddAutoAttackSpellEffectsComponent.class,
                        AddSpellsSpellEffectsComponent.class,
                        RemoveSpellEffectsComponent.class,
                        ReplaceSpellWithExistingSpellComponent.class,
                        TriggerSpellEffectsComponent.class,
                        PlayAnimationComponent.class,
                        StopAnimationComponent.class,
                        //specials
                        TriggerErikaPassiveComponent.class
                    });
                    effectImpact.setComponent(new ApplyEffectImpactComponent(targetEntity));
                }
                entityWorld.removeEntity(effectCast.getId());
            }
        }
    }
    
    public static float getResistanceDamageFactor(float resistance){
        if(resistance >= 0){
            return (100 / (100 + resistance));
        }
        else{
            return (2 - (100 / (100 - resistance)));
        }
    }
}
