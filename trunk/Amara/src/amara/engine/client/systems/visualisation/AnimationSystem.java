/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.scene.Node;
import amara.engine.client.models.ModelObject;
import amara.game.entitysystem.*;
import amara.game.entitysystem.ComponentMapObserver;
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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AnimationComponent.class, ModelComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AnimationComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(AnimationComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(AnimationComponent.class))
        {
            ModelObject modelObject = getModelObject(entity);
            if(modelObject != null){
                modelObject.stopAndRewindAnimation();
            }
        }
        for(int entity : observer.getNew().getEntitiesWithAll(ModelComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ModelComponent.class))
        {
            updateAnimation(entityWorld, entity);
        }
        observer.reset();
    }
    
    private void updateAnimation(EntityWorld entityWorld, int entity){
        ModelObject modelObject = getModelObject(entity);
        if(modelObject != null){
            AnimationComponent animationComponent = entityWorld.getComponent(entity, AnimationComponent.class);
            if(animationComponent != null){
                modelObject.playAnimation(animationComponent.getName(), animationComponent.getLoopDuration(), animationComponent.isLooped());
            }
        }
    }
    
    private ModelObject getModelObject(int entity){
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
