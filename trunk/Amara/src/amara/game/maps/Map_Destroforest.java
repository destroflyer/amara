/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.audio.*;
import amara.game.entitysystem.components.camps.*;
import amara.game.entitysystem.components.effects.game.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.games.Game;

/**
 *
 * @author Carl
 */
public class Map_Destroforest extends Map{

    public Map_Destroforest(){
        
    }

    @Override
    public void load(EntityWorld entityWorld){
        EntityWrapper audioBackgroundMusic = entityWorld.getWrapped(entityWorld.createEntity());
        audioBackgroundMusic.setComponent(new AudioComponent("Sounds/music/mystic_river.ogg"));
        audioBackgroundMusic.setComponent(new AudioVolumeComponent(1.75f));
        audioBackgroundMusic.setComponent(new AudioLoopComponent());
        audioBackgroundMusic.setComponent(new IsAudioPlayingComponent());
        
        EntityWrapper instantEffectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
        instantEffectTrigger.setComponent(new TriggerTemporaryComponent());
        instantEffectTrigger.setComponent(new InstantTriggerComponent());
        instantEffectTrigger.setComponent(new CustomTargetComponent(Game.ENTITY));
        EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
        effect1.setComponent(new PlayCinematicComponent(Map_Destroforest_CinematicIntro.class.getName()));
        instantEffectTrigger.setComponent(new TriggeredEffectComponent(effect1.getId()));
        
        for(int i=0;i<3;i++){
            EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
            unit.setComponent(new ModelComponent("Models/wizard/skin_default.xml"));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            unit.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            unit.setComponent(new HitboxComponent(new Circle(1)));
            unit.setComponent(new IntersectionPushComponent());
            unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            unit.setComponent(new HitboxActiveComponent());
            unit.setComponent(new IsAliveComponent());
            unit.setComponent(new BaseMaximumHealthComponent(1500));
            unit.setComponent(new BaseAttackDamageComponent((i == 2)?80:30));
            unit.setComponent(new BaseAttackSpeedComponent(0.5f));
            unit.setComponent(new BaseWalkSpeedComponent(3));
            unit.setComponent(new RequestUpdateAttributesComponent());
            unit.setComponent(new IsTargetableComponent());
            unit.setComponent(new IsVulnerableComponent());
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "default_autoattack");
            unit.setComponent(new AutoAttackComponent(autoAttack.getId()));
            unit.setComponent(new AutoAggroComponent(24));
            Vector2f position = null;
            Vector2f direction = null;
            switch(i){
                case 0:
                    unit.setComponent(new ScaleComponent(0.75f));
                    position = new Vector2f(142.5f, 195);
                    direction = new Vector2f(0, -1);
                    break;
                
                case 1:
                    unit.setComponent(new ScaleComponent(0.75f));
                    position = new Vector2f(142.5f, 167);
                    direction = new Vector2f(0, 1);
                    break;
                
                case 2:
                    unit.setComponent(new ScaleComponent(1.4f));
                    position = new Vector2f(142.5f, 182);
                    direction = new Vector2f(-1, 0);
                    break;
            }
            unit.setComponent(new PositionComponent(position));
            unit.setComponent(new DirectionComponent(direction));
            unit.setComponent(new TeamComponent(0));
            EntityWrapper camp = entityWorld.getWrapped(entityWorld.createEntity());
            camp.setComponent(new CampTransformComponent(position, direction));
            camp.setComponent(new CampMaximumAggroDistanceComponent(10));
            camp.setComponent(new CampHealthResetComponent());
            unit.setComponent(new CampComponent(camp.getId()));
        }
        //Enemies 1
        EntityWrapper enemy1 = EntityTemplate.createFromTemplate(entityWorld, "jaime");
        Vector2f positionEnemy1 = new Vector2f(98, 91);
        Vector2f directionEnemy1 = new Vector2f(0, -1);
        enemy1.setComponent(new PositionComponent(positionEnemy1));
        enemy1.setComponent(new DirectionComponent(directionEnemy1));
        enemy1.setComponent(new AutoAggroComponent(15));
        enemy1.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
        enemy1.setComponent(new TeamComponent(0));
        EntityWrapper camp1 = entityWorld.getWrapped(entityWorld.createEntity());
        camp1.setComponent(new CampTransformComponent(positionEnemy1, directionEnemy1));
        camp1.setComponent(new CampMaximumAggroDistanceComponent(15));
        camp1.setComponent(new CampHealthResetComponent());
        enemy1.setComponent(new CampComponent(camp1.getId()));
        //Enemies 2
        for(int i=0;i<3;i++){
            EntityWrapper enemy = EntityTemplate.createFromTemplate(entityWorld, "beetle_golem");
            Vector2f positionEnemy = null;
            Vector2f directionEnemy = null;
            switch(i){
                case 0:
                    positionEnemy = new Vector2f(173, 102);
                    directionEnemy = new Vector2f(-1, -1);
                    break;
                
                case 1:
                    positionEnemy = new Vector2f(190, 95);
                    directionEnemy = new Vector2f(-1, 0);
                    break;
                
                case 2:
                    positionEnemy = new Vector2f(169, 82);
                    directionEnemy = new Vector2f(-1, 1);
                    break;
            }
            enemy.setComponent(new PositionComponent(positionEnemy));
            enemy.setComponent(new DirectionComponent(directionEnemy));
            enemy.setComponent(new AutoAggroComponent(15));
            enemy.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
            enemy.setComponent(new SetNewTargetSpellsOnCooldownComponent(new int[]{0}, new int[]{6}));
            enemy.setComponent(new TeamComponent(0));
            EntityWrapper camp = entityWorld.getWrapped(entityWorld.createEntity());
            camp.setComponent(new CampTransformComponent(positionEnemy, directionEnemy));
            camp.setComponent(new CampMaximumAggroDistanceComponent(15));
            camp.setComponent(new CampHealthResetComponent());
            enemy.setComponent(new CampComponent(camp.getId()));
        }
        //Enemies 3
        for(int i=0;i<2;i++){
            EntityWrapper enemy = EntityTemplate.createFromTemplate(entityWorld, "earth_elemental");
            Vector2f positionEnemy = null;
            Vector2f directionEnemy = null;
            switch(i){
                case 0:
                    positionEnemy = new Vector2f(150,136);
                    directionEnemy = new Vector2f(1, 0);
                    break;
                
                case 1:
                    positionEnemy = new Vector2f(115,145);
                    directionEnemy = new Vector2f(-1, -1);
                    break;
            }
            enemy.setComponent(new PositionComponent(positionEnemy));
            enemy.setComponent(new DirectionComponent(directionEnemy));
            enemy.setComponent(new AutoAggroComponent(17));
            enemy.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
            enemy.setComponent(new TeamComponent(0));
            EntityWrapper camp = entityWorld.getWrapped(entityWorld.createEntity());
            camp.setComponent(new CampTransformComponent(positionEnemy, directionEnemy));
            camp.setComponent(new CampMaximumAggroDistanceComponent(15));
            camp.setComponent(new CampHealthResetComponent());
            enemy.setComponent(new CampComponent(camp.getId()));
        }
        //Boss
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new ModelComponent("Models/dragon/skin.xml"));
        EntityWrapper idleAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        idleAnimation.setComponent(new NameComponent("fly"));
        idleAnimation.setComponent(new LoopDurationComponent(2.5f));
        boss.setComponent(new IdleAnimationComponent(idleAnimation.getId()));
        boss.setComponent(new AnimationComponent(idleAnimation.getId()));
        boss.setComponent(new PositionComponent(new Vector2f(255, 213)));
        boss.setComponent(new DirectionComponent(new Vector2f(-0.5f, -1)));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new HitboxComponent(new Circle(2.25f)));
        boss.setComponent(new IntersectionPushComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        boss.setComponent(new HitboxActiveComponent());
        boss.setComponent(new IsAliveComponent());
        boss.setComponent(new BaseMaximumHealthComponent(4000));
        boss.setComponent(new BaseHealthRegenerationComponent(40));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new TeamComponent(0));
        EntityWrapper gameObjective = entityWorld.getWrapped(entityWorld.createEntity());
        gameObjective.setComponent(new MissingEntitiesComponent(new int[]{boss.getId()}));
        gameObjective.setComponent(new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(gameObjective.getId()));
        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        playerDeathRules.setComponent(new RespawnPlayersComponent());
        playerDeathRules.setComponent(new RespawnTimerComponent(10, 0));
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void spawn(EntityWorld entityWorld, int playerEntity){
        Vector2f position = new Vector2f();
        Vector2f direction = new Vector2f();
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        switch(playerIndex){
            case 0:
                position = new Vector2f(165, 54);
                direction = new Vector2f(0, -1);
                break;
            
            case 1:
                position = new Vector2f(173, 46);
                direction = new Vector2f(-1, 0);
                break;
            
            case 2:
                position = new Vector2f(165, 37);
                direction = new Vector2f(0, 1);
                break;
            
            case 3:
                position = new Vector2f(157, 46);
                direction = new Vector2f(1, 0);
                break;
        }
        int unitEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
        entityWorld.setComponent(unitEntity, new PositionComponent(position));
        entityWorld.setComponent(unitEntity, new DirectionComponent(direction));
        entityWorld.setComponent(unitEntity, new TeamComponent(1));
    }
}
