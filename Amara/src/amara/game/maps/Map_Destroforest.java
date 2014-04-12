/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
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
            EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
            unit.setComponent(new ModelComponent("Models/wizard/skin.xml"));
            unit.setComponent(new HitboxComponent(new Circle(1)));
            unit.setComponent(new IntersectionPushComponent());
            unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            unit.setComponent(new TeamComponent(0));
            unit.setComponent(new IsTargetableComponent());
            unit.setComponent(new IsVulnerableComponent());
            unit.setComponent(new BaseMaximumHealthComponent(500));
            unit.setComponent(new RequestUpdateAttributesComponent());
            switch(i){
                case 0:
                    unit.setComponent(new ScaleComponent(0.5f));
                    unit.setComponent(new PositionComponent(new Vector2f(35, 80)));
                    unit.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                    break;
                
                case 1:
                    unit.setComponent(new ScaleComponent(0.5f));
                    unit.setComponent(new PositionComponent(new Vector2f(35, 66)));
                    unit.setComponent(new DirectionComponent(new Vector2f(0, 1)));
                    break;
                
                case 2:
                    unit.setComponent(new ScaleComponent(0.9f));
                    unit.setComponent(new PositionComponent(new Vector2f(39, 73)));
                    unit.setComponent(new DirectionComponent(new Vector2f(-1, 0)));
                    break;
            }
        }
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new ModelComponent("Models/cow/skin.xml"));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new PositionComponent(new Vector2f(85.3f, 86.2f)));
        boss.setComponent(new DirectionComponent(new Vector2f(-0.5f, -1)));
        boss.setComponent(new HitboxComponent(new Circle(2.25f)));
        boss.setComponent(new IntersectionPushComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        boss.setComponent(new TeamComponent(0));
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new BaseMaximumHealthComponent(800));
        boss.setComponent(new RequestUpdateAttributesComponent());
        EntityWrapper gameObjective = entityWorld.getWrapped(entityWorld.createEntity());
        gameObjective.setComponent(new MissingEntitiesComponent(new int[]{boss.getId()}));
        gameObjective.setComponent(new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(gameObjective.getId()));
        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        playerDeathRules.setComponent(new RespawnPlayersComponent());
        playerDeathRules.setComponent(new RespawnTimerComponent(3, 0));
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void spawn(EntityWorld entityWorld, int playerEntity){
        Vector2f position = new Vector2f();
        Vector2f direction = new Vector2f();
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
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
        int unitEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntityID();
        entityWorld.setComponent(unitEntity, new PositionComponent(position));
        entityWorld.setComponent(unitEntity, new DirectionComponent(direction));
        entityWorld.setComponent(unitEntity, new TeamComponent(1));
    }
}
