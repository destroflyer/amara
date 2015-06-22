/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.game.entitysystem.*;
import amara.game.entitysystem.ComponentMapObserver;
import amara.game.entitysystem.components.game.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.games.Game;

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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AnimationComponent.class, ModelComponent.class, GameSpeedComponent.class);
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
        GameSpeedComponent gameSpeedComponent = observer.getChanged().getComponent(Game.ENTITY, GameSpeedComponent.class);
        if(gameSpeedComponent != null){
            for(int entity : entityWorld.getEntitiesWithAll(AnimationComponent.class)){
                updateAnimation(entityWorld, entity);
            }
        }
        observer.reset();
    }
    
    private void updateAnimation(EntityWorld entityWorld, int entity){
        ModelObject modelObject = getModelObject(entity);
        if(modelObject != null){
            AnimationComponent animationComponent = entityWorld.getComponent(entity, AnimationComponent.class);
            if(animationComponent != null){
                int animationEntity = animationComponent.getAnimationEntity();
                NameComponent nameComponent = entityWorld.getComponent(animationEntity, NameComponent.class);
                LoopDurationComponent loopDurationComponent = entityWorld.getComponent(animationEntity, LoopDurationComponent.class);
                if((nameComponent != null) && (loopDurationComponent != null)){
                    float gameSpeed = entityWorld.getComponent(Game.ENTITY, GameSpeedComponent.class).getSpeed();
                    float loopDuration = (loopDurationComponent.getDuration() / gameSpeed);
                    boolean isLoop = (!entityWorld.hasComponent(animationEntity, FreezeAfterPlayingComponent.class));
                    modelObject.playAnimation(nameComponent.getName(), loopDuration, isLoop);
                }
            }
        }
    }
    
    private ModelObject getModelObject(int entity){
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
