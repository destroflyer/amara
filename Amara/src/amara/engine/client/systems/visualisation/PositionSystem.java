/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import amara.engine.client.maps.MapTerrain;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;

/**
 *
 * @author Carl
 */
public class PositionSystem implements EntitySystem{
    
    public PositionSystem(EntitySceneMap entitySceneMap, MapTerrain mapTerrain){
        this.entitySceneMap = entitySceneMap;
        this.mapTerrain = mapTerrain;
    }
    private EntitySceneMap entitySceneMap;
    private MapTerrain mapTerrain;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(PositionComponent.class))
        {
            updatePosition(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(PositionComponent.class))
        {
            updatePosition(entityWorld, entity);
        }
    }
    
    private void updatePosition(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        Vector2f position = entityWorld.getCurrent().getComponent(entity, PositionComponent.class).getPosition();
        node.setLocalTranslation(position.getX(), mapTerrain.getHeight(position), position.getY());
    }
}
