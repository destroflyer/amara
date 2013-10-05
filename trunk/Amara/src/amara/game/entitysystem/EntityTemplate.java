/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effects.*;
import amara.game.entitysystem.components.units.intersections.*;
import amara.game.entitysystem.components.visuals.*;
import shapes.Circle;

/**
 *
 * @author Carl
 */
public class EntityTemplate{
    
    public static void loadTemplates(EntityWorld entityWorld, int entity, String[] templateNames){
        for(int i=0;i<templateNames.length;i++){
            loadTemplate(entityWorld, entity, templateNames[i]);
        }
    }
    
    public static void loadTemplate(EntityWorld entityWorld, int entity, String templateName){
        EntityWrapper entityWrapper = entityWorld.getWrapped(entity);
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
        else if(templateName.equals("cloud")){
            entityWrapper.setComponent(new ModelComponent("Models/cloud/skin.xml"));
        }
    }
}
