/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.server.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import amara.engine.appstates.*;
import amara.engine.client.appstates.MapAppState;
import amara.engine.server.ServerApplication;
import amara.engine.server.network.NetworkServer;
import amara.engine.server.network.backends.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.specials.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.aggro.*;
import amara.game.entitysystem.systems.attributes.*;
import amara.game.entitysystem.systems.buffs.*;
import amara.game.entitysystem.systems.commands.*;
import amara.game.entitysystem.systems.effects.*;
import amara.game.entitysystem.systems.effects.crowdcontrol.*;
import amara.game.entitysystem.systems.effects.damage.*;
import amara.game.entitysystem.systems.effects.triggers.*;
import amara.game.entitysystem.systems.general.*;
import amara.game.entitysystem.systems.movement.*;
import amara.game.entitysystem.systems.network.*;
import amara.game.entitysystem.systems.physics.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.spells.*;
import amara.game.entitysystem.systems.spells.casting.*;

/**
 *
 * @author Carl
 */
public class ServerEntitySystemAppState extends EntitySystemAppState<ServerApplication>{

    public ServerEntitySystemAppState(){
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new AssignPlayerEntityBackend(entityWorld));
        networkServer.addMessageBackend(new UpdateNewClientBackend(entityWorld));
        addEntitySystem(new SendEntityChangesSystem(networkServer));
        addEntitySystem(new UpdateAttributesSystem());
        addEntitySystem(new CountdownLifetimeSystem());
        addEntitySystem(new CountdownBuffsSystem());
        addEntitySystem(new CountdownCooldownSystem());
        addEntitySystem(new CountdownBindingSystem());
        addEntitySystem(new CountdownSilenceSystem());
        addEntitySystem(new CountdownStunSystem());
        addEntitySystem(new ExecutePlayerCommandsSystem(getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue()));
        addEntitySystem(new SetCooldownOnCastingSystem());
        addEntitySystem(new AttackAggroedTargetsSystem());
        addEntitySystem(new PerformAutoAttacksSystem());
        addEntitySystem(new CastSelfcastSpellSystem());
        addEntitySystem(new CastSingleTargetSpellSystem());
        addEntitySystem(new CastLinearSkillshotSpellSystem());
        addEntitySystem(new CastPositionalSkillshotSpellSystem());
        addEntitySystem(new RepeatingBuffEffectsSystem());
        addEntitySystem(new CalculateEffectImpactSystem());
        addEntitySystem(new ApplyPhysicalDamageSystem());
        addEntitySystem(new ApplyMagicDamageSystem());
        addEntitySystem(new ApplyBindingSystem());
        addEntitySystem(new ApplySilenceSystem());
        addEntitySystem(new ApplyStunSystem());
        addEntitySystem(new RemoveAppliedEffectsSystem());
        addEntitySystem(new DeathSystem());
        IntersectionSystem intersectionSystem = new IntersectionSystem();
        addEntitySystem(new IntersectionAntiGhostSystem(intersectionSystem));
        addEntitySystem(new MovementSystem());
        addEntitySystem(new TargetedMovementSystem());
        addEntitySystem(new TransformUpdateSystem());
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionSystem));
        addEntitySystem(new TriggerTargetReachedEffectSystem());
        addEntitySystem(intersectionSystem);
        addEntitySystem(new AggroSystem());
        addEntitySystem(new MapIntersectionSystem(100, 100, MapAppState.TEST_MAP_OBSTACLES));
        loadTestEntities();
    }
    
    private void loadTestEntities(){
        //Players
        EntityWrapper player1 = entityWorld.getWrapped(entityWorld.createEntity());
        EntityWrapper player2 = entityWorld.getWrapped(entityWorld.createEntity());
        //Entity #1
        EntityWrapper entity1 = entityWorld.getWrapped(entityWorld.createEntity());
        entity1.setComponent(new ModelComponent("Models/minion/skin.xml"));
        entity1.setComponent(new AnimationComponent("dance", 2.66f, true));
        entity1.setComponent(new ScaleComponent(0.75f));
        entity1.setComponent(new PositionComponent(new Vector2f(22, 16)));
        entity1.setComponent(new DirectionComponent(new Vector2f(0, -1)));
        entity1.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
        entity1.setComponent(new AntiGhostComponent());
        entity1.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        entity1.setComponent(new TeamComponent(1));
        entity1.setComponent(new IsTargetableComponent());
        player2.setComponent(new SelectedUnitComponent(entity1.getId()));
        //Entity #2
        EntityWrapper entity2 = entityWorld.getWrapped(entityWorld.createEntity());
        entity2.setComponent(new ModelComponent("Models/wizard/skin.xml"));
        entity2.setComponent(new ScaleComponent(0.5f));
        entity2.setComponent(new PositionComponent(new Vector2f(7, 7)));
        entity2.setComponent(new DirectionComponent(new Vector2f(1, 1)));
        entity2.setComponent(new MovementSpeedComponent(new Vector2f(2, 2)));
        entity2.setComponent(new HitboxComponent(new Circle(1)));
        entity2.setComponent(new AntiGhostComponent());
        entity2.setComponent(new HealthComponent(400));
        entity2.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        entity2.setComponent(new TeamComponent(2));
        entity2.setComponent(new IsTargetableComponent());
        player1.setComponent(new SelectedUnitComponent(entity2.getId()));
        //Entity #2 - Attributes
        entity2.setComponent(new BaseMaximumHealthComponent(500));
        entity2.setComponent(new BaseAttackDamageComponent(60));
        entity2.setComponent(new BaseAbilityPowerComponent(0));
        entity2.setComponent(new BaseAttackSpeedComponent(0.6f));
        EntityWrapper doransBlade = entityWorld.getWrapped(entityWorld.createEntity());
        doransBlade.setComponent(new ItemVisualisationComponent("dorans_blade"));
        doransBlade.setComponent(new BonusFlatMaximumHealthComponent(80));
        doransBlade.setComponent(new BonusFlatAttackDamageComponent(10));
        EntityWrapper doransRing = entityWorld.getWrapped(entityWorld.createEntity());
        doransRing.setComponent(new ItemVisualisationComponent("dorans_ring"));
        doransRing.setComponent(new BonusFlatMaximumHealthComponent(80));
        doransRing.setComponent(new BonusFlatAbilityPowerComponent(15));
        EntityWrapper needlesslyLargeRod = entityWorld.getWrapped(entityWorld.createEntity());
        needlesslyLargeRod.setComponent(new ItemVisualisationComponent("needlessly_large_rod"));
        needlesslyLargeRod.setComponent(new BonusFlatAbilityPowerComponent(80));
        EntityWrapper dagger = entityWorld.getWrapped(entityWorld.createEntity());
        dagger.setComponent(new ItemVisualisationComponent("dagger"));
        dagger.setComponent(new BonusPercentageAttackSpeedComponent(0.12f));
        entity2.setComponent(new InventoryComponent(new int[]{doransBlade.getId(), doransRing.getId(), needlesslyLargeRod.getId(), dagger.getId(), dagger.getId()}));
        entity1.setComponent(new InventoryComponent(new int[]{doransBlade.getId(), doransBlade.getId(), doransBlade.getId()}));
        entity2.setComponent(new RequestUpdateAttributesComponent());
        //Entity #1 - Spells
        //Spell #1
        EntityWrapper nullSphere = entityWorld.getWrapped(entityWorld.createEntity());
        nullSphere.setComponent(new NameComponent("Null Sphere"));
        nullSphere.setComponent(new DescriptionComponent("Silences an enemy."));
        EntityWrapper spawnInformation1 = entityWorld.getWrapped(entityWorld.createEntity());
        spawnInformation1.setComponent(new SpawnTemplateComponent("null_sphere"));
        spawnInformation1.setComponent(new SpawnMovementSpeedComponent(25));
        nullSphere.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation1.getId()}));
        nullSphere.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        //Spell #2
        EntityWrapper riftWalk = entityWorld.getWrapped(entityWorld.createEntity());
        riftWalk.setComponent(new NameComponent("Riftwalk"));
        riftWalk.setComponent(new DescriptionComponent("Teleports to the target location."));
        riftWalk.setComponent(new TeleportCasterToTargetPositionComponent());
        riftWalk.setComponent(new CastTypeComponent(CastTypeComponent.CastType.POSITIONAL_SKILLSHOT));
        //Spell #3
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
        entity1.setComponent(new SpellsComponent(new int[]{nullSphere.getId(), riftWalk.getId(), ignite.getId()}));
        //Entity #2 - Spells
        //Spell #1
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
        entity2.setComponent(new SpellsComponent(new int[]{sear.getId(), pillarOfFlame.getId(), battleCry.getId()}));
        //Autoattack
        EntityWrapper autoAttack1 = entityWorld.getWrapped(entityWorld.createEntity());
        EntityWrapper spawnInformation4 = entityWorld.getWrapped(entityWorld.createEntity());
        spawnInformation4.setComponent(new SpawnTemplateComponent("autoattack_projectile", "cloud"));
        spawnInformation4.setComponent(new SpawnMovementSpeedComponent(15));
        autoAttack1.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation4.getId()}));
        autoAttack1.setComponent(new CooldownComponent(1));
        autoAttack1.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        entity2.setComponent(new AutoAttackComponent(autoAttack1.getId()));
        entity2.setComponent(new AutoAggroComponent(9));
        //Field of test units
        for(int x=0;x<5;x++){
            for(int y=0;y<4;y++){
                EntityWrapper entity = entityWorld.getWrapped(entityWorld.createEntity());
                entity.setComponent(new ModelComponent("Models/wizard/skin.xml"));
                entity.setComponent(new ScaleComponent(0.5f));
                entity.setComponent(new PositionComponent(new Vector2f(12 + (x * 2), 22 + (y * 2))));
                entity.setComponent(new DirectionComponent(new Vector2f(1, 1)));
                entity.setComponent(new HitboxComponent(new Circle(1)));
                entity.setComponent(new AntiGhostComponent());
                entity.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                entity.setComponent(new TeamComponent(1));
                entity.setComponent(new IsTargetableComponent());
                entity.setComponent(new BaseMaximumHealthComponent(500));
                entity.setComponent(new RequestUpdateAttributesComponent());
            }
        }
    }
}
