package amara.applications.ingame.client.systems.visualisation;

import amara.applications.ingame.client.systems.visualisation.meshes.RectangleMesh;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.AggroTargetComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsMinionComponent;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class MinionAggroIndicatorSystem extends TopHUDAttachmentSystem {

    public MinionAggroIndicatorSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, AssetManager assetManager, int playerEntity){
        super(hudAttachmentsSystem, entityHeightMap, AggroTargetComponent.class, assetManager);
        this.playerEntity = playerEntity;
    }
    private int playerEntity;

    @Override
    protected boolean shouldHaveVisualAttachment(EntityWorld entityWorld, int entity) {
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
        if ((playerCharacterComponent != null) && entityWorld.hasComponent(entity, IsMinionComponent.class)) {
            AggroTargetComponent aggroTargetComponent = entityWorld.getComponent(entity, AggroTargetComponent.class);
            if (aggroTargetComponent != null) {
                return (aggroTargetComponent.getTargetEntity() == playerCharacterComponent.getEntity());
            }
        }
        return false;
    }

    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity) {
        Geometry geometry = new Geometry("", new RectangleMesh(-7.5f, 0, 0, 15, 11));
        Material material = MaterialFactory.generateUnshadedMaterial(assetManager, "Textures/effects/minion_aggro_indicator.png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        MaterialFactory.setFilter_Nearest(material);
        geometry.setMaterial(material);
        return geometry;
    }
}
