/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

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
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.util.SkyFactory;
import com.jme3.scene.Node;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.IngameClientApplication;
import amara.engine.applications.ingame.client.maps.*;
import amara.engine.applications.ingame.client.models.ModelObject;
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

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mapHeightmap = new MapHeightmap(map.getName(), map.getPhysicsInformation());
        mapTerrain = new MapTerrain(map);
        mainApplication.getRootNode().attachChild(mapTerrain.getTerrain());
        mainApplication.getRootNode().attachChild(visualsNode);
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
                        DirectionalLightShadowRenderer shadowRenderer = new DirectionalLightShadowRenderer(mainApplication.getAssetManager(), 2048, shadowQuality);
                        shadowRenderer.setLight(directionalLight);
                        shadowRenderer.setShadowIntensity(shadows.getIntensity());
                        lightAppState.addShadowRenderer(shadowRenderer);
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
    public void cleanup(){
        super.cleanup();
        mainApplication.getRootNode().detachChild(mapTerrain.getTerrain());
        mainApplication.getRootNode().detachChild(visualsNode);
    }
    
    public void updateVisuals(){
        visualsNode.detachAllChildren();
        modelObjectsVisuals.clear();
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
                Geometry waterPlane = getAppState(WaterAppState.class).createWaterPlane(waterVisual.getPosition(), waterVisual.getSize());
                visualsNode.attachChild(waterPlane);
            }
        }
        modelsNode.batch();
        modelsNode.setShadowMode(RenderQueue.ShadowMode.Cast);
        visualsNode.attachChild(modelsNode);
        visualsNode.attachChild(SkyFactory.createSky(mainApplication.getAssetManager(), "Textures/skies/default.jpg", true));
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
}
