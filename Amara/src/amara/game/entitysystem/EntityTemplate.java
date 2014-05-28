/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.buffs.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.effects.physics.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.placeholders.*;
import amara.game.entitysystem.components.spells.specials.*;
import amara.game.entitysystem.components.spells.triggers.*;
import amara.game.entitysystem.components.targets.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
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
            //Damage target
            EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger.setComponent(new TargetTargetComponent());
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
            effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
            effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("default_autoattack_projectile", "cloud"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(25));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new RangeComponent(15));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("default_autoattack_projectile")){
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetReachedTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetReachedTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
        }
        else if(templateName.equals("melee_autoattack")){
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new RangeComponent(6));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
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
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("death"));
            deathAnimation.setComponent(new LoopDurationComponent(1));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));

            entityWrapper.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
            entityWrapper.setComponent(new ScaleComponent(0.75f));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new BaseMaximumHealthComponent(300));
            entityWrapper.setComponent(new BaseAttackDamageComponent(30));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.6f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(2));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper doransBlade = createFromTemplate(entityWorld, "dorans_blade");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), doransBlade.getId(), doransBlade.getId(), doransBlade.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper nullSphere = createFromTemplate(entityWorld, "null_sphere");
            EntityWrapper riftwalk = createFromTemplate(entityWorld, "riftwalk");
            EntityWrapper ignite = createFromTemplate(entityWorld, "ignite");
            entityWrapper.setComponent(new SpellsComponent(new int[]{nullSphere.getId(), riftwalk.getId(), ignite.getId()}));
        }
        else if(templateName.equals("null_sphere")){
            entityWrapper.setComponent(new NameComponent("Null Sphere"));
            entityWrapper.setComponent(new DescriptionComponent("Silences an enemy."));
            entityWrapper.setComponent(new SpellVisualisationComponent("null_sphere"));
            //Target effect
            EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger.setComponent(new TargetTargetComponent());
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(80));
            effect.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.7f));
            effect.setComponent(new AddSilenceComponent(2));
            effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
            effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("null_sphere_projectile"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(25));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new RangeComponent(14));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("null_sphere_projectile")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetReachedTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetReachedTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
        }
        else if(templateName.equals("riftwalk")){
            entityWrapper.setComponent(new NameComponent("Riftwalk"));
            entityWrapper.setComponent(new DescriptionComponent("Teleports to the target location."));
            entityWrapper.setComponent(new SpellVisualisationComponent("riftwalk"));
            entityWrapper.setComponent(new TeleportCasterToTargetPositionComponent());
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
        }
        else if(templateName.equals("ignite")){
            entityWrapper.setComponent(new NameComponent("Ignite"));
            entityWrapper.setComponent(new DescriptionComponent("Deals damage over time to the target."));
            entityWrapper.setComponent(new SpellVisualisationComponent("ignite"));
            EntityWrapper igniteBuff = entityWorld.getWrapped(entityWorld.createEntity());
            igniteBuff.setComponent(new BuffVisualisationComponent("burning"));
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new FlatMagicDamageComponent(40));
            effect2.setComponent(new ScalingAbilityPowerMagicDamageComponent(1));
            igniteBuff.setComponent(new RepeatingEffectComponent(effect2.getId(), 0.5f));
            entityWrapper.setComponent(new InstantTargetBuffComponent(igniteBuff.getId(), 3));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new RangeComponent(10));
            entityWrapper.setComponent(new CooldownComponent(3));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("wizard")){
            entityWrapper.setComponent(new ModelComponent("Models/wizard/skin.xml"));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));

            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new ScaleComponent(0.5f));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new BaseMaximumHealthComponent(500));
            entityWrapper.setComponent(new BaseAttackDamageComponent(60));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.6f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(2));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper doransBlade = createFromTemplate(entityWorld, "dorans_blade");
            EntityWrapper doransRing = createFromTemplate(entityWorld, "dorans_ring");
            EntityWrapper needlesslyLargeRod = createFromTemplate(entityWorld, "needlessly_large_rod");
            EntityWrapper dagger = createFromTemplate(entityWorld, "dagger");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), doransBlade.getId(), doransRing.getId(), needlesslyLargeRod.getId(), dagger.getId(), dagger.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(4));

            EntityWrapper sear = createFromTemplate(entityWorld, "sear");
            EntityWrapper pillarOfFlame = createFromTemplate(entityWorld, "pillar_of_flame");
            EntityWrapper battleCry = createFromTemplate(entityWorld, "battle_cry");
            entityWrapper.setComponent(new SpellsComponent(new int[]{sear.getId(), pillarOfFlame.getId(), battleCry.getId()}));
        }
        else if(templateName.equals("sear")){
            entityWrapper.setComponent(new NameComponent("Sear"));
            entityWrapper.setComponent(new DescriptionComponent("Throws a fireball."));
            entityWrapper.setComponent(new SpellVisualisationComponent("sear"));
            //Target effect
            EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger.setComponent(new TargetTargetComponent());
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(165));
            effect.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.65f));
            effect.setComponent(new AddStunComponent(0.5f));
            effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
            effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
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
            entityWrapper.setComponent(new HitboxActiveComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CollisionTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
        }
        else if(templateName.equals("pillar_of_flame")){
            entityWrapper.setComponent(new NameComponent("Pillar of Flame"));
            entityWrapper.setComponent(new DescriptionComponent("Spawns a fire pillar at the target location."));
            entityWrapper.setComponent(new SpellVisualisationComponent("pillar_of_flame"));
            //Target effect
            EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger.setComponent(new TargetTargetComponent());
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(120));
            effect.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.6f));
            effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
            effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn object
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("flame_pillar"));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
            entityWrapper.setComponent(new RangeComponent(15));
            entityWrapper.setComponent(new CooldownComponent(2));
        }
        else if(templateName.equals("flame_pillar")){
            entityWrapper.setComponent(new ModelComponent("Models/fireball/skin.xml"));
            entityWrapper.setComponent(new HitboxComponent(new Circle(2)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CollisionTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new LifetimeComponent(1.5f));
        }
        else if(templateName.equals("battle_cry")){
            entityWrapper.setComponent(new NameComponent("Battle Cry"));
            entityWrapper.setComponent(new DescriptionComponent("Increases the attack speed for a few seconds."));
            entityWrapper.setComponent(new SpellVisualisationComponent("battle_cry"));
            //Add buff
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusPercentageAttackSpeedComponent(0.8f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 5));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(10));
        }
        else if(templateName.equals("robot")){
            entityWrapper.setComponent(new ModelComponent("Models/robot/skin.xml"));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("death"));
            deathAnimation.setComponent(new LoopDurationComponent(2.5f));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(1.5f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new BaseMaximumHealthComponent(700));
            entityWrapper.setComponent(new BaseAttackDamageComponent(40));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(1.75f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new HealthComponent(500));
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper grab = createFromTemplate(entityWorld, "grab," + entityWrapper.getId());
            EntityWrapper astralBlessing = createFromTemplate(entityWorld, "astral_blessing");
            EntityWrapper sonicWave = createFromTemplate(entityWorld, "sonic_wave," + 2);
            EntityWrapper wither = createFromTemplate(entityWorld, "wither");
            entityWrapper.setComponent(new SpellsComponent(new int[]{grab.getId(), astralBlessing.getId(), sonicWave.getId(), wither.getId()}));
        }
        else if(templateName.equals("grab")){
            entityWrapper.setComponent(new NameComponent("Grab"));
            entityWrapper.setComponent(new DescriptionComponent("Beep boop."));
            entityWrapper.setComponent(new SpellVisualisationComponent("grab"));
            //Target effect
            EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger.setComponent(new TargetTargetComponent());
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new MovementTargetComponent(parameters[0]));
            movement.setComponent(new MovementSpeedComponent(9));
            effect.setComponent(new MoveComponent(movement.getId()));
            effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
            effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("grab_projectile," + parameters[0]));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(12));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CastDurationComponent(1.5f));
            entityWrapper.setComponent(new StopBeforeCastingComponent());
            EntityWrapper castAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            castAnimation.setComponent(new NameComponent("grab"));
            castAnimation.setComponent(new LoopDurationComponent(1.5f));
            entityWrapper.setComponent(new CastAnimationComponent(castAnimation.getId()));
            entityWrapper.setComponent(new CooldownComponent(3));
        }
        else if(templateName.equals("grab_projectile")){
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.6f)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CollisionTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new LifetimeComponent(0.75f));
        }
        else if(templateName.equals("astral_blessing")){
            entityWrapper.setComponent(new NameComponent("Heal"));
            entityWrapper.setComponent(new DescriptionComponent("Soraka in a nutshell."));
            entityWrapper.setComponent(new SpellVisualisationComponent("astral_blessing"));
            //Target effect
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatHealComponent(100));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
        }
        else if(templateName.equals("sonic_wave")){
            entityWrapper.setComponent(new NameComponent("Sonic Wave"));
            entityWrapper.setComponent(new SpellVisualisationComponent("sonic_wave"));
            //Replace spell
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Add mark
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("sonic_wave_mark"));
            //On mark removal
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new ReplaceSpellWithExistingSpellComponent(parameters[0], entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new RemoveEffectTriggersComponent(effectTrigger3.getId()));
            effect2.setComponent(new AddBuffComponent(buff.getId(), 3));
            effect1.setComponent(new ReplaceSpellWithNewSpellComponent(parameters[0], "resonating_strike," + buff.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("sonic_wave_projectile"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(12));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CooldownComponent(3));
        }
        else if(templateName.equals("sonic_wave_projectile")){
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.9f)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CollisionTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new LifetimeComponent(1));
        }
        else if(templateName.equals("resonating_strike")){
            entityWrapper.setComponent(new NameComponent("Resonating Strike"));
            entityWrapper.setComponent(new SpellVisualisationComponent("resonating_strike"));
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatPhysicalDamageComponent(80));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove mark
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveBuffComponent(parameters[0]));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Move to target
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new MovementTargetComponent(parameters[1]));
            movement.setComponent(new MovementSpeedComponent(10));
            effect3.setComponent(new MoveComponent(movement.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new TargetReachedTriggerComponent());
            effectTrigger4.setComponent(new TargetTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerOnceComponent());
            effect3.setComponent(new AddEffectTriggersComponent(effectTrigger4.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(3));
        }
        else if(templateName.equals("wither")){
            entityWrapper.setComponent(new NameComponent("Wither"));
            entityWrapper.setComponent(new SpellVisualisationComponent("wither"));
            //Add buff
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("withered"));
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(-0.6f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 5));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new RangeComponent(14));
            entityWrapper.setComponent(new CooldownComponent(11));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("jaime")){
            entityWrapper.setComponent(new ModelComponent("Models/jaime/skin.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("Idle"));
            idleAnimation.setComponent(new LoopDurationComponent(8));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("Walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("Punches"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.8f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new BaseMaximumHealthComponent(450));
            entityWrapper.setComponent(new BaseAttackDamageComponent(50));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.8f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(2.25f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper sonicWave = createFromTemplate(entityWorld, "sonic_wave," + 0);
            EntityWrapper intervention = createFromTemplate(entityWorld, "intervention," + entityWrapper.getId());
            EntityWrapper zhonyas = createFromTemplate(entityWorld, "zhonyas," + entityWrapper.getId());
            EntityWrapper lunarRush = createFromTemplate(entityWorld, "lunar_rush");
            entityWrapper.setComponent(new SpellsComponent(new int[]{sonicWave.getId(), intervention.getId(), zhonyas.getId(), lunarRush.getId()}));
        }
        else if(templateName.equals("intervention")){
            entityWrapper.setComponent(new NameComponent("Intervention"));
            entityWrapper.setComponent(new SpellVisualisationComponent("intervention"));
            //Target effect
            float duration = 3;
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new AddBindingImmuneComponent(duration));
            effect1.setComponent(new AddSilenceImmuneComponent(duration));
            effect1.setComponent(new AddStunImmuneComponent(duration));
            effect1.setComponent(new RemoveVulnerabilityComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("intervention"));
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CustomTargetComponent(parameters[0]));
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new AddVulnerabilityComponent());
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new RemoveEffectTriggersComponent(effectTrigger3.getId()));
            effect2.setComponent(new AddBuffComponent(buff.getId(), duration));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new CasterTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger4.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(6));
        }
        else if(templateName.equals("zhonyas")){
            entityWrapper.setComponent(new NameComponent("Zhonyas"));
            entityWrapper.setComponent(new SpellVisualisationComponent("zhonyas"));
            //Target effect
            float duration = 2.5f;
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new RemoveTargetabilityComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("zhonyas"));
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CustomTargetComponent(parameters[0]));
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new AddTargetabilityComponent());
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new RemoveEffectTriggersComponent(effectTrigger3.getId()));
            effect2.setComponent(new AddBuffComponent(buff.getId(), duration));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new CasterTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger4.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(5));
        }
        else if(templateName.equals("lunar_rush")){
            entityWrapper.setComponent(new NameComponent("Lunar Rush"));
            entityWrapper.setComponent(new SpellVisualisationComponent("lunar_rush"));
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(100));
            effect1.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.6f));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Move to target
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new TargetedMovementTargetComponent());
            movement.setComponent(new MovementSpeedComponent(10));
            EntityWrapper movementAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            movementAnimation.setComponent(new NameComponent("Jumping"));
            movementAnimation.setComponent(new LoopDurationComponent(0.6f));
            movement.setComponent(new MovementAnimationComponent(movementAnimation.getId()));
            effect2.setComponent(new MoveComponent(movement.getId()));
            effect2.setComponent(new DeactivateHitboxComponent());
            //Trigger spell effects
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetReachedTriggerComponent());
            effectTrigger3.setComponent(new TargetTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerOnceComponent());
            //Reactivate hitbox
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new TargetReachedTriggerComponent());
            effectTrigger4.setComponent(new SourceTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new ActivateHitboxComponent());
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerOnceComponent());
            effect2.setComponent(new AddEffectTriggersComponent(effectTrigger3.getId(), effectTrigger4.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new RangeComponent(20));
            entityWrapper.setComponent(new CooldownComponent(3));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("soldier")){
            entityWrapper.setComponent(new ModelComponent("Models/soldier/skin.xml"));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("death"));
            deathAnimation.setComponent(new LoopDurationComponent(0.9f));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));

            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new BaseMaximumHealthComponent(400));
            entityWrapper.setComponent(new BaseAttackDamageComponent(80));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.6f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(2));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper doransBlade = createFromTemplate(entityWorld, "dorans_blade");
            EntityWrapper dagger = createFromTemplate(entityWorld, "dagger");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), doransBlade.getId(), dagger.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper leapStrike = createFromTemplate(entityWorld, "leap_strike");
            EntityWrapper empower = createFromTemplate(entityWorld, "empower");
            EntityWrapper spinningSlash = createFromTemplate(entityWorld, "spinning_slash");
            EntityWrapper bearStance = createFromTemplate(entityWorld, "bear_stance");
            entityWrapper.setComponent(new SpellsComponent(new int[]{leapStrike.getId(), empower.getId(), spinningSlash.getId(), bearStance.getId()}));
        }
        else if(templateName.equals("leap_strike")){
            entityWrapper.setComponent(new NameComponent("Leap Strike"));
            entityWrapper.setComponent(new SpellVisualisationComponent("leap_strike"));
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatPhysicalDamageComponent(70));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Move to target
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new TargetedMovementTargetComponent());
            movement.setComponent(new MovementSpeedComponent(30));
            effect2.setComponent(new MoveComponent(movement.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetReachedTriggerComponent());
            effectTrigger3.setComponent(new TargetTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerOnceComponent());
            effect2.setComponent(new AddEffectTriggersComponent(effectTrigger3.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new RangeComponent(9));
            entityWrapper.setComponent(new CooldownComponent(4));
        }
        else if(templateName.equals("empower")){
            entityWrapper.setComponent(new NameComponent("Empower"));
            entityWrapper.setComponent(new SpellVisualisationComponent("empower"));
            //Target effect
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spellEffect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new AddAutoAttackSpellEffectsComponent(spellEffect1.getId()));
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("empowered"));
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveSpellEffectsComponent(spellEffect1.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new RemoveEffectTriggersComponent(effectTrigger2.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 5));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect2 = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect2.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect2.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Damage target
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new FlatMagicDamageComponent(75));
            effect3.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.6f));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove buff
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new CasterTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new RemoveBuffComponent(buff.getId()));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            spellEffect1.setComponent(new CastedEffectTriggersComponent(effectTrigger3.getId(), effectTrigger4.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger5 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger5.setComponent(new CasterTargetComponent());
            EntityWrapper effect5 = entityWorld.getWrapped(entityWorld.createEntity());
            effect5.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger5.setComponent(new TriggeredEffectComponent(effect5.getId()));
            effectTrigger5.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger5.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(3));
        }
        else if(templateName.equals("spinning_slash")){
            entityWrapper.setComponent(new NameComponent("Spinning Slash"));
            entityWrapper.setComponent(new DescriptionComponent("Trynda."));
            entityWrapper.setComponent(new SpellVisualisationComponent("spinning_slash"));
            //Move to target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new TargetedMovementDirectionComponent());
            movement.setComponent(new MovementSpeedComponent(15));
            EntityWrapper movementAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            movementAnimation.setComponent(new NameComponent("spin"));
            movementAnimation.setComponent(new LoopDurationComponent(0.3f));
            movement.setComponent(new MovementAnimationComponent(movementAnimation.getId()));
            movement.setComponent(new DistanceLimitComponent(10));
            effect1.setComponent(new MoveComponent(movement.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger1.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CooldownComponent(1.5f));
        }
        else if(templateName.equals("bear_stance")){
            entityWrapper.setComponent(new NameComponent("Bear Stance"));
            entityWrapper.setComponent(new SpellVisualisationComponent("bear_stance"));
            //Add buff
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusFlatWalkSpeedComponent(10));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 3));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(5));
        }
        else if(templateName.equals("steve")){
            entityWrapper.setComponent(new ModelComponent("Models/steve/skin.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("stand"));
            idleAnimation.setComponent(new LoopDurationComponent(8));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.8f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new BaseMaximumHealthComponent(1200));
            entityWrapper.setComponent(new BaseHealthRegenerationComponent(10));
            entityWrapper.setComponent(new BaseAttackDamageComponent(40));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.65f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(2));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper doransRing = createFromTemplate(entityWorld, "dorans_ring");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), doransRing.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new HealthComponent(400));
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper infectedCleaver = createFromTemplate(entityWorld, "infected_cleaver");
            EntityWrapper sadism = createFromTemplate(entityWorld, "sadism");
            entityWrapper.setComponent(new SpellsComponent(new int[]{infectedCleaver.getId(), sadism.getId()}));
        }
        else if(templateName.equals("infected_cleaver")){
            entityWrapper.setComponent(new NameComponent("Infected Cleaver"));
            entityWrapper.setComponent(new SpellVisualisationComponent("infected_cleaver"));
            //Target effect
            EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger.setComponent(new TargetTargetComponent());
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(100));
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(-0.4f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect.setComponent(new AddBuffComponent(buff.getId(), 2));
            effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
            effectTrigger.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("infected_cleaver_object"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(12));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CooldownComponent(3));
        }
        else if(templateName.equals("infected_cleaver_object")){
            entityWrapper.setComponent(new ModelComponent("Models/cartoon_forest_stone_1/skin.xml"));
            entityWrapper.setComponent(new ScaleComponent(0.9f));
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.5f)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CollisionTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new LifetimeComponent(0.8f));
        }
        else if(templateName.equals("sadism")){
            entityWrapper.setComponent(new NameComponent("Sadism"));
            entityWrapper.setComponent(new SpellVisualisationComponent("sadism"));
            //Add buff
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("turbo"));
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusFlatHealthRegenerationComponent(180));
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(1));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 10));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(15));
        }
        else if(templateName.equals("bodyslam")){
            entityWrapper.setComponent(new NameComponent("Bodyslam"));
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatPhysicalDamageComponent(160));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn object
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("bodyslam_object"));
            entityWrapper.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation.getId()}));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CastDurationComponent(2));
            entityWrapper.setComponent(new StopBeforeCastingComponent());
            EntityWrapper castAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            castAnimation.setComponent(new NameComponent("bodyslam"));
            castAnimation.setComponent(new LoopDurationComponent(2));
            entityWrapper.setComponent(new CastAnimationComponent(castAnimation.getId()));
            entityWrapper.setComponent(new CooldownComponent(5));
        }
        else if(templateName.equals("bodyslam_object")){
            entityWrapper.setComponent(new HitboxComponent(new Circle(8)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CollisionTriggerComponent());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TriggerCastedSpellEffectsComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove object
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveEntityComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new LifetimeComponent(1));
        }
        else if(templateName.equals("boots")){
            entityWrapper.setComponent(new ItemVisualisationComponent("boots"));
            entityWrapper.setComponent(new BonusFlatWalkSpeedComponent(0.75f));
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
