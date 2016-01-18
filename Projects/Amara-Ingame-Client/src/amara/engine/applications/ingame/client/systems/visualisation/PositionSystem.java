/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import amara.engine.applications.ingame.client.maps.MapHeightmap;
import amara.game.entitysystem.components.physics.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class PositionSystem implements EntitySystem{
    
    public PositionSystem(EntitySceneMap entitySceneMap, MapHeightmap mapHeightmap){
        this.entitySceneMap = entitySceneMap;
        this.mapHeightmap =  mapHeightmap;
    }
    private EntitySceneMap entitySceneMap;
    private MapHeightmap mapHeightmap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PositionComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(PositionComponent.class)){
            updatePosition(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(PositionComponent.class)){
            updatePosition(entityWorld, entity);
        }
    }
    
    public void updatePosition(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        node.setLocalTranslation(position.getX(), mapHeightmap.getHeight(position), position.getY());
    }
}
