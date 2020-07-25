/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.shapes.*;

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
            float scale = 1;
            String modelSkinPath = "Models/wizard/skin_default.xml";
            String animationName = "dance";
            if(y > 5){
                countX = 11;
                marginX = 8;    
                modelSkinPath = "Models/dragon/skin.xml";
                animationName = "fly";
                scale = 0.5f;
            }
            for(int x=0;x<countX;x++){
                EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
                unit.setComponent(new NameComponent("TechTest Creep"));
                unit.setComponent(new IsMonsterComponent());
                unit.setComponent(new ModelComponent(modelSkinPath));
                unit.setComponent(new ScaleComponent(scale));
                EntityWrapper animation = entityWorld.getWrapped(entityWorld.createEntity());
                animation.setComponent(new NameComponent(animationName));
                animation.setComponent(new LoopDurationComponent(2.5f));
                unit.setComponent(new AnimationComponent(animation.getId()));
                unit.setComponent(new PositionComponent(new Vector2f(10 + (x * marginX), 15 + (y * 8) + unitMarginY)));
                unit.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                unit.setComponent(new HitboxComponent(new Circle(1)));
                unit.setComponent(new IntersectionPushesComponent());
                unit.setComponent(new IntersectionPushedComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
                unit.setComponent(new HitboxActiveComponent());
                unit.setComponent(new IsAliveComponent());
                int baseAttributesEntity = entityWorld.createEntity();
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(500));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(3));
                unit.setComponent(new BaseAttributesComponent(baseAttributesEntity));
                unit.setComponent(new RequestUpdateAttributesComponent());
                unit.setComponent(new IsTargetableComponent());
                unit.setComponent(new IsVulnerableComponent());
                unit.setComponent(new IsBindableComponent());
                unit.setComponent(new IsKnockupableComponent());
                unit.setComponent(new IsSilencableComponent());
                unit.setComponent(new IsStunnableComponent());
                unit.setComponent(new TeamComponent(0));
                //ExecutePlayerCommandsSystem.walk(entityWorld, unit.getId(), targetPositionEntity, -1);
                unitMarginY *= -1;
            }
        }
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Cow"));
        boss.setComponent(new IsMonsterComponent());
        boss.setComponent(new ModelComponent("Models/cow/skin_default.xml"));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new PositionComponent(new Vector2f(40, 5)));
        boss.setComponent(new DirectionComponent(new Vector2f(-1, 0)));
        boss.setComponent(new HitboxComponent(new Circle(2.25f)));
        boss.setComponent(new IntersectionPushesComponent());
        boss.setComponent(new IntersectionPushedComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
        boss.setComponent(new HitboxActiveComponent());
        boss.setComponent(new IsAliveComponent());
        int baseAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(800));
        boss.setComponent(new BaseAttributesComponent(baseAttributesEntity));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new IsBindableComponent());
        boss.setComponent(new IsKnockupableComponent());
        boss.setComponent(new IsSilencableComponent());
        boss.setComponent(new IsStunnableComponent());
        boss.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
        boss.setComponent(new TeamComponent(0));
        EntityWrapper gameObjective = entityWorld.getWrapped(entityWorld.createEntity());
        gameObjective.setComponent(new MissingEntitiesComponent(boss.getId()));
        gameObjective.setComponent(new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(gameObjective.getId()));
        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        playerDeathRules.setComponent(new RespawnPlayersComponent());
        playerDeathRules.setComponent(new RespawnTimerComponent(3, 0));
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity){
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.setComponent(characterEntity, new PositionComponent(new Vector2f(((playerIndex + 1) * 5), 5)));
        entityWorld.setComponent(characterEntity, new DirectionComponent(new Vector2f(0, -1)));
        entityWorld.setComponent(characterEntity, new TeamComponent(1));
    }
}
