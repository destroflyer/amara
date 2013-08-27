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
import amara.game.entitysystem.components.selection.*;
import amara.game.entitysystem.components.skillshots.SkillDamageComponent;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.physics.*;
import shapes.*;

/**
 *
 * @author Carl
 */
public class MapAppState extends BaseAppState{

    public MapAppState(String mapName){
        this.mapName = mapName;
    }
    private String mapName;
    private MapTerrain mapTerrain;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mapTerrain = new MapTerrain(mapName);
        mainApplication.getRootNode().attachChild(mapTerrain.getTerrain());
        EntitySystemAppState entitySystemAppState = getAppState(EntitySystemAppState.class);
        entitySystemAppState.addEntitySystem(new ModelYAdjustingSystem(entitySystemAppState.getEntitySceneMap(), mapTerrain));
        //Test-Obstacles
        ArrayList<Shape> obstacles = new ArrayList<Shape>();
        obstacles.add(new SimpleConvex(new Vector2D(0, 0), new Vector2D(100, 0)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 0), new Vector2D(100, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 100), new Vector2D(0, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(0, 100), new Vector2D(0, 0)));
        obstacles.add(new Rectangle(10, 7, 5, 8));
        obstacles.add(new Circle(20, 25, 3));
        entitySystemAppState.addEntitySystem(new MapIntersectionSystem(100, 100, obstacles));
        //Test-Entities
        EntityWorld entityWorld = entitySystemAppState.getEntityWorld();
        EntityWrapper entity1 = entityWorld.getWrapped(entityWorld.createEntity());
        entity1.setComponent(new ModelComponent("Models/minion/skin.xml"));
        entity1.setComponent(new AnimationComponent("dance", 1));
        entity1.setComponent(new ScaleComponent(0.75f));
        entity1.setComponent(new PositionComponent(new Vector2f(20, 20)));
        entity1.setComponent(new DirectionComponent(new Vector2f(0, -1)));
        entity1.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
        entity1.setComponent(new AntiGhostComponent());
        entity1.setComponent(new CollidesWithMapComponent());
        entity1.setComponent(new IsSelectableComponent());
        
        EntityWrapper entity2 = entityWorld.getWrapped(entityWorld.createEntity());
        entity2.setComponent(new ModelComponent("Models/wizard/skin.xml"));
        entity2.setComponent(new ScaleComponent(0.5f));
        entity2.setComponent(new PositionComponent(new Vector2f(7, 7)));
        entity2.setComponent(new DirectionComponent(new Vector2f(1, 1)));
        entity2.setComponent(new MovementSpeedComponent(new Vector2f(2, 2)));
        entity2.setComponent(new HitboxComponent(new Circle(1)));
        entity2.setComponent(new AntiGhostComponent());
        entity2.setComponent(new CollidesWithMapComponent());
        entity2.setComponent(new IsSelectableComponent());
        entity2.setComponent(new IsSelectedComponent());
        
        EntityWrapper entity3 = entityWorld.getWrapped(entityWorld.createEntity());
        entity3.setComponent(new PositionComponent(Vector2f.ZERO));
        entity3.setComponent(new MovementSpeedComponent(new Vector2f(2.3f, 2.3f)));
        entity3.setComponent(new HitboxComponent(new Circle(1)));
        entity3.setComponent(new SkillDamageComponent(5));
        //Debug View
        Node collisionDebugNode = new Node();
        collisionDebugNode.setLocalTranslation(0, 20, 0);
        mainApplication.getRootNode().attachChild(collisionDebugNode);
        entitySystemAppState.addEntitySystem(new CollisionDebugSystem(collisionDebugNode, obstacles));
    }

    public MapTerrain getMapTerrain(){
        return mapTerrain;
    }
}
