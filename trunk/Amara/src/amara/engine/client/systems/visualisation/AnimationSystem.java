/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.scene.Node;
import amara.engine.client.models.ModelObject;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class AnimationSystem implements EntitySystem{
    
    public AnimationSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(AnimationComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(AnimationComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(AnimationComponent.class))
        {
            ModelObject modelObject = getModelObject(entity);
            if(modelObject != null){
                modelObject.stopAndRewindAnimation();
            }
        }
        for(int entity : entityWorld.getNew().getEntitiesWithAll(ModelComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(ModelComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
    }
    
    private void updateAnimation(EntityWorld entityWorld, int entity){
        ModelObject modelObject = getModelObject(entity);
        AnimationComponent animationComponent = entityWorld.getCurrent().getComponent(entity, AnimationComponent.class);
        if(animationComponent != null){
            modelObject.playAnimation(animationComponent.getName(), animationComponent.getLoopDuration(), animationComponent.isLooped());
        }
    }
    
    private ModelObject getModelObject(int entity){
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
