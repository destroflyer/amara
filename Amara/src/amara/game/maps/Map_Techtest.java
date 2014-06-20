/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.game.entitysystem.systems.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class Map_Techtest extends Map{

    public Map_Techtest(){
        
    }

    @Override
    public void load(EntityWorld entityWorld){
        int targetPositionEntity = entityWorld.createEntity();
        entityWorld.setComponent(targetPositionEntity, new PositionComponent(new Vector2f(50, 50)));
        for(int y=0;y<10;y++){
            int unitMarginY = 2;
            int countX = 21;
            int marginX = 4;            
            String modelSkinPath = "Models/wizard/skin_default.xml";
            String animationName = "dance";
            if(y > 5){
                countX = 11;
                marginX = 8;    
                modelSkinPath = "Models/dragon/skin.xml";
                animationName = "fly";
            }
            for(int x=0;x<countX;x++){
                EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
                unit.setComponent(new ModelComponent(modelSkinPath));
                unit.setComponent(new ScaleComponent(0.5f));
                EntityWrapper animation = entityWorld.getWrapped(entityWorld.createEntity());
                animation.setComponent(new NameComponent(animationName));
                animation.setComponent(new LoopDurationComponent(2.5f));
                unit.setComponent(new AnimationComponent(animation.getId()));
                unit.setComponent(new PositionComponent(new Vector2f(10 + (x * marginX), 15 + (y * 8) + unitMarginY)));
                unit.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                unit.setComponent(new HitboxComponent(new Circle(1)));
                unit.setComponent(new IntersectionPushComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                unit.setComponent(new HitboxActiveComponent());
                unit.setComponent(new IsAliveComponent());
                unit.setComponent(new BaseMaximumHealthComponent(500));
                unit.setComponent(new BaseWalkSpeedComponent(2));
                unit.setComponent(new RequestUpdateAttributesComponent());
                unit.setComponent(new IsTargetableComponent());
                unit.setComponent(new IsVulnerableComponent());
                unit.setComponent(new TeamComponent(0));
                //ExecutePlayerCommandsSystem.walk(entityWorld, unit.getId(), targetPositionEntity, -1);
                unitMarginY *= -1;
            }
        }
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new ModelComponent("Models/cow/skin.xml"));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new PositionComponent(new Vector2f(40, 5)));
        boss.setComponent(new DirectionComponent(new Vector2f(-1, 0)));
        boss.setComponent(new HitboxComponent(new Circle(2.25f)));
        boss.setComponent(new IntersectionPushComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        boss.setComponent(new HitboxActiveComponent());
        boss.setComponent(new IsAliveComponent());
        boss.setComponent(new BaseMaximumHealthComponent(800));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new TeamComponent(0));
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
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int unitEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntityID();
        entityWorld.setComponent(unitEntity, new PositionComponent(new Vector2f(((playerIndex + 1) * 5), 5)));
        entityWorld.setComponent(unitEntity, new DirectionComponent(new Vector2f(0, -1)));
        entityWorld.setComponent(unitEntity, new TeamComponent(playerIndex + 1));
    }
}
