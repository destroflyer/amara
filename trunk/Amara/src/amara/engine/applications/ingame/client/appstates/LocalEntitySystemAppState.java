/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.appstates;

import java.util.Iterator;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.appstates.*;
import amara.engine.applications.ingame.client.maps.MapHeightmap;
import amara.engine.applications.ingame.client.network.backends.*;
import amara.engine.applications.ingame.client.systems.audio.*;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.applications.ingame.client.systems.visualisation.buffs.*;
import amara.engine.applications.ingame.client.systems.visualisation.effects.crodwcontrol.*;
import amara.engine.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class LocalEntitySystemAppState extends EntitySystemDisplayAppState{

    public LocalEntitySystemAppState(){
        
    }
    private Node entitiesNode = new Node();
    private EntitySceneMap entitySceneMap = new EntitySceneMap(){

        @Override
        public Node requestNode(int entity){
            Node node = getNode(entity);
            if(node == null){
                node = new Node();
                node.setUserData("entity", entity);
                entitiesNode.attachChild(node);
            }
            return node;
        }

        @Override
        public Node removeNode(int entity){
            Node node = getNode(entity);
            if(node != null){
                entitiesNode.detachChild(node);
            }
            return node;
        }
        
        private Node getNode(int entity){
            Iterator<Spatial> childrenIterator = entitiesNode.getChildren().iterator();
            while(childrenIterator.hasNext()){
                Spatial child = childrenIterator.next();
                if(((Integer) child.getUserData("entity")) == entity){
                    return (Node) child;
                }
            }
            return null;
        }
    };

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(entitiesNode);
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new EntitySynchronizeBackend(mainApplication, entityWorld));
        networkClient.addMessageBackend(new GameStartedBackend(mainApplication));
        networkClient.addMessageBackend(new GameOverBackend(mainApplication));
        MapHeightmap mapHeightmap = getAppState(MapAppState.class).getMapHeightmap();
        addEntitySystem(new AudioSystem(getAppState(AudioAppState.class)));
        PositionSystem positionSystem = new PositionSystem(entitySceneMap, mapHeightmap);
        addEntitySystem(positionSystem);
        addEntitySystem(new CollisionDebugSystem(getAppState(MapObstaclesAppState.class).getObstaclesNode()));
        addEntitySystem(new ModelSystem(entitySceneMap, mainApplication));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new SelectionMarkerSystem(entitySceneMap));
        addEntitySystem(new MaximumHealthBarSystem(entitySceneMap, mainApplication.getGuiNode(), mainApplication.getCamera(), mapHeightmap));
        addEntitySystem(new CurrentHealthBarSystem(entitySceneMap, mainApplication.getGuiNode(), mainApplication.getCamera(), mapHeightmap));
        addEntitySystem(new StunVisualisationSystem(entitySceneMap, mainApplication.getGuiNode(), mainApplication.getCamera(), mapHeightmap));
        addEntitySystem(new SilenceVisualisationSystem(entitySceneMap, mainApplication.getGuiNode(), mainApplication.getCamera(), mapHeightmap));
        addEntitySystem(new KnockupVisualisationSystem(entitySceneMap, positionSystem));
        addEntitySystem(new BuffVisualisationSystem_Bubble(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Burning(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Electrified(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Empowered(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Intervention(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_SonicWaveMark(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Turbo(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Wither(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Zhonyas(entitySceneMap));
        addEntitySystem(new TitleSystem(entitySceneMap, mainApplication.getGuiNode(), mainApplication.getCamera(), mapHeightmap));
    }

    public int getEntity(Spatial spatial){
        while(spatial != null){
            Integer entity = spatial.getUserData("entity");
            if(entity != null){
                return entity;
            }
            spatial = spatial.getParent();
        }
        return -1;
    }
    
    public Node getEntitiesNode(){
        return entitiesNode;
    }

    public EntitySceneMap getEntitySceneMap(){
        return entitySceneMap;
    }
}
