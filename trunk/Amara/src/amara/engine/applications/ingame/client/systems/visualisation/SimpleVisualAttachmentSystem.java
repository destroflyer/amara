/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Spatial;
import amara.game.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class SimpleVisualAttachmentSystem implements EntitySystem{

    public SimpleVisualAttachmentSystem(Class componentClass, boolean displayExistanceOrAbsence){
        componentClassesToObserve = new Class[]{componentClass};
        this.componentClass = componentClass;
        this.displayExistanceOrAbsence = displayExistanceOrAbsence;
    }
    private Class[] componentClassesToObserve;
    private Class componentClass;
    private boolean displayExistanceOrAbsence;
    
    protected void setComponentClassesToObserve(Class... additionalComponentClassesToObserve){
        componentClassesToObserve = new Class[additionalComponentClassesToObserve.length + 1];
        componentClassesToObserve[0] = componentClass;
        for(int i=1;i<componentClassesToObserve.length;i++){
            componentClassesToObserve[i] = additionalComponentClassesToObserve[i - 1];
        }
    }

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, componentClassesToObserve);
        observeEntities(entityWorld, observer);
        observer.reset();
    }
    
    protected void observeEntities(EntityWorld entityWorld, ComponentMapObserver observer){
        for(int entity : observer.getNew().getEntitiesWithAll(componentClass)){
            setVisualAttachmentVisible(entityWorld, entity, displayExistanceOrAbsence);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(componentClass)){
            setVisualAttachmentVisible(entityWorld, entity, displayExistanceOrAbsence);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(componentClass)){
            setVisualAttachmentVisible(entityWorld, entity, (!displayExistanceOrAbsence));
        }
    }
    
    private void setVisualAttachmentVisible(EntityWorld entityWorld,int entity, boolean isVisible){
        if(isVisible){
            updateVisualAttachment(entityWorld, entity);
        }
        else{
            detach(entityWorld, entity);
        }
    }
    
    private void updateVisualAttachment(EntityWorld entityWorld, int entity){
        detach(entityWorld, entity);
        Spatial visualAttachment = createVisualAttachment(entityWorld, entity);
        if(visualAttachment != null){
            visualAttachment.setName(getVisualAttachmentID(entity));
            attach(entityWorld, entity, visualAttachment);
        }
    }
    
    protected String getVisualAttachmentID(int entity){
        return ("visualAttachment_" + hashCode() + "_" + entity);
    }
    
    protected abstract void attach(EntityWorld entityWorld, int entity, Spatial visualAttachment);
    
    protected abstract void detach(EntityWorld entityWorld, int entity);
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);
}
