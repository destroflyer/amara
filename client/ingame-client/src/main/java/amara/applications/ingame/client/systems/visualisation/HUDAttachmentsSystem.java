package amara.applications.ingame.client.systems.visualisation;

import java.util.HashMap;
import java.util.LinkedList;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.healthbars.HealthBarStyleManager;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.applications.display.ingame.maps.MapHeightmap;
import amara.libraries.entitysystem.*;

public class HUDAttachmentsSystem implements EntitySystem {

    public HUDAttachmentsSystem(Node guiNode, Camera camera, MapHeightmap mapHeightmap, HealthBarStyleManager healthBarStyleManager, OwnTeamVisionSystem ownTeamVisionSystem) {
        this.guiNode = guiNode;
        this.camera = camera;
        this.mapHeightmap = mapHeightmap;
        this.healthBarStyleManager = healthBarStyleManager;
        this.ownTeamVisionSystem = ownTeamVisionSystem;
    }
    protected Node guiNode;
    private Camera camera;
    private MapHeightmap mapHeightmap;
    private HealthBarStyleManager healthBarStyleManager;
    private OwnTeamVisionSystem ownTeamVisionSystem;
    private boolean isEnabled = true;
    private HashMap<String, HUDAttachmentInfo> hudAttachmentInfos = new HashMap<>();
    private LinkedList<String> attachmentIdsToRemove = new LinkedList<>();
    private Vector3f tmpEntityPosition = new Vector3f();
    private Vector3f tmpAttachmentPosition = new Vector3f();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (HUDAttachmentInfo hudAttachmentInfo : hudAttachmentInfos.values()) {
            Vector2f gameLocation = hudAttachmentInfo.getFixedGameLocation();
            if (hudAttachmentInfo.isFollowEntity() || (gameLocation == null)) {
                PositionComponent positionComponent = entityWorld.getComponent(hudAttachmentInfo.getEntity(), PositionComponent.class);
                if (positionComponent != null) {
                    gameLocation = positionComponent.getPosition();
                    if (!hudAttachmentInfo.isFollowEntity()) {
                        hudAttachmentInfo.setFixedGamedLocation(gameLocation);
                    }
                }
            }
            if (gameLocation != null) {
                tmpEntityPosition.set(gameLocation.getX(), mapHeightmap.getHeight(gameLocation), gameLocation.getY());
                Spatial hudAttachment = getHUDAttachment(hudAttachmentInfo.getAttachmentID());
                if (hudAttachment != null) {
                    float healthBarY = healthBarStyleManager.getHealthBarY(entityWorld, hudAttachmentInfo.getEntity());
                    camera.getScreenCoordinates(tmpEntityPosition.addLocal(hudAttachmentInfo.getWorldOffset()), tmpAttachmentPosition).addLocal(0, healthBarY, 0).addLocal(hudAttachmentInfo.getHUDOffset());
                    hudAttachment.setLocalTranslation(tmpAttachmentPosition);
                    boolean isVisible = (isEnabled && ownTeamVisionSystem.isVisible(entityWorld, hudAttachmentInfo.getEntity()));
                    hudAttachment.setCullHint(isVisible ? Spatial.CullHint.Inherit : Spatial.CullHint.Always);
                } else {
                    attachmentIdsToRemove.add(hudAttachmentInfo.getAttachmentID());
                }
            }
        }
        for (String attachmentId : attachmentIdsToRemove) {
            detach(attachmentId);
        }
        attachmentIdsToRemove.clear();
    }

    public void attach(HUDAttachmentInfo hudAttachmentInfo, Spatial hudAttachment) {
        hudAttachment.setName(hudAttachmentInfo.getAttachmentID());
        guiNode.attachChild(hudAttachment);
        hudAttachmentInfos.put(hudAttachmentInfo.getAttachmentID(), hudAttachmentInfo);
    }

    public void detach(String attachmentId) {
        guiNode.detachChildNamed(attachmentId);
        hudAttachmentInfos.remove(attachmentId);
    }

    public Spatial getHUDAttachment(String attachmentID) {
        return guiNode.getChild(attachmentID);
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
