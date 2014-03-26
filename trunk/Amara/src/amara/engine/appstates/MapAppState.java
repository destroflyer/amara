/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.util.HashMap;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.util.SkyFactory;
import com.jme3.scene.Node;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.maps.*;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.game.maps.*;
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
        mapTerrain = new MapTerrain(map.getName(), map.getPhysicsInformation());
        mainApplication.getRootNode().attachChild(mapTerrain.getTerrain());
        mainApplication.getRootNode().attachChild(visualsNode);
        updateVisuals();
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
                Quad quad = new Quad(waterVisual.getSize().getX(), waterVisual.getSize().getY());
                quad.scaleTextureCoordinates(new Vector2f((quad.getWidth() / 20), (quad.getHeight() / 20)));
                Geometry waterPlane = new Geometry("", quad);
                waterPlane.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
                waterPlane.setMaterial(getAppState(WaterAppState.class).getMaterial());
                float z = (waterVisual.getPosition().getZ() + waterVisual.getSize().getY());
                waterPlane.setLocalTranslation(waterVisual.getPosition().getX(), waterVisual.getPosition().getY(), z);
                visualsNode.attachChild(waterPlane);
            }
        }
        modelsNode.batch();
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
