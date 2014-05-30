/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.*;
import amara.game.entitysystem.systems.physics.intersection.*;
import amara.game.entitysystem.systems.targets.TargetUtil;

/**
 *
 * @author Philipp
 */
public class TriggerCollisionEffectSystem implements EntitySystem{

    public TriggerCollisionEffectSystem(IntersectionInformant info){
        this.info = info;
    }
    private IntersectionInformant info;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        IntersectionTracker<Pair<Integer>> tracker = info.getTracker(entityWorld, this);
        for(Pair<Integer> pair: tracker.getEntries()){
            checkEffectTriggers(entityWorld, pair.getA(), pair.getB());
            checkEffectTriggers(entityWorld, pair.getB(), pair.getA());
        }
    }
    
    private void checkEffectTriggers(EntityWorld entityWorld, int effectingEntity, int targetEntity){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class)){
            int triggerSourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if(triggerSourceEntity == effectingEntity){
                if(entityWorld.hasComponent(effectTriggerEntity, CollisionTriggerComponent.class)){
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
}
