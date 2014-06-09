/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
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
                EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
                unit.setComponent(new ModelComponent("Models/wizard/skin.xml"));
                EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
                unit.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
                unit.setComponent(new ScaleComponent(0.5f));
                Vector2f position = new Vector2f(12 + (x * 2), 22 + (y * 2));
                Vector2f direction = new Vector2f(0.5f, -1);
                unit.setComponent(new PositionComponent(position));
                unit.setComponent(new DirectionComponent(direction));
                unit.setComponent(new HitboxComponent(new Circle(1)));
                unit.setComponent(new IntersectionPushComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                unit.setComponent(new HitboxActiveComponent());
                unit.setComponent(new IsAliveComponent());
                unit.setComponent(new BaseMaximumHealthComponent(500));
                unit.setComponent(new BaseAttackDamageComponent(25));
                unit.setComponent(new BaseAttackSpeedComponent(0.5f));
                unit.setComponent(new BaseWalkSpeedComponent(2.5f));
                unit.setComponent(new RequestUpdateAttributesComponent());
                unit.setComponent(new IsTargetableComponent());
                unit.setComponent(new IsVulnerableComponent());
                EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "default_autoattack");
                unit.setComponent(new AutoAttackComponent(autoAttack.getId()));
                unit.setComponent(new TeamComponent(0));
                EntityWrapper camp = entityWorld.getWrapped(entityWorld.createEntity());
                camp.setComponent(new CampTransformComponent(position, direction));
                camp.setComponent(new CampMaximumAggroDistanceComponent(5));
                camp.setComponent(new CampHealthResetComponent());
                unit.setComponent(new CampComponent(camp.getId()));
            }
        }
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Yalee"));
        boss.setComponent(new DescriptionComponent("Stupid."));
        boss.setComponent(new ModelComponent("Models/cow/skin.xml"));
        EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        walkAnimation.setComponent(new NameComponent("walk"));
        boss.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
        EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
        boss.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new PositionComponent(new Vector2f(35, 12)));
        boss.setComponent(new DirectionComponent(new Vector2f(-0.5f, -1)));
        boss.setComponent(new HitboxComponent(new Circle(2.25f)));
        boss.setComponent(new IntersectionPushComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        boss.setComponent(new HitboxActiveComponent());
        boss.setComponent(new IsAliveComponent());
        boss.setComponent(new BaseMaximumHealthComponent(800));
        boss.setComponent(new BaseAttackDamageComponent(50));
        boss.setComponent(new BaseAttackSpeedComponent(0.6f));
        boss.setComponent(new BaseWalkSpeedComponent(2.5f));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "default_autoattack");
        boss.setComponent(new AutoAttackComponent(autoAttack.getId()));
        EntityWrapper bodyslam = EntityTemplate.createFromTemplate(entityWorld, "bodyslam");
        boss.setComponent(new SpellsComponent(new int[]{bodyslam.getId()}));
        boss.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
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
        Vector2f position = new Vector2f();
        Vector2f direction = new Vector2f();
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        switch(playerIndex){
            case 0:
                position = new Vector2f(26.5f, 18);
                direction = new Vector2f(0, -1);
                break;
            
            case 1:
                position = new Vector2f(17.5f, 8);
                direction = new Vector2f(1, 1);
                break;
            
            case 2:
                position = new Vector2f(44, 21);
                direction = new Vector2f(-1, -1);
                break;
            
            case 3:
                position = new Vector2f(48, 11.5f);
                direction = new Vector2f(-1, -1);
                break;
        }
        int unitEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntityID();
        entityWorld.setComponent(unitEntity, new PositionComponent(position));
        entityWorld.setComponent(unitEntity, new DirectionComponent(direction));
        entityWorld.setComponent(unitEntity, new TeamComponent(playerIndex + 1));
    }
}
