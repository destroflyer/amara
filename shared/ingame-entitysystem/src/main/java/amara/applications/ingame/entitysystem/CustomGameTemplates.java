package amara.applications.ingame.entitysystem;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.costs.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.targets.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.bounties.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.applications.ingame.entitysystem.templates.EntityContentGenerator;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.*;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.PolygonBuilder;
import amara.libraries.physics.util2d.PointUtil;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;

public class CustomGameTemplates {

    public static int[] MAP_VEGAS_UNIT_COSTS = new int[]{ 1, 1, 1 };

    public static void registerLoader() {
        EntityTemplate.addLoader((entityWorld, entity, template) -> {
            EntityWrapper entityWrapper = entityWorld.getWrapped(entity);
            XMLTemplateManager.getInstance().loadTemplate(entityWorld, entity, template);
            if (template.getName().equals("spells/event_horizon_object")) {
                PolygonBuilder polygonBuilder = new PolygonBuilder();
                polygonBuilder.nextOutline(false);
                for (Vector2f point : PointUtil.getCirclePoints(6.25f, 5)) {
                    polygonBuilder.add(point.getX(), point.getY());
                }
                polygonBuilder.nextOutline(true);
                for (Vector2f point : PointUtil.getCirclePoints(4.25f, 5)) {
                    polygonBuilder.add(point.getX(), point.getY());
                }
                entityWrapper.setComponent(new HitboxComponent(new PolygonShape(polygonBuilder.build(false))));
            } else if (template.getName().equals("spells/firestorm/base")) {
                int[] effectTriggers = new int[46];
                for (int i = 0; i < effectTriggers.length; i++) {
                    EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                    effectTrigger.setComponent(new SourceCasterTargetComponent());
                    EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                    EntityWrapper spawnInformationEntity = entityWorld.getWrapped(entityWorld.createEntity());
                    spawnInformationEntity.setComponent(new SpawnTemplateComponent("spells/sear_projectile", "spells/firestorm/projectile(index=" + i + ")"));
                    effect.setComponent(new SpawnComponent(spawnInformationEntity.getId()));
                    effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                    effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
                    effectTrigger.setComponent(new TriggerDelayComponent(i * 0.05f));
                    effectTriggers[i] = effectTrigger.getId();
                }
                entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTriggers));
            } else if (template.getName().equals("spells/firestorm/projectile")) {
                Vector2f direction = new Vector2f(0, 1);
                direction.rotateAroundOrigin(template.getIntegerInput("index") * (FastMath.TWO_PI / 20), true);
                entityWrapper.setComponent(new DirectionComponent(direction));
                EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                movement.setComponent(new MovementDirectionComponent(direction));
                movement.setComponent(new MovementSpeedComponent(25));
                entityWrapper.setComponent(new MovementComponent(movement.getId()));
            } else if (template.getName().equals("arama_minion_melee")) {
                entityWrapper.setComponent(new IsMinionComponent());
                entityWrapper.setComponent(new NameComponent("Melee Minion"));
                entityWrapper.setComponent(new TeamModelComponent("Models/3dsa_medieval_knight/skin_team.xml"));
                EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                idleAnimation.setComponent(new NameComponent("idle"));
                idleAnimation.setComponent(new LoopDurationComponent(2));
                entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
                EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                walkAnimation.setComponent(new NameComponent("walk"));
                entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
                EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                autoAttackAnimation.setComponent(new NameComponent("attack_1"));
                entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

                entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
                entityWrapper.setComponent(new ScaleComponent(0.75f));
                entityWrapper.setComponent(new IntersectionPushesComponent());
                entityWrapper.setComponent(new IntersectionPushedComponent());
                entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
                entityWrapper.setComponent(new HitboxActiveComponent());

                entityWrapper.setComponent(new IsAliveComponent());
                int baseAttributesEntity = entityWorld.createEntity();
                int spawnCounter = entityWorld.getComponent(template.getIntegerInput("spawnTrigger"), RepeatingTriggerCounterComponent.class).getCounter();
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(420 + (spawnCounter * 3)));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(15 + (spawnCounter * 0.3f)));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.7f));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(4));
                entityWrapper.setComponent(new BaseAttributesComponent(baseAttributesEntity));
                entityWrapper.setComponent(new RequestUpdateAttributesComponent());
                entityWrapper.setComponent(new SightRangeComponent(28));
                entityWrapper.setComponent(new IsAutoAttackEnabledComponent());
                entityWrapper.setComponent(new IsTargetableComponent());
                entityWrapper.setComponent(new IsVulnerableComponent());
                entityWrapper.setComponent(new IsBindableComponent());
                entityWrapper.setComponent(new IsKnockupableComponent());
                entityWrapper.setComponent(new IsSilencableComponent());
                entityWrapper.setComponent(new IsStunnableComponent());

                EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/melee_autoattack");
                entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
                entityWrapper.setComponent(new AutoAggroComponent(12));
                entityWrapper.setComponent(new MaximumAggroRangeComponent(30));
                entityWrapper.setComponent(new AggroResetTimerComponent(3));
                entityWrapper.setComponent(new AggroPriorityComponent(1));

                int bountyEntity = entityWorld.createEntity();
                entityWorld.setComponent(bountyEntity, new BountyCreepScoreComponent());
                entityWorld.setComponent(bountyEntity, new BountyGoldComponent(20 + (int) (spawnCounter * 0.5)));
                entityWorld.setComponent(bountyEntity, new BountyExperienceComponent(25 + (spawnCounter * 5)));
                entityWrapper.setComponent(new BountyComponent(bountyEntity));
                entityWrapper.setComponent(new LocalAvoidanceWalkComponent());
            } else if (template.getName().equals("arama_minion_range")) {
                entityWrapper.setComponent(new IsMinionComponent());
                entityWrapper.setComponent(new NameComponent("Ranged Minion"));
                entityWrapper.setComponent(new TeamModelComponent("Models/3dsa_archer/skin_team.xml"));
                EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                idleAnimation.setComponent(new NameComponent("idle"));
                idleAnimation.setComponent(new LoopDurationComponent(2));
                entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
                EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                walkAnimation.setComponent(new NameComponent("walk"));
                entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
                EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                autoAttackAnimation.setComponent(new NameComponent("shoot_arrow"));
                entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

                entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
                entityWrapper.setComponent(new ScaleComponent(0.75f));
                entityWrapper.setComponent(new IntersectionPushesComponent());
                entityWrapper.setComponent(new IntersectionPushedComponent());
                entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
                entityWrapper.setComponent(new HitboxActiveComponent());

                entityWrapper.setComponent(new IsAliveComponent());
                int baseAttributesEntity = entityWorld.createEntity();
                int spawnCounter = entityWorld.getComponent(template.getIntegerInput("spawnTrigger"), RepeatingTriggerCounterComponent.class).getCounter();
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(320 + (spawnCounter * 3)));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(22 + (spawnCounter * 1)));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.7f));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(4));
                entityWrapper.setComponent(new BaseAttributesComponent(baseAttributesEntity));
                entityWrapper.setComponent(new RequestUpdateAttributesComponent());
                entityWrapper.setComponent(new SightRangeComponent(28));
                entityWrapper.setComponent(new IsAutoAttackEnabledComponent());
                entityWrapper.setComponent(new IsTargetableComponent());
                entityWrapper.setComponent(new IsVulnerableComponent());
                entityWrapper.setComponent(new IsBindableComponent());
                entityWrapper.setComponent(new IsKnockupableComponent());
                entityWrapper.setComponent(new IsSilencableComponent());
                entityWrapper.setComponent(new IsStunnableComponent());

                EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack(model=Models/3dsa_archer_arrow/skin.xml)");
                entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
                entityWrapper.setComponent(new AutoAggroComponent(12));
                entityWrapper.setComponent(new MaximumAggroRangeComponent(30));
                entityWrapper.setComponent(new AggroResetTimerComponent(3));
                entityWrapper.setComponent(new AggroPriorityComponent(1));

                int bountyEntity = entityWorld.createEntity();
                entityWorld.setComponent(bountyEntity, new BountyCreepScoreComponent());
                entityWorld.setComponent(bountyEntity, new BountyGoldComponent(15 + (int) (spawnCounter * 0.5)));
                entityWorld.setComponent(bountyEntity, new BountyExperienceComponent(15 + (spawnCounter * 3)));
                entityWrapper.setComponent(new BountyComponent(bountyEntity));
                entityWrapper.setComponent(new LocalAvoidanceWalkComponent());
            } else if (template.getName().equals("testmap_camp_pseudospider")) {
                entityWrapper.setComponent(new PositionComponent(new Vector2f(40 + (template.getIntegerInput("x") * 3), 68 + (template.getIntegerInput("y") * 3))));
                entityWrapper.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                entityWrapper.setComponent(new AutoAggroComponent(10));
                entityWrapper.setComponent(new TeamComponent(0));
                entityWrapper.setComponent(new BountyComponent(template.getIntegerInput("bounty")));
            } else if (template.getName().equals("testmap_camp_forest_monster")) {
                entityWrapper.setComponent(new PositionComponent(new Vector2f(57, 42)));
                entityWrapper.setComponent(new DirectionComponent(new Vector2f(-4, -1)));
                entityWrapper.setComponent(new TeamComponent(0));
                entityWrapper.setComponent(new BountyComponent(template.getIntegerInput("bounty")));
            } else if (template.getName().equals("arama_camp_pseudospider")) {
                Vector2f position = null;
                Vector2f direction = null;
                switch (template.getIntegerInput("index")) {
                    case 0:
                        position = new Vector2f(318, 298.5f);
                        direction = new Vector2f(0, 1);
                        break;
                    case 1:
                        position = new Vector2f(326.25f, 306.75f);
                        direction = new Vector2f(-1, 0);
                        break;
                }
                if (template.getIntegerInput("side") == 1) {
                    position.setX(525 - position.getX());
                    direction.setX(-1 * direction.getX());
                }
                entityWrapper.setComponent(new PositionComponent(position));
                entityWrapper.setComponent(new DirectionComponent(direction));
                entityWrapper.setComponent(new TeamComponent(0));
                EntityWrapper bounty = entityWorld.getWrapped(entityWorld.createEntity());
                bounty.setComponent(new BountyCreepScoreComponent());
                bounty.setComponent(new BountyGoldComponent(30));
                int bountyRulesEntity = entityWorld.createEntity();
                entityWorld.setComponent(bountyRulesEntity, new RequireCharacterComponent());
                entityWorld.setComponent(bountyRulesEntity, new AcceptEnemiesComponent());
                bounty.setComponent(new BountyRulesComponent(bountyRulesEntity));
                entityWrapper.setComponent(new BountyComponent(bounty.getId()));
            } else if (template.getName().equals("arama_camp_beetle_golem")) {
                Vector2f position = new Vector2f(324.75f, 300);
                Vector2f direction = new Vector2f(-1, 1);
                if (template.getIntegerInput("side") == 1) {
                    position.setX(525 - position.getX());
                    direction.setX(-1 * direction.getX());
                }
                entityWrapper.setComponent(new PositionComponent(position));
                entityWrapper.setComponent(new DirectionComponent(direction));
                entityWrapper.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
                entityWrapper.setComponent(new SetNewCampCombatSpellsOnCooldownComponent(new int[]{0}, new float[]{6}));
                entityWrapper.setComponent(new TeamComponent(0));
                EntityWrapper bounty = entityWorld.getWrapped(entityWorld.createEntity());
                bounty.setComponent(new BountyCreepScoreComponent());
                bounty.setComponent(new BountyGoldComponent(60));
                int bountyRulesEntity = entityWorld.createEntity();
                entityWorld.setComponent(bountyRulesEntity, new RequireCharacterComponent());
                entityWorld.setComponent(bountyRulesEntity, new AcceptEnemiesComponent());
                bounty.setComponent(new BountyRulesComponent(bountyRulesEntity));
                entityWrapper.setComponent(new BountyComponent(bounty.getId()));
            } else if (template.getName().equals("arama_boss")) {
                entityWrapper.setComponent(new NameComponent("Wragarak"));
                entityWrapper.setComponent(new IsMonsterComponent());
                entityWrapper.setComponent(new TitleComponent("Wragarak"));
                entityWrapper.setComponent(new ModelComponent("Models/3dsa_fire_dragon/skin.xml"));
                EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                idleAnimation.setComponent(new NameComponent("idle"));
                idleAnimation.setComponent(new LoopDurationComponent(4));
                entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
                EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                walkAnimation.setComponent(new NameComponent("walk"));
                entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
                entityWrapper.setComponent(new WalkStepDistanceComponent(6.5f));
                EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                autoAttackAnimation.setComponent(new NameComponent("projectile_attack"));
                entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
                entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));

                entityWrapper.setComponent(new HitboxComponent(new Circle(4.75f)));
                entityWrapper.setComponent(new IntersectionPushesComponent());
                entityWrapper.setComponent(new IntersectionPushedComponent());
                entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
                entityWrapper.setComponent(new HitboxActiveComponent());

                entityWrapper.setComponent(new IsAliveComponent());
                int baseAttributesEntity = entityWorld.createEntity();
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(4000));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatHealthRegenerationComponent(40));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(150));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.6f));
                entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(2.5f));
                entityWrapper.setComponent(new BaseAttributesComponent(baseAttributesEntity));
                entityWrapper.setComponent(new RequestUpdateAttributesComponent());
                entityWrapper.setComponent(new SightRangeComponent(30));
                entityWrapper.setComponent(new IsAutoAttackEnabledComponent());
                entityWrapper.setComponent(new IsTargetableComponent());
                entityWrapper.setComponent(new IsVulnerableComponent());
                entityWrapper.setComponent(new IsBindableComponent());
                entityWrapper.setComponent(new IsKnockupableComponent());
                entityWrapper.setComponent(new IsSilencableComponent());
                entityWrapper.setComponent(new IsStunnableComponent());

                EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack(model=Models/3dsa_fire_dragon_fireball/skin.xml)");
                entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

                entityWrapper.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
                EntityWrapper bounty = entityWorld.getWrapped(entityWorld.createEntity());
                bounty.setComponent(new BountyCreepScoreComponent());
                bounty.setComponent(new BountyGoldComponent(150));
                EntityWrapper bountyBuff = entityWorld.getWrapped(entityWorld.createEntity());
                bountyBuff.setComponent(new BuffVisualisationComponent("baron_nashor"));
                EntityWrapper bountyBuffAttributes = entityWorld.getWrapped(entityWorld.createEntity());
                bountyBuffAttributes.setComponent(new BonusFlatAttackDamageComponent(50));
                bountyBuffAttributes.setComponent(new BonusFlatAbilityPowerComponent(50));
                bountyBuffAttributes.setComponent(new BonusFlatWalkSpeedComponent(0.5f));
                bountyBuff.setComponent(new ContinuousAttributesComponent(bountyBuffAttributes.getId()));
                bounty.setComponent(new BountyBuffComponent(bountyBuff.getId(), 60));
                int bountyRulesEntity = entityWorld.createEntity();
                entityWorld.setComponent(bountyRulesEntity, new RequireCharacterComponent());
                entityWorld.setComponent(bountyRulesEntity, new AcceptEnemiesComponent());
                bounty.setComponent(new BountyRulesComponent(bountyRulesEntity));
                entityWrapper.setComponent(new BountyComponent(bounty.getId()));
            } else if(template.getName().equals("arama_camp_boss")) {
                entityWrapper.setComponent(new PositionComponent(new Vector2f(262.5f, 339)));
                entityWrapper.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                // entityWrapper.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
                entityWrapper.setComponent(new TeamComponent(0));
            } else if (template.getName().startsWith("items/etherdesert_tower_")) {
                int towerIndex = Integer.parseInt(template.getName().substring("items/etherdesert_tower_".length()));
                entityWrapper.setComponent(new ItemIDComponent("etherdesert_tower_" + towerIndex));
                entityWrapper.setComponent(new ItemVisualisationComponent("etherdesert_tower_" + towerIndex));
                entityWrapper.setComponent(new NameComponent("Tower #" + towerIndex));
                int[] costs = new int[]{ 40, 120, 200, 330, 550, 90, 120, 40, 180, 150 };
                int combineCostEntity = entityWorld.createEntity();
                entityWorld.setComponent(combineCostEntity, new GoldCostComponent(costs[towerIndex]));
                entityWrapper.setComponent(new ItemRecipeComponent(combineCostEntity));
                entityWrapper.setComponent(new IsSellableComponent(costs[towerIndex]));
                entityWrapper.setComponent(new ItemCategoriesComponent("military"));
                int itemActiveEntity = entityWorld.createEntity();
                entityWorld.setComponent(itemActiveEntity, new DescriptionComponent("Builds tower #" + towerIndex));
                EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                effectTrigger.setComponent(new TargetTargetComponent());
                EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformationEntity = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformationEntity.setComponent(new SpawnTemplateComponent("../Maps/etherdesert/templates/tower_" + towerIndex));
                spawnInformationEntity.setComponent(new SpawnRedirectReceivedBountiesComponent());
                spawnInformationEntity.setComponent(new SpawnSetAsRespawnTransformComponent());
                effect.setComponent(new SpawnComponent(spawnInformationEntity.getId()));
                effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                effectTrigger.setComponent(new TriggerSourceComponent(itemActiveEntity));
                entityWorld.setComponent(itemActiveEntity, new InstantEffectTriggersComponent(effectTrigger.getId()));
                entityWorld.setComponent(itemActiveEntity, new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
                entityWrapper.setComponent(new ItemActiveComponent(itemActiveEntity, true));
            } else if (template.getName().startsWith("units/astrudan_creep")) {
                EntityTemplate.loadTemplate(entityWorld, entity, "units/pseudospider");
                entityWorld.setComponent(entity, new NameComponent("Astrudan Spider"));
                int bounty = entityWorld.createEntity();
                int bountyGold = EntityContentGenerator.generateWeightedValue(0.9f);
                entityWorld.setComponent(bounty, new BountyGoldComponent(bountyGold));
                int bountyItem = EntityContentGenerator.generateRandomItem(entityWorld);
                entityWorld.setComponent(bounty, new BountyItemsComponent(bountyItem));
                entityWorld.setComponent(entity, new BountyComponent(bounty));
            } else if (template.getName().equals("spells/dosaz_wall/object_wall_part")) {
                int circlePointsCount = 32;
                Vector2f[] circlePointsBig = PointUtil.getCirclePoints(7, circlePointsCount);
                Vector2f[] circlePointsSmall = PointUtil.getCirclePoints(5, circlePointsCount);
                int circlePointStartIndex = (2 + template.getIntegerInput("index"));
                Vector2D[] outline = new Vector2D[4];
                outline[0] = getVector2D(circlePointsBig[circlePointStartIndex]);
                outline[1] = getVector2D(circlePointsBig[circlePointStartIndex + 1]);
                outline[2] = getVector2D(circlePointsSmall[circlePointStartIndex + 1]);
                outline[3] = getVector2D(circlePointsSmall[circlePointStartIndex]);
                ConvexShape convexShape = new SimpleConvexPolygon(outline);
                entityWrapper.setComponent(new HitboxComponent(convexShape));
            } else if (template.getName().equals("spells/tristan_ult/object")) {
                int circlePointsCount = 32;
                Vector2f[] circlePoints = PointUtil.getCirclePoints(6, circlePointsCount);
                Vector2D[] outline = new Vector2D[17];
                for (int i = 0; i < outline.length; i++) {
                    outline[i] = getVector2D(circlePoints[i]);
                }
                ConvexShape convexShape = new SimpleConvexPolygon(outline);
                entityWrapper.setComponent(new HitboxComponent(convexShape));
            } else if (template.getName().equals("spells/elven_archer_ult/template")) {
                int waves = 15;
                int arrowsPerWave = template.getIntegerInput("arrowsPerWave");
                int angle = template.getIntegerInput("angle");
                int[] newInstantEffectTriggers = new int[waves * arrowsPerWave];
                int i = 0;
                for (int wave = 0; wave < waves; wave++) {
                    for (int arrowInWave = 0; arrowInWave < arrowsPerWave; arrowInWave++) {
                        int effectTrigger = entityWorld.createEntity();
                        entityWorld.setComponent(effectTrigger, new TargetTargetComponent());
                        int effect = entityWorld.createEntity();
                        int spawnInformation = entityWorld.createEntity();
                        entityWorld.setComponent(spawnInformation, new SpawnTemplateComponent("spells/elven_archer_ult/projectile"));
                        entityWorld.setComponent(spawnInformation, new SpawnMovementSpeedComponent(20));
                        // -22.5 to +22.5
                        entityWorld.setComponent(spawnInformation, new SpawnRelativeDirectionComponent((-0.5f + (((float) arrowInWave) / (arrowsPerWave - 1))) * angle));
                        entityWorld.setComponent(effect, new SpawnComponent(spawnInformation));
                        entityWorld.setComponent(effectTrigger, new TriggeredEffectComponent(effect));
                        entityWorld.setComponent(effectTrigger, new TriggerSourceComponent(entity));
                        entityWorld.setComponent(effectTrigger, new TriggerDelayComponent(wave * 0.2f));
                        newInstantEffectTriggers[i] = effectTrigger;
                        i++;
                    }
                }
                int[] oldInstantEffectTriggers = entityWrapper.getComponent(InstantEffectTriggersComponent.class).getEffectTriggerEntities();
                int[] effectTriggers = new int[oldInstantEffectTriggers.length + newInstantEffectTriggers.length];
                System.arraycopy(oldInstantEffectTriggers, 0, effectTriggers, 0, oldInstantEffectTriggers.length);
                System.arraycopy(newInstantEffectTriggers, 0, effectTriggers, oldInstantEffectTriggers.length, newInstantEffectTriggers.length);
                entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTriggers));
            } else if (template.getName().startsWith("items/vegas_unit_")){
                int unitIndex = Integer.parseInt(template.getName().substring("items/vegas_unit_".length()));
                entityWrapper.setComponent(new ItemIDComponent("vegas_unit_" + unitIndex));
                entityWrapper.setComponent(new ItemVisualisationComponent("vegas_unit_" + unitIndex));
                entityWrapper.setComponent(new NameComponent("Unit #" + unitIndex));
                int combineCostEntity = entityWorld.createEntity();
                entityWorld.setComponent(combineCostEntity, new GoldCostComponent(MAP_VEGAS_UNIT_COSTS[unitIndex]));
                entityWrapper.setComponent(new ItemRecipeComponent(combineCostEntity));
                entityWrapper.setComponent(new IsSellableComponent(MAP_VEGAS_UNIT_COSTS[unitIndex]));
                int itemActiveEntity = entityWorld.createEntity();
                entityWorld.setComponent(itemActiveEntity, new DescriptionComponent("Puts unit #" + unitIndex + " on your bench"));
                int effectTriggerEntity = entityWorld.createEntity();
                // BuffTargetsTargetComponent will be created by map
                entityWorld.setComponent(effectTriggerEntity, new MaximumTargetsComponent(1));
                int effectEntity = entityWorld.createEntity();
                int spawnInformationEntity = entityWorld.createEntity();
                // SpawnTemplateComponent will be created by map
                // SpawnBuffsComponent will be created by map
                entityWorld.setComponent(effectEntity, new SpawnComponent(spawnInformationEntity));
                entityWorld.setComponent(effectTriggerEntity, new TriggeredEffectComponent(effectEntity));
                entityWorld.setComponent(effectTriggerEntity, new TriggerSourceComponent(itemActiveEntity));
                entityWorld.setComponent(itemActiveEntity, new InstantEffectTriggersComponent(effectTriggerEntity));
                entityWorld.setComponent(itemActiveEntity, new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
                // CastCostComponent will be created by map
                entityWrapper.setComponent(new ItemActiveComponent(itemActiveEntity, true));
            }
        });
    }

    private static Vector2D getVector2D(Vector2f vector2f) {
        return new Vector2D(vector2f.getX(), vector2f.getY());
    }
}
