/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.effects.reactions;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.applications.ingame.client.systems.visualisation.meshes.SpeechBubbleMesh;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.components.units.ReactionComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ReactionVisualisationSystem extends TopHUDAttachmentSystem{

    public ReactionVisualisationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap){
        super(hudAttachmentsSystem, entityHeightMap, ReactionComponent.class);
        hudOffset = new Vector3f(0, 26, 0);
    }
    
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        Geometry geometry = new Geometry("", new SpeechBubbleMesh());
        return geometry;
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        Geometry geometry = (Geometry) visualAttachment;
        String reaction = entityWorld.getComponent(entity, ReactionComponent.class).getReaction();
        Material material = MaterialFactory.generateUnshadedMaterial("Textures/effects/reactions/" + reaction + ".png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
    }
}
