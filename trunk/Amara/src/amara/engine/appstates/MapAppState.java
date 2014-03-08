/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.maps.MapTerrain;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class MapAppState extends BaseDisplayAppState{

    public MapAppState(Map map){
        this.map = map;
    }
    private Map map;
    private MapTerrain mapTerrain;
    private Node visualsNode = new Node();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        MapPhysicsInformation physicsInformation = map.getPhysicsInformation();
        mapTerrain = new MapTerrain(physicsInformation.getTerrainName(), physicsInformation.getWidth(), physicsInformation.getHeight());
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
        MapVisuals visuals = map.getVisuals();
        for(MapVisual visual : visuals.getMapVisuals()){
            ModelObject modelObject = new ModelObject(mainApplication, "/" + visual.getModelSkinPath());
            Vector3f translation = visual.getPosition().clone();
            translation.setY(mapTerrain.getHeight(translation.getX(), translation.getZ()));
            modelObject.setLocalTranslation(translation);
            JMonkeyUtil.setLocalRotation(modelObject, visual.getDirection());
            modelObject.setLocalScale(visual.getScale());
            visualsNode.attachChild(modelObject);
        }
    }
    
    public Vector2f getCursorHoveredGroundLocation(){
        CollisionResult groundCollision = mainApplication.getRayCastingResults_Cursor(mapTerrain.getTerrain()).getClosestCollision();
        if(groundCollision != null){
            return new Vector2f(groundCollision.getContactPoint().getX(), groundCollision.getContactPoint().getZ());
        }
        return null;
    }

    public Map getMap(){
        return map;
    }

    public MapTerrain getMapTerrain(){
        return mapTerrain;
    }

    public Node getVisualsNode(){
        return visualsNode;
    }
}
