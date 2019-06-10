/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.filters;

import java.util.ArrayList;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture2D;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.shared.maps.MapPhysicsInformation;
import amara.core.settings.Settings;
import amara.libraries.applications.display.appstates.PostFilterAppState;
import amara.libraries.applications.display.filters.FogOfWarFilter;
import amara.libraries.applications.display.materials.*;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.PolyHelper;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.*;
import amara.libraries.physics.shapes.vision.*;

/**
 *
 * @author Carl
 */
public class FogOfWarSystem implements EntitySystem {

    public FogOfWarSystem(PlayerTeamSystem playerTeamSystem, PostFilterAppState postFilterAppState, final MapPhysicsInformation mapPhysicsInformation) {
        this.playerTeamSystem = playerTeamSystem;
        this.mapPhysicsInformation = mapPhysicsInformation;
        Vector2D[] mapBorderPoints = new Vector2D[]{
            new Vector2D(0, 0),
            new Vector2D(0, mapPhysicsInformation.getHeight()),
            new Vector2D(mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight()),
            new Vector2D(mapPhysicsInformation.getWidth(), 0)
        };
        teamVision = new MergedVision(mapBorderPoints, VisionObstacle.generateDefaultObstacles(mapPhysicsInformation.getObstacles()));
        teamVision.setEnableSightInSolidObstacles(true);
        float resolutionFactor = Settings.getFloat("fog_of_war_resolution");
        fogImage = new PaintableImage((int) (mapPhysicsInformation.getWidth() * resolutionFactor), (int) (mapPhysicsInformation.getHeight() * resolutionFactor));
        fogRaster = new ImageChannelRaster(fogImage, resolutionFactor, 80, 255, 0);
        fogOfWarFilter = new FogOfWarFilter() {

            @Override
            protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort viewPort, int width, int height) {
                super.initFilter(manager, renderManager, viewPort, width, height);
                setMapSize(mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight());
            }
        };
        postFilterAppState.addFilter(fogOfWarFilter);
    }
    private PlayerTeamSystem playerTeamSystem;
    private MapPhysicsInformation mapPhysicsInformation;
    private MergedVision teamVision;
    private PaintableImage fogImage;
    private Texture2D fogTexture = new Texture2D();
    private ImageChannelRaster fogRaster;
    private FogOfWarFilter fogOfWarFilter;
    private boolean isInitialized;
    private float timeSinceLastUpdate;
    private boolean isUpdateNeeded;
    private boolean displayAllSight;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        if (isInitialized) {
            timeSinceLastUpdate += deltaSeconds;
            if (isUpdateNeeded || (timeSinceLastUpdate > Settings.getFloat("fog_of_war_update_interval"))) {
                updateFogOfWar(entityWorld);
            }
        } else {
            updateFogOfWar(entityWorld);
            isInitialized = true;
        }
    }

    private void updateFogOfWar(EntityWorld entityWorld){
        if(!isUpdateNeeded){
            ComponentMapObserver observer = entityWorld.requestObserver(this, IsHiddenAreaComponent.class, PositionComponent.class);
            //Hidden areas
            for (int entity : observer.getNew().getEntitiesWithAny(IsHiddenAreaComponent.class)) {
                HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
                if(hitboxComponent != null){
                    teamVision.setObstacle(entity, new VisionObstacle((ConvexShape) hitboxComponent.getShape(), false));
                    isUpdateNeeded = true;
                }
            }
            for (int entity : observer.getRemoved().getEntitiesWithAny(IsHiddenAreaComponent.class)) {
                teamVision.removeObstacle(entity);
                isUpdateNeeded = true;
            }
            //Moved units
            for (int entity : observer.getNew().getEntitiesWithAny(PositionComponent.class)) {
                checkChangedPosition(entityWorld, entity);
            }
            for (int entity : observer.getChanged().getEntitiesWithAny(PositionComponent.class)) {
                checkChangedPosition(entityWorld, entity);
            }
        }
        if (isUpdateNeeded) {
            updateFogTexture_PlayerSight(entityWorld);
            isUpdateNeeded = false;
        }
        timeSinceLastUpdate = 0;
    }

    private void checkChangedPosition(EntityWorld entityWorld, int entity) {
        if (entityWorld.hasComponent(entity, SightRangeComponent.class) && isEntitySightIncluded(entityWorld, entity)) {
            isUpdateNeeded = true;
        }
    }

    public void setDisplayAllSight(boolean displayAllSight) {
        if (displayAllSight != this.displayAllSight) {
            this.displayAllSight = displayAllSight;
            isUpdateNeeded = true;
        }
    }

    private void updateFogTexture_PlayerSight(EntityWorld entityWorld) {
        resetFogTexture();
        for (int entity : entityWorld.getEntitiesWithAll(PositionComponent.class, SightRangeComponent.class)) {
            if (isEntitySightIncluded(entityWorld, entity)) {
                PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
                Vector2D position = new Vector2D(positionComponent.getPosition().getX(), positionComponent.getPosition().getY());
                float sightRange = entityWorld.getComponent(entity, SightRangeComponent.class).getRange();
                Polygon sightPolygon;
                if (true) {
                    //Fast way using shapes
                    ArrayList<Vector2D> sightOutline = teamVision.getSightOutline(position, sightRange);
                    sightPolygon = PolyHelper.fromOutline(sightOutline);
                } else {
                    //Slow way using polygons
                    sightPolygon = mapPhysicsInformation.getPolyMapManager().sightPolygon(position, sightRange);
                }
                sightPolygon.rasterize(fogRaster, position, sightRange);
            }
        }
        fogTexture.setImage(fogImage.getImage());
        fogOfWarFilter.setFog(fogTexture);
    }

    private boolean isEntitySightIncluded(EntityWorld entityWorld, int entity) {
        return (displayAllSight || playerTeamSystem.isAllied(entityWorld, entity));
    }

    private void resetFogTexture() {
        int bytesCount = (4 * fogImage.getWidth() * fogImage.getHeight());
        for (int i = 0; i < bytesCount; i += 4) {
            fogImage.setPixel(i, 80);
        }
    }

    public void setEnabled(boolean enabled) {
        fogOfWarFilter.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return fogOfWarFilter.isEnabled();
    }

    public PaintableImage getFogImage() {
        return fogImage;
    }

    public boolean isDisplayAllSight() {
        return displayAllSight;
    }
}
