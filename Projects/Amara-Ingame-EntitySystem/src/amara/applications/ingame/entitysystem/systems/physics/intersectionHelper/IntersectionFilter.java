/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.physics.intersectionHelper;

import amara.applications.ingame.entitysystem.components.physics.CollisionGroupComponent;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.physics.intersection.Filter;

/**
 *
 * @author Philipp
 */
public class IntersectionFilter extends Filter<Hitbox>{
    
    public IntersectionFilter(EntityWorld entityWorld){
        this.entityWorld = entityWorld;
    }
    private EntityWorld entityWorld;

    @Override
    public boolean pass(Hitbox a, Hitbox b){
        return (areCollisionGroupsMatching(entityWorld, a.getId(), b.getId()) && a.getShape().intersects(b.getShape()));
    }

    public static boolean areCollisionGroupsMatching(EntityWorld entityWorld, int entity1, int entity2){
        CollisionGroupComponent collisionGroupComponent1 = entityWorld.getComponent(entity1, CollisionGroupComponent.class);
        if(collisionGroupComponent1 == null){
            return true;
        }
        CollisionGroupComponent collisionGroupComponent2 = entityWorld.getComponent(entity2, CollisionGroupComponent.class);
        if(collisionGroupComponent2 == null){
            return true;
        }
        return (CollisionGroupComponent.isColliding(collisionGroupComponent1.getTargetOf(), collisionGroupComponent2.getTargets())
             || CollisionGroupComponent.isColliding(collisionGroupComponent2.getTargetOf(), collisionGroupComponent1.getTargets()));
    }
}
