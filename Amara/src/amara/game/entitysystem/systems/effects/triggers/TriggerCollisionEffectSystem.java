/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.spawns.CastSourceComponent;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effects.*;
import amara.game.entitysystem.components.units.intersections.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.game.entitysystem.systems.physics.intersection.*;
import amara.game.entitysystem.systems.physics.shapes.*;

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
            if(entityWorld.hasAllComponents(pair.getA(), CollisionTriggerEffectComponent.class)){
                applyDamage(entityWorld, pair.getA(), pair.getB());
            }
            if(entityWorld.hasAllComponents(pair.getB(), CollisionTriggerEffectComponent.class)){
                applyDamage(entityWorld, pair.getB(), pair.getA());
            }
        }
    }
    
    private void applyDamage(EntityWorld entityWorld, int damageEntity, int damagedEntity){
        EntityWrapper intersectionRules = entityWorld.getWrapped(entityWorld.getComponent(damageEntity, IntersectionRulesComponent.class).getRulesEntityID());
        if(entityWorld.hasComponent(damagedEntity, IsTargetableComponent.class)){
            boolean triggerEffect;
            TeamComponent damageTeamComponent = entityWorld.getComponent(damageEntity, TeamComponent.class);
            TeamComponent damagedTeamComponent = entityWorld.getComponent(damagedEntity, TeamComponent.class);
            if((damageTeamComponent != null) && (damagedTeamComponent != null)){
                triggerEffect = false;
                if(intersectionRules.getComponent(AcceptAlliesComponent.class) != null){
                    triggerEffect |= (damageTeamComponent.getTeamEntityID() == damagedTeamComponent.getTeamEntityID());
                }
                if(intersectionRules.getComponent(AcceptEnemiesComponent.class) != null){
                    triggerEffect |= (damageTeamComponent.getTeamEntityID() != damagedTeamComponent.getTeamEntityID());
                }
            }
            else{
                triggerEffect = true;
            }
            if(triggerEffect){
                EntityWrapper effectCast = entityWorld.getWrapped(entityWorld.createEntity());
                int effectID = entityWorld.getComponent(damageEntity, CollisionTriggerEffectComponent.class).getEffectEntityID();
                effectCast.setComponent(new PrepareEffectComponent(effectID));
                CastSourceComponent castSourceComponent = entityWorld.getComponent(damageEntity, CastSourceComponent.class);
                if(castSourceComponent != null){
                    effectCast.setComponent(new EffectSourceComponent(castSourceComponent.getSourceEntitiyID()));
                }
                effectCast.setComponent(new AffectedTargetsComponent(new int[]{damagedEntity}));
                entityWorld.removeEntity(damageEntity);
            }
        }
    }
}
