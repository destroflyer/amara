/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.units.intersections.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.*;
import amara.game.entitysystem.systems.physics.intersection.*;

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
        for(Pair<Integer> pair: info.getEntries()){
            checkEffectTriggers(entityWorld, pair.getA(), pair.getB());
            checkEffectTriggers(entityWorld, pair.getB(), pair.getA());
        }
    }
    
    private void checkEffectTriggers(EntityWorld entityWorld, int effectingEntity, int targetEntity){
        EffectTriggersComponent effectTriggersComponent = entityWorld.getComponent(effectingEntity, EffectTriggersComponent.class);
        if(effectTriggersComponent != null){
            boolean wasTriggered = false;
            for(int effectTriggerEntity : effectTriggersComponent.getEffectTriggerEntities()){
                if(entityWorld.hasComponent(effectTriggerEntity, CollisionTriggerComponent.class)){
                    if(onEffectTriggered(entityWorld, effectingEntity, targetEntity, effectTriggerEntity)){
                        wasTriggered = true;
                    }
                }
            }
            if(wasTriggered){
                entityWorld.removeEntity(effectingEntity);
            }
        }
    }
    
    private boolean onEffectTriggered(EntityWorld entityWorld, int effectingEntity, int targetEntity, int effectTriggerEntity){
        boolean triggerEffect = false;
        EntityWrapper intersectionRules = entityWorld.getWrapped(entityWorld.getComponent(effectingEntity, IntersectionRulesComponent.class).getRulesEntityID());
        if(entityWorld.hasComponent(targetEntity, IsTargetableComponent.class)){
            TeamComponent effectTeamComponent = entityWorld.getComponent(effectingEntity, TeamComponent.class);
            TeamComponent targetTeamComponent = entityWorld.getComponent(targetEntity, TeamComponent.class);
            if((effectTeamComponent != null) && (targetTeamComponent != null)){
                triggerEffect = false;
                if(intersectionRules.getComponent(AcceptAlliesComponent.class) != null){
                    triggerEffect |= (effectTeamComponent.getTeamEntityID() == targetTeamComponent.getTeamEntityID());
                }
                if(intersectionRules.getComponent(AcceptEnemiesComponent.class) != null){
                    triggerEffect |= (effectTeamComponent.getTeamEntityID() != targetTeamComponent.getTeamEntityID());
                }
            }
            else{
                triggerEffect = true;
            }
            if(triggerEffect){
                EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
            }
        }
        return triggerEffect;
    }
}
