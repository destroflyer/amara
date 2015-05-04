/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import amara.engine.materials.MaterialFactory;
import amara.engine.applications.ingame.client.systems.visualisation.meshes.RectangleMesh;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class CurrentHealthBarSystem extends SimpleHUDAttachmentSystem{

    public CurrentHealthBarSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntitySceneMap entitySceneMap){
        super(hudAttachmentsSystem, HealthComponent.class);
        this.entitySceneMap = entitySceneMap;
        hudOffset = new Vector3f(0, 0, 1);
    }
    private EntitySceneMap entitySceneMap;
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        HealthComponent healthComponent = entityWorld.getComponent(entity, HealthComponent.class);
        MaximumHealthComponent maximumHealthComponent = entityWorld.getComponent(entity, MaximumHealthComponent.class);
        if((healthComponent != null) && (maximumHealthComponent != null)){
            Geometry geometry = new Geometry();
            Material material = MaterialFactory.generateUnshadedMaterial(ColorRGBA.Black);
            geometry.setMaterial(material);
            return geometry;
        }
        return null;
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        Geometry geometry = (Geometry) visualAttachment;
        float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
        float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
        float healthPortion = (1 - (health / maximumHealth));
        geometry.setMesh(new RectangleMesh((MaximumHealthBarSystem.BAR_WIDTH / 2) - (healthPortion * MaximumHealthBarSystem.BAR_WIDTH), 0, 0, (healthPortion * MaximumHealthBarSystem.BAR_WIDTH), MaximumHealthBarSystem.BAR_HEIGHT));
    }

    @Override
    protected Vector3f getWorldOffset(EntityWorld entityWorld, int entity){
        return MaximumHealthBarSystem.getWorldOffset(entityWorld, entity, entitySceneMap);
    }
}
