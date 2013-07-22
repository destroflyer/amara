/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.visuals.ModelComponent;

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
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(ScaleComponent.class))
        {
            updateScale(entity, 1);
        }
        for(int entity : entityWorld.getNew().getEntitiesWithAll(ModelComponent.class))
        {
            updateScale(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(ModelComponent.class))
        {
            updateScale(entityWorld, entity);
        }
    }
    
    private void updateScale(EntityWorld entityWorld, int entity){
        ScaleComponent scaleComponent = entityWorld.getCurrent().getComponent(entity, ScaleComponent.class);
        if(scaleComponent != null){
            updateScale(entity, scaleComponent.getScale());
        }
    }
    
    private void updateScale(int entity, float scale){
        Node node = entitySceneMap.requestNode(entity);
        Spatial modelObject = node.getChild(ModelSystem.NODE_NAME_MODEL);
        if(modelObject != null){
            modelObject.setLocalScale(scale);
        }
    }
}
