/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import amara.engine.materials.MaterialFactory;
import amara.engine.applications.ingame.client.systems.visualisation.meshes.RectangleMesh;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class CurrentHealthBarSystem extends SimpleVisualAttachmentSystem{

    public CurrentHealthBarSystem(EntitySceneMap entitySceneMap){
        super(entitySceneMap, HealthComponent.class);
    }
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        HealthComponent healthComponent = entityWorld.getComponent(entity, HealthComponent.class);
        MaximumHealthComponent maximumHealthComponent = entityWorld.getComponent(entity, MaximumHealthComponent.class);
        if((healthComponent != null) && (maximumHealthComponent != null)){
            float health = healthComponent.getValue();
            float maximumHealth = maximumHealthComponent.getValue();
            float healthPortion = (1 - (health / maximumHealth));
            Geometry geometry = new Geometry("", new RectangleMesh((MaximumHealthBarSystem.BAR_WIDTH / 2) - (healthPortion * MaximumHealthBarSystem.BAR_WIDTH), 0, 0, (healthPortion * MaximumHealthBarSystem.BAR_WIDTH), MaximumHealthBarSystem.BAR_HEIGHT));
            Material material = MaterialFactory.generateUnshadedMaterial(ColorRGBA.Black);
            material.getAdditionalRenderState().setDepthTest(false);
            geometry.setMaterial(material);
            geometry.addControl(new BillboardControl());
            geometry.setLocalTranslation(MaximumHealthBarSystem.BAR_LOCATION);
            geometry.setUserData("layer", 6);
            return geometry;
        }
        return null;
    }
}