/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;

/**
 *
 * @author Carl
 */
public class SetAutoAttacksCastAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AutoAttackComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AutoAttackComponent.class)){
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(AutoAttackComponent.class)){
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AutoAttackComponent.class)){
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.removeComponent(autoAttackEntity, CastAnimationComponent.class);
        }
        observer.reset();
    }
    
    private void updateAnimation(EntityWorld entityWorld, int entity){
        AutoAttackAnimationComponent autoAttackAnimationComponent = entityWorld.getComponent(entity, AutoAttackAnimationComponent.class);
        if(autoAttackAnimationComponent != null){
            int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.setComponent(autoAttackEntity, new CastAnimationComponent(autoAttackAnimationComponent.getAnimationEntity()));
        }
    }
}
