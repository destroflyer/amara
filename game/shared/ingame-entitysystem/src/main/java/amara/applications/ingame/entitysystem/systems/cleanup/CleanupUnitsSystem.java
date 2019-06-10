/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupUnitsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IdleAnimationComponent.class, WalkAnimationComponent.class, AutoAttackAnimationComponent.class, DeathAnimationComponent.class, AnimationComponent.class, IntersectionRulesComponent.class, AutoAttackComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAny(IdleAnimationComponent.class)){
            int animationEntity = observer.getRemoved().getComponent(entity, IdleAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(WalkAnimationComponent.class)){
            int animationEntity = observer.getRemoved().getComponent(entity, WalkAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(AutoAttackAnimationComponent.class)){
            int animationEntity = observer.getRemoved().getComponent(entity, AutoAttackAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(DeathAnimationComponent.class)){
            int animationEntity = observer.getRemoved().getComponent(entity, DeathAnimationComponent.class).getAnimationEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(AnimationComponent.class)){
            if(!entityWorld.hasEntity(entity)){
                int animationEntity = observer.getRemoved().getComponent(entity, AnimationComponent.class).getAnimationEntity();
                CleanupUtil.tryCleanupEntity(entityWorld, animationEntity);
            }
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(IntersectionRulesComponent.class)){
            int targetRulesEntity = observer.getRemoved().getComponent(entity, IntersectionRulesComponent.class).getTargetRulesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, targetRulesEntity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(AutoAttackComponent.class)){
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, autoAttackEntity);
        }
    }
}
