/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.shop.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.bounties.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.systems.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class Map_Testmap extends Map{

    public Map_Testmap(){
        
    }

    @Override
    public void load(EntityWorld entityWorld){
        //Field of test units
        EntityWrapper testUnitBounty = entityWorld.getWrapped(entityWorld.createEntity());
        testUnitBounty.setComponent(new BountyGoldComponent(20));
        testUnitBounty.setComponent(new BountyExperienceComponent(100));
        EntityWrapper testUnitCamp = entityWorld.getWrapped(entityWorld.createEntity());
        testUnitCamp.setComponent(new CampMaximumAggroDistanceComponent(5));
        testUnitCamp.setComponent(new CampHealthResetComponent());
        for(int x=0;x<5;x++){
            for(int y=0;y<4;y++){
                EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
                unit.setComponent(new NameComponent("Test Wizard"));
                unit.setComponent(new ModelComponent("Models/wizard/skin_default.xml"));
                EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                idleAnimation.setComponent(new NameComponent("idle"));
                idleAnimation.setComponent(new LoopDurationComponent(6));
                unit.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
                EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
                unit.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
                unit.setComponent(new AnimationComponent(idleAnimation.getId()));
                Vector2f position = new Vector2f(12 + (x * 2), 22 + (y * 2));
                Vector2f direction = new Vector2f(0.5f, -1);
                unit.setComponent(new PositionComponent(position));
                unit.setComponent(new DirectionComponent(direction));
                unit.setComponent(new HitboxComponent(new Circle(1)));
                unit.setComponent(new IntersectionPushComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                unit.setComponent(new HitboxActiveComponent());
                unit.setComponent(new IsAliveComponent());
                unit.setComponent(new BaseMaximumHealthComponent(1500));
                unit.setComponent(new BaseHealthRegenerationComponent(10));
                unit.setComponent(new BaseAttackDamageComponent(25));
                unit.setComponent(new BaseAttackSpeedComponent(0.5f));
                unit.setComponent(new BaseWalkSpeedComponent(3));
                unit.setComponent(new RequestUpdateAttributesComponent());
                unit.setComponent(new IsTargetableComponent());
                unit.setComponent(new IsVulnerableComponent());
                EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/default_autoattack");
                unit.setComponent(new AutoAttackComponent(autoAttack.getId()));
                unit.setComponent(new TeamComponent(0));
                unit.setComponent(new BountyComponent(testUnitBounty.getId()));
                unit.setComponent(new CampComponent(testUnitCamp.getId(), position, direction));
            }
        }
        //Test Camp
        EntityWrapper testCampUnitBounty = entityWorld.getWrapped(entityWorld.createEntity());
        testCampUnitBounty.setComponent(new BountyGoldComponent(10));
        EntityWrapper testBountyBuff = entityWorld.getWrapped(entityWorld.createEntity());
        testBountyBuff.setComponent(new BuffVisualisationComponent("turbo"));
        EntityWrapper testBountyBuffEffect = entityWorld.getWrapped(entityWorld.createEntity());
        testBountyBuffEffect.setComponent(new BonusFlatWalkSpeedComponent(4));
        testBountyBuff.setComponent(new ContinuousEffectComponent(testBountyBuffEffect.getId()));
        testCampUnitBounty.setComponent(new BountyBuffComponent(testBountyBuff.getId(), 3));
        EntityWrapper testCamp = entityWorld.getWrapped(entityWorld.createEntity());
        testCamp.setComponent(new CampUnionAggroComponent());
        testCamp.setComponent(new CampMaximumAggroDistanceComponent(10));
        testCamp.setComponent(new CampHealthResetComponent());
        int[] campSpawnInformationEntities = new int[6];
        for(int x=0;x<3;x++){
            for(int y=0;y<2;y++){
                EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation.setComponent(new SpawnTemplateComponent("units/pseudospider", "testmap_camp_pseudospider," + x + "," + y + "," + testCampUnitBounty.getId()));
                campSpawnInformationEntities[(x * 2) + y] = spawnInformation.getId();
            }
        }
        testCamp.setComponent(new CampSpawnInformationComponent(campSpawnInformationEntities));
        testCamp.setComponent(new CampRespawnDurationComponent(5));
        testCamp.setComponent(new CampSpawnComponent());
        //Boss
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Yalee"));
        boss.setComponent(new DescriptionComponent("Stupid."));
        boss.setComponent(new ModelComponent("Models/cow/skin_default.xml"));
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
        boss.setComponent(new BaseHealthRegenerationComponent(40));
        boss.setComponent(new BaseAttackDamageComponent(150));
        boss.setComponent(new BaseAttackSpeedComponent(0.6f));
        boss.setComponent(new BaseWalkSpeedComponent(2.5f));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/default_autoattack");
        boss.setComponent(new AutoAttackComponent(autoAttack.getId()));
        EntityWrapper bodyslam = EntityTemplate.createFromTemplate(entityWorld, "spells/bodyslam");
        boss.setComponent(new SpellsComponent(new int[]{bodyslam.getId()}));
        boss.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
        boss.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
        boss.setComponent(new TeamComponent(0));
        EntityWrapper shop = entityWorld.getWrapped(entityWorld.createEntity());
        shop.setComponent(new ModelComponent("Models/chest/skin.xml"));
        shop.setComponent(new PositionComponent(new Vector2f(52, 25)));
        shop.setComponent(new DirectionComponent(new Vector2f(-1, -1)));
        shop.setComponent(new ShopRangeComponent(15));
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
        int unitEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
        entityWorld.setComponent(unitEntity, new PositionComponent(position));
        entityWorld.setComponent(unitEntity, new DirectionComponent(direction));
        entityWorld.setComponent(unitEntity, new TeamComponent(playerIndex + 1));
        EntityWrapper characterBounty = entityWorld.getWrapped(entityWorld.createEntity());
        characterBounty.setComponent(new BountyGoldComponent(300));
        entityWorld.setComponent(unitEntity, new BountyComponent(characterBounty.getId()));
    }
}
