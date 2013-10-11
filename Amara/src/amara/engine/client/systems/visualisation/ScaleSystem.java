/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, ScaleComponent.class, ModelComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(ScaleComponent.class))
        {
            updateScale(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ScaleComponent.class))
        {
            updateScale(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(ScaleComponent.class))
        {
            updateScale(entity, 1);
        }
        for(int entity : observer.getNew().getEntitiesWithAll(ModelComponent.class))
        {
            updateScale(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ModelComponent.class))
        {
            updateScale(entityWorld, entity);
        }
        observer.reset();
    }
    
    private void updateScale(EntityWorld entityWorld, int entity){
        ScaleComponent scaleComponent = entityWorld.getComponent(entity, ScaleComponent.class);
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
