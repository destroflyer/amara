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
public abstract class EntityNodeAttachmentSystem extends SimpleVisualAttachmentSystem{

    public EntityNodeAttachmentSystem(Class componentClass, boolean displayExistanceOrAbsence, EntitySceneMap entitySceneMap){
        super(componentClass, displayExistanceOrAbsence);
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    protected void attach(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        Node node = entitySceneMap.requestNode(entity);
        node.attachChild(visualAttachment);
    }
    
    @Override
    protected void detach(EntityWorld entityWorld, int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(getVisualAttachmentID(entity));
    }
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);
}
