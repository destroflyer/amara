package amara.applications.ingame.maps;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.targets.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.bounties.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.applications.ingame.entitysystem.systems.spells.SpellUtil;
import amara.applications.ingame.shared.maps.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.physics.shapes.*;

public class Map_Testmap extends Map {

    public Map_Testmap(){
        spells = new MapSpells[]{
            new MapSpells("backport", new MapSpell("spells/blessing_of_might/base"))
        };
    }

    @Override
    public void load(EntityWorld entityWorld, int teamPlayersCount){
        //Field of test units
        EntityWrapper testUnitBounty = entityWorld.getWrapped(entityWorld.createEntity());
        testUnitBounty.setComponent(new BountyCreepScoreComponent());
        testUnitBounty.setComponent(new BountyGoldComponent(20));
        testUnitBounty.setComponent(new BountyExperienceComponent(100));
        EntityWrapper testUnitCamp = entityWorld.getWrapped(entityWorld.createEntity());
        testUnitCamp.setComponent(new CampMaximumAggroDistanceComponent(5));
        testUnitCamp.setComponent(new CampHealthResetComponent());
        for(int x=0;x<5;x++){
            for(int y=0;y<4;y++){
                EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
                unit.setComponent(new NameComponent("Test Wizard"));
                unit.setComponent(new IsMonsterComponent());
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
                unit.setComponent(new IntersectionPushesComponent());
                unit.setComponent(new IntersectionPushedComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
                unit.setComponent(new HitboxActiveComponent());
                unit.setComponent(new IsAliveComponent());
                int baseAttributesEntity = entityWorld.createEntity();
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(1500));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatHealthRegenerationComponent(10));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(25));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.5f));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(3));
                unit.setComponent(new BaseAttributesComponent(baseAttributesEntity));
                unit.setComponent(new RequestUpdateAttributesComponent());
                unit.setComponent(new IsAutoAttackEnabledComponent());
                unit.setComponent(new IsTargetableComponent());
                unit.setComponent(new IsVulnerableComponent());
                unit.setComponent(new IsBindableComponent());
                unit.setComponent(new IsKnockupableComponent());
                unit.setComponent(new IsSilencableComponent());
                unit.setComponent(new IsStunnableComponent());
                unit.setComponent(new SightRangeComponent(30));
                EntityWrapper autoAttack = EntityTemplate.createReader().createFromTemplate(entityWorld, "spells/ranged_autoattack");
                unit.setComponent(new AutoAttackComponent(autoAttack.getId()));
                unit.setComponent(new TeamComponent(0));
                unit.setComponent(new BountyComponent(testUnitBounty.getId()));
                unit.setComponent(new CampComponent(testUnitCamp.getId(), position, direction));
            }
        }
        //Test Camp 1
        EntityWrapper testCampUnitBounty = entityWorld.getWrapped(entityWorld.createEntity());
        testCampUnitBounty.setComponent(new BountyCreepScoreComponent());
        testCampUnitBounty.setComponent(new BountyGoldComponent(10));
        EntityWrapper testBountyBuff = entityWorld.getWrapped(entityWorld.createEntity());
        testBountyBuff.setComponent(new BuffVisualisationComponent("turbo"));
        EntityWrapper testBountyBuffAttributes = entityWorld.getWrapped(entityWorld.createEntity());
        testBountyBuffAttributes.setComponent(new BonusFlatWalkSpeedComponent(4));
        testBountyBuff.setComponent(new ContinuousAttributesComponent(testBountyBuffAttributes.getId()));
        testCampUnitBounty.setComponent(new BountyBuffComponent(testBountyBuff.getId(), 3));
        EntityWrapper testCamp = entityWorld.getWrapped(entityWorld.createEntity());
        testCamp.setComponent(new CampUnionAggroComponent());
        testCamp.setComponent(new CampMaximumAggroDistanceComponent(10));
        testCamp.setComponent(new CampHealthResetComponent());
        int[] campSpawnInformationEntities = new int[6];
        for(int x=0;x<3;x++){
            for(int y=0;y<2;y++){
                EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation.setComponent(new SpawnTemplateComponent("units/pseudospider", "testmap_camp_pseudospider(x=" + x + ",y=" + y + ",bounty=" + testCampUnitBounty.getId() + ")"));
                campSpawnInformationEntities[(x * 2) + y] = spawnInformation.getId();
            }
        }
        testCamp.setComponent(new CampSpawnInformationComponent(campSpawnInformationEntities));
        testCamp.setComponent(new CampRespawnDurationComponent(5));
        testCamp.setComponent(new CampSpawnComponent());
        //Test Camp 2
        EntityWrapper forestMonsterBounty = entityWorld.getWrapped(entityWorld.createEntity());
        forestMonsterBounty.setComponent(new BountyCharacterKillComponent());
        forestMonsterBounty.setComponent(new BountyGoldComponent(20));
        forestMonsterBounty.setComponent(new BountyExperienceComponent(200));
        int forestMonsterBountyRulesEntity = entityWorld.createEntity();
        entityWorld.setComponent(forestMonsterBountyRulesEntity, new RequireCharacterComponent());
        entityWorld.setComponent(forestMonsterBountyRulesEntity, new RequiresEnemyComponent());
        forestMonsterBounty.setComponent(new BountyRulesComponent(forestMonsterBountyRulesEntity));
        EntityWrapper forestMonsterCamp = entityWorld.getWrapped(entityWorld.createEntity());
        forestMonsterCamp.setComponent(new CampUnionAggroComponent());
        forestMonsterCamp.setComponent(new CampMaximumAggroDistanceComponent(10));
        forestMonsterCamp.setComponent(new CampHealthResetComponent());
        EntityWrapper forestMonsterCamp_SpawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
        forestMonsterCamp_SpawnInformation.setComponent(new SpawnTemplateComponent("units/forest_monster", "testmap_camp_forest_monster(bounty=" + forestMonsterBounty.getId() + ")"));
        forestMonsterCamp.setComponent(new CampSpawnInformationComponent(forestMonsterCamp_SpawnInformation.getId()));
        forestMonsterCamp.setComponent(new CampRespawnDurationComponent(5));
        forestMonsterCamp.setComponent(new CampSpawnComponent());
        //Bush
        EntityWrapper bush = EntityTemplate.createReader().createFromTemplate(entityWorld, "other/bush");
        bush.setComponent(new PositionComponent(new Vector2f(47, 8.5f)));
        bush.setComponent(new HitboxComponent(new RegularCyclic(6, 6)));
        //Boss
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Yalee"));
        boss.setComponent(new DescriptionComponent("Stupid."));
        boss.setComponent(new IsMonsterComponent());
        boss.setComponent(new IsAlwaysVisibleComponent());
        boss.setComponent(new ModelComponent("Models/cow/skin_baron.xml"));
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
        boss.setComponent(new IntersectionPushesComponent());
        boss.setComponent(new IntersectionPushedComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
        boss.setComponent(new HitboxActiveComponent());
        boss.setComponent(new IsAliveComponent());
        int baseAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(2000));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatHealthRegenerationComponent(30));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(150));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.6f));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatArmorComponent(50));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatMagicResistanceComponent(50));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(2.5f));
        boss.setComponent(new BaseAttributesComponent(baseAttributesEntity));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new SightRangeComponent(30));
        boss.setComponent(new IsAutoAttackEnabledComponent());
        boss.setComponent(new IsSpellsEnabledComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new IsBindableComponent());
        boss.setComponent(new IsKnockupableComponent());
        boss.setComponent(new IsSilencableComponent());
        boss.setComponent(new IsStunnableComponent());
        EntityWrapper autoAttack = EntityTemplate.createReader().createFromTemplate(entityWorld, "spells/ranged_autoattack");
        boss.setComponent(new AutoAttackComponent(autoAttack.getId()));
        EntityWrapper bodyslam = EntityTemplate.createReader().createFromTemplate(entityWorld, "spells/bodyslam");
        boss.setComponent(new SpellsComponent(bodyslam.getId()));
        boss.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
        boss.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
        boss.setComponent(new TeamComponent(0));
        EntityWrapper shop = entityWorld.getWrapped(entityWorld.createEntity());
        shop.setComponent(new ModelComponent("Models/chest/skin.xml"));
        shop.setComponent(new PositionComponent(new Vector2f(52, 25)));
        shop.setComponent(new DirectionComponent(new Vector2f(-1, -1)));
        shop.setComponent(new ShopItemsComponent(MapDefaults.getShopItemTemplateNames()));
        shop.setComponent(new ShopRangeComponent(15));
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
    public void initializePlayer(EntityWorld entityWorld, int playerEntity) {
        super.initializePlayer(entityWorld, playerEntity);
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        EntityWrapper characterBounty = entityWorld.getWrapped(entityWorld.createEntity());
        characterBounty.setComponent(new BountyCharacterKillComponent());
        characterBounty.setComponent(new BountyGoldComponent(300));
        int bountyRulesEntity = entityWorld.createEntity();
        entityWorld.setComponent(bountyRulesEntity, new RequireCharacterComponent());
        entityWorld.setComponent(bountyRulesEntity, new RequiresEnemyComponent());
        characterBounty.setComponent(new BountyRulesComponent(bountyRulesEntity));
        entityWorld.setComponent(characterEntity, new BountyComponent(characterBounty.getId()));
        entityWorld.setComponent(characterEntity, new LevelComponent(12));
        entityWorld.setComponent(characterEntity, new SpellsUpgradePointsComponent(12));
        for (int i = 0; i < 4; i++) {
            SpellUtil.learnSpell(entityWorld, characterEntity, i);
        }
    }

    @Override
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity){
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
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.setComponent(characterEntity, new PositionComponent(position));
        entityWorld.setComponent(characterEntity, new DirectionComponent(direction));
        entityWorld.setComponent(characterEntity, new TeamComponent((playerIndex % 2) + 1));
    }
}
