/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.objectives.*;
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
                entity.setComponent(new TeamComponent(0));
                entity.setComponent(new IsTargetableComponent());
                entity.setComponent(new IsVulnerableComponent());
                entity.setComponent(new BaseMaximumHealthComponent(500));
                entity.setComponent(new RequestUpdateAttributesComponent());
            }
        }
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Yalee"));
        boss.setComponent(new DescriptionComponent("Stupid."));
        boss.setComponent(new ModelComponent("Models/cow/skin.xml"));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new PositionComponent(new Vector2f(35, 12)));
        boss.setComponent(new DirectionComponent(new Vector2f(-0.5f, -1)));
        boss.setComponent(new HitboxComponent(new Circle(2.25f)));
        boss.setComponent(new AntiGhostComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        boss.setComponent(new TeamComponent(0));
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new BaseMaximumHealthComponent(800));
        boss.setComponent(new RequestUpdateAttributesComponent());
        objectiveEntity = entityWorld.createEntity();
        entityWorld.setComponent(objectiveEntity, new MissingEntitiesComponent(new int[]{boss.getId()}));
        entityWorld.setComponent(objectiveEntity, new OpenObjectiveComponent());
    }

    @Override
    public void spawn(EntityWorld entityWorld, int playerIndex, int playerUnitEntity){
        Vector2f position = new Vector2f();
        Vector2f direction = new Vector2f();
        switch(playerIndex){
            case 0:
                position = new Vector2f(22, 16.5f);
                direction = new Vector2f(0, -1);
                break;
            
            case 1:
                position = new Vector2f(7, 7);
                direction = new Vector2f(1, 1);
                break;
            
            case 2:
                position = new Vector2f(20, 7);
                direction = new Vector2f(-1, -1);
                break;
            
            case 3:
                position = new Vector2f(29, 4);
                direction = new Vector2f(-1, -1);
                break;
        }
        entityWorld.setComponent(playerUnitEntity, new PositionComponent(position));
        entityWorld.setComponent(playerUnitEntity, new DirectionComponent(direction));
        entityWorld.setComponent(playerUnitEntity, new TeamComponent(playerIndex + 1));
    }
}
