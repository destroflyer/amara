/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public abstract class EntityNodeAttachmentSystem extends SimpleVisualAttachmentSystem{

    public EntityNodeAttachmentSystem(Class componentClass, EntitySceneMap entitySceneMap){
        super(componentClass);
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    protected void attach(int entity, Spatial visualAttachment){
        Node node = entitySceneMap.requestNode(entity);
        node.attachChild(visualAttachment);
    }

    @Override
    protected void detach(int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(getVisualAttachmentID(entity));
    }

    @Override
    protected Spatial getVisualAttachment(int entity){
        Node node = entitySceneMap.requestNode(entity);
        return node.getChild(getVisualAttachmentID(entity));
    }
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);

    protected abstract void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment);
}
