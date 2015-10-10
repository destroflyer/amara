/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.camps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class CampSpawnSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(Integer campEntity : entityWorld.getEntitiesWithAll(CampSpawnComponent.class, CampSpawnInformationComponent.class)){
            int[] spawnInformationEntites = entityWorld.getComponent(campEntity, CampSpawnInformationComponent.class).getSpawnInformationEntites();
            for(int spawnInformationEntity : spawnInformationEntites){
                String[] templateNames = entityWorld.getComponent(spawnInformationEntity, SpawnTemplateComponent.class).getTemplateNames();
                Integer entity = entityWorld.createEntity();
                EntityTemplate.loadTemplates(entityWorld, entity, templateNames);
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                Vector2f direction = entityWorld.getComponent(entity, DirectionComponent.class).getVector();
                entityWorld.setComponent(entity, new CampComponent(campEntity, position, direction));
            }
            entityWorld.removeComponent(campEntity, CampSpawnComponent.class);
        }
    }
}
