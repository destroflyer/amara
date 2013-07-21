/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import amara.engine.client.systems.DirectionSystem;
import amara.engine.client.systems.EntitySceneMap;
import amara.engine.client.systems.ModelSystem;
import amara.engine.client.systems.PositionSystem;
import amara.engine.client.systems.ScaleSystem;
import java.util.ArrayList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.systems.physics.*;
import com.jme3.scene.Node;
import shapes.*;

/**
 *
 * @author Carl
 */
public class EntitySystemAppState extends BaseAppState{

    public EntitySystemAppState(){
        
    }
    private EntityWorld entityWorld;
    private ArrayList<EntitySystem> entitySystems = new ArrayList<EntitySystem>();
    private Node entitiesNode = new Node();
    private EntitySceneMap entitySceneMap = new EntitySceneMap(){

        @Override
        public Node requestNode(int entity){
            Node node = getNode(entity);
            if(node == null){
                node = new Node(getNodeName(entity));
                entitiesNode.attachChild(node);
            }
            return node;
        }

        public Node removeNode(int entity){
            Node node = getNode(entity);
            if(node != null){
                entitiesNode.detachChild(node);
            }
            return node;
        }
        
        private Node getNode(int entity){
            return (Node) entitiesNode.getChild(getNodeName(entity));
        }
        
        private String getNodeName(int entity){
            return ("entity_" + entity);
        }
    };

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(entitiesNode);
        IntersectionSystem intersectionSystem = new IntersectionSystem();
        addEntitySystem(new IntersectionPushSystem(intersectionSystem));
        addEntitySystem(new MovementSystem());
        addEntitySystem(intersectionSystem);
        addEntitySystem(new ModelSystem(entitySceneMap));
        addEntitySystem(new PositionSystem(entitySceneMap));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        //Test
        entityWorld = new EntityWorld();
        EntityWrapper entity1 = entityWorld.getWrapped(entityWorld.createEntity());
//        entity1.setComponent(new ScaleComponent(1));
        entity1.setComponent(new PositionComponent(new Vector2f(0, 0)));
        entity1.setComponent(new DirectionComponent(new Vector2f(1, 0)));
        entity1.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
        entity1.setComponent(new PushComponent());
        EntityWrapper entity2 = entityWorld.getWrapped(entityWorld.createEntity());
        entity2.setComponent(new ScaleComponent(1.5f));
        entity2.setComponent(new PositionComponent(new Vector2f(7, 7)));
        entity2.setComponent(new DirectionComponent(new Vector2f(-1, -1)));
        entity2.setComponent(new MovementSpeedComponent(new Vector2f(-1, -1)));
        entity2.setComponent(new HitboxComponent(new Circle(1)));
        entity2.setComponent(new PushComponent());
    }
    
    public void addEntitySystem(EntitySystem entitySystem){
        entitySystems.add(entitySystem);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        for(int i=0;i<entitySystems.size();i++){
            EntitySystem entitySystem = entitySystems.get(i);
            entitySystem.update(entityWorld, lastTimePerFrame);
        }
        entityWorld.onFrameEnded();
    }
}
