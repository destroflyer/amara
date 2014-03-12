/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class Map_Destroforest extends Map{

    public Map_Destroforest(){
        
    }

    @Override
    public void load(EntityWorld entityWorld){
        for(int i=0;i<3;i++){
            EntityWrapper entity = entityWorld.getWrapped(entityWorld.createEntity());
            entity.setComponent(new ModelComponent("Models/wizard/skin.xml"));
            entity.setComponent(new HitboxComponent(new Circle(1)));
            entity.setComponent(new AntiGhostComponent());
            entity.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entity.setComponent(new TeamComponent(0));
            entity.setComponent(new IsTargetableComponent());
            entity.setComponent(new IsVulnerableComponent());
            entity.setComponent(new BaseMaximumHealthComponent(500));
            entity.setComponent(new RequestUpdateAttributesComponent());
            switch(i){
                case 0:
                    entity.setComponent(new ScaleComponent(0.5f));
                    entity.setComponent(new PositionComponent(new Vector2f(35, 80)));
                    entity.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                    break;
                
                case 1:
                    entity.setComponent(new ScaleComponent(0.5f));
                    entity.setComponent(new PositionComponent(new Vector2f(35, 66)));
                    entity.setComponent(new DirectionComponent(new Vector2f(0, 1)));
                    break;
                
                case 2:
                    entity.setComponent(new ScaleComponent(0.9f));
                    entity.setComponent(new PositionComponent(new Vector2f(39, 73)));
                    entity.setComponent(new DirectionComponent(new Vector2f(-1, 0)));
                    break;
            }
        }
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new ModelComponent("Models/cow/skin.xml"));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new PositionComponent(new Vector2f(85.3f, 86.2f)));
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
                position = new Vector2f(45.4f, 18);
                direction = new Vector2f(0, -1);
                break;
            
            case 1:
                position = new Vector2f(48.3f, 16.7f);
                direction = new Vector2f(-1, -1);
                break;
            
            case 2:
                position = new Vector2f(49.7f, 14);
                direction = new Vector2f(-1, 0);
                break;
            
            case 3:
                position = new Vector2f(48.3f, 11.3f);
                direction = new Vector2f(-1, 1);
                break;
            
            case 4:
                position = new Vector2f(45.4f, 9.4f);
                direction = new Vector2f(0, 1);
                break;
        }
        entityWorld.setComponent(playerUnitEntity, new PositionComponent(position));
        entityWorld.setComponent(playerUnitEntity, new DirectionComponent(direction));
        entityWorld.setComponent(playerUnitEntity, new TeamComponent(1));
    }
}
