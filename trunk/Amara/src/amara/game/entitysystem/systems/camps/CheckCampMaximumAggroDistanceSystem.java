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
        for(int entity : entityWorld.getEntitiesWithAll(CampComponent.class))
        {
            int campEntity = entityWorld.getComponent(entity, CampComponent.class).getCampEntity();
            CampMaximumAggroDistanceComponent campMaximumAggroDistanceComponent = entityWorld.getComponent(campEntity, CampMaximumAggroDistanceComponent.class);
            if(campMaximumAggroDistanceComponent != null){
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                Vector2f campPosition = entityWorld.getComponent(campEntity, CampTransformComponent.class).getPosition();
                if(position.distance(campPosition) > campMaximumAggroDistanceComponent.getDistance()){
                    entityWorld.setComponent(entity, new ResetCampComponent());
                }
            }
        }
    }
}