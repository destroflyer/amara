/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.spells.*;

/**
 *
 * @author Carl
 */
public class CalculateSpellEffectSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(ApplyCastedSpellComponent.class))
        {
            ApplyCastedSpellComponent castedSpellComponent = entityWorld.getCurrent().getComponent(entity, ApplyCastedSpellComponent.class);
            EntityWrapper spellCaster = entityWorld.getWrapped(castedSpellComponent.getCasterEntityID());
            EntityWrapper spellInstantCastEffect = entityWorld.getWrapped(entityWorld.getCurrent().getComponent(castedSpellComponent.getCastedSpellEntityID(), InstantSpellEffectComponent.class).getEffectEntityID());
            int[] affectedTargetEntitesIDs = entityWorld.getCurrent().getComponent(entity, AffectedTargetsComponent.class).getTargetEntitiesIDs();
            for(int i=0;i<affectedTargetEntitesIDs.length;i++){
                EntityWrapper targetEntity = entityWorld.getWrapped(affectedTargetEntitesIDs[i]);
                EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
                float physicalDamage = 0;
                float magicDamage = 0;
                FlatPhysicalDamageComponent flatPhysicalDamageComponent = spellInstantCastEffect.getComponent(FlatPhysicalDamageComponent.class);
                if(flatPhysicalDamageComponent != null){
                    physicalDamage += flatPhysicalDamageComponent.getValue();
                }
                FlatMagicDamageComponent flatMagicDamageComponent = spellInstantCastEffect.getComponent(FlatMagicDamageComponent.class);
                if(flatMagicDamageComponent != null){
                    magicDamage += flatMagicDamageComponent.getValue();
                }
                ScalingAbilityPowerMagicDamageComponent scalingAbilityPowerMagicDamageComponent = spellInstantCastEffect.getComponent(ScalingAbilityPowerMagicDamageComponent.class);
                if(scalingAbilityPowerMagicDamageComponent != null){
                    magicDamage += (spellCaster.getComponent(AbilityPowerComponent.class).getValue() * scalingAbilityPowerMagicDamageComponent.getRatio());
                }
                ScalingAttackDamagePhysicalDamageComponent scalingAttackDamagePhysicalDamageComponent = spellInstantCastEffect.getComponent(ScalingAttackDamagePhysicalDamageComponent.class);
                if(scalingAttackDamagePhysicalDamageComponent != null){
                    physicalDamage += (spellCaster.getComponent(AttackDamageComponent.class).getValue() * scalingAttackDamagePhysicalDamageComponent.getRatio());
                }
                if(physicalDamage != 0){
                    spellEffect.setComponent(new PhysicalDamageComponent(physicalDamage));
                }
                if(magicDamage != 0){
                    spellEffect.setComponent(new MagicDamageComponent(magicDamage));
                }
                BindingComponent bindingComponent = spellInstantCastEffect.getComponent(BindingComponent.class);
                if(bindingComponent != null){
                    spellEffect.setComponent(bindingComponent);
                }
                SilenceComponent silenceComponent = spellInstantCastEffect.getComponent(SilenceComponent.class);
                if(silenceComponent != null){
                    spellEffect.setComponent(silenceComponent);
                }
                StunComponent stunComponent = spellInstantCastEffect.getComponent(StunComponent.class);
                if(stunComponent != null){
                    spellEffect.setComponent(stunComponent);
                }
                spellEffect.setComponent(new ApplyEffectComponent(targetEntity.getId()));
            }
            entityWorld.removeEntity(entity);
        }
    }
}
