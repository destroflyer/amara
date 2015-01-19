/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.ArrayList;
import java.util.HashMap;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.maps.MapHeightmap;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.PositionComponent;

/**
 *
 * @author Carl
 */
public abstract class HUDAttachmentSystem extends SimpleVisualAttachmentSystem{

    public HUDAttachmentSystem(Class componentClass, Node guiNode, Camera camera, MapHeightmap mapHeightmap){
        super(componentClass);
        this.guiNode = guiNode;
        this.camera = camera;
        this.mapHeightmap = mapHeightmap;
        setComponentClassesToObserve(PositionComponent.class);
    }
    protected Vector3f hudOffset = new Vector3f();
    private HashMap<Integer, Vector3f> worldOffsets = new HashMap<Integer, Vector3f>();
    protected Node guiNode;
    private Camera camera;
    private MapHeightmap mapHeightmap;
    private ArrayList<Integer> entitiesWithAttachments = new ArrayList<Integer>();
    private boolean isEnabled = true;
    private Vector3f tmpEntityPosition = new Vector3f();
    private Vector3f tmpAttachmentPosition = new Vector3f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        super.update(entityWorld, deltaSeconds);
        for(int entity : entitiesWithAttachments){
            PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
            if(positionComponent != null){
                tmpEntityPosition.set(positionComponent.getPosition().getX(), mapHeightmap.getHeight(positionComponent.getPosition()), positionComponent.getPosition().getY());
                Spatial visualAttachment = guiNode.getChild(getVisualAttachmentID(entity));
                Vector3f worldOffset = worldOffsets.get(entity);
                if(worldOffset == null){
                    worldOffset = getWorldOffset(entityWorld, entity);
                    worldOffsets.put(entity, worldOffset);
                }
                camera.getScreenCoordinates(tmpEntityPosition.addLocal(worldOffset), tmpAttachmentPosition).addLocal(hudOffset);
                visualAttachment.setLocalTranslation(tmpAttachmentPosition);
            }
        }
    }
    
    protected Vector3f getWorldOffset(EntityWorld entityWorld, int entity){
        return Vector3f.ZERO;
    }

    @Override
    protected void attach(int entity, Spatial visualAttachment){
        updateVisualAttachmentVisibility(visualAttachment);
        guiNode.attachChild(visualAttachment);
        entitiesWithAttachments.add(entity);
    }

    @Override
    protected void detach(int entity){
        guiNode.detachChildNamed(getVisualAttachmentID(entity));
        entitiesWithAttachments.remove((Integer) entity);
    }

    @Override
    protected Spatial getVisualAttachment(int entity){
        return guiNode.getChild(getVisualAttachmentID(entity));
    }
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);

    protected abstract void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment);
    
    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
        for(int entity : entitiesWithAttachments){
            Spatial visualAttachment = guiNode.getChild(getVisualAttachmentID(entity));
            updateVisualAttachmentVisibility(visualAttachment);
        }
    }
    
    private void updateVisualAttachmentVisibility(Spatial visualAttachment){
        visualAttachment.setCullHint(isEnabled?Spatial.CullHint.Inherit:Spatial.CullHint.Always);
    }
}
