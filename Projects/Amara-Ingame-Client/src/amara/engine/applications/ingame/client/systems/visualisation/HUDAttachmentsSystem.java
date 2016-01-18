/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import java.util.LinkedList;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.healthbars.HealthBarStyleManager;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.libraries.applications.display.ingame.maps.MapHeightmap;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class HUDAttachmentsSystem implements EntitySystem{

    public HUDAttachmentsSystem(Node guiNode, Camera camera, MapHeightmap mapHeightmap, HealthBarStyleManager healthBarStyleManager){
        this.guiNode = guiNode;
        this.camera = camera;
        this.mapHeightmap = mapHeightmap;
        this.healthBarStyleManager = healthBarStyleManager;
    }
    protected Node guiNode;
    private Camera camera;
    private MapHeightmap mapHeightmap;
    private HealthBarStyleManager healthBarStyleManager;
    private boolean isEnabled = true;
    private HashMap<String, HUDAttachmentInfo> hudAttachmentInfos = new HashMap<String, HUDAttachmentInfo>();
    private LinkedList<String> attachmentIDsToRemove = new LinkedList<String>();
    private Vector3f tmpEntityPosition = new Vector3f();
    private Vector3f tmpAttachmentPosition = new Vector3f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(HUDAttachmentInfo hudAttachmentInfo : hudAttachmentInfos.values()){
            Vector2f gameLocation = hudAttachmentInfo.getFixedGameLocation();
            if(hudAttachmentInfo.isFollowEntity() || (gameLocation == null)){
                PositionComponent positionComponent = entityWorld.getComponent(hudAttachmentInfo.getEntity(), PositionComponent.class);
                if(positionComponent != null){
                    gameLocation = positionComponent.getPosition();
                    if(!hudAttachmentInfo.isFollowEntity()){
                        hudAttachmentInfo.setFixedGamedLocation(gameLocation);
                    }
                }
            }
            if(gameLocation != null){
                tmpEntityPosition.set(gameLocation.getX(), mapHeightmap.getHeight(gameLocation), gameLocation.getY());
                Spatial hudAttachment = getHUDAttachment(hudAttachmentInfo.getAttachmentID());
                if(hudAttachment != null){
                    float healthBarY = healthBarStyleManager.getHealthBarY(entityWorld, hudAttachmentInfo.getEntity());
                    camera.getScreenCoordinates(tmpEntityPosition.addLocal(hudAttachmentInfo.getWorldOffset()), tmpAttachmentPosition).addLocal(0, healthBarY, 0).addLocal(hudAttachmentInfo.getHUDOffset());
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
