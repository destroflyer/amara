package amara.applications.ingame.client.systems.visualisation.healthbars;

import amara.applications.ingame.client.systems.visualisation.EntityHeightMap;
import amara.applications.ingame.client.systems.visualisation.HUDAttachmentsSystem;
import amara.applications.ingame.client.systems.visualisation.TopHUDAttachmentSystem;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.MaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.units.ShieldsComponent;
import amara.applications.ingame.entitysystem.components.units.shields.ActiveShieldComponent;
import amara.applications.ingame.entitysystem.components.units.shields.ShieldAmountComponent;
import amara.applications.ingame.entitysystem.systems.units.shields.ShieldUtil;
import amara.libraries.applications.display.materials.PaintableImage;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

public class HealthBarSystem extends TopHUDAttachmentSystem {

    public HealthBarSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, HealthBarStyleManager healthBarStyleManager, PlayerTeamSystem playerTeamSystem) {
        super(hudAttachmentsSystem, entityHeightMap, new Class[] { MaximumHealthComponent.class, HealthComponent.class }, new Class[] { ShieldsComponent.class, ShieldAmountComponent.class });
        this.healthBarStyleManager = healthBarStyleManager;
        this.playerTeamSystem = playerTeamSystem;
    }
    private HealthBarStyleManager healthBarStyleManager;
    private PlayerTeamSystem playerTeamSystem;
    private Vector3f preparedHudOffset;

    @Override
    protected int getPrimaryEntityBySecondaryComponent(EntityWorld entityWorld, int entity, Class<?> secondaryComponentClass) {
        if (secondaryComponentClass == ShieldAmountComponent.class) {
            ActiveShieldComponent activeShieldComponent = entityWorld.getComponent(entity, ActiveShieldComponent.class);
            if (activeShieldComponent != null) {
                // Shield status entity
                return activeShieldComponent.getTargetEntity();
            } else {
                // Shield entity
                return -1;
            }
        } else {
            return entity;
        }
    }

    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity) {
        return healthBarStyleManager.createGeometry(entityWorld, entity);
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment) {
        HealthBarStyle style = healthBarStyleManager.getStyle(entityWorld, entity);
        PaintableImage paintableImage = healthBarStyleManager.getImage_MaximumHealth(entity, style);
        float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
        float currentHealth = entityWorld.getComponent(entity, HealthComponent.class).getValue();
        float totalShieldAmount = ShieldUtil.getTotalShieldAmount(entityWorld, entity);
        boolean isAllied = playerTeamSystem.isAllied(entityWorld, entity);
        style.draw(paintableImage, maximumHealth, currentHealth, totalShieldAmount, isAllied);
        Geometry geometry = (Geometry) visualAttachment;
        Texture texture = geometry.getMaterial().getTextureParam("ColorMap").getTextureValue();
        texture.setImage(paintableImage.getImage());
        preparedHudOffset = hudOffset.clone().setY(-1 * style.getBarHeight());
    }

    @Override
    protected Vector3f getPreparedHUDOffset() {
        return preparedHudOffset;
    }
}
