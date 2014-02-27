/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effects.*;
import amara.game.entitysystem.components.units.intersections.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
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
            if(entityWorld.hasComponent(pair.getA(), CollisionTriggerEffectComponent.class)){
                applyEffect(entityWorld, pair.getA(), pair.getB());
            }
            if(entityWorld.hasComponent(pair.getB(), CollisionTriggerEffectComponent.class)){
                applyEffect(entityWorld, pair.getB(), pair.getA());
            }
        }
    }
    
    private void applyEffect(EntityWorld entityWorld, int effectingEntity, int targetEntity){
        EntityWrapper intersectionRules = entityWorld.getWrapped(entityWorld.getComponent(effectingEntity, IntersectionRulesComponent.class).getRulesEntityID());
        if(entityWorld.hasComponent(targetEntity, IsTargetableComponent.class)){
            boolean triggerEffect;
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
                EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
                int effectEntity = entityWorld.getComponent(effectingEntity, CollisionTriggerEffectComponent.class).getEffectEntityID();
                effectCast.setComponent(new PrepareEffectComponent(effectEntity));
                CastSourceComponent castSourceComponent = entityWorld.getComponent(effectingEntity, CastSourceComponent.class);
                if(castSourceComponent != null){
                    effectCast.setComponent(new EffectSourceComponent(castSourceComponent.getSourceEntitiyID()));
                }
                effectCast.setComponent(new AffectedTargetsComponent(new int[]{targetEntity}));
                entityWorld.removeEntity(effectingEntity);
            }
        }
    }
}
