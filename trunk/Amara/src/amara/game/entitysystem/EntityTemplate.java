/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.specials.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.effects.*;
import amara.game.entitysystem.components.units.intersections.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.systems.physics.shapes.*;


/**
 *
 * @author Carl
 */
public class EntityTemplate{
    
    public static EntityWrapper createFromTemplate(EntityWorld entityWorld, String... templateNames){
        int entity = entityWorld.createEntity();
        loadTemplates(entityWorld, entity, templateNames);
        return entityWorld.getWrapped(entity);
    }
    
    public static void loadTemplates(EntityWorld entityWorld, int entity, String... templateNames){
        for(int i=0;i<templateNames.length;i++){
            loadTemplate(entityWorld, entity, templateNames[i]);
        }
    }
    
    public static void loadTemplate(EntityWorld entityWorld, int entity, String template){
        EntityWrapper entityWrapper = entityWorld.getWrapped(entity);
        String[] parts = template.split(",");
        String templateName = parts[0];
        int[] parameters = new int[parts.length - 1];
        for(int i=0;i<parameters.length;i++){
            parameters[i] = Integer.parseInt(parts[i + 1]);
        }
        if(templateName.equals("default_autoattack")){
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("default_autoattack_projectile", "cloud"));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(25));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        }
        else if(templateName.equals("default_autoattack_projectile")){
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
            entityWrapper.setComponent(new TargetReachedTriggerEffectComponent(effect.getId()));
        }
        else if(templateName.equals("cloud")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
        }
        else if(templateName.equals("minion")){
            entityWrapper.setComponent(new ModelComponent("Models/minion/skin.xml"));
            EntityWrapper danceAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            danceAnimation.setComponent(new NameComponent("dance"));
            danceAnimation.setComponent(new LoopDurationComponent(2.66f));
            entityWrapper.setComponent(new AnimationComponent(danceAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            walkAnimation.setComponent(new LoopDurationComponent(1));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

            entityWrapper.setComponent(new ScaleComponent(0.75f));
            entityWrapper.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
            entityWrapper.setComponent(new AntiGhostComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new IsTargetableComponent());

            entityWrapper.setComponent(new BaseMaximumHealthComponent(300));
            EntityWrapper doransBlade = createFromTemplate(entityWorld, "dorans_blade");
            entityWrapper.setComponent(new InventoryComponent(new int[]{doransBlade.getId(), doransBlade.getId(), doransBlade.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());

            EntityWrapper nullSphere = createFromTemplate(entityWorld, "null_sphere");
            EntityWrapper riftwalk = createFromTemplate(entityWorld, "riftwalk");
            EntityWrapper ignite = createFromTemplate(entityWorld, "ignite");
            entityWrapper.setComponent(new SpellsComponent(new int[]{nullSphere.getId(), riftwalk.getId(), ignite.getId()}));
        }
        else if(templateName.equals("null_sphere")){
            entityWrapper.setComponent(new NameComponent("Null Sphere"));
            entityWrapper.setComponent(new DescriptionComponent("Silences an enemy."));
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("null_sphere_projectile"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(25));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        }
        else if(templateName.equals("null_sphere_projectile")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(80));
            effect.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.7f));
            effect.setComponent(new SilenceComponent(2));
            entityWrapper.setComponent(new TargetReachedTriggerEffectComponent(effect.getId()));
        }
        else if(templateName.equals("riftwalk")){
            entityWrapper.setComponent(new NameComponent("Riftwalk"));
            entityWrapper.setComponent(new DescriptionComponent("Teleports to the target location."));
            entityWrapper.setComponent(new TeleportCasterToTargetPositionComponent());
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
        }
        else if(templateName.equals("ignite")){
            entityWrapper.setComponent(new NameComponent("Ignite"));
            entityWrapper.setComponent(new DescriptionComponent("Deals damage over time to the target."));
            EntityWrapper igniteBuff = entityWorld.getWrapped(entityWorld.createEntity());
            igniteBuff.setComponent(new BuffVisualisationComponent("burning"));
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new FlatMagicDamageComponent(40));
            effect2.setComponent(new ScalingAbilityPowerMagicDamageComponent(1));
            igniteBuff.setComponent(new RepeatingEffectComponent(effect2.getId(), 0.5f));
            entityWrapper.setComponent(new InstantTargetBuffComponent(igniteBuff.getId(), 3));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        }
        else if(templateName.equals("wizard")){
            entityWrapper.setComponent(new ModelComponent("Models/wizard/skin.xml"));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            walkAnimation.setComponent(new LoopDurationComponent(1));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

            entityWrapper.setComponent(new ScaleComponent(0.5f));
            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new AntiGhostComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new IsTargetableComponent());

            entityWrapper.setComponent(new BaseMaximumHealthComponent(500));
            entityWrapper.setComponent(new BaseAttackDamageComponent(60));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.6f));
            EntityWrapper doransBlade = createFromTemplate(entityWorld, "dorans_blade");
            EntityWrapper doransRing = createFromTemplate(entityWorld, "dorans_ring");
            EntityWrapper needlesslyLargeRod = createFromTemplate(entityWorld, "needlessly_large_rod");
            EntityWrapper dagger = createFromTemplate(entityWorld, "dagger");
            entityWrapper.setComponent(new InventoryComponent(new int[]{doransBlade.getId(), doransRing.getId(), needlesslyLargeRod.getId(), dagger.getId(), dagger.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());

            EntityWrapper autoAttack1 = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack1.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(4));

            EntityWrapper sear = createFromTemplate(entityWorld, "sear");
            EntityWrapper pillarOfFlame = createFromTemplate(entityWorld, "pillar_of_flame");
            EntityWrapper battleCry = createFromTemplate(entityWorld, "battle_cry");
            entityWrapper.setComponent(new SpellsComponent(new int[]{sear.getId(), pillarOfFlame.getId(), battleCry.getId()}));
        }
        else if(templateName.equals("sear")){
            entityWrapper.setComponent(new NameComponent("Sear"));
            entityWrapper.setComponent(new DescriptionComponent("Throws a fireball."));
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("fireball"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(4));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CooldownComponent(1));
        }
        else if(templateName.equals("fireball")){
            entityWrapper.setComponent(new ModelComponent("Models/fireball/skin.xml"));
            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            EntityWrapper intersectionRules = entityWorld.getWrapped(entityWorld.createEntity());
            intersectionRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(intersectionRules.getId()));
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(165));
            effect.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.65f));
            effect.setComponent(new StunComponent(0.5f));
            entityWrapper.setComponent(new CollisionTriggerEffectComponent(effect.getId()));
        }
        else if(templateName.equals("pillar_of_flame")){
            entityWrapper.setComponent(new NameComponent("Pillar of Flame"));
            entityWrapper.setComponent(new DescriptionComponent("Spawns a fire pillar at the target location."));
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("flame_pillar"));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
            entityWrapper.setComponent(new CooldownComponent(2));
        }
        else if(templateName.equals("flame_pillar")){
            entityWrapper.setComponent(new ModelComponent("Models/fireball/skin.xml"));
            entityWrapper.setComponent(new HitboxComponent(new Circle(2)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            EntityWrapper intersectionRules = entityWorld.getWrapped(entityWorld.createEntity());
            intersectionRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(intersectionRules.getId()));
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(120));
            effect.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.6f));
            entityWrapper.setComponent(new CollisionTriggerEffectComponent(effect.getId()));
            entityWrapper.setComponent(new LifetimeComponent(1.5f));
        }
        else if(templateName.equals("battle_cry")){
            entityWrapper.setComponent(new NameComponent("Battle Cry"));
            entityWrapper.setComponent(new DescriptionComponent("Increases the attack speed for a few seconds."));
            EntityWrapper battleCryBuff = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new BonusPercentageAttackSpeedComponent(0.8f));
            battleCryBuff.setComponent(new ContinuousEffectComponent(effect3.getId()));
            entityWrapper.setComponent(new InstantTargetBuffComponent(battleCryBuff.getId(), 5));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
        }
        else if(templateName.equals("robot")){
            entityWrapper.setComponent(new ModelComponent("Models/robot/skin.xml"));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            walkAnimation.setComponent(new LoopDurationComponent(1));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(1.5f)));
            entityWrapper.setComponent(new AntiGhostComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new IsTargetableComponent());

            entityWrapper.setComponent(new BaseMaximumHealthComponent(700));
            entityWrapper.setComponent(new BaseAttackDamageComponent(40));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new HealthComponent(500));

            EntityWrapper autoAttack2 = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack2.getId()));

            EntityWrapper grab = createFromTemplate(entityWorld, "grab," + entityWrapper.getId());
            EntityWrapper astralBlessing = createFromTemplate(entityWorld, "astral_blessing");
            entityWrapper.setComponent(new SpellsComponent(new int[]{grab.getId(), astralBlessing.getId()}));
        }
        else if(templateName.equals("grab")){
            entityWrapper.setComponent(new NameComponent("Grab"));
            entityWrapper.setComponent(new DescriptionComponent("Beep boop."));
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("grab_projectile," + parameters[0]));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(12));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CooldownComponent(3));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            EntityWrapper castAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            castAnimation.setComponent(new NameComponent("grab"));
            castAnimation.setComponent(new LoopDurationComponent(1.5f));
            entityWrapper.setComponent(new CastAnimationComponent(castAnimation.getId()));
        }
        else if(templateName.equals("grab_projectile")){
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.6f)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            EntityWrapper intersectionRules = entityWorld.getWrapped(entityWorld.createEntity());
            intersectionRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(intersectionRules.getId()));
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new MoveToEntityPositionComponent(parameters[0], 9));
            entityWrapper.setComponent(new CollisionTriggerEffectComponent(effect.getId()));
            entityWrapper.setComponent(new LifetimeComponent(0.75f));
        }
        else if(templateName.equals("astral_blessing")){
            entityWrapper.setComponent(new NameComponent("Heal"));
            entityWrapper.setComponent(new DescriptionComponent("Soraka in a nutshell."));
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatHealComponent(100));
            entityWrapper.setComponent(new InstantTargetEffectComponent(effect.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
        }
        else if(templateName.equals("dorans_blade")){
            entityWrapper.setComponent(new ItemVisualisationComponent("dorans_blade"));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(80));
            entityWrapper.setComponent(new BonusFlatAttackDamageComponent(10));
        }
        else if(templateName.equals("dorans_ring")){
            entityWrapper.setComponent(new ItemVisualisationComponent("dorans_ring"));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(80));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(15));
        }
        else if(templateName.equals("needlessly_large_rod")){
            entityWrapper.setComponent(new ItemVisualisationComponent("needlessly_large_rod"));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(80));
        }
        else if(templateName.equals("dagger")){
            entityWrapper.setComponent(new ItemVisualisationComponent("dagger"));
            entityWrapper.setComponent(new BonusPercentageAttackSpeedComponent(0.12f));
        }
    }
}
