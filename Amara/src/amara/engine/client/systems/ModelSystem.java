/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems;

import com.jme3.scene.Node;
import amara.engine.client.models.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class ModelSystem implements EntitySystem{
    
    public ModelSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(ModelComponent.class))
        {
            Node node = entitySceneMap.requestNode(entity);
            ModelComponent modelComponent = entityWorld.getCurrent().getComponent(entity, ModelComponent.class);
            ModelObject modelObject = new ModelObject("/" + modelComponent.getModelSkinPath());
            node.attachChild(modelObject);
        }
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(ModelComponent.class))
        {
            entitySceneMap.removeNode(entity);
        }
    }
}
