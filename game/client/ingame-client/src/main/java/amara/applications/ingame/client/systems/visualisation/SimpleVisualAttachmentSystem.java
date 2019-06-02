/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Spatial;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class SimpleVisualAttachmentSystem implements EntitySystem{

    public SimpleVisualAttachmentSystem(Class componentClass){
        componentClassesToObserve = new Class[]{componentClass};
        this.componentClass = componentClass;
    }
    private Class[] componentClassesToObserve;
    private Class componentClass;
    
    protected void setComponentClassesToObserve(Class... additionalComponentClassesToObserve){
        componentClassesToObserve = new Class[additionalComponentClassesToObserve.length + 1];
        componentClassesToObserve[0] = componentClass;
        for(int i=1;i<componentClassesToObserve.length;i++){
            componentClassesToObserve[i] = additionalComponentClassesToObserve[i - 1];
        }
    }

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, componentClassesToObserve);
        for(int entity : observer.getNew().getEntitiesWithAll(componentClass)){
            createAndAttachVisualAttachment(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(componentClass)){
            Spatial visualAttachment = getVisualAttachment(entity);
            updateVisualAttachment(entityWorld, entity, visualAttachment);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(componentClass)){
            detach(entity);
        }
    }
    
    protected void createAndAttachVisualAttachment(EntityWorld entityWorld, int entity){
        Spatial visualAttachment = createVisualAttachment(entityWorld, entity);
        if(visualAttachment != null){
            visualAttachment.setName(getVisualAttachmentID(entity));
            updateVisualAttachment(entityWorld, entity, visualAttachment);
            attach(entity, visualAttachment);
        }
    }
    
    protected abstract void attach(int entity, Spatial visualAttachment);
    
    protected abstract void detach(int entity);
    
    protected abstract Spatial getVisualAttachment(int entity);
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);
    
    protected abstract void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment);
    
    protected String getVisualAttachmentID(int entity){
        return ("visualAttachment_" + hashCode() + "_" + entity);
    }
}