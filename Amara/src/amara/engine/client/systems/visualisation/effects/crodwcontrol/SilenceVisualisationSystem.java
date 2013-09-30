/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation.effects.crodwcontrol;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import amara.engine.client.MaterialFactory;
import amara.engine.client.systems.visualisation.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;

/**
 *
 * @author Carl
 */
public class SilenceVisualisationSystem extends SimpleVisualAttachmentSystem{

    public SilenceVisualisationSystem(EntitySceneMap entitySceneMap){
        super(entitySceneMap, IsSilencedComponent.class);
    }
    
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        Geometry geometry = new Geometry("", new SpeechBubbleMesh());
        Material material = MaterialFactory.generateUnshadedMaterial("Textures/effects/silence.png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setDepthTest(false);
        geometry.setMaterial(material);
        geometry.addControl(new BillboardControl());
        geometry.setLocalTranslation(0, 2, 0);
        geometry.setUserData("layer", 3);
        return geometry;
    }
}
