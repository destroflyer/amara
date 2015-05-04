/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import java.util.LinkedList;
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
public class HUDAttachmentsSystem implements EntitySystem{

    public HUDAttachmentsSystem(Node guiNode, Camera camera, MapHeightmap mapHeightmap){
        this.guiNode = guiNode;
        this.camera = camera;
        this.mapHeightmap = mapHeightmap;
    }
    protected Node guiNode;
    private Camera camera;
    private MapHeightmap mapHeightmap;
    private boolean isEnabled = true;
    private HashMap<String, HUDAttachmentInfo> hudAttachmentInfos = new HashMap<String, HUDAttachmentInfo>();
    private LinkedList<String> attachmentIDsToRemove = new LinkedList<String>();
    private Vector3f tmpEntityPosition = new Vector3f();
    private Vector3f tmpAttachmentPosition = new Vector3f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(HUDAttachmentInfo hudAttachmentInfo : hudAttachmentInfos.values()){
            PositionComponent positionComponent = entityWorld.getComponent(hudAttachmentInfo.getEntity(), PositionComponent.class);
            if(positionComponent != null){
                tmpEntityPosition.set(positionComponent.getPosition().getX(), mapHeightmap.getHeight(positionComponent.getPosition()), positionComponent.getPosition().getY());
                Spatial hudAttachment = getHUDAttachment(hudAttachmentInfo.getAttachmentID());
                if(hudAttachment != null){
                    camera.getScreenCoordinates(tmpEntityPosition.addLocal(hudAttachmentInfo.getWorldOffset()), tmpAttachmentPosition).addLocal(hudAttachmentInfo.getHUDOffset());
                    hudAttachment.setLocalTranslation(tmpAttachmentPosition);
                }
                else{
                    attachmentIDsToRemove.add(hudAttachmentInfo.getAttachmentID());
                }
            }
        }
        for(String attachmentID : attachmentIDsToRemove){
            detach(attachmentID);
        }
        attachmentIDsToRemove.clear();
    }
    
    public void attach(HUDAttachmentInfo hudAttachmentInfo, Spatial hudAttachment){
        hudAttachment.setName(hudAttachmentInfo.getAttachmentID());
        updateAttachmentVisibility(hudAttachment, isEnabled);
        guiNode.attachChild(hudAttachment);
        hudAttachmentInfos.put(hudAttachmentInfo.getAttachmentID(), hudAttachmentInfo);
    }
    
    public void detach(String attachmentID){
        guiNode.detachChildNamed(attachmentID);
        hudAttachmentInfos.remove(attachmentID);
    }
    
    public Spatial getHUDAttachment(String attachmentID){
        return guiNode.getChild(attachmentID);
    }

    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
        for(HUDAttachmentInfo hudAttachmentInfo : hudAttachmentInfos.values()){
            Spatial hudAttachment = getHUDAttachment(hudAttachmentInfo.getAttachmentID());
            updateAttachmentVisibility(hudAttachment, isEnabled);
        }
    }
    
    private void updateAttachmentVisibility(Spatial hudAttachment, boolean isVisible){
        hudAttachment.setCullHint(isVisible?Spatial.CullHint.Inherit: Spatial.CullHint.Always);
    }

    public boolean isEnabled(){
        return isEnabled;
    }
}
