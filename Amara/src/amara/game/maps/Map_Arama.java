/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.audio.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.spawns.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.shop.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class Map_Arama extends Map{

    public Map_Arama(){
        
    }
    private final float laneCenterY = 173.5f;

    @Override
    public void load(EntityWorld entityWorld){
        EntityWrapper audioBackgroundMusic = entityWorld.getWrapped(entityWorld.createEntity());
        audioBackgroundMusic.setComponent(new AudioComponent("Sounds/music/world_of_ice.ogg"));
        audioBackgroundMusic.setComponent(new AudioVolumeComponent(1.25f));
        audioBackgroundMusic.setComponent(new AudioLoopComponent());
        audioBackgroundMusic.setComponent(new IsAudioPlayingComponent());
        //Nexus
        EntityWrapper[] nexi = new EntityWrapper[2];
        float[] nexiX = new float[]{252, 98};
        for(int i=0;i<2;i++){
            EntityWrapper nexus = entityWorld.getWrapped(entityWorld.createEntity());
            nexus.setComponent(new NameComponent("Nexus"));
            nexus.setComponent(new ModelComponent("Models/column/skin_nexus.xml"));
            nexus.setComponent(new PositionComponent(new Vector2f(nexiX[i], laneCenterY)));
            nexus.setComponent(new DirectionComponent(new Vector2f(0, -1)));
            nexus.setComponent(new IsAliveComponent());
            nexus.setComponent(new BaseMaximumHealthComponent(1000));
            nexus.setComponent(new BaseHealthRegenerationComponent(2));
            nexus.setComponent(new RequestUpdateAttributesComponent());
            nexus.setComponent(new IsTargetableComponent());
            nexus.setComponent(new IsVulnerableComponent());
            nexus.setComponent(new TeamComponent(i + 1));
            nexi[i] = nexus;
        }
        for(int i=0;i<2;i++){
            EntityWrapper shop = entityWorld.getWrapped(entityWorld.createEntity());
            shop.setComponent(new ModelComponent("Models/chest/skin.xml"));
            shop.setComponent(new PositionComponent(new Vector2f(((i == 0)?275:75), laneCenterY)));
            shop.setComponent(new DirectionComponent(new Vector2f(((i == 0)?-1:1), 0)));
            shop.setComponent(new ShopRangeComponent(10));
            shop.setComponent(new TeamComponent(i + 1));
            //Tower
            EntityWrapper tower1 = EntityTemplate.createFromTemplate(entityWorld, "tower");
            tower1.setComponent(new PositionComponent(new Vector2f(((i == 0)?236:113), laneCenterY)));
            tower1.setComponent(new DirectionComponent(new Vector2f(((i == 0)?-1:1), 0)));
            tower1.setComponent(new TeamComponent(i + 1));
            EntityWrapper tower2 = EntityTemplate.createFromTemplate(entityWorld, "tower");
            tower2.setComponent(new PositionComponent(new Vector2f(((i == 0)?214:136), laneCenterY)));
            tower2.setComponent(new DirectionComponent(new Vector2f(((i == 0)?-1:1), 0)));
            tower2.setComponent(new TeamComponent(i + 1));
            //Waves
            int spawnCasterEntity = entityWorld.createEntity();
            entityWorld.setComponent(spawnCasterEntity, new PositionComponent(new Vector2f(nexiX[i] + (((i == 0)?-1:1) * 5), laneCenterY)));
            entityWorld.setComponent(spawnCasterEntity, new TeamComponent(i + 1));
            int spawnSourceEntity = entityWorld.createEntity();
            entityWorld.setComponent(spawnSourceEntity, new EffectCastSourceComponent(spawnCasterEntity));
            for(int r=0;r<3;r++){
                EntityWrapper spawnTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                spawnTrigger.setComponent(new RepeatingTriggerComponent(20));
                spawnTrigger.setComponent(new TimeSinceLastRepeatTriggerComponent(17));
                spawnTrigger.setComponent(new CustomTargetComponent(nexi[(i == 0)?1:0].getId()));
                EntityWrapper spawnEffect = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
                String unitTemplate = ((r < 3)?"etherdesert_creep_melee":"etherdesert_creep_range");
                spawnInformation.setComponent(new SpawnTemplateComponent(unitTemplate + "," + spawnTrigger.getId() + "," + i));
                spawnInformation.setComponent(new SpawnMoveToTargetComponent());
                spawnInformation.setComponent(new SpawnAttackMoveComponent());
                spawnEffect.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
                spawnTrigger.setComponent(new TriggeredEffectComponent(spawnEffect.getId()));
                spawnTrigger.setComponent(new TriggerSourceComponent(spawnSourceEntity));
                spawnTrigger.setComponent(new TriggerDelayComponent(1.25f * r));
            }
        }
        //Boss
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Baron Nashor"));
        boss.setComponent(new TitleComponent("Baron Nashor"));
        boss.setComponent(new ModelComponent("Models/cow/skin_baron.xml"));
        EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        walkAnimation.setComponent(new NameComponent("walk"));
        boss.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
        EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
        boss.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
        boss.setComponent(new ScaleComponent(2.25f));
        boss.setComponent(new PositionComponent(new Vector2f(175, 226)));
        boss.setComponent(new DirectionComponent(new Vector2f(0, -1)));
        boss.setComponent(new HitboxComponent(new Circle(2)));
        boss.setComponent(new IntersectionPushComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        boss.setComponent(new HitboxActiveComponent());
        boss.setComponent(new IsAliveComponent());
        boss.setComponent(new BaseMaximumHealthComponent(4000));
        boss.setComponent(new BaseHealthRegenerationComponent(40));
        boss.setComponent(new BaseAttackDamageComponent(150));
        boss.setComponent(new BaseAttackSpeedComponent(0.6f));
        boss.setComponent(new BaseWalkSpeedComponent(2.5f));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "default_autoattack");
        boss.setComponent(new AutoAttackComponent(autoAttack.getId()));
        EntityWrapper bodyslam = EntityTemplate.createFromTemplate(entityWorld, "bodyslam");
        boss.setComponent(new SpellsComponent(new int[]{bodyslam.getId()}));
        boss.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
        boss.setComponent(new TeamComponent(0));
        //GameObjective
        EntityWrapper gameObjective = entityWorld.getWrapped(entityWorld.createEntity());
        EntityWrapper nexusObjective1 = entityWorld.getWrapped(entityWorld.createEntity());
        nexusObjective1.setComponent(new MissingEntitiesComponent(nexi[0].getId()));
        nexusObjective1.setComponent(new OpenObjectiveComponent());
        EntityWrapper nexusObjective2 = entityWorld.getWrapped(entityWorld.createEntity());
        nexusObjective2.setComponent(new MissingEntitiesComponent(nexi[1].getId()));
        nexusObjective2.setComponent(new OpenObjectiveComponent());
        gameObjective.setComponent(new OrObjectivesComponent(nexusObjective1.getId(), nexusObjective2.getId()));
        gameObjective.setComponent(new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(gameObjective.getId()));
        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        playerDeathRules.setComponent(new RespawnPlayersComponent());
        playerDeathRules.setComponent(new RespawnTimerComponent(3, 0));
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void spawn(EntityWorld entityWorld, int playerEntity){
        Vector2f position = new Vector2f();
        Vector2f direction = new Vector2f();
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        switch(playerIndex){
            case 0:
                position = new Vector2f(271, laneCenterY);
                direction = new Vector2f(-1, 0);
                break;
            
            case 1:
                position = new Vector2f(79, laneCenterY);
                direction = new Vector2f(1, 0);
                break;
        }
        int unitEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
        entityWorld.setComponent(unitEntity, new PositionComponent(position));
        entityWorld.setComponent(unitEntity, new DirectionComponent(direction));
        entityWorld.setComponent(unitEntity, new TeamComponent(playerIndex + 1));
    }
}
