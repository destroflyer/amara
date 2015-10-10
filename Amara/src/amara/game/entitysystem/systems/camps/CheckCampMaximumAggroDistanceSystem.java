/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class CheckCampMaximumAggroDistanceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(Integer entity : entityWorld.getEntitiesWithAll(CampComponent.class)){
            if(!entityWorld.hasComponent(entity, CampResetComponent.class)){
                CampComponent campComponent = entityWorld.getComponent(entity, CampComponent.class);
                CampMaximumAggroDistanceComponent campMaximumAggroDistanceComponent = entityWorld.getComponent(campComponent.getCampEntity(), CampMaximumAggroDistanceComponent.class);
                if(campMaximumAggroDistanceComponent != null){
                    Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                    Vector2f campPosition = campComponent.getPosition();
                    if(position.distance(campPosition) > campMaximumAggroDistanceComponent.getDistance()){
                        entityWorld.setComponent(entity, new CampResetComponent());
                    }
                }
            }
        }
    }
}
