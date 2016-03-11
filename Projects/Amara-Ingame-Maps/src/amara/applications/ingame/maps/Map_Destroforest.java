/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.effects.game.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.applications.ingame.shared.games.Game;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.physics.shapes.*;

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
        audioBackgroundMusic.setComponent(new StartPlayingAudioComponent());
        
        EntityWrapper instantEffectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
        instantEffectTrigger.setComponent(new InstantTriggerComponent());
        instantEffectTrigger.setComponent(new SourceTargetComponent());
        EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
        effect1.setComponent(new PlayCinematicComponent("amara.applications.ingame.maps.Map_Destroforest_CinematicIntro"));
        instantEffectTrigger.setComponent(new TriggeredEffectComponent(effect1.getId()));
        instantEffectTrigger.setComponent(new TriggerSourceComponent(Game.ENTITY));
        instantEffectTrigger.setComponent(new TriggerOnceComponent());
        
        EntityWrapper campWizards = entityWorld.getWrapped(entityWorld.createEntity());
        campWizards.setComponent(new CampUnionAggroComponent());
        campWizards.setComponent(new CampMaximumAggroDistanceComponent(10));
        campWizards.setComponent(new CampHealthResetComponent());
        for(int i=0;i<3;i++){
            EntityWrapper unit = entityWorld.getWrapped(entityWorld.createEntity());
            unit.setComponent(new NameComponent("Wizard Creep"));
            unit.setComponent(new ModelComponent("Models/wizard/skin_default.xml"));
            EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
            autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
            unit.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
            unit.setComponent(new HitboxComponent(new Circle(1)));
            unit.setComponent(new IntersectionPushComponent());
            unit.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
            unit.setComponent(new HitboxActiveComponent());
            unit.setComponent(new IsAliveComponent());
            int baseAttributesEntity = entityWorld.createEntity();
            entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(1500));
            entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent((i == 2)?80:30));
            entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.5f));
            entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(3));
            unit.setComponent(new BaseAttributesComponent(baseAttributesEntity));
            unit.setComponent(new RequestUpdateAttributesComponent());
            unit.setComponent(new IsTargetableComponent());
            unit.setComponent(new IsVulnerableComponent());
            EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack");
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
            unit.setComponent(new CampComponent(campWizards.getId(), position, direction));
        }
        //Enemies 1
        EntityWrapper campEnemies1 = entityWorld.getWrapped(entityWorld.createEntity());
        campEnemies1.setComponent(new CampMaximumAggroDistanceComponent(15));
        campEnemies1.setComponent(new CampHealthResetComponent());
        EntityWrapper enemy1 = EntityTemplate.createFromTemplate(entityWorld, "units/jaime");
        EntityWrapper enemy1Spell = EntityTemplate.createFromTemplate(entityWorld, "spells/sonic_wave,0");
        enemy1.setComponent(new SpellsComponent(enemy1Spell.getId()));
        Vector2f positionEnemy1 = new Vector2f(98, 91);
        Vector2f directionEnemy1 = new Vector2f(0, -1);
        enemy1.setComponent(new PositionComponent(positionEnemy1));
        enemy1.setComponent(new DirectionComponent(directionEnemy1));
        enemy1.setComponent(new AutoAggroComponent(15));
        enemy1.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
        enemy1.setComponent(new TeamComponent(0));
        enemy1.setComponent(new CampComponent(campEnemies1.getId(), positionEnemy1, directionEnemy1));
        //Enemies 2
        EntityWrapper campEnemies2 = entityWorld.getWrapped(entityWorld.createEntity());
        campEnemies2.setComponent(new CampMaximumAggroDistanceComponent(15));
        campEnemies2.setComponent(new CampHealthResetComponent());
        for(int i=0;i<3;i++){
            EntityWrapper enemy = EntityTemplate.createFromTemplate(entityWorld, "units/beetle_golem");
            Vector2f position = null;
            Vector2f direction = null;
            switch(i){
                case 0:
                    position = new Vector2f(173, 102);
                    direction = new Vector2f(-1, -1);
                    break;
                
                case 1:
                    position = new Vector2f(190, 95);
                    direction = new Vector2f(-1, 0);
                    break;
                
                case 2:
                    position = new Vector2f(169, 82);
                    direction = new Vector2f(-1, 1);
                    break;
            }
            enemy.setComponent(new PositionComponent(position));
            enemy.setComponent(new DirectionComponent(direction));
            enemy.setComponent(new AutoAggroComponent(15));
            enemy.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
            enemy.setComponent(new SetNewTargetSpellsOnCooldownComponent(new int[]{0}, new float[]{6}));
            enemy.setComponent(new TeamComponent(0));
            enemy.setComponent(new CampComponent(campEnemies2.getId(), position, direction));
        }
        //Enemies 3
        EntityWrapper campEnemies3 = entityWorld.getWrapped(entityWorld.createEntity());
        campEnemies3.setComponent(new CampMaximumAggroDistanceComponent(15));
        campEnemies3.setComponent(new CampHealthResetComponent());
        for(int i=0;i<2;i++){
            EntityWrapper enemy = EntityTemplate.createFromTemplate(entityWorld, "units/earth_elemental");
            Vector2f position = null;
            Vector2f direction = null;
            switch(i){
                case 0:
                    position = new Vector2f(150,136);
                    direction = new Vector2f(1, 0);
                    break;
                
                case 1:
                    position = new Vector2f(115,145);
                    direction = new Vector2f(-1, -1);
                    break;
            }
            enemy.setComponent(new PositionComponent(position));
            enemy.setComponent(new DirectionComponent(direction));
            enemy.setComponent(new AutoAggroComponent(17));
            enemy.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
            enemy.setComponent(new TeamComponent(0));
            enemy.setComponent(new CampComponent(campEnemies3.getId(), position, direction));
        }
        //Boss
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Dragon"));
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
        int baseAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(4000));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatHealthRegenerationComponent(40));
        boss.setComponent(new BaseAttributesComponent(baseAttributesEntity));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
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
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity){
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
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.setComponent(characterEntity, new PositionComponent(position));
        entityWorld.setComponent(characterEntity, new DirectionComponent(direction));
        entityWorld.setComponent(characterEntity, new TeamComponent(1));
    }
}
