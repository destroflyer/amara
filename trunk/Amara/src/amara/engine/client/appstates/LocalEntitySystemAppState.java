/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.Iterator;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.appstates.*;
import amara.engine.client.*;
import amara.engine.client.gui.*;
import amara.engine.client.network.NetworkClient;
import amara.engine.client.network.backends.*;
import amara.engine.client.systems.*;
import amara.engine.client.systems.gui.*;
import amara.engine.client.systems.visualisation.*;
import amara.engine.client.systems.visualisation.buffs.*;
import amara.engine.client.systems.visualisation.effects.crodwcontrol.*;

/**
 *
 * @author Carl
 */
public class LocalEntitySystemAppState extends EntitySystemAppState<ClientApplication>{

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
        NetworkClient networkClient = getAppState(NetworkClientAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new EntitySynchronizeBackend(entityWorld));
        addEntitySystem(new ModelSystem(entitySceneMap, mainApplication));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new SelectionMarkerSystem(entitySceneMap));
        addEntitySystem(new MaximumHealthBarSystem(entitySceneMap));
        addEntitySystem(new CurrentHealthBarSystem(entitySceneMap));
        addEntitySystem(new StunVisualisationSystem(entitySceneMap));
        addEntitySystem(new SilenceVisualisationSystem(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Burning(entitySceneMap));
        PlayerInformationSystem playerInformationSystem = new PlayerInformationSystem(networkClient.getID());
        addEntitySystem(playerInformationSystem);
        NiftyAppState niftyAppState = getAppState(NiftyAppState.class);
        addEntitySystem(new DisplayAttributesSystem(playerInformationSystem, niftyAppState.getScreenController(ScreenController_HUD.class)));
        addEntitySystem(new DisplayInventorySystem(playerInformationSystem, niftyAppState.getScreenController(ScreenController_HUD.class)));
        mainApplication.getRootNode().attachChild(entitiesNode);
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
