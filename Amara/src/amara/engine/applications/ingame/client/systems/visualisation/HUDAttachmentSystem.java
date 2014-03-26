/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.ArrayList;
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

    public HUDAttachmentSystem(Class componentClass, boolean displayExistanceOrAbsence, Node guiNode, Camera camera, MapHeightmap mapHeightmap){
        super(componentClass, displayExistanceOrAbsence);
        this.guiNode = guiNode;
        this.camera = camera;
        this.mapHeightmap = mapHeightmap;
        setComponentClassesToObserve(PositionComponent.class);
    }
    protected Vector3f worldOffset = new Vector3f();
    protected Vector3f hudOffset = new Vector3f();
    protected Node guiNode;
    private Camera camera;
    private MapHeightmap mapHeightmap;
    private ArrayList<Integer> entitiesWithAttachments = new ArrayList<Integer>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        super.update(entityWorld, deltaSeconds);
        for(int entity : entitiesWithAttachments){
            PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
            if(positionComponent != null){
                Vector3f entityPosition = new Vector3f(positionComponent.getPosition().getX(), mapHeightmap.getHeight(positionComponent.getPosition()), positionComponent.getPosition().getY());
                Spatial visualAttachment = guiNode.getChild(getVisualAttachmentID(entity));
                Vector3f attachmentPosition = camera.getScreenCoordinates(entityPosition.addLocal(worldOffset)).addLocal(hudOffset);
                visualAttachment.setLocalTranslation(attachmentPosition);
            }
        }
    }

    @Override
    protected void attach(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        guiNode.attachChild(visualAttachment);
        entitiesWithAttachments.add(entity);
    }

    @Override
    protected void detach(EntityWorld entityWorld, int entity){
        guiNode.detachChildNamed(getVisualAttachmentID(entity));
        entitiesWithAttachments.remove((Integer) entity);
    }
    
    protected abstract Spatial createVisualAttachment(EntityWorld entityWorld, int entity);
}
