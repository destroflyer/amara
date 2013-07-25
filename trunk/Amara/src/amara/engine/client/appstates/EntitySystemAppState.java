/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.ArrayList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import amara.engine.client.maps.MapTerrain;
import amara.engine.client.systems.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.physics.*;
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
        //Test: Load map
        MapTerrain mapTerrain = new MapTerrain("testmap");
        mainApplication.getRootNode().attachChild(mapTerrain.getTerrain());
        ArrayList<Shape> obstacles = new ArrayList<Shape>();
        obstacles.add(new shapes.SimpleConvex(new Vector2D(0, 0), new Vector2D(100, 0)));
        obstacles.add(new shapes.SimpleConvex(new Vector2D(100, 0), new Vector2D(100, 100)));
        obstacles.add(new shapes.SimpleConvex(new Vector2D(100, 100), new Vector2D(0, 100)));
        obstacles.add(new shapes.SimpleConvex(new Vector2D(0, 100), new Vector2D(0, 0)));
        //Systems
        IntersectionSystem intersectionSystem = new IntersectionSystem();
        addEntitySystem(new IntersectionAntiGhostSystem(intersectionSystem));
        addEntitySystem(new MovementSystem());
        addEntitySystem(new TransformUpdateSystem());
        addEntitySystem(intersectionSystem);
        addEntitySystem(new ModelSystem(entitySceneMap));
        addEntitySystem(new PositionSystem(entitySceneMap));
        addEntitySystem(new ModelYAdjustingSystem(entitySceneMap, mapTerrain));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new MapIntersectionSystem(100, 100, obstacles));
        //Debug
        Node collisionDebugNode = new Node();
        collisionDebugNode.setLocalTranslation(0, 20, 0);
        mainApplication.getRootNode().attachChild(collisionDebugNode);
        addEntitySystem(new CollisionDebugSystem(collisionDebugNode, obstacles));
        //Test-Entities
        entityWorld = new EntityWorld();
        EntityWrapper entity1 = entityWorld.getWrapped(entityWorld.createEntity());
        entity1.setComponent(new ModelComponent("Models/minion/skin.xml"));
        entity1.setComponent(new AnimationComponent("dance", 1));
        entity1.setComponent(new ScaleComponent(0.75f));
        entity1.setComponent(new PositionComponent(new Vector2f(20, 20)));
        entity1.setComponent(new DirectionComponent(new Vector2f(0, -1)));
        entity1.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
        entity1.setComponent(new AntiGhostComponent());
        entity1.setComponent(new CollidesWithMapComponent());
        EntityWrapper entity2 = entityWorld.getWrapped(entityWorld.createEntity());
        entity2.setComponent(new ModelComponent("Models/wizard/skin.xml"));
        entity2.setComponent(new ScaleComponent(0.5f));
        entity2.setComponent(new PositionComponent(new Vector2f(7, 7)));
        entity2.setComponent(new DirectionComponent(new Vector2f(1, 1)));
        entity2.setComponent(new MovementSpeedComponent(new Vector2f(2, 2)));
        entity2.setComponent(new HitboxComponent(new Circle(1)));
        entity2.setComponent(new AntiGhostComponent());
        entity2.setComponent(new CollidesWithMapComponent());
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
