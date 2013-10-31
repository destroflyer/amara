/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.ArrayList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import amara.engine.client.maps.MapTerrain;
import amara.engine.client.systems.visualisation.*;
import amara.game.entitysystem.systems.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class MapAppState extends ClientBaseAppState{

    public MapAppState(String mapName){
        this.mapName = mapName;
    }
    private String mapName;
    private MapTerrain mapTerrain;
    public final static ArrayList<Shape> TEST_MAP_OBSTACLES = new ArrayList<Shape>();
    static{
        TEST_MAP_OBSTACLES.add(new SimpleConvex(new Vector2D(0, 0), new Vector2D(100, 0)));
        TEST_MAP_OBSTACLES.add(new SimpleConvex(new Vector2D(100, 0), new Vector2D(100, 100)));
        TEST_MAP_OBSTACLES.add(new SimpleConvex(new Vector2D(100, 100), new Vector2D(0, 100)));
        TEST_MAP_OBSTACLES.add(new SimpleConvex(new Vector2D(0, 100), new Vector2D(0, 0)));
        TEST_MAP_OBSTACLES.add(new Rectangle(10, 7, 5, 8));
        TEST_MAP_OBSTACLES.add(new Circle(30, 25, 3));
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        int mapWidth = 100;
        int mapHeight = 100;
        mapTerrain = new MapTerrain(mapName, mapWidth, mapHeight);
        mainApplication.getRootNode().attachChild(mapTerrain.getTerrain());
        LocalEntitySystemAppState localEntitySystemAppState = getAppState(LocalEntitySystemAppState.class);
        localEntitySystemAppState.addEntitySystem(new PositionSystem(localEntitySystemAppState.getEntitySceneMap(), mapTerrain));
        //Debug View
        Node collisionDebugNode = new Node();
        collisionDebugNode.setLocalTranslation(0, 1, 0);
        mainApplication.getRootNode().attachChild(collisionDebugNode);
        localEntitySystemAppState.addEntitySystem(new CollisionDebugSystem(collisionDebugNode, TEST_MAP_OBSTACLES));
    }

    public MapTerrain getMapTerrain(){
        return mapTerrain;
    }
}
