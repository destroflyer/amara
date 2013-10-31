/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.scene.Node;
import amara.engine.client.ClientApplication;
import amara.engine.client.models.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class ModelSystem implements EntitySystem{
    
    public ModelSystem(EntitySceneMap entitySceneMap, ClientApplication clientApplication){
        this.entitySceneMap = entitySceneMap;
        this.clientApplication = clientApplication;
    }
    public final static String NODE_NAME_MODEL = "model";
    private EntitySceneMap entitySceneMap;
    private ClientApplication clientApplication;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, ModelComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(ModelComponent.class))
        {
            updateModel(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ModelComponent.class))
        {
            updateModel(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(ModelComponent.class))
        {
            entitySceneMap.removeNode(entity);
        }
        observer.reset();
    }
    
    private void updateModel(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(NODE_NAME_MODEL);
        ModelComponent modelComponent = entityWorld.getComponent(entity, ModelComponent.class);
        ModelObject modelObject = new ModelObject(clientApplication, "/" + modelComponent.getModelSkinPath());
        modelObject.setName(NODE_NAME_MODEL);
        node.attachChild(modelObject);
    }
}
