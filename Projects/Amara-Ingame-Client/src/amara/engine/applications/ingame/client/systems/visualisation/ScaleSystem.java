/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.libraries.entitysystem.*;

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
        ComponentMapObserver observer = entityWorld.requestObserver(this, ScaleComponent.class, ModelComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(ScaleComponent.class)){
            updateScale(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ScaleComponent.class)){
            updateScale(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(ScaleComponent.class)){
            updateScale(entity, 1);
        }
    }
    
    private void updateScale(EntityWorld entityWorld, int entity){
        ScaleComponent scaleComponent = entityWorld.getComponent(entity, ScaleComponent.class);
        updateScale(entity, scaleComponent.getScale());
    }
    
    private void updateScale(int entity, float scale){
        Node node = entitySceneMap.requestNode(entity);
        node.setLocalScale(scale);
    }
}
