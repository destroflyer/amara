/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import com.jme3.math.Vector2f;
import amara.engine.applications.ingame.client.models.modifiers.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.audio.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.areas.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.aggro.*;
import amara.game.entitysystem.components.effects.audio.*;
import amara.game.entitysystem.components.effects.buffs.*;
import amara.game.entitysystem.components.effects.buffs.areas.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.crowdcontrol.knockup.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.effects.physics.*;
import amara.game.entitysystem.components.effects.spawns.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.effects.visuals.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.placeholders.*;
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
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;


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
            entityWrapper.setComponent(new NameComponent("Ranged Autoattack"));
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
            effect1.setComponent(new DrawTeamAggroComponent(15));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("default_autoattack_projectile", "cloud"));
            spawnInformation.setComponent(new SpawnMoveToTargetComponent());
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(25));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/range_autoattack_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CastCancelableComponent());
            entityWrapper.setComponent(new StopBeforeCastingComponent());
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
            entityWrapper.setComponent(new NameComponent("Melee Autoattack"));
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
            effect1.setComponent(new DrawTeamAggroComponent(15));
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/melee_autoattack_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect1.setComponent(new PlayAudioComponent(audioCast.getId()));
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
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CastCancelableComponent());
            entityWrapper.setComponent(new StopBeforeCastingComponent());
            entityWrapper.setComponent(new RangeComponent(6));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("empty_autoattack")){
            entityWrapper.setComponent(new RangeComponent(0));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("cloud")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
        }
        else if(templateName.equals("minion")){
            entityWrapper.setComponent(new ModelComponent("Models/varus/skin_default.xml"));
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
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(300));
            entityWrapper.setComponent(new BaseAttackDamageComponent(30));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5));
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
            float duration = 2;
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(80));
            effect1.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.7f));
            effect1.setComponent(new AddSilenceComponent(duration));
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("electrified"));
            effect1.setComponent(new AddBuffComponent(buff.getId(), duration));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            float delay = 0.3f;
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("null_sphere_projectile"));
            spawnInformation.setComponent(new SpawnMoveToTargetComponent());
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(25));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggerDelayComponent(delay));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/null_sphere_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggerDelayComponent(delay));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CastDurationComponent(0.6f));
            entityWrapper.setComponent(new StopBeforeCastingComponent());
            EntityWrapper castAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            castAnimation.setComponent(new NameComponent("cast_1"));
            castAnimation.setComponent(new LoopDurationComponent(0.6f));
            entityWrapper.setComponent(new CastAnimationComponent(castAnimation.getId()));
            entityWrapper.setComponent(new RangeComponent(14));
            entityWrapper.setComponent(new CooldownComponent(0.6f));
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
            EntityWrapper audioHit = entityWorld.getWrapped(entityWorld.createEntity());
            audioHit.setComponent(new AudioComponent("Sounds/sounds/spells/thunder.ogg"));
            effect1.setComponent(new PlayAudioComponent(audioHit.getId()));
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
            //Play sound
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new TeleportToTargetPositionComponent());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/riftwalk_cast.ogg"));
            effect1.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            float castDuration = 0.5f;
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggerDelayComponent(castDuration / 2));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CastDurationComponent(castDuration));
            EntityWrapper castAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            castAnimation.setComponent(new NameComponent("pop"));
            castAnimation.setComponent(new LoopDurationComponent(castDuration));
            entityWrapper.setComponent(new CastAnimationComponent(castAnimation.getId()));
            entityWrapper.setComponent(new CooldownComponent(castDuration));
        }
        else if(templateName.equals("ignite")){
            entityWrapper.setComponent(new NameComponent("Ignite"));
            entityWrapper.setComponent(new DescriptionComponent("Deals damage over time to the target."));
            entityWrapper.setComponent(new SpellVisualisationComponent("ignite"));
            //Add buff
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("burning"));
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new FlatMagicDamageComponent(40));
            buffEffect.setComponent(new ScalingAbilityPowerMagicDamageComponent(1));
            buff.setComponent(new RepeatingEffectComponent(buffEffect.getId(), 0.5f));
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
            entityWrapper.setComponent(new RangeComponent(10));
            entityWrapper.setComponent(new CooldownComponent(3));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("wizard")){
            entityWrapper.setComponent(new ModelComponent("Models/wizard/skin_default.xml"));
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
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(500));
            entityWrapper.setComponent(new BaseAttackDamageComponent(60));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5));
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
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(165));
            effect1.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.65f));
            effect1.setComponent(new AddStunComponent(0.5f));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("sear_projectile"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(10));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/sear_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CooldownComponent(1));
        }
        else if(templateName.equals("sear_projectile")){
            entityWrapper.setComponent(new ModelComponent("Models/fireball/skin.xml"));
            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            entityWrapper.setComponent(new RemoveOnMapLeaveComponent());
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
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(120));
            effect1.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.6f));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn object
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("pillar_of_flame_object"));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/pillar_of_flame_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new RangeComponent(15));
            entityWrapper.setComponent(new CooldownComponent(2));
        }
        else if(templateName.equals("pillar_of_flame_object")){
            entityWrapper.setComponent(new ModelComponent("Models/pillar_of_flame/skin.xml"));
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
            effect2.setComponent(new DeactivateHitboxComponent());
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
            entityWrapper.setComponent(new ModelComponent("Models/robot/skin_default.xml"));
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
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(700));
            entityWrapper.setComponent(new BaseAttackDamageComponent(40));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new HealthComponent(500));
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "melee_autoattack");
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
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new MovementTargetComponent(parameters[0]));
            movement.setComponent(new MovementSpeedComponent(9));
            effect1.setComponent(new MoveComponent(movement.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("grab_projectile," + parameters[0]));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(12));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/grab_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
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
            EntityWrapper audioHit = entityWorld.getWrapped(entityWorld.createEntity());
            audioHit.setComponent(new AudioComponent("Sounds/sounds/spells/grab_hit.ogg"));
            effect1.setComponent(new PlayAudioComponent(audioHit.getId()));
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
            buff.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger3.getId()));
            effect2.setComponent(new AddBuffComponent(buff.getId(), 3));
            effect1.setComponent(new ReplaceSpellWithNewSpellComponent(parameters[0], "resonating_strike," + buff.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new TargetTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("sonic_wave_projectile"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(17));
            effect4.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger4.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CooldownComponent(3));
        }
        else if(templateName.equals("sonic_wave_projectile")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
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
            entityWrapper.setComponent(new LifetimeComponent(0.9f));
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
            movement.setComponent(new MovementSpeedComponent(18));
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
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CooldownComponent(3));
        }
        else if(templateName.equals("wither")){
            entityWrapper.setComponent(new NameComponent("Wither"));
            entityWrapper.setComponent(new SpellVisualisationComponent("wither"));
            //Play sound
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioStart = entityWorld.getWrapped(entityWorld.createEntity());
            audioStart.setComponent(new AudioComponent("Sounds/sounds/spells/wither_start.ogg"));
            effect1.setComponent(new PlayAudioComponent(audioStart.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Add buff
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioLoop = entityWorld.getWrapped(entityWorld.createEntity());
            audioLoop.setComponent(new AudioComponent("Sounds/sounds/spells/wither_loop.ogg"));
            audioLoop.setComponent(new AudioLoopComponent());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("withered"));
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(-0.6f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            //On buff remove
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new StopAudioComponent(audioLoop.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger3.getId()));
            effect2.setComponent(new AddBuffComponent(buff.getId(), 4));
            effect2.setComponent(new PlayAudioComponent(audioLoop.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new TargetTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger4.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new RangeComponent(14));
            entityWrapper.setComponent(new CooldownComponent(11));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("jaime")){
            entityWrapper.setComponent(new ModelComponent("Models/jaime/skin_default.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("Idle"));
            idleAnimation.setComponent(new LoopDurationComponent(8));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("Walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            entityWrapper.setComponent(new WalkStepDistanceComponent(4.5f));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("Punches"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.8f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(450));
            entityWrapper.setComponent(new BaseAttackDamageComponent(50));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5.5f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper zhonyasHourglass = createFromTemplate(entityWorld, "zhonyas_hourglass");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), zhonyasHourglass.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper sonicWave = createFromTemplate(entityWorld, "sonic_wave," + 0);
            EntityWrapper intervention = createFromTemplate(entityWorld, "intervention," + entityWrapper.getId());
            EntityWrapper lunarRush = createFromTemplate(entityWorld, "lunar_rush");
            EntityWrapper dragonsRage = createFromTemplate(entityWorld, "dragons_rage");
            entityWrapper.setComponent(new SpellsComponent(new int[]{sonicWave.getId(), intervention.getId(), lunarRush.getId(), dragonsRage.getId()}));
        }
        else if(templateName.equals("intervention")){
            entityWrapper.setComponent(new NameComponent("Intervention"));
            entityWrapper.setComponent(new SpellVisualisationComponent("intervention"));
            //Target effect
            float duration = 3;
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
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
            buff.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger3.getId()));
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
            movement.setComponent(new MovementSpeedComponent(40));
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
            effectTrigger4.setComponent(new TriggerOnCancelComponent());
            effect2.setComponent(new AddEffectTriggersComponent(effectTrigger3.getId(), effectTrigger4.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new RangeComponent(20));
            entityWrapper.setComponent(new CooldownComponent(3));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("dragons_rage")){
            entityWrapper.setComponent(new NameComponent("Dragon's Rage"));
            entityWrapper.setComponent(new SpellVisualisationComponent("dragons_rage"));
            //Knockback target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatPhysicalDamageComponent(200));
            effect1.setComponent(new ScalingAttackDamagePhysicalDamageComponent(2));
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new DisplacementComponent());
            movement.setComponent(new SourceMovementDirectionComponent());
            movement.setComponent(new MovementSpeedComponent(13));
            movement.setComponent(new DistanceLimitComponent(13));
            effect1.setComponent(new MoveComponent(movement.getId()));
            EntityWrapper audioCast1 = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast1.setComponent(new AudioComponent("Sounds/sounds/spells/dragons_rage_cast.ogg"));
            audioCast1.setComponent(new AudioVolumeComponent(1.5f));
            EntityWrapper audioCast2 = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast2.setComponent(new AudioComponent("Sounds/sounds/spells/dragons_rage_hit_knockback.ogg"));
            audioCast2.setComponent(new AudioVolumeComponent(0.75f));
            effect1.setComponent(new PlayAudioComponent(audioCast1.getId(), audioCast2.getId()));
            //Knockup targets
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new FlatPhysicalDamageComponent(200));
            effect2.setComponent(new ScalingAttackDamagePhysicalDamageComponent(2));
            EntityWrapper knockup = entityWorld.getWrapped(entityWorld.createEntity());
            knockup.setComponent(new KnockupDurationComponent(1));
            knockup.setComponent(new KnockupHeightComponent(5));
            effect2.setComponent(new AddKnockupComponent(knockup.getId()));
            EntityWrapper audioCast3 = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast3.setComponent(new AudioComponent("Sounds/sounds/spells/dragons_rage_hit_knockup.ogg"));
            audioCast3.setComponent(new AudioVolumeComponent(0.75f));
            effect2.setComponent(new PlayAudioComponent(audioCast3.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            //Knockup targets
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetReachedTriggerComponent());
            effectTrigger3.setComponent(new SourceTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new RemoveEffectTriggersComponent(effectTrigger2.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerOnceComponent());
            effectTrigger3.setComponent(new TriggerOnCancelComponent());
            effect1.setComponent(new AddEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new TargetTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger4.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new RangeComponent(6));
            entityWrapper.setComponent(new CooldownComponent(4));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("soldier")){
            entityWrapper.setComponent(new ModelComponent("Models/soldier/skin_default.xml"));
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
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(400));
            entityWrapper.setComponent(new BaseAttackDamageComponent(80));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5));
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
            entityWrapper.setComponent(new CastCancelActionComponent());
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
            buff.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger2.getId()));
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
            EntityWrapper audioHit = entityWorld.getWrapped(entityWorld.createEntity());
            audioHit.setComponent(new AudioComponent("Sounds/sounds/spells/empower_hit.ogg"));
            effect3.setComponent(new PlayAudioComponent(audioHit.getId()));
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
            movement.setComponent(new MovementSpeedComponent(20));
            EntityWrapper movementAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            movementAnimation.setComponent(new NameComponent("spin"));
            movementAnimation.setComponent(new LoopDurationComponent(0.3f));
            movement.setComponent(new MovementAnimationComponent(movementAnimation.getId()));
            movement.setComponent(new DistanceLimitComponent(10));
            effect1.setComponent(new MoveComponent(movement.getId()));
            effect1.setComponent(new DeactivateHitboxComponent());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/spinning_slash_cast.ogg"));
            effect1.setComponent(new PlayAudioComponent(audioCast.getId()));
            //Reactivate hitbox
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetReachedTriggerComponent());
            effectTrigger2.setComponent(new SourceTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new ActivateHitboxComponent());
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerOnceComponent());
            effectTrigger2.setComponent(new TriggerOnCancelComponent());
            effect1.setComponent(new AddEffectTriggersComponent(effectTrigger2.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
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
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(0.55f));
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
            entityWrapper.setComponent(new ModelComponent("Models/steve/skin_default.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("stand"));
            idleAnimation.setComponent(new LoopDurationComponent(8));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            entityWrapper.setComponent(new WalkStepDistanceComponent(5));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("punch"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.8f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(1200));
            entityWrapper.setComponent(new BaseHealthRegenerationComponent(10));
            entityWrapper.setComponent(new BaseAttackDamageComponent(40));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper doransRing = createFromTemplate(entityWorld, "dorans_ring");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), doransRing.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new HealthComponent(400));
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper infectedCleaver = createFromTemplate(entityWorld, "infected_cleaver");
            EntityWrapper burningAgony = createFromTemplate(entityWorld, "burning_agony," + 1);
            EntityWrapper eventHorizon = createFromTemplate(entityWorld, "event_horizon");
            EntityWrapper sadism = createFromTemplate(entityWorld, "sadism");
            entityWrapper.setComponent(new SpellsComponent(new int[]{infectedCleaver.getId(), burningAgony.getId(), eventHorizon.getId(), sadism.getId()}));
        }
        else if(templateName.equals("infected_cleaver")){
            entityWrapper.setComponent(new NameComponent("Infected Cleaver"));
            entityWrapper.setComponent(new SpellVisualisationComponent("infected_cleaver"));
            //Target effect
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(100));
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(-0.4f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 2));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("infected_cleaver_object"));
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(17));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/infected_cleaver_cast.ogg"));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CastDurationComponent(0.3f));
            entityWrapper.setComponent(new StopBeforeCastingComponent());
            EntityWrapper castAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            castAnimation.setComponent(new NameComponent("punch"));
            castAnimation.setComponent(new LoopDurationComponent(0.25f));
            entityWrapper.setComponent(new CastAnimationComponent(castAnimation.getId()));
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
            EntityWrapper audioHit = entityWorld.getWrapped(entityWorld.createEntity());
            audioHit.setComponent(new AudioComponent("Sounds/sounds/spells/infected_cleaver_hit.ogg"));
            effect1.setComponent(new PlayAudioComponent(audioHit.getId()));
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
            entityWrapper.setComponent(new LifetimeComponent(0.6f));
        }
        else if(templateName.equals("burning_agony")){
            entityWrapper.setComponent(new NameComponent("Burning Agony"));
            entityWrapper.setComponent(new SpellVisualisationComponent("burning_agony"));
            //Add buff area
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buffArea = entityWorld.getWrapped(entityWorld.createEntity());
            buffArea.setComponent(new HitboxComponent(new Circle(2.25f)));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            buffArea.setComponent(new AreaBuffTargetRulesComponent(targetRules.getId()));
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("burning"));
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new FlatMagicDamageComponent(30));
            buff.setComponent(new RepeatingEffectComponent(buffEffect.getId(), 0.5f));
            buffArea.setComponent(new AreaBuffComponent(buff.getId()));
            effect1.setComponent(new AddBuffAreaComponent(buffArea.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Add visualisation
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper visualisationBuff = entityWorld.getWrapped(entityWorld.createEntity());
            visualisationBuff.setComponent(new BuffVisualisationComponent("burning"));
            effect2.setComponent(new AddBuffComponent(visualisationBuff.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Replace spell
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper burningAgonyDeactivate = createFromTemplate(entityWorld, "burning_agony_deactivate," + parameters[0] + "," + buffArea.getId() + "," + entityWrapper.getId() + "," + visualisationBuff.getId());
            effect3.setComponent(new ReplaceSpellWithExistingSpellComponent(parameters[0], burningAgonyDeactivate.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId(), effectTrigger3.getId()));
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
        }
        else if(templateName.equals("burning_agony_deactivate")){
            entityWrapper.setComponent(new NameComponent("Deactivate Burning Agony"));
            entityWrapper.setComponent(new SpellVisualisationComponent("burning_agony_deactivate"));
            //Remove buff area
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new RemoveBuffAreaComponent(parameters[1]));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove visualisation
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveBuffComponent(parameters[3]));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Replace spell
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new ReplaceSpellWithExistingSpellComponent(parameters[0], parameters[2]));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId(), effectTrigger3.getId()));
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
        }
        else if(templateName.equals("event_horizon")){
            entityWrapper.setComponent(new NameComponent("Event Horizon"));
            entityWrapper.setComponent(new SpellVisualisationComponent("event_horizon"));
            //Target effect
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new AddStunComponent(2.5f));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn object
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("event_horizon_object"));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new RangeComponent(12));
            entityWrapper.setComponent(new CooldownComponent(5));
        }
        else if(templateName.equals("event_horizon_object")){
            entityWrapper.setComponent(new ModelComponent("Models/event_horizon/skin.xml"));
            PolygonBuilder polygonBuilder = new PolygonBuilder();
            polygonBuilder.nextOutline(false);
            for(Vector2f point : ModelModifier_EventHorizon.getCirclePoints(6.25f, 5)){
                polygonBuilder.add(point.getX(), point.getY());
            }
            polygonBuilder.nextOutline(true);
            for(Vector2f point : ModelModifier_EventHorizon.getCirclePoints(4.25f, 5)){
                polygonBuilder.add(point.getX(), point.getY());
            }
            entityWrapper.setComponent(new HitboxComponent(new PolygonShape(polygonBuilder.build(false))));
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
            entityWrapper.setComponent(new LifetimeComponent(3));
        }
        else if(templateName.equals("sadism")){
            entityWrapper.setComponent(new NameComponent("Sadism"));
            entityWrapper.setComponent(new SpellVisualisationComponent("sadism"));
            //Add buff
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioStart = entityWorld.getWrapped(entityWorld.createEntity());
            audioStart.setComponent(new AudioComponent("Sounds/sounds/spells/sadism_start.ogg"));
            EntityWrapper audioLoop = entityWorld.getWrapped(entityWorld.createEntity());
            audioLoop.setComponent(new AudioComponent("Sounds/sounds/spells/sadism_loop.ogg"));
            audioLoop.setComponent(new AudioLoopComponent());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("turbo"));
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusFlatHealthRegenerationComponent(180));
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(0.65f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            //On buff remove
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new StopAudioComponent(audioLoop.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger2.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 10));
            effect1.setComponent(new PlayAudioComponent(/*audioStart.getId(),*/audioLoop.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CooldownComponent(15));
        }
        else if(templateName.equals("daydream")){
            entityWrapper.setComponent(new ModelComponent("Models/daydream/skin_default.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("stand"));
            idleAnimation.setComponent(new LoopDurationComponent(8));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            entityWrapper.setComponent(new WalkStepDistanceComponent(7));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("attack_1"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("death"));
            deathAnimation.setComponent(new LoopDurationComponent(1));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(1f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(1000));
            entityWrapper.setComponent(new BaseHealthRegenerationComponent(10));
            entityWrapper.setComponent(new BaseAttackDamageComponent(200));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.6f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(4.5f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper youmuusGhostblade = createFromTemplate(entityWorld, "youmuus_ghostblade");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), youmuusGhostblade.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
        }
        else if(templateName.equals("oz")){
            entityWrapper.setComponent(new ModelComponent("Models/oz/skin_default.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("stand"));
            idleAnimation.setComponent(new LoopDurationComponent(8));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            entityWrapper.setComponent(new WalkStepDistanceComponent(8));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("attack1"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("die"));
            deathAnimation.setComponent(new LoopDurationComponent(4));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(2f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(1000));
            entityWrapper.setComponent(new BaseHealthRegenerationComponent(10));
            entityWrapper.setComponent(new BaseAttackDamageComponent(200));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.6f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(4.5f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper youmuusGhostblade = createFromTemplate(entityWorld, "youmuus_ghostblade");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), youmuusGhostblade.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper pulverize = createFromTemplate(entityWorld, "pulverize");
            EntityWrapper headbutt = createFromTemplate(entityWorld, "headbutt");
            EntityWrapper slap = createFromTemplate(entityWorld, "slap");
            EntityWrapper unstoppableForce = createFromTemplate(entityWorld, "unstoppable_force");
            entityWrapper.setComponent(new SpellsComponent(new int[]{pulverize.getId(), headbutt.getId(), slap.getId(), unstoppableForce.getId()}));
        }
        else if(templateName.equals("pulverize")){
            entityWrapper.setComponent(new NameComponent("Pulverize"));
            entityWrapper.setComponent(new SpellVisualisationComponent("pulverize"));
            //Damage target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(80));
            effect1.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.5f));
            EntityWrapper knockup = entityWorld.getWrapped(entityWorld.createEntity());
            knockup.setComponent(new KnockupDurationComponent(1));
            knockup.setComponent(new KnockupHeightComponent(5));
            effect1.setComponent(new AddKnockupComponent(knockup.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn object
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("pulverize_object"));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/pulverize_hit.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(1));
            effect2.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
            entityWrapper.setComponent(new CastDurationComponent(0.8f));
            entityWrapper.setComponent(new StopAfterCastingComponent());
            entityWrapper.setComponent(new StopBeforeCastingComponent());
            EntityWrapper castAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            castAnimation.setComponent(new NameComponent("attack1"));
            castAnimation.setComponent(new LoopDurationComponent(0.5f));
            entityWrapper.setComponent(new CastAnimationComponent(castAnimation.getId()));
            entityWrapper.setComponent(new CooldownComponent(0.75f));
        }
        else if(templateName.equals("pulverize_object")){
            entityWrapper.setComponent(new HitboxComponent(new Circle(7)));
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
            entityWrapper.setComponent(new LifetimeComponent(0.2f));
        }
        else if(templateName.equals("headbutt")){
            entityWrapper.setComponent(new NameComponent("Headbutt"));
            entityWrapper.setComponent(new SpellVisualisationComponent("headbutt"));
            //Move to target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement1 = entityWorld.getWrapped(entityWorld.createEntity());
            movement1.setComponent(new TargetedMovementTargetComponent());
            movement1.setComponent(new MovementSpeedComponent(30));
            effect1.setComponent(new MoveComponent(movement1.getId()));
            EntityWrapper audioCast1 = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast1.setComponent(new AudioComponent("Sounds/sounds/spells/headbutt_cast.ogg"));
            audioCast1.setComponent(new AudioVolumeComponent(0.75f));
            effect1.setComponent(new PlayAudioComponent(audioCast1.getId()));
            //Knockback target
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetReachedTriggerComponent());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new FlatMagicDamageComponent(110));
            effect2.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.7f));
            EntityWrapper movement2 = entityWorld.getWrapped(entityWorld.createEntity());
            movement2.setComponent(new DisplacementComponent());
            movement2.setComponent(new SourceMovementDirectionComponent());
            movement2.setComponent(new MovementSpeedComponent(30));
            movement2.setComponent(new DistanceLimitComponent(10));
            effect2.setComponent(new MoveComponent(movement2.getId()));
            EntityWrapper audioCast2 = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast2.setComponent(new AudioComponent("Sounds/sounds/spells/headbutt_hit.ogg"));
            audioCast2.setComponent(new AudioVolumeComponent(0.75f));
            effect2.setComponent(new PlayAudioComponent(audioCast2.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerOnceComponent());
            effect1.setComponent(new AddEffectTriggersComponent(effectTrigger2.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new RangeComponent(14));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
            entityWrapper.setComponent(new CooldownComponent(2));
        }
        else if(templateName.equals("slap")){
            entityWrapper.setComponent(new NameComponent("Slap"));
            entityWrapper.setComponent(new SpellVisualisationComponent("slap"));
            //Target effect
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spellEffect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new AddAutoAttackSpellEffectsComponent(spellEffect1.getId()));
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("slap"));
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveSpellEffectsComponent(spellEffect1.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger2.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 5));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect2 = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect2.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect2.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Knockback target
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new FlatMagicDamageComponent(30));
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new DisplacementComponent());
            movement.setComponent(new SourceMovementDirectionComponent());
            movement.setComponent(new MovementSpeedComponent(20));
            movement.setComponent(new DistanceLimitComponent(5));
            effect3.setComponent(new MoveComponent(movement.getId()));
            EntityWrapper audioHit = entityWorld.getWrapped(entityWorld.createEntity());
            audioHit.setComponent(new AudioComponent("Sounds/sounds/spells/slap_hit.ogg"));
            effect3.setComponent(new PlayAudioComponent(audioHit.getId()));
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
        else if(templateName.equals("unstoppable_force")){
            entityWrapper.setComponent(new NameComponent("Unstoppable Force"));
            entityWrapper.setComponent(new SpellVisualisationComponent("unstoppable_force"));
            //Knockup target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(200));
            effect1.setComponent(new ScalingAbilityPowerMagicDamageComponent(1));
            EntityWrapper knockup = entityWorld.getWrapped(entityWorld.createEntity());
            knockup.setComponent(new KnockupDurationComponent(1.5f));
            knockup.setComponent(new KnockupHeightComponent(7));
            effect1.setComponent(new AddKnockupComponent(knockup.getId()));
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
            movement.setComponent(new MovementSpeedComponent(35));
            effect2.setComponent(new MoveComponent(movement.getId()));
            effect2.setComponent(new DeactivateHitboxComponent());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/unstoppable_force_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(1.5f));
            effect2.setComponent(new PlayAudioComponent(audioCast.getId()));
            //Spawn object
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetReachedTriggerComponent());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("unstoppable_force_object," + entityWrapper.getId()));
            effect3.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerOnceComponent());
            //Reactivate hitbox
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new TargetReachedTriggerComponent());
            effectTrigger4.setComponent(new CasterTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new ActivateHitboxComponent());
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerOnceComponent());
            effectTrigger4.setComponent(new TriggerOnCancelComponent());
            effect2.setComponent(new AddEffectTriggersComponent(effectTrigger3.getId(), effectTrigger4.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new RangeComponent(16));
            entityWrapper.setComponent(new CooldownComponent(2));
        }
        else if(templateName.equals("unstoppable_force_object")){
            entityWrapper.setComponent(new HitboxComponent(new Circle(7)));
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
            effect1.setComponent(new TriggerSpellEffectsComponent(parameters[0]));
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/unstoppable_force_hit.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(1.5f));
            effect1.setComponent(new PlayAudioComponent(audioCast.getId()));
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
            entityWrapper.setComponent(new LifetimeComponent(0.2f));
        }
        else if(templateName.equals("varus")){
            entityWrapper.setComponent(new ModelComponent("Models/varus/skin_default.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("idle"));
            idleAnimation.setComponent(new LoopDurationComponent(3));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            entityWrapper.setComponent(new WalkStepDistanceComponent(4.5f));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("death"));
            deathAnimation.setComponent(new LoopDurationComponent(1.5f));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(1.5f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(500));
            entityWrapper.setComponent(new BaseAttackDamageComponent(80));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5.5f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper doransBlade = createFromTemplate(entityWorld, "dorans_blade");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), doransBlade.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper bubbleGun = createFromTemplate(entityWorld, "bubble_gun");
            EntityWrapper saplingToss = createFromTemplate(entityWorld, "sapling_toss");
            entityWrapper.setComponent(new SpellsComponent(new int[]{-1, bubbleGun.getId(), saplingToss.getId()}));
        }
        else if(templateName.equals("bubble_gun")){
            entityWrapper.setComponent(new NameComponent("BubbleGun"));
            entityWrapper.setComponent(new DescriptionComponent("Bubbledubblebubbleblubb."));
            entityWrapper.setComponent(new SpellVisualisationComponent("bubble"));
            //Target effect
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spellEffect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new AddAutoAttackSpellEffectsComponent(spellEffect1.getId()));
            EntityWrapper buff1 = entityWorld.getWrapped(entityWorld.createEntity());
            buff1.setComponent(new BuffVisualisationComponent("bubble"));
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new RemoveSpellEffectsComponent(spellEffect1.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff1.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger2.getId()));
            effect1.setComponent(new AddBuffComponent(buff1.getId(), 5));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect2 = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect2.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect2.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Add buff
            float duration = 4;
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper knockup = entityWorld.getWrapped(entityWorld.createEntity());
            knockup.setComponent(new KnockupDurationComponent(duration));
            knockup.setComponent(new KnockupHeightComponent(5));
            effect3.setComponent(new AddKnockupComponent(knockup.getId()));
            EntityWrapper buff2 = entityWorld.getWrapped(entityWorld.createEntity());
            buff2.setComponent(new BuffVisualisationComponent("bubble"));
            effect3.setComponent(new AddBuffComponent(buff2.getId(), duration));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove buff
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new CasterTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new RemoveBuffComponent(buff1.getId()));
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
            entityWrapper.setComponent(new CooldownComponent(10));
        }
        else if(templateName.equals("sapling_toss")){
            entityWrapper.setComponent(new NameComponent("Sapling Toss"));
            entityWrapper.setComponent(new SpellVisualisationComponent("sapling_toss"));
            //Target effect
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new FlatMagicDamageComponent(120));
            effect1.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.4f));
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(-0.5f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 1));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Spawn projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("sapling_toss_projectile"));
            spawnInformation.setComponent(new SpawnMoveToTargetComponent());
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(15));
            EntityWrapper movementAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            movementAnimation.setComponent(new NameComponent("walk"));
            movementAnimation.setComponent(new LoopDurationComponent(1));
            spawnInformation.setComponent(new SpawnMovementAnimationComponent(movementAnimation.getId()));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/sapling_toss_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
            entityWrapper.setComponent(new CastCancelActionComponent());
        }
        else if(templateName.equals("sapling_toss_projectile")){
            entityWrapper.setComponent(new ModelComponent("Models/varus/skin_default.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("idle"));
            idleAnimation.setComponent(new LoopDurationComponent(1));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            walkAnimation.setComponent(new LoopDurationComponent(1));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new ScaleComponent(0.6f));
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_SPELLS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new IntersectionRulesComponent(targetRules.getId()));
            entityWrapper.setComponent(new RemoveOnMapLeaveComponent());
            //Activate hitbox
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetReachedTriggerComponent());
            effectTrigger1.setComponent(new SourceTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new ActivateHitboxComponent());
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            effectTrigger1.setComponent(new TriggerOnceComponent());
            //Trigger spell effects
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CollisionTriggerComponent());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new TriggerCastedSpellEffectsComponent());
            EntityWrapper audioHit = entityWorld.getWrapped(entityWorld.createEntity());
            audioHit.setComponent(new AudioComponent("Sounds/sounds/spells/sapling_toss_hit.ogg"));
            audioHit.setComponent(new AudioVolumeComponent(1.25f));
            effect2.setComponent(new PlayAudioComponent(audioHit.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Remove projectile
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CollisionTriggerComponent());
            effectTrigger3.setComponent(new SourceTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new RemoveEntityComponent());
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            
            entityWrapper.setComponent(new WalkSpeedComponent(5.5f));
            EntityWrapper autoAttack = createFromTemplate(entityWorld, "empty_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(12));
            entityWrapper.setComponent(new LifetimeComponent(30));
        }
        else if(templateName.equals("eragon")){
            entityWrapper.setComponent(new ModelComponent("Models/little_dragon/skin_default.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("default"));
            idleAnimation.setComponent(new LoopDurationComponent(8));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(0.8f)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(450));
            entityWrapper.setComponent(new BaseAttackDamageComponent(50));
            entityWrapper.setComponent(new BaseAbilityPowerComponent(0));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5.5f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper needlesslyLargeRod = createFromTemplate(entityWorld, "needlessly_large_rod");
            entityWrapper.setComponent(new InventoryComponent(new int[]{boots.getId(), needlesslyLargeRod.getId()}));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper sear = createFromTemplate(entityWorld, "sear");
            entityWrapper.setComponent(new SpellsComponent(new int[]{sear.getId()}));
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
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("bodyslam_object"));
            effect2.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggerDelayComponent(1.1f));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId()));
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
            entityWrapper.setComponent(new LifetimeComponent(0.2f));
        }
        else if(templateName.equals("etherdesert_creep_melee")){
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
            int spawnCounter = entityWorld.getComponent(parameters[0], RepeatingTriggerCounterComponent.class).getCounter();
            entityWrapper.setComponent(new BaseMaximumHealthComponent(600 + (spawnCounter * 10)));
            entityWrapper.setComponent(new BaseAttackDamageComponent(30 + (spawnCounter * 1)));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(3));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(12));
        }
        else if(templateName.equals("etherdesert_creep_range")){
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
            entityWrapper.setComponent(new ScaleComponent(0.5f));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            int spawnCounter = entityWorld.getComponent(parameters[0], RepeatingTriggerCounterComponent.class).getCounter();
            entityWrapper.setComponent(new BaseMaximumHealthComponent(450 + (spawnCounter * 10)));
            entityWrapper.setComponent(new BaseAttackDamageComponent(20 + (spawnCounter * 1)));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(3));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(12));
        }
        else if(templateName.equals("beetle_golem")){
            entityWrapper.setComponent(new ModelComponent("Models/beetle_golem/skin_dark.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("Idle_Normal"));
            idleAnimation.setComponent(new LoopDurationComponent(2.5f));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("Walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("Attack1"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("Death"));
            deathAnimation.setComponent(new LoopDurationComponent(2));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(900));
            entityWrapper.setComponent(new BaseAttackDamageComponent(35));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(4));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper sleep = createFromTemplate(entityWorld, "beetle_golem_sleep");
            entityWrapper.setComponent(new SpellsComponent(new int[]{sleep.getId()}));
        }
        else if(templateName.equals("beetle_golem_sleep")){
            entityWrapper.setComponent(new NameComponent("Sleep"));
            float transitionTime = 2.5f;
            float sleepTime = 4;
            //Start
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper startAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            startAnimation.setComponent(new NameComponent("Sleep_Start"));
            startAnimation.setComponent(new LoopDurationComponent(2.5f));
            startAnimation.setComponent(new FreezeAfterPlayingComponent());
            effect1.setComponent(new PlayAnimationComponent(startAnimation.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Idle
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new CasterTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("meditating"));
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusFlatHealthRegenerationComponent(100));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect2.setComponent(new AddBuffComponent(buff.getId(), sleepTime));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("Sleep_Idle"));
            idleAnimation.setComponent(new LoopDurationComponent(2.5f));
            effect2.setComponent(new PlayAnimationComponent(idleAnimation.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            effectTrigger2.setComponent(new TriggerDelayComponent(transitionTime));
            //End
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper endAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            endAnimation.setComponent(new NameComponent("Sleep_End"));
            endAnimation.setComponent(new LoopDurationComponent(transitionTime));
            endAnimation.setComponent(new FreezeAfterPlayingComponent());
            effect3.setComponent(new PlayAnimationComponent(endAnimation.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            effectTrigger3.setComponent(new TriggerDelayComponent(transitionTime + sleepTime));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId(), effectTrigger2.getId(), effectTrigger3.getId()));
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
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CastDurationComponent(transitionTime + sleepTime + transitionTime));
            entityWrapper.setComponent(new CooldownComponent(15));
        }
        else if(templateName.equals("earth_elemental")){
            entityWrapper.setComponent(new ModelComponent("Models/earth_elemental/skin.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("idle"));
            idleAnimation.setComponent(new LoopDurationComponent(2.5f));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("punch_right"));
            entityWrapper.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            EntityWrapper deathAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            deathAnimation.setComponent(new NameComponent("death"));
            deathAnimation.setComponent(new LoopDurationComponent(1));
            deathAnimation.setComponent(new FreezeAfterPlayingComponent());
            entityWrapper.setComponent(new DeathAnimationComponent(deathAnimation.getId()));
            entityWrapper.setComponent(new AnimationComponent(idleAnimation.getId()));
            
            entityWrapper.setComponent(new HitboxComponent(new Circle(1)));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(1100));
            entityWrapper.setComponent(new BaseAttackDamageComponent(25));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(4));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper powerball = createFromTemplate(entityWorld, "powerball");
            entityWrapper.setComponent(new SpellsComponent(new int[]{powerball.getId()}));
        }
        else if(templateName.equals("powerball")){
            entityWrapper.setComponent(new NameComponent("Powerball"));
            entityWrapper.setComponent(new SpellVisualisationComponent("powerball"));
            //Move to target
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new CasterTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper movement1 = entityWorld.getWrapped(entityWorld.createEntity());
            movement1.setComponent(new TargetedMovementTargetComponent());
            movement1.setComponent(new MovementSpeedComponent(9));
            EntityWrapper loopAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            loopAnimation.setComponent(new NameComponent("roll_loop"));
            loopAnimation.setComponent(new LoopDurationComponent(0.3f));
            movement1.setComponent(new MovementAnimationComponent(loopAnimation.getId()));
            effect1.setComponent(new MoveComponent(movement1.getId()));
            EntityWrapper audioStart = entityWorld.getWrapped(entityWorld.createEntity());
            audioStart.setComponent(new AudioComponent("Sounds/sounds/spells/powerball_start.ogg"));
            EntityWrapper audioLoop = entityWorld.getWrapped(entityWorld.createEntity());
            audioLoop.setComponent(new AudioComponent("Sounds/sounds/spells/powerball_loop.ogg"));
            audioLoop.setComponent(new AudioLoopComponent());
            audioLoop.setComponent(new AudioSuccessorComponent(audioStart.getId(), 0.1f));
            effect1.setComponent(new PlayAudioComponent(audioStart.getId(), audioLoop.getId()));
            //Knockback target
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetReachedTriggerComponent());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            effect2.setComponent(new FlatMagicDamageComponent(120));
            EntityWrapper movement2 = entityWorld.getWrapped(entityWorld.createEntity());
            movement2.setComponent(new DisplacementComponent());
            movement2.setComponent(new SourceMovementDirectionComponent());
            movement2.setComponent(new MovementSpeedComponent(30));
            movement2.setComponent(new DistanceLimitComponent(2));
            effect2.setComponent(new MoveComponent(movement2.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerOnceComponent());
            //Stop animation
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new TargetReachedTriggerComponent());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new StopAnimationComponent());
            effect3.setComponent(new StopAudioComponent(audioLoop.getId()));
            EntityWrapper audioHit = entityWorld.getWrapped(entityWorld.createEntity());
            audioHit.setComponent(new AudioComponent("Sounds/sounds/spells/powerball_hit.ogg"));
            audioHit.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioHit.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerOnceComponent());
            effectTrigger3.setComponent(new TriggerOnCancelComponent());
            effect1.setComponent(new AddEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper spellEffect = entityWorld.getWrapped(entityWorld.createEntity());
            spellEffect.setComponent(new CastedEffectTriggersComponent(effectTrigger1.getId()));
            spellEffect.setComponent(new CastedSpellComponent(entityWrapper.getId()));
            //Trigger spell effects
            EntityWrapper effectTrigger4 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger4.setComponent(new TargetTargetComponent());
            EntityWrapper effect4 = entityWorld.getWrapped(entityWorld.createEntity());
            effect4.setComponent(new TriggerSpellEffectsComponent(entityWrapper.getId()));
            effectTrigger4.setComponent(new TriggeredEffectComponent(effect4.getId()));
            effectTrigger4.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger4.getId()));
            entityWrapper.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
            entityWrapper.setComponent(new CastCancelActionComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
            entityWrapper.setComponent(new CooldownComponent(7));
        }
        else if(templateName.equals("boots")){
            entityWrapper.setComponent(new ItemIDComponent("boots"));
            entityWrapper.setComponent(new ItemRecipeComponent(325));
            entityWrapper.setComponent(new IsSellableComponent(227));
            entityWrapper.setComponent(new BonusFlatWalkSpeedComponent(0.5f));
        }
        else if(templateName.equals("dorans_blade")){
            entityWrapper.setComponent(new ItemIDComponent("dorans_blade"));
            entityWrapper.setComponent(new ItemRecipeComponent(440));
            entityWrapper.setComponent(new IsSellableComponent(176));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(80));
            entityWrapper.setComponent(new BonusFlatAttackDamageComponent(10));
        }
        else if(templateName.equals("dorans_ring")){
            entityWrapper.setComponent(new ItemIDComponent("dorans_ring"));
            entityWrapper.setComponent(new ItemRecipeComponent(400));
            entityWrapper.setComponent(new IsSellableComponent(160));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(80));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(15));
        }
        else if(templateName.equals("needlessly_large_rod")){
            entityWrapper.setComponent(new ItemIDComponent("needlessly_large_rod"));
            entityWrapper.setComponent(new ItemRecipeComponent(1600));
            entityWrapper.setComponent(new IsSellableComponent(1120));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(80));
        }
        else if(templateName.equals("dagger")){
            entityWrapper.setComponent(new ItemIDComponent("dagger"));
            entityWrapper.setComponent(new ItemRecipeComponent(450));
            entityWrapper.setComponent(new IsSellableComponent(315));
            entityWrapper.setComponent(new BonusPercentageAttackSpeedComponent(0.12f));
        }
        else if(templateName.equals("zhonyas_hourglass")){
            entityWrapper.setComponent(new ItemIDComponent("zhonyas_hourglass"));
            entityWrapper.setComponent(new ItemRecipeComponent(500, "dorans_ring", "dorans_ring", "needlessly_large_rod"));
            entityWrapper.setComponent(new IsSellableComponent(2310));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(120));
            EntityWrapper itemActive = createFromTemplate(entityWorld, "zhonyas_hourglass_active");
            entityWrapper.setComponent(new ItemActiveComponent(itemActive.getId()));
        }
        else if(templateName.equals("zhonyas_hourglass_active")){
            //Target effect
            float duration = 2.5f;
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            effect1.setComponent(new RemoveTargetabilityComponent());
            effect1.setComponent(new RemoveVulnerabilityComponent());
            EntityWrapper audioStart = entityWorld.getWrapped(entityWorld.createEntity());
            audioStart.setComponent(new AudioComponent("Sounds/sounds/spells/zhonyas_start.ogg"));
            EntityWrapper audioLoop = entityWorld.getWrapped(entityWorld.createEntity());
            audioLoop.setComponent(new AudioComponent("Sounds/sounds/spells/zhonyas_loop.ogg"));
            audioLoop.setComponent(new AudioLoopComponent());
            audioLoop.setComponent(new AudioSuccessorComponent(audioStart.getId(), 0.05f));
            effect1.setComponent(new PlayAudioComponent(audioStart.getId(), audioLoop.getId()));
            effectTrigger1.setComponent(new TriggeredEffectComponent(effect1.getId()));
            effectTrigger1.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            buff.setComponent(new BuffVisualisationComponent("zhonyas"));
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            effect3.setComponent(new AddTargetabilityComponent());
            effect3.setComponent(new AddVulnerabilityComponent());
            EntityWrapper audioEnd = entityWorld.getWrapped(entityWorld.createEntity());
            audioEnd.setComponent(new AudioComponent("Sounds/sounds/spells/zhonyas_end.ogg"));
            effect3.setComponent(new StopAudioComponent(audioLoop.getId()));
            effect3.setComponent(new PlayAudioComponent(audioEnd.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            buff.setComponent(new OnBuffRemoveEffectTriggersComponent(effectTrigger3.getId()));
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
        else if(templateName.equals("youmuus_ghostblade")){
            entityWrapper.setComponent(new ItemIDComponent("youmuus_ghostblade"));
            entityWrapper.setComponent(new ItemRecipeComponent(2700));
            entityWrapper.setComponent(new IsSellableComponent(1890));
            entityWrapper.setComponent(new BonusFlatAttackDamageComponent(30));
            EntityWrapper itemActive = createFromTemplate(entityWorld, "youmuus_ghostblade_active");
            entityWrapper.setComponent(new ItemActiveComponent(itemActive.getId()));
        }
        else if(templateName.equals("youmuus_ghostblade_active")){
            //Add buff
            EntityWrapper effectTrigger1 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger1.setComponent(new TargetTargetComponent());
            EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            buffEffect.setComponent(new BonusPercentageAttackSpeedComponent(1));
            buffEffect.setComponent(new BonusPercentageWalkSpeedComponent(0.35f));
            buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
            effect1.setComponent(new AddBuffComponent(buff.getId(), 6));
            EntityWrapper audioStart = entityWorld.getWrapped(entityWorld.createEntity());
            audioStart.setComponent(new AudioComponent("Sounds/sounds/spells/youmuus_ghostblade_cast.ogg"));
            effect1.setComponent(new PlayAudioComponent(audioStart.getId()));
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
        else if(templateName.equals("tiamat")){
            entityWrapper.setComponent(new ItemIDComponent("tiamat"));
            entityWrapper.setComponent(new ItemRecipeComponent(1900));
            entityWrapper.setComponent(new IsSellableComponent(1330));
            entityWrapper.setComponent(new BonusFlatAttackDamageComponent(40));
            entityWrapper.setComponent(new BonusFlatHealthRegenerationComponent(15));
        }
        else if(templateName.equals("warmogs_armor")){
            entityWrapper.setComponent(new ItemIDComponent("warmogs_armor"));
            entityWrapper.setComponent(new ItemRecipeComponent(2500));
            entityWrapper.setComponent(new IsSellableComponent(1981));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(1000));
            entityWrapper.setComponent(new BonusFlatHealthRegenerationComponent(15));
        }
    }
}
