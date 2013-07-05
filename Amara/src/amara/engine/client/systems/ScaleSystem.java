/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems;

import com.jme3.scene.Node;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;

/**
 *
 * @author Carl
 */
public class ScaleSystem implements EntitySystem{
    
    public ScaleSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(ScaleComponent.class))
        {
            updateScale(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(ScaleComponent.class))
        {
            updateScale(entityWorld, entity);
        }
    }
    
    private void updateScale(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        ScaleComponent scaleComponent = entityWorld.getCurrent().getComponent(entity, ScaleComponent.class);
        node.setLocalScale(scaleComponent.getScale());
    }
}
