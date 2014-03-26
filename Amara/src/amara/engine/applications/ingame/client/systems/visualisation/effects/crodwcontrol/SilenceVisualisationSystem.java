/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.effects.crodwcontrol;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.maps.MapHeightmap;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;

/**
 *
 * @author Carl
 */
public class SilenceVisualisationSystem extends HUDAttachmentSystem{

    public SilenceVisualisationSystem(Node guiNode, Camera camera, MapHeightmap mapHeightmap){
        super(IsSilencedComponent.class, true, guiNode, camera, mapHeightmap);
        worldOffset = MaximumHealthBarSystem.BAR_LOCATION;
        hudOffset = new Vector3f(0, 26, 0);
    }
    
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        Geometry geometry = new Geometry("", new SpeechBubbleMesh());
        Material material = MaterialFactory.generateUnshadedMaterial("Textures/effects/silence.png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        return geometry;
    }
}
