package amara.applications.ingame.client.systems.gui;

import java.awt.image.BufferedImage;
import java.awt.Color;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.systems.filters.FogOfWarSystem;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.client.systems.visualisation.OwnTeamVisionSystem;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.cameras.MapCamera_TopDown;
import amara.core.files.FileAssets;
import amara.core.settings.Settings;
import amara.libraries.applications.display.ingame.appstates.IngameCameraAppState;
import amara.libraries.applications.display.materials.PaintableImage;
import amara.libraries.entitysystem.*;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture2D;

public class DisplayMinimapSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayMinimapSystem(
        PlayerAppState playerAppState,
        ScreenController_HUD screenController_HUD,
        Map map,
        PlayerTeamSystem playerTeamSystem,
        OwnTeamVisionSystem ownTeamVisionSystem,
        FogOfWarSystem fogOfWarSystem,
        IngameCameraAppState ingameCameraAppState
    ) {
        super(playerAppState, screenController_HUD);
        this.map = map;
        this.playerTeamSystem = playerTeamSystem;
        this.ownTeamVisionSystem = ownTeamVisionSystem;
        this.fogOfWarSystem = fogOfWarSystem;
        this.ingameCameraAppState = ingameCameraAppState;
        scaleX_Map = (minimapImage.getWidth() / map.getMinimapInformation().getWidth());
        scaleY_Map = (minimapImage.getHeight() / map.getMinimapInformation().getHeight());
        BufferedImage backgroundImage = FileAssets.getImage("Maps/" + map.getName() + "/minimap.png", minimapImage.getWidth(), minimapImage.getHeight());
        minimapImage.loadImage(backgroundImage, false);
        backgroundImageData = minimapImage.getData().clone();
        towerImage = FileAssets.getImage("Interface/hud/minimap/tower.png");
    }
    private static final Color COLOR_BORDER = new Color(34, 34, 34);
    private static final Color COLOR_TEAM_ALLIED = new Color(0, 0.9f, 0);
    private static final Color COLOR_TEAM_ENEMY = new Color(0.9f, 0.2f, 0);
    private static final Color COLOR_TEAM_OTHER = new Color(100, 50, 0);
    private static final Color COLOR_CAMERA_BOUNDS = new Color(255, 255, 255);
    private Map map;
    private PlayerTeamSystem playerTeamSystem;
    private OwnTeamVisionSystem ownTeamVisionSystem;
    private FogOfWarSystem fogOfWarSystem;
    private IngameCameraAppState ingameCameraAppState;
    private PaintableImage minimapImage = new PaintableImage(264, 264);
    private Texture2D texture2D = new Texture2D();
    private float scaleX_Map;
    private float scaleY_Map;
    private byte[] backgroundImageData;
    private BufferedImage towerImage;
    private boolean hasCameraMovedSinceLastUpdate;
    private boolean isInitialized;
    private float timeSinceLastUpdate;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        hasCameraMovedSinceLastUpdate |= ingameCameraAppState.hasMoved();
        if (isInitialized) {
            timeSinceLastUpdate += deltaSeconds;
            if (timeSinceLastUpdate > Settings.getFloat("minimap_update_interval")) {
                updateMinimap(entityWorld);
            }
        } else {
            updateMinimap(entityWorld);
            isInitialized = true;
        }
    }

    private void updateMinimap(EntityWorld entityWorld) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, PositionComponent.class);
        if (hasCameraMovedSinceLastUpdate || (!observer.getNew().isEmpty()) || (!observer.getChanged().isEmpty()) || (!observer.getRemoved().isEmpty())) {
            minimapImage.setData(backgroundImageData);
            for (int entity : entityWorld.getEntitiesWithAny(PositionComponent.class)) {
                if (ownTeamVisionSystem.isVisible(entityWorld, entity)) {
                    paintEntity(entityWorld, entity);
                }
            }
            if (fogOfWarSystem.isEnabled()) {
                paintFogOfWar();
            }
            paintCameraBounds();
            minimapImage.flipY();
            texture2D.setImage(minimapImage.getImage());
            screenController.setMinimapImage(texture2D);
        }
        hasCameraMovedSinceLastUpdate = false;
        timeSinceLastUpdate = 0;
    }

    private void paintEntity(EntityWorld entityWorld, int entity) {
        Color color;
        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        int x = getPixelPosition_X(position.getX());
        int y = getPixelPosition_X(position.getY());
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        if ((teamComponent != null) && (teamComponent.getTeamEntity() != 0)) {
            color = (playerTeamSystem.isAllied(teamComponent)?COLOR_TEAM_ALLIED:COLOR_TEAM_ENEMY);
        } else{
            color = COLOR_TEAM_OTHER;
        }
        if (entityWorld.hasComponent(entity, IsMinionComponent.class)){
            paintSquare(x, y, color, 2);
        } else if (entityWorld.hasComponent(entity, IsCharacterComponent.class)) {
            paintSquare(x, y, color, 3);
        } else if (entityWorld.hasComponent(entity, IsMonsterComponent.class)) {
            paintSquare(x, y, color, 2);
        } else if (entityWorld.hasComponent(entity, IsStructureComponent.class)) {
            int towerX = (x - (towerImage.getWidth() / 2));
            int towerY = (y - (towerImage.getHeight() / 2));
            for (int i = 1; i < (towerImage.getWidth() - 1); i++) {
                for (int r = 1; r < (towerImage.getHeight() - 1); r++) {
                    minimapImage.setPixel(towerX + i, towerY + r, color);
                }
            }
            minimapImage.paintImage(towerImage, towerX, towerY);
        }
    }

    private void paintSquare(int centerX, int centerY, Color color, int halfExtent) {
        int startX = Math.max(0, centerX - halfExtent);
        int startY = Math.max(0, centerY - halfExtent);
        int endX = Math.min(centerX + halfExtent, minimapImage.getWidth() - 1);
        int endY = Math.min(centerY + halfExtent, minimapImage.getHeight() - 1);
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                boolean isBorder = ((x == startX) || (x == endX) || (y == startY) || (y == endY));
                minimapImage.setPixel(x, y, (isBorder ? COLOR_BORDER : color));
            }
        }
    }

    private void paintFogOfWar() {
        // Recalculating is easier than subscribing (the fog of war resolution setting can be changed at runtime)
        float scaleX_Fog = (fogOfWarSystem.getFogImage().getWidth() / map.getPhysicsInformation().getWidth());
        float scaleY_Fog = (fogOfWarSystem.getFogImage().getHeight() / map.getPhysicsInformation().getHeight());
        for (int x = 0; x < minimapImage.getWidth(); x++) {
            for (int y = 0; y < minimapImage.getHeight(); y++) {
                float mapX = (map.getPhysicsInformation().getWidth() - (((x / scaleX_Map) + map.getMinimapInformation().getX())));
                float mapY = (map.getPhysicsInformation().getHeight() - (((y / scaleY_Map) + map.getMinimapInformation().getY())));
                // Check for the maximum boundary since the physical size of the map reaches 1 unit further than the images
                int fogX = Math.min((int) (mapX * scaleX_Fog), (fogOfWarSystem.getFogImage().getWidth() - 1));
                int fogY = Math.min((int) (mapY * scaleY_Fog), (fogOfWarSystem.getFogImage().getHeight() - 1));
                int visibility = fogOfWarSystem.getFogImage().getPixel_Red(fogX, fogY);
                int indexRed = minimapImage.getIndex(x, y, 0);
                int red = ((minimapImage.getPixel(indexRed) * visibility) / 255);
                int green = ((minimapImage.getPixel(indexRed + 1) * visibility) / 255);
                int blue = ((minimapImage.getPixel(indexRed + 2) * visibility) / 255);
                minimapImage.setPixel(indexRed, red);
                minimapImage.setPixel(indexRed + 1, green);
                minimapImage.setPixel(indexRed + 2, blue);
            }
        }
    }

    private void paintCameraBounds() {
        // TODO: Paint the camera bounds when in 3rd person camera
        if (map.getCamera() instanceof MapCamera_TopDown) {
            Vector3f leftTop = ingameCameraAppState.getLeftTopCornerWorldSurfaceLocation();
            Vector3f rightBottom = ingameCameraAppState.getRightBottomCornerWorldSurfaceLocation();
            int startX = Math.max(0, Math.min(getPixelPosition_X(leftTop.getX()), minimapImage.getWidth() - 1));
            int startY = Math.max(0, Math.min(getPixelPosition_Y(leftTop.getZ()), minimapImage.getHeight() - 1));
            int endX = Math.max(0, Math.min(getPixelPosition_X(rightBottom.getX()), minimapImage.getWidth() - 1));
            int endY = Math.max(0, Math.min(getPixelPosition_Y(rightBottom.getZ()), minimapImage.getHeight() - 1));
            for (int x = startX; x <= endX; x++) {
                minimapImage.setPixel(x, startY, COLOR_CAMERA_BOUNDS);
                minimapImage.setPixel(x, endY, COLOR_CAMERA_BOUNDS);
            }
            for (int y = (startY + 1); y <= (endY - 1); y++) {
                minimapImage.setPixel(startX, y, COLOR_CAMERA_BOUNDS);
                minimapImage.setPixel(endX, y, COLOR_CAMERA_BOUNDS);
            }
        }
    }

    private int getPixelPosition_X(float mapX) {
        return getPixelPosition(mapX, map.getPhysicsInformation().getWidth(), map.getMinimapInformation().getX(), scaleX_Map);
    }

    private int getPixelPosition_Y(float mapY) {
        return getPixelPosition(mapY, map.getPhysicsInformation().getHeight(), map.getMinimapInformation().getY(), scaleY_Map);
    }

    private static int getPixelPosition(float mapPosition, float mapSize, float minimapPosition, float scaleMap) {
        return Math.round(((mapSize - mapPosition) - minimapPosition) * scaleMap);
    }
}
