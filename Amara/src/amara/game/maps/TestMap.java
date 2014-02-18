/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import java.util.ArrayList;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class TestMap extends Map{

    public TestMap(){
        ArrayList<Shape> obstacles = new ArrayList<Shape>();
        obstacles.add(new SimpleConvex(new Vector2D(0, 0), new Vector2D(100, 0)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 0), new Vector2D(100, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 100), new Vector2D(0, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(0, 100), new Vector2D(0, 0)));
        obstacles.add(new Rectangle(10, 7, 5, 8));
        obstacles.add(new Circle(30, 25, 3));
        physicsInformation = new MapPhysicsInformation("testmap", 100, 100, obstacles);
    }

    @Override
    public void load(EntityWorld entityWorld){
        //Field of test units
        for(int x=0;x<5;x++){
            for(int y=0;y<4;y++){
                EntityWrapper entity = entityWorld.getWrapped(entityWorld.createEntity());
                entity.setComponent(new ModelComponent("Models/wizard/skin.xml"));
                entity.setComponent(new ScaleComponent(0.5f));
                entity.setComponent(new PositionComponent(new Vector2f(12 + (x * 2), 22 + (y * 2))));
                entity.setComponent(new DirectionComponent(new Vector2f(1, 1)));
                entity.setComponent(new HitboxComponent(new Circle(1)));
                entity.setComponent(new AntiGhostComponent());
                entity.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                entity.setComponent(new TeamComponent(1));
                entity.setComponent(new IsTargetableComponent());
                entity.setComponent(new BaseMaximumHealthComponent(500));
                entity.setComponent(new RequestUpdateAttributesComponent());
            }
        }
    }
}
