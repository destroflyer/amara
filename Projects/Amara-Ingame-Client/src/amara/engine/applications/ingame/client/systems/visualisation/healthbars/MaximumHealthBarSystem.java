/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.healthbars;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent;
import amara.engine.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.libraries.applications.display.materials.PaintableImage;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class MaximumHealthBarSystem extends TopHUDAttachmentSystem{

    public MaximumHealthBarSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, HealthBarStyleManager healthBarStyleManager, PlayerTeamSystem playerTeamSystem){
        super(hudAttachmentsSystem, entityHeightMap, MaximumHealthComponent.class);
        this.healthBarStyleManager = healthBarStyleManager;
        this.playerTeamSystem = playerTeamSystem;
    }
    private HealthBarStyleManager healthBarStyleManager;
    private PlayerTeamSystem playerTeamSystem;
    private Vector3f preparedHudOffset;
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        return healthBarStyleManager.createGeometry(entityWorld, entity);
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        HealthBarStyle style = healthBarStyleManager.getStyle(entityWorld, entity);
        PaintableImage paintableImage = healthBarStyleManager.getImage_MaximumHealth(entity, style);
        float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
        style.drawMaximumHealth(paintableImage, maximumHealth, playerTeamSystem.isAllied(entityWorld, entity));
        Geometry geometry = (Geometry) visualAttachment;
        Texture texture = geometry.getMaterial().getTextureParam("ColorMap").getTextureValue();
        texture.setImage(paintableImage.getImage());
        preparedHudOffset = hudOffset.clone().setY(-1 * style.getBarHeight());
    }

    @Override
    protected Vector3f getPreparedHUDOffset(){
        return preparedHudOffset;
    }
}
