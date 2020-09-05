/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SetAutoAttacksCastAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AutoAttackComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(AutoAttackComponent.class)){
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(AutoAttackComponent.class)){
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(AutoAttackComponent.class)){
            int autoAttackEntity = observer.getRemoved().getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.removeComponent(autoAttackEntity, CastAnimationComponent.class);
        }
    }
    
    private void updateAnimation(EntityWorld entityWorld, int entity){
        AutoAttackAnimationComponent autoAttackAnimationComponent = entityWorld.getComponent(entity, AutoAttackAnimationComponent.class);
        if(autoAttackAnimationComponent != null){
            int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            entityWorld.setComponent(autoAttackEntity, new CastAnimationComponent(autoAttackAnimationComponent.getAnimationEntity()));
        }
    }
}
