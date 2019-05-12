/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.physics;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.physics.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyRemoveCollisiongGroupsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveCollisionGroupsComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            CollisionGroupComponent oldCollisionGroupComponent = entityWorld.getComponent(targetEntity, CollisionGroupComponent.class);
            RemoveCollisionGroupsComponent removeCollisionGroupsComponent = entityWrapper.getComponent(RemoveCollisionGroupsComponent.class);
            long targetOf = (oldCollisionGroupComponent.getTargetOf() & (~removeCollisionGroupsComponent.getTargetOf()));
            long targets = (oldCollisionGroupComponent.getTargets() & (~removeCollisionGroupsComponent.getTargets()));
            entityWorld.setComponent(targetEntity, new CollisionGroupComponent(targetOf, targets));
        }
    }
}
