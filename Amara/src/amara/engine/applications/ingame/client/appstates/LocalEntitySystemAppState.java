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
import amara.engine.applications.ingame.client.systems.cinematics.*;
import amara.engine.applications.ingame.client.systems.filters.*;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.applications.ingame.client.systems.visualisation.buffs.*;
import amara.engine.applications.ingame.client.systems.visualisation.effects.crodwcontrol.*;
import amara.engine.applications.ingame.client.systems.visualisation.effects.reactions.*;
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
        MapAppState mapAppState = getAppState(MapAppState.class);
        MapHeightmap mapHeightmap = mapAppState.getMapHeightmap();
        addEntitySystem(new AudioSystem(getAppState(AudioAppState.class)));
        PositionSystem positionSystem = new PositionSystem(entitySceneMap, mapHeightmap);
        addEntitySystem(positionSystem);
        addEntitySystem(new CollisionDebugSystem(getAppState(MapObstaclesAppState.class).getObstaclesNode()));
        addEntitySystem(new ModelSystem(entitySceneMap, mainApplication));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new SelectionMarkerSystem(entitySceneMap));
        addEntitySystem(new KnockupVisualisationSystem(entitySceneMap, positionSystem));
        addEntitySystem(new BuffVisualisationSystem_BaronNashor(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Bubble(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Burning(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Electrified(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Empowered(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Intervention(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Meditating(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Slap(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_SonicWaveMark(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Turbo(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Wither(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Zhonyas(entitySceneMap));
        HUDAttachmentsSystem hudAttachmentsSystem = new HUDAttachmentsSystem(mainApplication.getGuiNode(), mainApplication.getCamera(), mapHeightmap);
        addEntitySystem(hudAttachmentsSystem);
        EntityHeightMap entityHeightMap = new EntityHeightMap(entitySceneMap);
        addEntitySystem(new MaximumHealthBarSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new CurrentHealthBarSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new StunVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new SilenceVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new TitleSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new GoldChangeSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new ExperienceChangeSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new LevelUpNotificationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new ReactionVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new WaterSpeedSystem(mapAppState));
        addEntitySystem(new CinematicsSystem(getAppState(CinematicAppState.class)));
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
