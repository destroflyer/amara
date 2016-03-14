/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.meshes.RectangleMesh;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class MinionAggroIndicatorSystem extends TopHUDAttachmentSystem{

    public MinionAggroIndicatorSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, int playerEntity){
        super(hudAttachmentsSystem, entityHeightMap, AggroTargetComponent.class);
        this.playerEntity = playerEntity;
    }
    private int playerEntity;
    
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        if(isTargetingPlayerCharacter(entityWorld, entity)){
            Geometry geometry = new Geometry("", new RectangleMesh(-7.5f, 0, 0, 15, 11));
            Material material = MaterialFactory.generateUnshadedMaterial("Textures/effects/minion_aggro_indicator.png");
            material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            MaterialFactory.setFilter_Nearest(material);
            geometry.setMaterial(material);
            return geometry;
        }
        return null;
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        if(!isTargetingPlayerCharacter(entityWorld, entity)){
            detach(entity);
        }
    }
    
    private boolean isTargetingPlayerCharacter(EntityWorld entityWorld, int entity){
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
        if((playerCharacterComponent != null) && entityWorld.hasComponent(entity, IsMinionComponent.class)){
            int aggroTargetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            return (aggroTargetEntity == playerCharacterComponent.getEntity());
        }
        return false;
    }
}
