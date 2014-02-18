/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import amara.engine.applications.ingame.client.maps.MapTerrain;
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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, PositionComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(PositionComponent.class))
        {
            updatePosition(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(PositionComponent.class))
        {
            updatePosition(entityWorld, entity);
        }
        observer.reset();
    }
    
    private void updatePosition(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        node.setLocalTranslation(position.getX(), mapTerrain.getHeight(position), position.getY());
    }
}
