/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
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
        ComponentMapObserver observer = entityWorld.requestObserver(this, ScaleComponent.class, ModelComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(ScaleComponent.class)){
            updateScale(entityWorld, entity);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(ScaleComponent.class)){
            updateScale(entityWorld, entity);
        }
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(ScaleComponent.class)){
            updateScale(entity, 1);
        }
    }
    
    private void updateScale(EntityWorld entityWorld, Integer entity){
        ScaleComponent scaleComponent = entityWorld.getComponent(entity, ScaleComponent.class);
        updateScale(entity, scaleComponent.getScale());
    }
    
    private void updateScale(Integer entity, float scale){
        Node node = entitySceneMap.requestNode(entity);
        node.setLocalScale(scale);
    }
}
