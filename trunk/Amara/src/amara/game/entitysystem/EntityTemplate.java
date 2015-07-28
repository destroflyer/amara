/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;

import com.jme3.math.Vector2f;
import amara.engine.applications.ingame.client.models.modifiers.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.bounties.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;
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
        if(templateName.equals("spells/event_horizon_object")){
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
            entityWrapper.setComponent(new BaseAttackDamageComponent(18 + (spawnCounter * 0.3f)));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(3));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
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
            entityWrapper.setComponent(new ScaleComponent(0.5f));
            entityWrapper.setComponent(new IntersectionPushComponent());
            entityWrapper.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            entityWrapper.setComponent(new HitboxActiveComponent());
            
            entityWrapper.setComponent(new IsAliveComponent());
            int spawnCounter = entityWorld.getComponent(parameters[0], RepeatingTriggerCounterComponent.class).getCounter();
            entityWrapper.setComponent(new BaseMaximumHealthComponent(450 + (spawnCounter * 10)));
            entityWrapper.setComponent(new BaseAttackDamageComponent(22 + (spawnCounter * 1)));
            entityWrapper.setComponent(new BaseAttackSpeedComponent(0.7f));
            entityWrapper.setComponent(new BaseWalkSpeedComponent(3));
            entityWrapper.setComponent(new RequestUpdateAttributesComponent());
            entityWrapper.setComponent(new IsTargetableComponent());
            entityWrapper.setComponent(new IsVulnerableComponent());
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            entityWrapper.setComponent(new AutoAggroComponent(12));
            entityWrapper.setComponent(new MaximumAggroRangeComponent(30));
            entityWrapper.setComponent(new AggroResetTimerComponent(4));
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
            
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/default_autoattack");
            entityWrapper.setComponent(new AutoAttackComponent(autoAttack.getId()));
            
            EntityWrapper bodyslam = EntityTemplate.createFromTemplate(entityWorld, "spells/bodyslam");
            entityWrapper.setComponent(new SpellsComponent(bodyslam.getId()));
            
            entityWrapper.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
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
    }
}
