/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import amara.applications.ingame.entitysystem.components.visuals.ModelComponent;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ModelSystem implements EntitySystem {
    
    public ModelSystem(EntitySceneMap entitySceneMap, DisplayApplication mainApplication) {
        this.entitySceneMap = entitySceneMap;
        this.mainApplication = mainApplication;
    }
    public final static String NODE_NAME_MODEL = "model";
    private EntitySceneMap entitySceneMap;
    private DisplayApplication mainApplication;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, ModelComponent.class);
        for (int entity : observer.getNew().getEntitiesWithAny(ModelComponent.class)) {
            updateModel(entityWorld, entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(ModelComponent.class)) {
            updateModel(entityWorld, entity);
        }
    }

    private void updateModel(EntityWorld entityWorld, int entity) {
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(NODE_NAME_MODEL);
        ModelComponent modelComponent = entityWorld.getComponent(entity, ModelComponent.class);
        ModelObject modelObject = new ModelObject(mainApplication, modelComponent.getModelSkinPath());
        modelObject.setName(NODE_NAME_MODEL);
        modelObject.setShadowMode(RenderQueue.ShadowMode.Cast);
        node.attachChild(modelObject);
    }
}