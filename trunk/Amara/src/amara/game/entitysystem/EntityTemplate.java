/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.heals.FlatHealComponent;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
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
        if(templateName.equals("autoattack_projectile")){
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
            entityWrapper.setComponent(new TargetReachedTriggerEffectComponent(effect.getId()));
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
        else if(templateName.equals("null_sphere")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
            EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
            effect.setComponent(new FlatMagicDamageComponent(80));
            effect.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.7f));
            effect.setComponent(new SilenceComponent(2));
            entityWrapper.setComponent(new TargetReachedTriggerEffectComponent(effect.getId()));
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
        else if(templateName.equals("cloud")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
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

    public static EntityWrapper createPlayerEntity(EntityWorld entityWorld, int playerID){
        EntityWrapper player = entityWorld.getWrapped(entityWorld.createEntity());
        EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
        
        EntityWrapper doransBlade = EntityTemplate.createFromTemplate(entityWorld, "dorans_blade");
        EntityWrapper doransRing = EntityTemplate.createFromTemplate(entityWorld, "dorans_ring");
        EntityWrapper needlesslyLargeRod = EntityTemplate.createFromTemplate(entityWorld, "needlessly_large_rod");
        EntityWrapper dagger = EntityTemplate.createFromTemplate(entityWorld, "dagger");
        
        EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        walkAnimation.setComponent(new NameComponent("walk"));
        walkAnimation.setComponent(new LoopDurationComponent(1));
        unit.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
        EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
        unit.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
        
        switch(playerID){
            case 0:
                unit.setComponent(new ModelComponent("Models/minion/skin.xml"));
                EntityWrapper danceAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                danceAnimation.setComponent(new NameComponent("dance"));
                danceAnimation.setComponent(new LoopDurationComponent(2.66f));
                unit.setComponent(new AnimationComponent(danceAnimation.getId()));
                
                unit.setComponent(new ScaleComponent(0.75f));
                unit.setComponent(new PositionComponent(new Vector2f(22, 16.5f)));
                unit.setComponent(new DirectionComponent(new Vector2f(0, -1)));
                unit.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
                unit.setComponent(new AntiGhostComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                unit.setComponent(new TeamComponent(1));
                unit.setComponent(new IsTargetableComponent());
                
                unit.setComponent(new BaseMaximumHealthComponent(300));
                unit.setComponent(new InventoryComponent(new int[]{doransBlade.getId(), doransBlade.getId(), doransBlade.getId()}));
                unit.setComponent(new RequestUpdateAttributesComponent());
                
                EntityWrapper nullSphere = entityWorld.getWrapped(entityWorld.createEntity());
                nullSphere.setComponent(new NameComponent("Null Sphere"));
                nullSphere.setComponent(new DescriptionComponent("Silences an enemy."));
                EntityWrapper spawnInformation1 = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation1.setComponent(new SpawnTemplateComponent("null_sphere"));
                spawnInformation1.setComponent(new SpawnMovementSpeedComponent(25));
                nullSphere.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation1.getId()}));
                nullSphere.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
                
                EntityWrapper riftWalk = entityWorld.getWrapped(entityWorld.createEntity());
                riftWalk.setComponent(new NameComponent("Riftwalk"));
                riftWalk.setComponent(new DescriptionComponent("Teleports to the target location."));
                riftWalk.setComponent(new TeleportCasterToTargetPositionComponent());
                riftWalk.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
                
                EntityWrapper ignite = entityWorld.getWrapped(entityWorld.createEntity());
                ignite.setComponent(new NameComponent("Ignite"));
                ignite.setComponent(new DescriptionComponent("Deals damage over time to the target."));
                EntityWrapper igniteBuff = entityWorld.getWrapped(entityWorld.createEntity());
                igniteBuff.setComponent(new BuffVisualisationComponent("burning"));
                EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
                effect2.setComponent(new FlatMagicDamageComponent(40));
                effect2.setComponent(new ScalingAbilityPowerMagicDamageComponent(1));
                igniteBuff.setComponent(new RepeatingEffectComponent(effect2.getId(), 0.5f));
                ignite.setComponent(new InstantTargetBuffComponent(igniteBuff.getId(), 3));
                ignite.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
                
                unit.setComponent(new SpellsComponent(new int[]{nullSphere.getId(), riftWalk.getId(), ignite.getId()}));
                break;
            
            case 1:
                unit.setComponent(new ModelComponent("Models/wizard/skin.xml"));
                
                unit.setComponent(new ScaleComponent(0.5f));
                unit.setComponent(new PositionComponent(new Vector2f(7, 7)));
                unit.setComponent(new DirectionComponent(new Vector2f(1, 1)));
                unit.setComponent(new HitboxComponent(new Circle(1)));
                unit.setComponent(new AntiGhostComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                unit.setComponent(new TeamComponent(2));
                unit.setComponent(new IsTargetableComponent());
                
                unit.setComponent(new BaseMaximumHealthComponent(500));
                unit.setComponent(new BaseAttackDamageComponent(60));
                unit.setComponent(new BaseAbilityPowerComponent(0));
                unit.setComponent(new BaseAttackSpeedComponent(0.6f));
                unit.setComponent(new InventoryComponent(new int[]{doransBlade.getId(), doransRing.getId(), needlesslyLargeRod.getId(), dagger.getId(), dagger.getId()}));
                unit.setComponent(new RequestUpdateAttributesComponent());
                
                EntityWrapper sear = entityWorld.getWrapped(entityWorld.createEntity());
                sear.setComponent(new NameComponent("Sear"));
                sear.setComponent(new DescriptionComponent("Throws a fireball."));
                EntityWrapper spawnInformation2 = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation2.setComponent(new SpawnTemplateComponent("fireball"));
                spawnInformation2.setComponent(new SpawnMovementSpeedComponent(4));
                sear.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation2.getId()}));
                sear.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
                sear.setComponent(new CooldownComponent(1));
                //Spell #2
                EntityWrapper pillarOfFlame = entityWorld.getWrapped(entityWorld.createEntity());
                pillarOfFlame.setComponent(new NameComponent("Pillar of Flame"));
                pillarOfFlame.setComponent(new DescriptionComponent("Spawns a fire pillar at the target location."));
                EntityWrapper spawnInformation3 = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation3.setComponent(new SpawnTemplateComponent("pillar_of_flame"));
                pillarOfFlame.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation3.getId()}));
                pillarOfFlame.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
                pillarOfFlame.setComponent(new CooldownComponent(2));
                //Spell #3
                EntityWrapper battleCry = entityWorld.getWrapped(entityWorld.createEntity());
                battleCry.setComponent(new NameComponent("Battle Cry"));
                battleCry.setComponent(new DescriptionComponent("Increases the attack speed for a few seconds."));
                EntityWrapper battleCryBuff = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper effect3 = entityWorld.getWrapped(entityWorld.createEntity());
                effect3.setComponent(new BonusPercentageAttackSpeedComponent(0.8f));
                battleCryBuff.setComponent(new ContinuousEffectComponent(effect3.getId()));
                battleCry.setComponent(new InstantTargetBuffComponent(battleCryBuff.getId(), 5));
                battleCry.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
                
                unit.setComponent(new SpellsComponent(new int[]{sear.getId(), pillarOfFlame.getId(), battleCry.getId()}));
                
                EntityWrapper autoAttack1 = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation4 = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation4.setComponent(new SpawnTemplateComponent("autoattack_projectile", "cloud"));
                spawnInformation4.setComponent(new SpawnMovementSpeedComponent(15));
                autoAttack1.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation4.getId()}));
                autoAttack1.setComponent(new CooldownComponent(1));
                autoAttack1.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
                unit.setComponent(new AutoAttackComponent(autoAttack1.getId()));
                unit.setComponent(new AutoAggroComponent(4));
                break;
            
            case 2:
                unit.setComponent(new ModelComponent("Models/robot/skin.xml"));
                
                unit.setComponent(new PositionComponent(new Vector2f(20, 7)));
                unit.setComponent(new DirectionComponent(new Vector2f(-1, -1)));
                unit.setComponent(new HitboxComponent(new Circle(1.5f)));
                unit.setComponent(new AntiGhostComponent());
                unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                unit.setComponent(new TeamComponent(3));
                unit.setComponent(new IsTargetableComponent());
                
                unit.setComponent(new BaseMaximumHealthComponent(700));
                unit.setComponent(new BaseAttackDamageComponent(40));
                unit.setComponent(new BaseAbilityPowerComponent(0));
                unit.setComponent(new BaseAttackSpeedComponent(0.7f));
                unit.setComponent(new RequestUpdateAttributesComponent());
                unit.setComponent(new HealthComponent(500));
                
                EntityWrapper autoAttack2 = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper autoAttackEffect1 = entityWorld.getWrapped(entityWorld.createEntity());
                autoAttackEffect1.setComponent(new SpawnTemplateComponent("autoattack_projectile", "cloud"));
                autoAttackEffect1.setComponent(new ScalingAttackDamagePhysicalDamageComponent(1));
                autoAttack2.setComponent(new InstantTargetEffectComponent(autoAttackEffect1.getId()));
                autoAttack2.setComponent(new CooldownComponent(1));
                autoAttack2.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
                unit.setComponent(new AutoAttackComponent(autoAttack2.getId()));
                
                EntityWrapper grab = entityWorld.getWrapped(entityWorld.createEntity());
                grab.setComponent(new NameComponent("Grab"));
                grab.setComponent(new DescriptionComponent("Beep boop."));
                EntityWrapper spawnInformation5 = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation5.setComponent(new SpawnTemplateComponent("grab_projectile," + unit.getId()));
                spawnInformation5.setComponent(new SpawnMovementSpeedComponent(12));
                grab.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation5.getId()}));
                grab.setComponent(new CooldownComponent(3));
                grab.setComponent(new CastTypeComponent(CastTypeComponent.CastType.LINEAR_SKILLSHOT));
                EntityWrapper grabAnimation = entityWorld.getWrapped(entityWorld.createEntity());
                grabAnimation.setComponent(new NameComponent("grab"));
                grabAnimation.setComponent(new LoopDurationComponent(1.5f));
                grab.setComponent(new CastAnimationComponent(grabAnimation.getId()));
                
                EntityWrapper heal = entityWorld.getWrapped(entityWorld.createEntity());
                grab.setComponent(new NameComponent("Heal"));
                grab.setComponent(new DescriptionComponent("Sona in a nutshell."));
                EntityWrapper healEffect = entityWorld.getWrapped(entityWorld.createEntity());
                healEffect.setComponent(new FlatHealComponent(100));
                heal.setComponent(new InstantTargetEffectComponent(healEffect.getId()));
                heal.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SELFCAST));
                
                unit.setComponent(new SpellsComponent(new int[]{grab.getId(), heal.getId()}));
                break;
        }
        player.setComponent(new SelectedUnitComponent(unit.getId()));
        return player;
    }
}
