/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.game.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class SimpleVisualAttachmentSystem implements EntitySystem{
    
    protected SimpleVisualAttachmentSystem(EntitySceneMap entitySceneMap, Class... componentClasses){
        this.entitySceneMap = entitySceneMap;
        this.componentClasses = componentClasses;
    }
    private EntitySceneMap entitySceneMap;
    private Class[] componentClasses;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(componentClasses))
        {
            updateVisualAttachment(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(componentClasses))
        {
            updateVisualAttachment(entityWorld, entity);
        }
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(componentClasses))
        {
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
