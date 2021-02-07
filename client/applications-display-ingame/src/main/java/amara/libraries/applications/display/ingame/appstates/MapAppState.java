package amara.libraries.applications.display.ingame.appstates;

import java.util.ArrayList;
import java.util.HashMap;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.Listener;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.post.Filter;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import amara.applications.ingame.shared.maps.*;
import amara.applications.ingame.shared.maps.cameras.*;
import amara.applications.ingame.shared.maps.filters.*;
import amara.applications.ingame.shared.maps.lights.*;
import amara.applications.ingame.shared.maps.visuals.*;
import amara.libraries.applications.display.*;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.maps.*;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.models.objects.SnowEmitter;

public class MapAppState extends BaseDisplayAppState<DisplayApplication> {

    public MapAppState(Map map) {
        this.map = map;
    }
    private Map map;
    private MapHeightmap mapHeightmap;
    private MapTerrain mapTerrain;
    private Geometry groundHeightPlane;
    private Node visualsNode = new Node();
    private HashMap<ModelObject, MapVisual> modelObjectsVisuals = new HashMap<>();
    private Node cameraNode = new Node();
    private ChaseCamera chaseCamera;
    private HashMap<MapLight_Directional_Shadows, DirectionalLightShadowFilter> shadowFilters = new HashMap<>();
    private HashMap<MapFilter, SSAOFilter> ssaoFilters = new HashMap<>();
    private ArrayList<Filter> activeFilters = new ArrayList<>();

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        mapHeightmap = new MapHeightmap(map.getName(), map.getPhysicsInformation());
        mapTerrain = new MapTerrain(map, getAppState(SettingsAppState.class), mainApplication.getAssetManager());
        mainApplication.getRootNode().attachChild(mapTerrain.getNode());
        mainApplication.getRootNode().attachChild(visualsNode);
        mainApplication.getRootNode().attachChild(cameraNode);
        initializeGroundHeightPlane();
        initializeCamera();
        new Thread(() -> {
            removeFilters();
            initializeLights();
            initializeFilters();
            updateVisuals();
        }).start();
    }

    private void initializeGroundHeightPlane() {
        // Make the plane way bigger than the map to ensure that the camera always covers it when at the edges
        groundHeightPlane = new Geometry(null, new Quad(map.getPhysicsInformation().getWidth() * 3, map.getPhysicsInformation().getHeight() * 3));
        groundHeightPlane.setLocalTranslation((-1 * map.getPhysicsInformation().getWidth()), map.getPhysicsInformation().getGroundHeight(), (2 * map.getPhysicsInformation().getHeight()));
        groundHeightPlane.rotate(JMonkeyUtil.getQuaternion_X(-90));
    }

    public void initializeCamera() {
        IngameCameraAppState ingameCameraAppState = getAppState(IngameCameraAppState.class);
        Camera camera = mainApplication.getCamera();
        MapCamera mapCamera = map.getCamera();
        MapCamera_Zoom zoom = mapCamera.getZoom();
        if (mapCamera instanceof MapCamera_TopDown) {
            MapCamera_TopDown mapCamera_TopDown = (MapCamera_TopDown) mapCamera;
            camera.setLocation(mapCamera_TopDown.getInitialPosition());
            camera.lookAtDirection(mapCamera_TopDown.getInitialDirection(), Vector3f.UNIT_Y);
            if (ingameCameraAppState.shouldBeLimited()) {
                MapCamera_Limit limit = mapCamera_TopDown.getLimit();
                if (limit != null) {
                    ingameCameraAppState.setLimit(limit.getMinimum(), limit.getMaximum());
                }
            }
            // Zoom
            ingameCameraAppState.initializeZoom(zoom.getInitialDistance(), getGroundLocation_ScreenCenter());
            ingameCameraAppState.setZoomMinimumDistance(zoom.getMinimumDistance());
            ingameCameraAppState.setZoomMaximumDistance(zoom.getMaximumDistance());
            ingameCameraAppState.setZoomInterval(zoom.getInterval());
        } else if (mapCamera instanceof MapCamera_3rdPerson) {
            MapCamera_3rdPerson mapCamera_3rdPerson = (MapCamera_3rdPerson) mapCamera;
            chaseCamera = new ChaseCamera(camera, mainApplication.getInputManager());
            chaseCamera.setDefaultHorizontalRotation(mapCamera_3rdPerson.getInitialRotationHorizontal());
            chaseCamera.setDefaultVerticalRotation(mapCamera_3rdPerson.getInitialRotationVertical());
            chaseCamera.setLookAtOffset(new Vector3f(0, 2, 0));
            // Zoom
            chaseCamera.setDefaultDistance(zoom.getInitialDistance());
            chaseCamera.setMinDistance(zoom.getMinimumDistance());
            chaseCamera.setMaxDistance(zoom.getMaximumDistance());
            chaseCamera.setZoomSensitivity(zoom.getInterval());
        }
    }

    private void initializeLights() {
        LightAppState lightAppState = getAppState(LightAppState.class);
        lightAppState.removeAll();
        for (MapLight mapLight : map.getLights().getMapLights()) {
            Light light = null;
            if (mapLight instanceof MapLight_Ambient) {
                light = new AmbientLight();
            } else if (mapLight instanceof MapLight_Directional) {
                MapLight_Directional mapLight_Directional = (MapLight_Directional) mapLight;
                DirectionalLight directionalLight = new DirectionalLight();
                directionalLight.setDirection(mapLight_Directional.getDirection());
                light = directionalLight;
                MapLight_Directional_Shadows shadows = mapLight_Directional.getShadows();
                if (shadows != null) {
                    getAppState(SettingsAppState.class).subscribeInteger("shadow_quality", shadowQuality -> {
                        DirectionalLightShadowFilter shadowFilter = shadowFilters.remove(shadows);
                        if (shadowFilter != null) {
                            removeFilter(shadowFilter);
                        }
                        if (shadowQuality > 0) {
                            shadowFilter = new DirectionalLightShadowFilter(mainApplication.getAssetManager(), 2048, shadowQuality);
                            shadowFilter.setLight(directionalLight);
                            shadowFilter.setShadowIntensity(shadows.getIntensity());
                            addFilter(shadowFilter);
                            shadowFilters.put(shadows, shadowFilter);
                        }
                    });
                }
            }
            if (light != null) {
                light.setColor(mapLight.getColor());
                lightAppState.addLight(light);
            }
        }
    }

    private void initializeFilters() {
        for (MapFilter mapFilter : map.getFilters()) {
            if (mapFilter instanceof MapFilter_SSAO) {
                MapFilter_SSAO mapFilter_SSAO = (MapFilter_SSAO) mapFilter;
                getAppState(SettingsAppState.class).subscribeBoolean("ssao", ssao -> {
                    SSAOFilter ssaoFilter = ssaoFilters.remove(mapFilter);
                    if (ssaoFilter != null) {
                        removeFilter(ssaoFilter);
                    }
                    if (ssao) {
                        ssaoFilter = new SSAOFilter(mapFilter_SSAO.getSampleRadius(), mapFilter_SSAO.getIntensity(), mapFilter_SSAO.getScale(), mapFilter_SSAO.getBias());
                        // Currently approximateNormals=false is not supported due to the way the ground textures (e.g. spell indicators) are setup, but it would be a graphical enhancement
                        ssaoFilter.setApproximateNormals(true);
                        addFilter(ssaoFilter);
                        ssaoFilters.put(mapFilter, ssaoFilter);
                    }
                });
            }
        }
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        updateCameraInformation();
    }

    private void updateCameraInformation() {
        boolean hasCameraMoved = true;
        if (map.getCamera() instanceof MapCamera_TopDown) {
            hasCameraMoved = getAppState(IngameCameraAppState.class).hasMoved();
        }
        if (hasCameraMoved) {
            onCameraMoved();
        }
    }

    private void onCameraMoved() {
        Camera camera = mainApplication.getCamera();
        // CameraNode
        cameraNode.setLocalTranslation(camera.getLocation());
        cameraNode.setLocalRotation(camera.getRotation());
        // AudioListener
        Vector3f audioListenerLocation = null;
        if (map.getCamera() instanceof MapCamera_3rdPerson) {
            audioListenerLocation = new Vector3f(camera.getLocation().getX(), 0, camera.getLocation().getZ());
        } else {
            CollisionResult groundCollision = mainApplication.getRayCastingResults_ScreenCenter(mapTerrain.getNode()).getClosestCollision();
            if (groundCollision != null) {
                audioListenerLocation = new Vector3f(groundCollision.getContactPoint().getX(), 0, groundCollision.getContactPoint().getZ());
            }
        }
        if (audioListenerLocation != null) {
            Listener listener = getAppState(AudioAppState.class).getListener();
            listener.setLocation(audioListenerLocation);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        mainApplication.getRootNode().detachChild(mapTerrain.getNode());
        mainApplication.getRootNode().detachChild(visualsNode);
        mainApplication.getRootNode().detachChild(cameraNode);
        if (chaseCamera != null) {
            chaseCamera.cleanupWithInput(mainApplication.getInputManager());
        }
        removeFilters();
    }

    public void updateVisuals() {
        visualsNode.detachAllChildren();
        modelObjectsVisuals.clear();
        cameraNode.detachAllChildren();
        final BatchNode modelsNode = new BatchNode();
        MapVisuals visuals = map.getVisuals();
        for (MapVisual visual : visuals.getMapVisuals()) {
            if(visual instanceof ModelVisual){
                ModelVisual modelVisual = (ModelVisual) visual;
                ModelObject modelObject = new ModelObject(mainApplication.getAssetManager(), modelVisual.getModelSkinPath());
                Vector3f translation = modelVisual.getPosition().clone();
                translation.setY(mapHeightmap.getHeight(translation.getX(), translation.getZ()));
                modelObject.setLocalTranslation(translation);
                JMonkeyUtil.setLocalRotation(modelObject, modelVisual.getDirection());
                modelObject.setLocalScale(modelVisual.getScale());
                modelsNode.attachChild(modelObject);
                modelObjectsVisuals.put(modelObject, modelVisual);
            } else if (visual instanceof WaterVisual) {
                WaterVisual waterVisual = (WaterVisual) visual;
                if (true) {
                    WaterFilter waterFilter = getAppState(WaterAppState.class).createWaterFilter(waterVisual.getPosition(), waterVisual.getSize());
                    addFilter(waterFilter);
                } else {
                    Geometry waterPlane = getAppState(WaterAppState.class).createWaterPlane(waterVisual.getPosition(), waterVisual.getSize());
                    mainApplication.enqueue(() -> visualsNode.attachChild(waterPlane));
                }
            } else if (visual instanceof SnowVisual) {
                SnowEmitter snowEmitter = new SnowEmitter(mainApplication.getAssetManager());
                mainApplication.enqueue(() -> cameraNode.attachChild(snowEmitter.getParticleEmitter()));
            }
        }
        modelsNode.batch();
        modelsNode.setQueueBucket(RenderQueue.Bucket.Transparent);
        modelsNode.setShadowMode(RenderQueue.ShadowMode.Cast);
        mainApplication.enqueue(() -> {
            visualsNode.attachChild(modelsNode);
            addSky("miramar");
        });
    }

    private void addSky(String skyName) {
        Texture textureWest = mainApplication.getAssetManager().loadTexture("Textures/skies/" + skyName + "/left.png");
        Texture textureEast = mainApplication.getAssetManager().loadTexture("Textures/skies/" + skyName + "/right.png");
        Texture textureNorth = mainApplication.getAssetManager().loadTexture("Textures/skies/" + skyName + "/front.png");
        Texture textureSouth = mainApplication.getAssetManager().loadTexture("Textures/skies/" + skyName + "/back.png");
        Texture textureUp = mainApplication.getAssetManager().loadTexture("Textures/skies/" + skyName + "/up.png");
        Texture textureDown = mainApplication.getAssetManager().loadTexture("Textures/skies/" + skyName + "/down.png");
        visualsNode.attachChild(SkyFactory.createSky(mainApplication.getAssetManager(), textureWest, textureEast, textureNorth, textureSouth, textureUp, textureDown));
    }

    private void addFilter(Filter filter) {
        mainApplication.enqueue(() -> getAppState(PostFilterAppState.class).addFilter(filter));
        activeFilters.add(filter);
    }

    private void removeFilters() {
        while (activeFilters.size() > 0) {
            removeFilter(activeFilters.get(0));
        }
    }

    private void removeFilter(Filter filter) {
        mainApplication.enqueue(() -> getAppState(PostFilterAppState.class).removeFilter(filter));
        activeFilters.remove(filter);
    }

    public Vector2f getGroundLocation_Cursor(){
        return getGroundLocation(mainApplication.getRayCastingResults_Cursor(mapTerrain.getNode()));
    }
    
    public Vector2f getGroundLocation_ScreenCenter(){
        return getGroundLocation(mainApplication.getRayCastingResults_ScreenCenter(mapTerrain.getNode()));
    }
    
    private Vector2f getGroundLocation(CollisionResults groundCollisionResults){
        CollisionResult groundCollision = groundCollisionResults.getClosestCollision();
        if(groundCollision != null){
            return new Vector2f(groundCollision.getContactPoint().getX(), groundCollision.getContactPoint().getZ());
        }
        return null;
    }
    
    public MapVisual getMapVisual(ModelObject modelObject){
        return modelObjectsVisuals.get(modelObject);
    }

    public Map getMap(){
        return map;
    }

    public MapHeightmap getMapHeightmap(){
        return mapHeightmap;
    }

    public MapTerrain getMapTerrain(){
        return mapTerrain;
    }

    public Geometry getGroundHeightPlane() {
        return groundHeightPlane;
    }

    public Node getVisualsNode(){
        return visualsNode;
    }

    public ChaseCamera getChaseCamera() {
        return chaseCamera;
    }

    public ArrayList<Filter> getActiveFilters(){
        return activeFilters;
    }
}
