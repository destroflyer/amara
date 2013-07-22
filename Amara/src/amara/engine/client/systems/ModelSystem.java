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
    public final static String NODE_NAME_MODEL = "model";
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(ModelComponent.class))
        {
            updateModel(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(ModelComponent.class))
        {
            updateModel(entityWorld, entity);
        }
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(ModelComponent.class))
        {
            entitySceneMap.removeNode(entity);
        }
    }
    
    private void updateModel(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(NODE_NAME_MODEL);
        ModelComponent modelComponent = entityWorld.getCurrent().getComponent(entity, ModelComponent.class);
        ModelObject modelObject = new ModelObject("/" + modelComponent.getModelSkinPath());
        modelObject.setName(NODE_NAME_MODEL);
        node.attachChild(modelObject);
    }
}
