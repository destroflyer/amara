/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.spawns.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.bounties.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.PolygonBuilder;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.physics.util2d.PointUtil;
import amara.game.entitysystem.templates.XMLTemplateManager;


/**
 *
 * @author Carl
 */
public class CustomGameTemplates{
    
    public static void registerLoader(){
        EntityTemplate.addLoader(new EntityTemplate_Loader(){

            @Override
            public void loadTemplate(EntityWorld entityWorld, int entity, String templateName, String[] parametersText){
                EntityWrapper entityWrapper = entityWorld.getWrapped(entity);
                XMLTemplateManager.getInstance().loadTemplate(entityWorld, entity, templateName, parametersText);
                int[] parameters = new int[parametersText.length];
                for(int i=0;i<parameters.length;i++){
                    try{
                        parameters[i] = Integer.parseInt(parametersText[i]);
                    }catch(NumberFormatException ex){
                        parameters[i] = -1;
                    }
                }
                if(templateName.equals("spells/event_horizon_object")){
                    PolygonBuilder polygonBuilder = new PolygonBuilder();
                    polygonBuilder.nextOutline(false);
                    for(Vector2f point : PointUtil.getCirclePoints(6.25f, 5)){
                        polygonBuilder.add(point.getX(), point.getY());
                    }
                    polygonBuilder.nextOutline(true);
                    for(Vector2f point : PointUtil.getCirclePoints(4.25f, 5)){
                        polygonBuilder.add(point.getX(), point.getY());
                    }
                    entityWrapper.setComponent(new HitboxComponent(new PolygonShape(polygonBuilder.build(false))));
                }
                else if(templateName.equals("spells/firestorm/base")){
                    int[] effectTriggers = new int[46];
                    for(int i=0;i<effectTriggers.length;i++){
                        EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                        effectTrigger.setComponent(new CasterTargetComponent());
                        EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                        EntityWrapper spawnInformationEntity = entityWorld.getWrapped(entityWorld.createEntity());
                        spawnInformationEntity.setComponent(new SpawnTemplateComponent("spells/sear_projectile", "spells/firestorm/projectile," + i));
                        effect.setComponent(new SpawnComponent(spawnInformationEntity.getId()));
                        effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                        effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
                        effectTrigger.setComponent(new TriggerDelayComponent(i * 0.05f));
                        effectTriggers[i] = effectTrigger.getId();
                    }
                    entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTriggers));
                }
                else if(templateName.equals("spells/firestorm/projectile")){
                    Vector2f direction = new Vector2f(0, 1);
                    direction.rotateAroundOrigin(parameters[0] * (FastMath.TWO_PI / 20), true);
                    entityWrapper.setComponent(new DirectionComponent(direction));
                    EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                    movement.setComponent(new MovementDirectionComponent(direction));
                    movement.setComponent(new MovementSpeedComponent(25));
                    entityWrapper.setComponent(new MovementComponent(movement.getId()));
                }
                else if(templateName.equals("etherdesert_creep_melee")){
                    entityWrapper.setComponent(new NameComponent("Melee Creep"));
                    String skinName = "default";
                    if(parameters.length > 1){
                        skinName = "team_" + parameters[1];
                    }
                    entityWrapper.setComponent(new ModelComponent("Models/minion/skin_" + skinName + ".xml"));
                    EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                    walkAnimation.setComponent(new NameComponent("walk"));
                    entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
                    EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                    autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
                    entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

                    entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
                    entityWrapper.setComponent(new ScaleComponent(0.75f));
                    entityWrapper.setComponent(new IntersectionPushComponent());
                    entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                    entityWrapper.setComponent(new HitboxActiveComponent());

                    entityWrapper.setComponent(new IsAliveComponent());
                    int baseAttributesEntity = entityWorld.createEntity();
                    int spawnCounter = entityWorld.getComponent(parameters[0], RepeatingTriggerCounterComponent.class).getCounter();
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(420 + (spawnCounter * 3)));
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(15 + (spawnCounter * 0.3f)));
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.7f));
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(3));
                    entityWrapper.setComponent(new BaseAttributesComponent(baseAttributesEntity));
                    entityWrapper.setComponent(new RequestUpdateAttributesComponent());
                    entityWrapper.setComponent(new IsTargetableComponent());
                    entityWrapper.setComponent(new IsVulnerableComponent());
                    entityWrapper.setComponent(new SightRangeComponent(28));

                    EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/melee_autoattack");
                    entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
                    entityWrapper.setComponent(new AutoAggroComponent(12));
                    entityWrapper.setComponent(new MaximumAggroRangeComponent(30));
                    entityWrapper.setComponent(new AggroResetTimerComponent(3));

                    int bountyEntity = entityWorld.createEntity();
                    entityWorld.setComponent(bountyEntity, new BountyGoldComponent(20 + (int) (spawnCounter * 0.5)));
                    entityWorld.setComponent(bountyEntity, new BountyExperienceComponent(59));
                    entityWrapper.setComponent(new BountyComponent(bountyEntity));
                    entityWrapper.setComponent(new LocalAvoidanceWalkComponent());
                }
                else if(templateName.equals("etherdesert_creep_range")){
                    entityWrapper.setComponent(new NameComponent("Ranged Creep"));
                    String skinName = "default";
                    if(parameters.length > 1){
                        skinName = "team_" + parameters[1];
                    }
                    entityWrapper.setComponent(new ModelComponent("Models/wizard/skin_" + skinName + ".xml"));
                    EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                    walkAnimation.setComponent(new NameComponent("walk"));
                    entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
                    EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                    autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
                    entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

                    entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
                    entityWrapper.setComponent(new ScaleComponent(0.75f));
                    entityWrapper.setComponent(new IntersectionPushComponent());
                    entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                    entityWrapper.setComponent(new HitboxActiveComponent());

                    entityWrapper.setComponent(new IsAliveComponent());
                    int baseAttributesEntity = entityWorld.createEntity();
                    int spawnCounter = entityWorld.getComponent(parameters[0], RepeatingTriggerCounterComponent.class).getCounter();
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(320 + (spawnCounter * 3)));
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(22 + (spawnCounter * 1)));
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.7f));
                    entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(3));
                    entityWrapper.setComponent(new BaseAttributesComponent(baseAttributesEntity));
                    entityWrapper.setComponent(new RequestUpdateAttributesComponent());
                    entityWrapper.setComponent(new IsTargetableComponent());
                    entityWrapper.setComponent(new IsVulnerableComponent());

                    EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack");
                    entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
                    entityWrapper.setComponent(new AutoAggroComponent(12));
                    entityWrapper.setComponent(new MaximumAggroRangeComponent(30));
                    entityWrapper.setComponent(new AggroResetTimerComponent(4));

                    int bountyEntity = entityWorld.createEntity();
                    entityWorld.setComponent(bountyEntity, new BountyGoldComponent(15 + (int) (spawnCounter * 0.5)));
                    entityWorld.setComponent(bountyEntity, new BountyExperienceComponent(29));
                    entityWrapper.setComponent(new BountyComponent(bountyEntity));
                    entityWrapper.setComponent(new LocalAvoidanceWalkComponent());
                }
                else if(templateName.equals("testmap_camp_pseudospider")){
                    entityWrapper.setComponent(new PositionComponent(new Vector2f(40 + (parameters[0] * 3), 68 + (parameters[1] * 3))));
                    entityWrapper.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                    entityWrapper.setComponent(new AutoAggroComponent(10));
                    entityWrapper.setComponent(new TeamComponent(0));
                    entityWrapper.setComponent(new BountyComponent(parameters[2]));
                }
                else if(templateName.equals("arama_camp_pseudospider")){
                    Vector2f position = null;
                    Vector2f direction = null;
                    switch(parameters[1]){
                        case 0:
                            position = new Vector2f(318, 298.5f);
                            direction = new Vector2f(0, 1);
                            break;

                        case 1:
                            position = new Vector2f(326.25f, 306.75f);
                            direction = new Vector2f(-1, 0);
                            break;
                    }
                    if(parameters[0] == 1){
                        position.setX(525 - position.getX());
                        direction.setX(-1 * direction.getX());
                    }
                    entityWrapper.setComponent(new PositionComponent(position));
                    entityWrapper.setComponent(new DirectionComponent(direction));
                    entityWrapper.setComponent(new TeamComponent(0));
                    EntityWrapper bounty = entityWorld.getWrapped(entityWorld.createEntity());
                    bounty.setComponent(new BountyGoldComponent(30));
                    entityWrapper.setComponent(new BountyComponent(bounty.getId()));
                }
                else if(templateName.equals("arama_camp_beetle_golem")){
                    Vector2f position = new Vector2f(324.75f, 300);
                    Vector2f direction = new Vector2f(-1, 1);
                    if(parameters[0] == 1){
                        position.setX(525 - position.getX());
                        direction.setX(-1 * direction.getX());
                    }
                    entityWrapper.setComponent(new PositionComponent(position));
                    entityWrapper.setComponent(new DirectionComponent(direction));
                    entityWrapper.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
                    entityWrapper.setComponent(new SetNewTargetSpellsOnCooldownComponent(new int[]{0}, new float[]{6}));
                    entityWrapper.setComponent(new TeamComponent(0));
                    EntityWrapper bounty = entityWorld.getWrapped(entityWorld.createEntity());
                    bounty.setComponent(new BountyGoldComponent(60));
                    entityWrapper.setComponent(new BountyComponent(bounty.getId()));
                }
                else if(templateName.equals("arama_boss")){
                    entityWrapper.setComponent(new NameComponent("Baron Nashor"));
                    entityWrapper.setComponent(new TitleComponent("Baron Nashor"));
                    entityWrapper.setComponent(new ModelComponent("Models/cow/skin_baron.xml"));
                    EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                    walkAnimation.setComponent(new NameComponent("walk"));
                    entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
                    EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                    autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
                    entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

                    entityWrapper.setComponent(new ScaleComponent(2.25f));
                    entityWrapper.setComponent(new HitboxComponent(new Circle(2)));
                    entityWrapper.setComponent(new IntersectionPushComponent());
                    entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
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
                    entityWrapper.setComponent(new IsTargetableComponent());
                    entityWrapper.setComponent(new IsVulnerableComponent());

                    EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack");
                    entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

                    EntityWrapper bodyslam = EntityTemplate.createFromTemplate(entityWorld, "spells/bodyslam");
                    entityWrapper.setComponent(new SpellsComponent(bodyslam.getId()));

                    entityWrapper.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
                    EntityWrapper bounty = entityWorld.getWrapped(entityWorld.createEntity());
                    bounty.setComponent(new BountyGoldComponent(150));
                    EntityWrapper bountyBuff = entityWorld.getWrapped(entityWorld.createEntity());
                    bountyBuff.setComponent(new BuffVisualisationComponent("baron_nashor"));
                    EntityWrapper bountyBuffAttributes = entityWorld.getWrapped(entityWorld.createEntity());
                    bountyBuffAttributes.setComponent(new BonusFlatAttackDamageComponent(50));
                    bountyBuffAttributes.setComponent(new BonusFlatAbilityPowerComponent(50));
                    bountyBuffAttributes.setComponent(new BonusFlatWalkSpeedComponent(0.5f));
                    bountyBuff.setComponent(new ContinuousAttributesComponent(bountyBuffAttributes.getId()));
                    bounty.setComponent(new BountyBuffComponent(bountyBuff.getId(), 60));
                    entityWrapper.setComponent(new BountyComponent(bounty.getId()));
                }
                else if(templateName.equals("arama_camp_boss")){
                    entityWrapper.setComponent(new PositionComponent(new Vector2f(262.5f, 339)));
                    entityWrapper.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                    entityWrapper.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
                    entityWrapper.setComponent(new TeamComponent(0));
                }
            }
        });
    }
}
