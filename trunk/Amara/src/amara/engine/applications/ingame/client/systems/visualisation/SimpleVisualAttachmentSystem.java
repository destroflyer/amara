/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.game.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class SimpleVisualAttachmentSystem implements EntitySystem{

    public SimpleVisualAttachmentSystem(EntitySceneMap entitySceneMap, Class componentClass, boolean displayExistanceOrAbsence){
        this.entitySceneMap = entitySceneMap;
        this.componentClass = componentClass;
        this.displayExistanceOrAbsence = displayExistanceOrAbsence;
    }
    private EntitySceneMap entitySceneMap;
    private Class componentClass;
    private boolean displayExistanceOrAbsence;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, componentClass);
        for(int entity : observer.getNew().getEntitiesWithAll(componentClass)){
            setVisualAttachmentVisible(entityWorld, entity, displayExistanceOrAbsence);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(componentClass)){
            setVisualAttachmentVisible(entityWorld, entity, displayExistanceOrAbsence);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(componentClass)){
            setVisualAttachmentVisible(entityWorld, entity, (!displayExistanceOrAbsence));
        }
        observer.reset();
    }
    
    private void setVisualAttachmentVisible(EntityWorld entityWorld,int entity, boolean isVisible){
        if(isVisible){
            updateVisualAttachment(entityWorld, entity);
        }
        else{
            removeVisualAttachment(entity);
        }
    }
    
    private void updateVisualAttachment(EntityWorld entityWorld, int entity){
        removeVisualAttachment(entity);
        Node node = entitySceneMap.requestNode(entity);
        Spatial visualAttachment = createVisualAttachment(entityWorld, entity);
        if(visualAttachment != null){
            visualAttachment.setName(getVisualAttachmentID());
            node.attachChild(visualAttachment);
        }
    }
    
    private void removeVisualAttachment(int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(getVisualAttachmentID());
    }
    
    private String getVisualAttachmentID(){
        return ("visualAttachment_" + hashCode());
    }
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);
}
