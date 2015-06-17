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
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.audio.*;
import amara.game.entitysystem.components.effects.buffs.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.effects.movement.*;
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
import amara.game.entitysystem.components.units.bounties.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import amara.game.entitysystem.templates.XMLTemplateManager;


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
        String[] parts = template.split(",");
        String templateName = parts[0];
        String[] parameters = new String[parts.length - 1];
        for(int i=0;i<parameters.length;i++){
            parameters[i] = parts[1 + i];
        }
        loadTemplate(entityWorld, entity, templateName, parameters);
    }
    
    public static void loadTemplate(EntityWorld entityWorld, int entity, String templateName, String[] parametersText){
        EntityWrapper entityWrapper = entityWorld.getWrapped(entity);
        XMLTemplateManager.getInstance().loadTemplate(entityWorld, entity, templateName, parametersText);
        int[] parameters = new int[parametersText.length];
        for(int i=0;i<parameters.length;i++){
            parameters[i] = Integer.parseInt(parametersText[i]);
        }
        if(templateName.equals("event_horizon_object")){
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
        }
        else if(templateName.equals("eragon")){
            entityWrapper.setComponent(new NameComponent("Eragon"));
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
            entityWrapper.setComponent(new BaseArmorComponent(25));
            entityWrapper.setComponent(new BaseMagicResistanceComponent(30));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(5.5f));
            EntityWrapper boots = createFromTemplate(entityWorld, "boots");
            EntityWrapper needlesslyLargeRod = createFromTemplate(entityWorld, "needlessly_large_rod");
            entityWrapper.setComponent(new InventoryComponent(boots.getId(), needlesslyLargeRod.getId()));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());

            EntityWrapper autoAttack = createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));

            EntityWrapper sear = createFromTemplate(entityWorld, "sear");
            entityWrapper.setComponent(new SpellsComponent(sear.getId()));
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
            effect2.setComponent(new SpawnComponent(spawnInformation.getId()));
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
            entityWrapper.setComponent(new BaseCooldownComponent(5));
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
            int spawnCounter = entityWorld.getComponent(parameters[0], RepeatingTriggerCounterComponent.class).getCounter();
            entityWrapper.setComponent(new BaseMaximumHealthComponent(320 + (spawnCounter * 2)));
            entityWrapper.setComponent(new BaseAttackDamageComponent(12 + (spawnCounter * 0.25f)));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(3));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(12));
            entityWrapper.setComponent(new MaximumAggroRangeComponent(30));
            int bountyEntity = entityWorld.createEntity();
            entityWorld.setComponent(bountyEntity, new BountyGoldComponent(20 + (int) (spawnCounter * 0.5)));
            entityWrapper.setComponent(new BountyComponent(bountyEntity));
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
            entityWrapper.setComponent(new NameComponent("Beetle Golem"));
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
            entityWrapper.setComponent(new SpellsComponent(sleep.getId()));
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
            entityWrapper.setComponent(new BaseCooldownComponent(15));
        }
        else if(templateName.equals("earth_elemental")){
            entityWrapper.setComponent(new NameComponent("Earth Elemental"));
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
            entityWrapper.setComponent(new SpellsComponent(powerball.getId()));
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
            entityWrapper.setComponent(new CastTurnToTargetComponent());
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
            entityWrapper.setComponent(new BaseCooldownComponent(7));
        }
        else if(templateName.equals("pseudospider")){
            entityWrapper.setComponent(new NameComponent("Pseudospider"));
            entityWrapper.setComponent(new ModelComponent("Models/pseudospider/skin.xml"));
            EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            idleAnimation.setComponent(new NameComponent("idle"));
            idleAnimation.setComponent(new LoopDurationComponent(1.5f));
            entityWrapper.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
            EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            walkAnimation.setComponent(new NameComponent("walk"));
            entityWrapper.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("melee_attack"));
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
            entityWrapper.setComponent(new BaseMaximumHealthComponent(400));
            entityWrapper.setComponent(new BaseAttackDamageComponent(20));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.9f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(6));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "melee_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
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
                    position = new Vector2f(212, 199);
                    direction = new Vector2f(0, 1);
                    break;
                
                case 1:
                    position = new Vector2f(217.5f, 204.5f);
                    direction = new Vector2f(-1, 0);
                    break;
            }
            if(parameters[0] == 1){
                position.setX(350 - position.getX());
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
            Vector2f position = new Vector2f(216.5f, 200);
            Vector2f direction = new Vector2f(-1, 1);
            if(parameters[0] == 1){
                position.setX(350 - position.getX());
                direction.setX(-1 * direction.getX());
            }
            entityWrapper.setComponent(new PositionComponent(position));
            entityWrapper.setComponent(new DirectionComponent(direction));
            entityWrapper.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
            entityWrapper.setComponent(new SetNewTargetSpellsOnCooldownComponent(new int[]{0}, new int[]{6}));
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
            entityWrapper.setComponent(new BaseMaximumHealthComponent(4000));
            entityWrapper.setComponent(new BaseHealthRegenerationComponent(40));
            entityWrapper.setComponent(new BaseAttackDamageComponent(150));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.6f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(2.5f));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            
            EntityWrapper bodyslam = EntityTemplate.createFromTemplate(entityWorld, "bodyslam");
            entityWrapper.setComponent(new SpellsComponent(bodyslam.getId()));
            
            EntityWrapper bounty = entityWorld.getWrapped(entityWorld.createEntity());
            bounty.setComponent(new BountyGoldComponent(150));
            EntityWrapper bountyBuff = entityWorld.getWrapped(entityWorld.createEntity());
            bountyBuff.setComponent(new BuffVisualisationComponent("baron_nashor"));
            EntityWrapper bountyBuffEffect = entityWorld.getWrapped(entityWorld.createEntity());
            bountyBuffEffect.setComponent(new BonusFlatAttackDamageComponent(50));
            bountyBuffEffect.setComponent(new BonusFlatAbilityPowerComponent(50));
            bountyBuffEffect.setComponent(new BonusFlatWalkSpeedComponent(0.5f));
            bountyBuff.setComponent(new ContinuousEffectComponent(bountyBuffEffect.getId()));
            bounty.setComponent(new BountyBuffComponent(bountyBuff.getId(), 60));
            entityWrapper.setComponent(new BountyComponent(bounty.getId()));
        }
        else if(templateName.equals("arama_camp_boss")){
            entityWrapper.setComponent(new PositionComponent(new Vector2f(175, 226)));
            entityWrapper.setComponent(new DirectionComponent(new Vector2f(0, -1)));
            entityWrapper.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
            entityWrapper.setComponent(new TeamComponent(0));
        }
        else if(templateName.equals("tower")){
            entityWrapper.setComponent(new NameComponent("Tower"));
            entityWrapper.setComponent(new ModelComponent("Models/tower/skin.xml"));
            
            entityWrapper.setComponent(new IsAliveComponent());
            entityWrapper.setComponent(new BaseMaximumHealthComponent(1200));
            entityWrapper.setComponent(new BaseAttackDamageComponent(150));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.8f));
            entityWrapper.setComponent(new BaseArmorComponent(100));
            entityWrapper.setComponent(new BaseMagicResistanceComponent(100));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "tower_shot");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(19));
            entityWrapper.setComponent(new MaximumAggroRangeComponent(19));
        }
        else if(templateName.equals("tower_shot")){
            entityWrapper.setComponent(new NameComponent("Tower Shot"));
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
            //Spawn projectile
            EntityWrapper effectTrigger2 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger2.setComponent(new TargetTargetComponent());
            EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("tower_shot_projectile"));
            spawnInformation.setComponent(new SpawnMoveToTargetComponent());
            spawnInformation.setComponent(new SpawnMovementSpeedComponent(18));
            effect2.setComponent(new SpawnComponent(spawnInformation.getId()));
            effectTrigger2.setComponent(new TriggeredEffectComponent(effect2.getId()));
            effectTrigger2.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            //Play audio
            EntityWrapper effectTrigger3 = entityWorld.getWrapped(entityWorld.createEntity());
            effectTrigger3.setComponent(new CasterTargetComponent());
            EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper audioCast = entityWorld.getWrapped(entityWorld.createEntity());
            audioCast.setComponent(new AudioComponent("Sounds/sounds/spells/tower_shot_cast.ogg"));
            audioCast.setComponent(new AudioVolumeComponent(0.75f));
            effect3.setComponent(new PlayAudioComponent(audioCast.getId()));
            effectTrigger3.setComponent(new TriggeredEffectComponent(effect3.getId()));
            effectTrigger3.setComponent(new TriggerSourceComponent(entityWrapper.getId()));
            entityWrapper.setComponent(new InstantEffectTriggersComponent(effectTrigger2.getId(), effectTrigger3.getId()));
            entityWrapper.setComponent(new CastCancelActionComponent());
            entityWrapper.setComponent(new CastCancelableComponent());
            entityWrapper.setComponent(new StopBeforeCastingComponent());
            entityWrapper.setComponent(new RangeComponent(19));
            EntityWrapper targetRules = entityWorld.getWrapped(entityWorld.createEntity());
            targetRules.setComponent(new AcceptEnemiesComponent());
            entityWrapper.setComponent(new SpellTargetRulesComponent(targetRules.getId()));
        }
        else if(templateName.equals("tower_shot_projectile")){
            entityWrapper.setComponent(new ModelComponent("Models/fireball/skin.xml"));
            entityWrapper.setComponent(new IsProjectileComponent());
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
            entityWrapper.setComponent(new BaseCooldownComponent(5));
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
            entityWrapper.setComponent(new BaseCooldownComponent(15));
        }
        else if(templateName.equals("tiamat")){
            entityWrapper.setComponent(new ItemIDComponent("tiamat"));
            entityWrapper.setComponent(new ItemRecipeComponent(1900));
            entityWrapper.setComponent(new IsSellableComponent(1330));
            entityWrapper.setComponent(new BonusFlatAttackDamageComponent(40));
            entityWrapper.setComponent(new BonusFlatHealthRegenerationComponent(15));
        }
        else if(templateName.equals("giants_belt")){
            entityWrapper.setComponent(new ItemIDComponent("giants_belt"));
            entityWrapper.setComponent(new ItemRecipeComponent(1000));
            entityWrapper.setComponent(new IsSellableComponent(700));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(380));
        }
        else if(templateName.equals("rejuvenation_bead")){
            entityWrapper.setComponent(new ItemIDComponent("rejuvenation_bead"));
            entityWrapper.setComponent(new ItemRecipeComponent(180));
            entityWrapper.setComponent(new IsSellableComponent(126));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(2));
        }
        else if(templateName.equals("warmogs_armor")){
            entityWrapper.setComponent(new ItemIDComponent("warmogs_armor"));
            entityWrapper.setComponent(new ItemRecipeComponent(1500, "giants_belt", "rejuvenation_bead", "rejuvenation_bead"));
            entityWrapper.setComponent(new IsSellableComponent(1981));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(1000));
            entityWrapper.setComponent(new BonusFlatHealthRegenerationComponent(15));
        }
        else if(templateName.equals("rod_of_ages")){
            entityWrapper.setComponent(new ItemIDComponent("rod_of_ages"));
            entityWrapper.setComponent(new ItemRecipeComponent(2800));
            entityWrapper.setComponent(new IsSellableComponent(1960));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(450));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(60));
        }
        else if(templateName.equals("amplifying_tome")){
            entityWrapper.setComponent(new ItemIDComponent("amplifying_tome"));
            entityWrapper.setComponent(new ItemRecipeComponent(435));
            entityWrapper.setComponent(new IsSellableComponent(305));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(20));
        }
        else if(templateName.equals("fiendish_codex")){
            entityWrapper.setComponent(new ItemIDComponent("fiendish_codex"));
            entityWrapper.setComponent(new ItemRecipeComponent(385, "amplifying_tome"));
            entityWrapper.setComponent(new IsSellableComponent(574));
            entityWrapper.setComponent(new BonusFlatAbilityPowerComponent(30));
            entityWrapper.setComponent(new BonusPercentageCooldownSpeedComponent(0.1f));
        }
        else if(templateName.equals("ionian_boots_of_lucidity")){
            entityWrapper.setComponent(new ItemIDComponent("ionian_boots_of_lucidity"));
            entityWrapper.setComponent(new ItemRecipeComponent(675, "boots"));
            entityWrapper.setComponent(new IsSellableComponent(700));
            entityWrapper.setComponent(new BonusFlatWalkSpeedComponent(0.9f));
            entityWrapper.setComponent(new BonusPercentageCooldownSpeedComponent(0.15f));
        }
        else if(templateName.equals("cloth_armor")){
            entityWrapper.setComponent(new ItemIDComponent("cloth_armor"));
            entityWrapper.setComponent(new ItemRecipeComponent(300));
            entityWrapper.setComponent(new IsSellableComponent(210));
            entityWrapper.setComponent(new BonusFlatArmorComponent(15));
        }
        else if(templateName.equals("chain_vest")){
            entityWrapper.setComponent(new ItemIDComponent("chain_vest"));
            entityWrapper.setComponent(new ItemRecipeComponent(450, "cloth_armor"));
            entityWrapper.setComponent(new IsSellableComponent(525));
            entityWrapper.setComponent(new BonusFlatArmorComponent(40));
        }
        else if(templateName.equals("randuins_omen")){
            entityWrapper.setComponent(new ItemIDComponent("randuins_omen"));
            entityWrapper.setComponent(new ItemRecipeComponent(800, "giants_belt", "chain_vest"));
            entityWrapper.setComponent(new IsSellableComponent(1995));
            entityWrapper.setComponent(new BonusFlatMaximumHealthComponent(500));
            entityWrapper.setComponent(new BonusFlatArmorComponent(70));
        }
        else if(templateName.equals("null_magic_mantle")){
            entityWrapper.setComponent(new ItemIDComponent("null_magic_mantle"));
            entityWrapper.setComponent(new ItemRecipeComponent(500));
            entityWrapper.setComponent(new IsSellableComponent(350));
            entityWrapper.setComponent(new BonusFlatMagicResistanceComponent(25));
        }
        else if(templateName.equals("negatron_cloak")){
            entityWrapper.setComponent(new ItemIDComponent("negatron_cloak"));
            entityWrapper.setComponent(new ItemRecipeComponent(350, "null_magic_mantle"));
            entityWrapper.setComponent(new IsSellableComponent(595));
            entityWrapper.setComponent(new BonusFlatMagicResistanceComponent(45));
        }
        else if(templateName.equals("force_of_nature")){
            entityWrapper.setComponent(new ItemIDComponent("force_of_nature"));
            entityWrapper.setComponent(new ItemRecipeComponent(1000, "rejuvenation_bead", "rejuvenation_bead", "negatron_cloak"));
            entityWrapper.setComponent(new IsSellableComponent(1700));
            entityWrapper.setComponent(new BonusFlatHealthRegenerationComponent(10));
            entityWrapper.setComponent(new BonusFlatMagicResistanceComponent(76));
            entityWrapper.setComponent(new BonusFlatWalkSpeedComponent(0.16f));
        }
    }
}
