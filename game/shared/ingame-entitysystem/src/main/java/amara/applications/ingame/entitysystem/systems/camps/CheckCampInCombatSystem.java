/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.camps;

import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckCampInCombatSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, InCombatComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(InCombatComponent.class)){
            CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
            if((campComponent != null) && (!entityWorld.hasComponent(campComponent.getCampEntity(), CampInCombatComponent.class))){
                entityWorld.setComponent(campComponent.getCampEntity(), new CampInCombatComponent());
            }
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(InCombatComponent.class)){
            CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
            if((campComponent != null) && (!isCampInCombat(entityWorld, campComponent.getCampEntity()))){
                entityWorld.removeComponent(campComponent.getCampEntity(), CampInCombatComponent.class);
            }
        }
    }
    
    private static boolean isCampInCombat(EntityWorld entityWorld, int campEntity){
        for(int entity : entityWorld.getEntitiesWithAny(CampComponent.class)){
            if((entityWorld.getComponent(entity, CampComponent.class).getCampEntity() == campEntity) && entityWorld.hasComponent(entity, InCombatComponent.class)){
                return true;
            }
        }
        return false;
    }
}
