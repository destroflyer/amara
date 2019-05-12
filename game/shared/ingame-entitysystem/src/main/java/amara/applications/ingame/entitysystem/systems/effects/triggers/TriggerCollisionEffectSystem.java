/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersection.*;

/**
 *
 * @author Philipp
 */
public class TriggerCollisionEffectSystem implements EntitySystem{

    public TriggerCollisionEffectSystem(IntersectionInformant intersectionInformant){
        this.intersectionInformant = intersectionInformant;
    }
    private IntersectionInformant intersectionInformant;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        IntersectionTracker<Pair<Integer>> tracker = intersectionInformant.getTracker(entityWorld, this);
        for(Pair<Integer> pair : tracker.getEntries()){
            checkEffectTriggers(entityWorld, pair.getA(), pair.getB());
            checkEffectTriggers(entityWorld, pair.getB(), pair.getA());
        }
    }
    
    private void checkEffectTriggers(EntityWorld entityWorld, int effectingEntity, int targetEntity){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class)){
            int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if((triggerSourceEntity == effectingEntity)
            && entityWorld.hasComponent(effectTriggerEntity, CollisionTriggerComponent.class)
            && (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class))){
                boolean triggerEffect = true;
                IntersectionRulesComponent intersectionRulesComponent = entityWorld.getComponent(effectingEntity, IntersectionRulesComponent.class);
                if(intersectionRulesComponent != null){
                    triggerEffect = TargetUtil.isValidTarget(entityWorld, effectingEntity, targetEntity, intersectionRulesComponent.getTargetRulesEntity());
                }
                if(triggerEffect){
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
                }
            }
        }
    }
}
