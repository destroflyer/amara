/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public abstract class SimpleHUDAttachmentSystem extends SimpleVisualAttachmentSystem{

    public SimpleHUDAttachmentSystem(HUDAttachmentsSystem hudAttachmentsSystem, Class componentClass){
        super(componentClass);
        this.hudAttachmentsSystem = hudAttachmentsSystem;
    }
    private HUDAttachmentsSystem hudAttachmentsSystem;
    private EntityWorld entityWorld;
    protected Vector3f hudOffset = new Vector3f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        this.entityWorld = entityWorld;
        super.update(entityWorld, deltaSeconds);
    }

    @Override
    protected void attach(int entity, Spatial visualAttachment){
        hudAttachmentsSystem.attach(new HUDAttachmentInfo(entity, getVisualAttachmentID(entity), getWorldOffset(entityWorld, entity), getPreparedHUDOffset(), true), visualAttachment);
    }

    @Override
    protected void detach(int entity){
        hudAttachmentsSystem.detach(getVisualAttachmentID(entity));
    }

    @Override
    protected Spatial getVisualAttachment(int entity){
        return hudAttachmentsSystem.getHUDAttachment(getVisualAttachmentID(entity));
    }
    
    protected Vector3f getPreparedHUDOffset(){
        return hudOffset.clone();
    }
    
    protected Vector3f getWorldOffset(EntityWorld entityWorld, int entity){
        return Vector3f.ZERO;
    }
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);

    protected abstract void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment);
}
