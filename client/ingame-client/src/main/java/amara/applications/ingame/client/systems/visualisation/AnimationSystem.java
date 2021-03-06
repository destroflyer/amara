/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import java.util.LinkedList;

import com.jme3.scene.Node;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.applications.ingame.entitysystem.systems.network.SendEntityChangesSystem;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class AnimationSystem implements EntitySystem{
    
    public AnimationSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;
    //Entity->Animation
    private HashMap<Integer, Integer> currentAnimations = new HashMap<Integer, Integer>();
    //Animation->Entities
    private HashMap<Integer, LinkedList<Integer>> animatedEntitiesMap = new HashMap<Integer, LinkedList<Integer>>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SendEntityChangesSystem.COMPONENT_EQUALITY_DEFINITION, AnimationComponent.class, ModelComponent.class, NameComponent.class, LoopDurationComponent.class, RestartClientAnimationComponent.class, GameSpeedComponent.class);
        //Animation
        for(int entity : observer.getNew().getEntitiesWithAny(AnimationComponent.class)){
            addAnimation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(AnimationComponent.class)){
            addAnimation(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(AnimationComponent.class)){
            removeAnimation(entity, observer.getRemoved().getComponent(entity, AnimationComponent.class).getAnimationEntity());
        }
        //Model
        for(int entity : observer.getNew().getEntitiesWithAny(ModelComponent.class)){
            addAnimation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(ModelComponent.class)){
            addAnimation(entityWorld, entity);
        }
        //Name
        for(int animationEntity : observer.getNew().getEntitiesWithAny(NameComponent.class)){
            updateAnimation(entityWorld, animationEntity, true, false);
        }
        for(int animationEntity : observer.getChanged().getEntitiesWithAny(NameComponent.class)){
            updateAnimation(entityWorld, animationEntity, true, false);
        }
        //LoopDuration
        for(int animationEntity : observer.getNew().getEntitiesWithAny(LoopDurationComponent.class)){
            updateAnimation(entityWorld, animationEntity, false, true);
        }
        for(int animationEntity : observer.getChanged().getEntitiesWithAny(LoopDurationComponent.class)){
            updateAnimation(entityWorld, animationEntity, false, true);
        }
        //AssignedAnimation
        for(int animationEntity : observer.getChanged().getEntitiesWithAny(RestartClientAnimationComponent.class)){
            updateAnimation(entityWorld, animationEntity, true, true);
        }
        //GameSpeed
        GameSpeedComponent gameSpeedComponent = observer.getChanged().getComponent(Map.GAME_ENTITY, GameSpeedComponent.class);
        if(gameSpeedComponent != null){
            for(int animationEntity : animatedEntitiesMap.keySet()){
                updateAnimation(entityWorld, animationEntity, false, true);
            }
        }
    }
    
    private void addAnimation(EntityWorld entityWorld, int entity){
        AnimationComponent animationComponent = entityWorld.getComponent(entity, AnimationComponent.class);
        if(animationComponent != null){
            //CurrentAnimations
            Integer oldAnimationEntity = currentAnimations.get(entity);
            if(oldAnimationEntity != null){
                LinkedList<Integer> oldAnimatedEntities = animatedEntitiesMap.get(oldAnimationEntity);
                oldAnimatedEntities.remove((Integer) entity);
            }
            int newAnimationEntity = animationComponent.getAnimationEntity();
            currentAnimations.put(entity, newAnimationEntity);
            //AnimatedEntites
            LinkedList<Integer> animatedEntities = animatedEntitiesMap.get(newAnimationEntity);
            if(animatedEntities == null){
                animatedEntities = new LinkedList<Integer>();
                animatedEntitiesMap.put(newAnimationEntity, animatedEntities);
            }
            if(!animatedEntities.contains(entity)){
                animatedEntities.add(entity);
            }
            updateModel(entityWorld, entity, newAnimationEntity, true, true);
        }
    }
    
    private void removeAnimation(int entity, int animationEntity){
        currentAnimations.remove(entity);
        LinkedList<Integer> animatedEntities = animatedEntitiesMap.get(entity);
        if(animatedEntities != null){
            animatedEntitiesMap.get(entity).remove(animationEntity);
        }
        ModelObject modelObject = getModelObject(entity);
        if(modelObject != null){
            modelObject.stopAndRewindAnimation();
        }
    }
    
    private void updateAnimation(EntityWorld entityWorld, int animationEntity, boolean updateName, boolean updateProperties){
        LinkedList<Integer> animatedEntities = animatedEntitiesMap.get(animationEntity);
        if(animatedEntities != null){
            for(int entity : animatedEntities){
                updateModel(entityWorld, entity, animationEntity, updateName, updateProperties);
            }
        }
    }
    
    private void updateModel(EntityWorld entityWorld, int entity, int animationEntity, boolean updateName, boolean updateProperties){
        ModelObject modelObject = getModelObject(entity);
        if(modelObject != null){
            NameComponent nameComponent = entityWorld.getComponent(animationEntity, NameComponent.class);
            if (nameComponent != null) {
                if(updateName){
                    modelObject.setAnimationName(nameComponent.getName());
                }
                if(updateProperties){
                    LoopDurationComponent loopDurationComponent = entityWorld.getComponent(animationEntity, LoopDurationComponent.class);
                    if(loopDurationComponent != null){
                        float gameSpeed = entityWorld.getComponent(Map.GAME_ENTITY, GameSpeedComponent.class).getSpeed();
                        float loopDuration = (loopDurationComponent.getDuration() / gameSpeed);
                        boolean isLoop = (!entityWorld.hasComponent(animationEntity, FreezeAfterPlayingComponent.class));
                        modelObject.setAnimationProperties(loopDuration, isLoop);
                    }
                }
            }
        }
    }
    
    private ModelObject getModelObject(int entity){
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
