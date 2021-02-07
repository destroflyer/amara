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
import amara.core.settings.SettingsSubscriptions;
import amara.libraries.applications.display.appstates.PostFilterAppState;
import amara.libraries.applications.display.filters.FogOfWarFilter;
import amara.libraries.applications.display.materials.*;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.PolyHelper;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.*;
import amara.libraries.physics.shapes.vision.*;

public class FogOfWarSystem implements EntitySystem {

    public FogOfWarSystem(PlayerTeamSystem playerTeamSystem, PostFilterAppState postFilterAppState, MapPhysicsInformation mapPhysicsInformation, SettingsSubscriptions settingsSubscriptions) {
        this.playerTeamSystem = playerTeamSystem;
        this.postFilterAppState = postFilterAppState;
        this.mapPhysicsInformation = mapPhysicsInformation;
        ArrayList<Vector2D> mapBorderCcwOutline = new ArrayList<>();
        mapBorderCcwOutline.add(new Vector2D(mapPhysicsInformation.getWidth(), 0));
        mapBorderCcwOutline.add(new Vector2D(mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight()));
        mapBorderCcwOutline.add(new Vector2D(0, mapPhysicsInformation.getHeight()));
        mapBorderCcwOutline.add(new Vector2D(0, 0));
        teamVision = new MergedVision(mapBorderCcwOutline, mapPhysicsInformation.generateVisionObstacles());
        // For fog of war, the obstacles are added reversed, so they are not darkened by the fog - Therefore, they have to be reversed when the player is inside
        teamVision.setReverseSightInsideObstacles(true);
        settingsSubscriptions.subscribeFloat("fog_of_war_resolution", fogOfWarResolution -> {
            isRecreationNeeded = true;
        });
    }
    private PlayerTeamSystem playerTeamSystem;
    private PostFilterAppState postFilterAppState;
    private MapPhysicsInformation mapPhysicsInformation;
    private MergedVision teamVision;
    private PaintableImage fogImage;
    private Texture2D fogTexture = new Texture2D();
    private ImageChannelRaster fogRaster;
    private FogOfWarFilter fogOfWarFilter;
    private boolean isRecreationNeeded;
    private boolean isUpdateNeeded;
    private float timeSinceLastUpdate;
    private boolean displayAllSight;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        if (isRecreationNeeded) {
            recreate();
            isRecreationNeeded = false;
            isUpdateNeeded = true;
        }
        timeSinceLastUpdate += deltaSeconds;
        if (isUpdateNeeded || (timeSinceLastUpdate > Settings.getFloat("fog_of_war_update_interval"))) {
            checkChangedEntities(entityWorld);
            if (isUpdateNeeded) {
                updateFogOfWar(entityWorld);
            }
        }
    }

    private void recreate() {
        if (fogOfWarFilter != null) {
            postFilterAppState.removeFilter(fogOfWarFilter);
        }
        float resolutionFactor = Settings.getFloat("fog_of_war_resolution");
        fogImage = new PaintableImage((int) (mapPhysicsInformation.getWidth() * resolutionFactor), (int) (mapPhysicsInformation.getHeight() * resolutionFactor));
        fogRaster = new ImageChannelRaster(fogImage, resolutionFactor, 80, 255, 0);
        fogOfWarFilter = new FogOfWarFilter() {

            @Override
            protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort viewPort, int width, int height) {
                super.initFilter(manager, renderManager, viewPort, width, height);
                setMapSize(mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight());
                isUpdateNeeded = true;
            }
        };
        postFilterAppState.addFilter(fogOfWarFilter);
    }

    private void checkChangedEntities(EntityWorld entityWorld) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsHiddenAreaComponent.class, PositionComponent.class, SightRangeComponent.class, TeamComponent.class);
        // Hidden areas
        for (int entity : observer.getNew().getEntitiesWithAny(IsHiddenAreaComponent.class)) {
            HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
            if (hitboxComponent != null) {
                SimpleConvexPolygon simpleConvexPolygon = (SimpleConvexPolygon) hitboxComponent.getShape();
                ConvexedOutline convexedOutline = new ConvexedOutline(simpleConvexPolygon);
                teamVision.setObstacle(entity, new VisionObstacle(convexedOutline, false));
                isUpdateNeeded = true;
            }
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(IsHiddenAreaComponent.class)) {
            teamVision.removeObstacle(entity);
            isUpdateNeeded = true;
        }
        // Units
        for (int entity : observer.getNew().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class, TeamComponent.class)) {
            requireUpdateIfRelevant(entityWorld, entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class, TeamComponent.class)) {
            requireUpdateIfRelevant(entityWorld, entity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class, TeamComponent.class)) {
            requireUpdateIfIsOrWasRelevant(entityWorld, observer, entity);
        }
    }

    private void requireUpdateIfRelevant(EntityWorld entityWorld, int entity) {
        if (entityWorld.hasComponent(entity, SightRangeComponent.class)) {
            if (isEntitySightIncluded(entityWorld, entity)) {
                isUpdateNeeded = true;
            }
        }
    }

    private void requireUpdateIfIsOrWasRelevant(EntityWorld entityWorld, ComponentMapObserver observer, int entity) {
        if (entityWorld.hasComponent(entity, SightRangeComponent.class) || observer.getRemoved().hasComponent(entity, SightRangeComponent.class)) {
            TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
            if (teamComponent == null) {
                teamComponent = observer.getRemoved().getComponent(entity, TeamComponent.class);
            }
            if (isEntitySightIncluded(teamComponent)) {
                isUpdateNeeded = true;
            }
        }
    }

    private void updateFogOfWar(EntityWorld entityWorld) {
        resetFogTexture();
        for (int entity : entityWorld.getEntitiesWithAll(PositionComponent.class, SightRangeComponent.class)) {
            if (isEntitySightIncluded(entityWorld, entity)) {
                PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
                Vector2D position = new Vector2D(positionComponent.getPosition().getX(), positionComponent.getPosition().getY());
                float sightRange = entityWorld.getComponent(entity, SightRangeComponent.class).getRange();
                ArrayList<Vector2D> sightOutline = teamVision.getSightOutline(position, sightRange);
                Polygon sightPolygon = PolyHelper.fromOutline(sightOutline);
                sightPolygon.rasterize(fogRaster, position, sightRange);
            }
        }
        fogTexture.setImage(fogImage.getImage());
        fogOfWarFilter.setFog(fogTexture);
        isUpdateNeeded = false;
        timeSinceLastUpdate = 0;
    }

    private void resetFogTexture() {
        int bytesCount = (4 * fogImage.getWidth() * fogImage.getHeight());
        for (int i = 0; i < bytesCount; i += 4) {
            fogImage.setPixel(i, 80);
        }
    }

    private boolean isEntitySightIncluded(EntityWorld entityWorld, int entity) {
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        return isEntitySightIncluded(teamComponent);
    }

    private boolean isEntitySightIncluded(TeamComponent teamComponent) {
        return (displayAllSight || playerTeamSystem.isAllied(teamComponent));
    }

    public void setDisplayAllSight(boolean displayAllSight) {
        if (displayAllSight != this.displayAllSight) {
            this.displayAllSight = displayAllSight;
            isUpdateNeeded = true;
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
