/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.effects.crodwcontrol;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;

/**
 *
 * @author Carl
 */
public class StunVisualisationSystem extends SimpleHUDAttachmentSystem{

    public StunVisualisationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntitySceneMap entitySceneMap){
        super(hudAttachmentsSystem, IsStunnedComponent.class);
        this.entitySceneMap = entitySceneMap;
        hudOffset = new Vector3f(0, 26, 0);
    }
    private EntitySceneMap entitySceneMap;
    
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        Geometry geometry = new Geometry("", new SpeechBubbleMesh());
        Material material = MaterialFactory.generateUnshadedMaterial("Textures/effects/stun.png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        return geometry;
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        
    }

    @Override
    protected Vector3f getWorldOffset(EntityWorld entityWorld, int entity){
        return MaximumHealthBarSystem.getWorldOffset(entityWorld, entity, entitySceneMap);
    }
}
