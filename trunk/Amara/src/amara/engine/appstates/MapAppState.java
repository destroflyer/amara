/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.util.ArrayList;
import java.util.HashMap;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.post.Filter;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.util.SkyFactory;
import com.jme3.scene.Node;
import com.jme3.water.WaterFilter;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.IngameClientApplication;
import amara.engine.applications.ingame.client.maps.*;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.models.objects.SnowEmitter;
import amara.engine.settings.Settings;
import amara.game.maps.*;
import amara.game.maps.lights.*;
import amara.game.maps.visuals.*;

/**
 *
 * @author Carl
 */
public class MapAppState extends BaseDisplayAppState{

    public MapAppState(Map map){
        this.map = map;
    }
    private Map map;
    private MapHeightmap mapHeightmap;
    private MapTerrain mapTerrain;
    private Node visualsNode = new Node();
    private HashMap<ModelObject, MapVisual> modelObjectsVisuals = new HashMap<ModelObject, MapVisual>();
    private Node cameraNode = new Node();
    private ArrayList<Filter> activeFilters = new ArrayList<Filter>();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mapHeightmap = new MapHeightmap(map.getName(), map.getPhysicsInformation());
        mapTerrain = new MapTerrain(map);
        mainApplication.getRootNode().attachChild(mapTerrain.getTerrain());
        mainApplication.getRootNode().attachChild(visualsNode);
        mainApplication.getRootNode().attachChild(cameraNode);
        initializeCamera();
        initializeLights();
        updateVisuals();
    }
    
    private void initializeCamera(){
        Camera camera = mainApplication.getCamera();
        camera.setLocation(map.getCamera().getInitialPosition());
        camera.lookAtDirection(map.getCamera().getInitialDirection(), Vector3f.UNIT_Y);
        IngameCameraAppState ingameCameraAppState = getAppState(IngameCameraAppState.class);
        MapCamera_Zoom zoom = map.getCamera().getZoom();
        if(zoom != null){
            ingameCameraAppState.setZoomInterval(zoom.getInterval());
            ingameCameraAppState.zoom(zoom.getInitialLevel());
        }
        if(mainApplication instanceof IngameClientApplication){
            MapCamera_Limit limit = map.getCamera().getLimit();
            if(limit != null){
                ingameCameraAppState.setLimit(limit.getMinimum(), limit.getMaximum(), mapTerrain.getTerrain());
            }
            if(zoom != null){
                ingameCameraAppState.setMaximumZoomLevel(zoom.getMaximumLevel());
            }
        }
    }
    
    private void initializeLights(){
        LightAppState lightAppState = getAppState(LightAppState.class);
        lightAppState.removeAll();
        for(MapLight mapLight : map.getLights().getMapLights()){
            Light light = null;
            if(mapLight instanceof MapLight_Ambient){
                MapLight_Ambient mapLight_Ambient = (MapLight_Ambient) mapLight;
                light = new AmbientLight();
            }
            else if(mapLight instanceof MapLight_Directional){
                MapLight_Directional mapLight_Directional = (MapLight_Directional) mapLight;
                DirectionalLight directionalLight = new DirectionalLight();
                directionalLight.setDirection(mapLight_Directional.getDirection());
                light = directionalLight;
                MapLight_Directional_Shadows shadows = mapLight_Directional.getShadows();
                if(shadows != null){
                    int shadowQuality = Settings.getInt("shadow_quality");
                    if(shadowQuality > 0){
                        DirectionalLightShadowFilter shadowFilter = new DirectionalLightShadowFilter(mainApplication.getAssetManager(), 2048, shadowQuality);
                        shadowFilter.setLight(directionalLight);
                        shadowFilter.setShadowIntensity(shadows.getIntensity());
                        lightAppState.addShadowFilter(shadowFilter);
                    }
                }
            }
            if(light != null){
                light.setColor(mapLight.getColor());
                lightAppState.addLight(light);
            }
        }
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        cameraNode.setLocalTranslation(mainApplication.getCamera().getLocation());
        JMonkeyUtil.setLocalRotation(cameraNode, mainApplication.getCamera().getDirection());
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getRootNode().detachChild(mapTerrain.getTerrain());
        mainApplication.getRootNode().detachChild(visualsNode);
        mainApplication.getRootNode().detachChild(cameraNode);
        removeFilters();
    }
    
    public void updateVisuals(){
        visualsNode.detachAllChildren();
        modelObjectsVisuals.clear();
        cameraNode.detachAllChildren();
        removeFilters();
        BatchNode modelsNode = new BatchNode();
        MapVisuals visuals = map.getVisuals();
        for(MapVisual visual : visuals.getMapVisuals()){
            if(visual instanceof ModelVisual){
                ModelVisual modelVisual = (ModelVisual) visual;
                ModelObject modelObject = new ModelObject(mainApplication, "/" + modelVisual.getModelSkinPath());
                Vector3f translation = modelVisual.getPosition().clone();
                translation.setY(mapHeightmap.getHeight(translation.getX(), translation.getZ()));
                modelObject.setLocalTranslation(translation);
                JMonkeyUtil.setLocalRotation(modelObject, modelVisual.getDirection());
                modelObject.setLocalScale(modelVisual.getScale());
                modelsNode.attachChild(modelObject);
                modelObjectsVisuals.put(modelObject, modelVisual);
            }
            else if(visual instanceof WaterVisual){
                WaterVisual waterVisual = (WaterVisual) visual;
                if(true){
                    WaterFilter waterFilter = getAppState(WaterAppState.class).createWaterFilter(waterVisual.getPosition(), waterVisual.getSize());
                    addFilter(waterFilter);
                }
                else{
                    Geometry waterPlane = getAppState(WaterAppState.class).createWaterPlane(waterVisual.getPosition(), waterVisual.getSize());
                    visualsNode.attachChild(waterPlane);
                }
            }
            else if(visual instanceof SnowVisual){
                SnowVisual snowVisual = (SnowVisual) visual;
                SnowEmitter snowEmitter = new SnowEmitter();
                cameraNode.attachChild(snowEmitter.getParticleEmitter());
            }
        }
        modelsNode.batch();
        modelsNode.setShadowMode(RenderQueue.ShadowMode.Cast);
        visualsNode.attachChild(modelsNode);
        visualsNode.attachChild(SkyFactory.createSky(mainApplication.getAssetManager(), "Textures/skies/default.jpg", true));
    }
    
    private void addFilter(Filter filter){
        getAppState(PostFilterAppState.class).addFilter(filter);
        activeFilters.add(filter);
    }
    
    private void removeFilters(){
        for(Filter filter : activeFilters){
            getAppState(PostFilterAppState.class).removeFilter(filter);
        }
        activeFilters.clear();
    }
    
    public Vector2f getCursorHoveredGroundLocation(){
        CollisionResult groundCollision = mainApplication.getRayCastingResults_Cursor(mapTerrain.getTerrain()).getClosestCollision();
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

    public Node getVisualsNode(){
        return visualsNode;
    }

    public Node getCameraNode(){
        return cameraNode;
    }

    public ArrayList<Filter> getActiveFilters(){
        return activeFilters;
    }
}
