/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import amara.engine.client.maps.MapTerrain;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.PositionComponent;

/**
 *
 * @author Carl
 */
public class ModelYAdjustingSystem implements EntitySystem{
    
    public ModelYAdjustingSystem(EntitySceneMap entitySceneMap, MapTerrain mapTerrain){
        this.entitySceneMap = entitySceneMap;
        this.mapTerrain = mapTerrain;
    }
    public final static String NODE_NAME_MODEL = "model";
    private EntitySceneMap entitySceneMap;
    private MapTerrain mapTerrain;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(PositionComponent.class))
        {
            Node node = entitySceneMap.requestNode(entity);
            PositionComponent positionComponent = entityWorld.getCurrent().getComponent(entity, PositionComponent.class);
            Vector2f location = positionComponent.getPosition();
            node.setLocalTranslation(location.getX(), mapTerrain.getHeight(location), location.getY());
        }
    }
}
