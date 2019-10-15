/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.camps;

import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.aggro.AggroUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckCampUnionAggroSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, AggroTargetComponent.class);
        for (int entity : observer.getNew().getEntitiesWithAny(AggroTargetComponent.class)) {
            onAggroTargetChanged(entityWorld, entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(AggroTargetComponent.class)) {
            onAggroTargetChanged(entityWorld, entity);
        }
    }

    private void onAggroTargetChanged(EntityWorld entityWorld, int entity) {
        CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
        if ((campComponent != null) && entityWorld.hasComponent(campComponent.getCampEntity(), CampUnionAggroComponent.class)) {
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            for (int otherEntity : entityWorld.getEntitiesWithAny(CampComponent.class)) {
                int otherCampEntity = entityWorld.getComponent(otherEntity, CampComponent.class).getCampEntity();
                if (otherCampEntity == campComponent.getCampEntity()) {
                    AggroUtil.tryDrawAggro(entityWorld, otherEntity, targetEntity);
                }
            }
        }
    }
}
