package amara.applications.ingame.client.systems.visualisation.effects.reactions;

import amara.applications.ingame.client.systems.visualisation.EntityHeightMap;
import amara.applications.ingame.client.systems.visualisation.HUDAttachmentsSystem;
import amara.applications.ingame.client.systems.visualisation.TopHUDAttachmentSystem;
import amara.applications.ingame.client.systems.visualisation.meshes.SpeechBubbleMesh;
import amara.applications.ingame.entitysystem.components.units.ReactionComponent;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class ReactionVisualisationSystem extends TopHUDAttachmentSystem {

    public ReactionVisualisationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, AssetManager assetManager) {
        super(hudAttachmentsSystem, entityHeightMap, ReactionComponent.class, assetManager);
        hudOffset = new Vector3f(0, 26, 0);
    }

    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity) {
        return new Geometry("", new SpeechBubbleMesh());
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment) {
        Geometry geometry = (Geometry) visualAttachment;
        String reaction = entityWorld.getComponent(entity, ReactionComponent.class).getReaction();
        Material material = MaterialFactory.generateUnshadedMaterial(assetManager, "Textures/effects/reactions/" + reaction + ".png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
    }
}
