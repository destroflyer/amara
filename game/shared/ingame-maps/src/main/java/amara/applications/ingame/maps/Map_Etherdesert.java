/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.buffs.*;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.components.effects.players.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.effects.units.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class Map_Etherdesert extends Map {

    public Map_Etherdesert() {
        
    }
    private int nexus;
    private int respawnTeamTrigger;
    private int winMapObjectiveTrigger;

    @Override
    public void load(EntityWorld entityWorld) {
        EntityWrapper audioBackgroundMusic = entityWorld.getWrapped(entityWorld.createEntity());
        audioBackgroundMusic.setComponent(new AudioComponent("Sounds/music/desert_village.ogg"));
        audioBackgroundMusic.setComponent(new AudioVolumeComponent(1.75f));
        audioBackgroundMusic.setComponent(new AudioLoopComponent());
        audioBackgroundMusic.setComponent(new AudioGlobalComponent());
        audioBackgroundMusic.setComponent(new StartPlayingAudioComponent());
        // Shop
        EntityWrapper shop = entityWorld.getWrapped(entityWorld.createEntity());
        shop.setComponent(new IsAlwaysVisibleComponent());
        shop.setComponent(new ModelComponent("Models/chest/skin.xml"));
        shop.setComponent(new PositionComponent(new Vector2f(100, 20)));
        shop.setComponent(new DirectionComponent(new Vector2f(0, 1)));
        shop.setComponent(new ShopItemsComponent(
            "items/etherdesert_income_small",
            "items/etherdesert_income_big",
            "items/etherdesert_tower_0",
            "items/etherdesert_tower_7",
            "items/etherdesert_tower_5",
            "items/etherdesert_tower_1",
            "items/etherdesert_tower_6",
            "items/etherdesert_tower_9",
            "items/etherdesert_tower_8",
            "items/etherdesert_tower_2",
            "items/etherdesert_tower_3",
            "items/etherdesert_tower_4"
        ));
        shop.setComponent(new ShopRangeComponent(20));
        shop.setComponent(new TeamComponent(0));
        // Nexus
        nexus = entityWorld.createEntity();
        entityWorld.setComponent(nexus, new NameComponent("Nexus"));
        entityWorld.setComponent(nexus, new IsStructureComponent());
        entityWorld.setComponent(nexus, new IsAlwaysVisibleComponent());
        entityWorld.setComponent(nexus, new ModelComponent("Models/column/skin_nexus.xml"));
        entityWorld.setComponent(nexus, new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
        entityWorld.setComponent(nexus, new PositionComponent(new Vector2f(100, 39)));
        entityWorld.setComponent(nexus, new DirectionComponent(new Vector2f(0, -1)));
        entityWorld.setComponent(nexus, new IsAliveComponent());
        int nexusBaseAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatMaximumHealthComponent(2500));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatArmorComponent(300));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatMagicResistanceComponent(300));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatHealthRegenerationComponent(4));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatAttackDamageComponent(100));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatAttackSpeedComponent(2));
        entityWorld.setComponent(nexus, new BaseAttributesComponent(nexusBaseAttributesEntity));
        EntityWrapper nexusAutoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack");
        nexusAutoAttack.removeComponent(CastTurnToTargetComponent.class);
        nexusAutoAttack.setComponent(new RangeComponent(1000));
        entityWorld.setComponent(nexus, new AutoAttackComponent(nexusAutoAttack.getId()));
        entityWorld.setComponent(nexus, new AutoAggroComponent(24));
        entityWorld.setComponent(nexus, new RequestUpdateAttributesComponent());
        entityWorld.setComponent(nexus, new IsTargetableComponent());
        entityWorld.setComponent(nexus, new IsVulnerableComponent());
        entityWorld.setComponent(nexus, new TeamComponent(0));
        // Campfires
        Vector2f[] campfirePositions = new Vector2f[]{
                new Vector2f(175.62367f, 145.24966f),
                new Vector2f(113.570404f, 140.61807f),
                new Vector2f(154.77113f, 106.40196f),
                new Vector2f(120.59867f, 98.81795f),
                new Vector2f(110.84865f, 68.97857f),
                new Vector2f(26.024944f, 138.34921f),
                new Vector2f(84.90381f, 138.57642f),
                new Vector2f(45.01973f, 104.95874f),
                new Vector2f(88.50022f, 69.143105f),
                new Vector2f(81.168564f, 97.66634f)
        };
        Vector2f[] campfireDirections = new Vector2f[]{
                new Vector2f(-0.6427891f, -0.7660469f),
                new Vector2f(0.6427901f, -0.76604664f),
                new Vector2f(0.42261922f, 0.90631133f),
                new Vector2f(0.64279f, 0.76604795f),
                new Vector2f(0.5735785f, 0.81915575f),
                new Vector2f(-0.5735804f, 0.8191566f),
                new Vector2f(0.6427914f, 0.7660497f),
                new Vector2f(-0.3420229f, 0.93969864f),
                new Vector2f(-0.50000393f, 0.8660311f),
                new Vector2f(-0.6427925f, 0.7660494f)
        };
        for (int i = 0; i < campfirePositions.length; i++) {
            int campfireEntity = entityWorld.createEntity();
            entityWorld.setComponent(campfireEntity, new ModelComponent("Models/3dsa_fantasy_forest_tree_log_1/skin_burning.xml"));
            entityWorld.setComponent(campfireEntity, new PositionComponent(campfirePositions[i]));
            entityWorld.setComponent(campfireEntity, new DirectionComponent(campfireDirections[i]));
            entityWorld.setComponent(campfireEntity, new SightRangeComponent(30));
            entityWorld.setComponent(campfireEntity, new TeamComponent(0));
        }
        // Respawn towers trigger
        respawnTeamTrigger = entityWorld.createEntity();
        entityWorld.setComponent(respawnTeamTrigger, new InstantTriggerComponent());
        entityWorld.setComponent(respawnTeamTrigger, new TeamTargetComponent(0));
        int respawnTeamEffect = entityWorld.createEntity();
        entityWorld.setComponent(respawnTeamEffect, new RespawnComponent());
        entityWorld.setComponent(respawnTeamTrigger, new TriggeredEffectComponent(respawnTeamEffect));
        entityWorld.setComponent(respawnTeamTrigger, new TriggerOnceComponent());
        // MapObjective
        int mapObjective = entityWorld.createEntity();
        entityWorld.setComponent(mapObjective, new MissingEntitiesComponent(nexus));
        entityWorld.setComponent(mapObjective, new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(mapObjective));
        // MapObjective - Win Trigger
        winMapObjectiveTrigger = entityWorld.createEntity();
        entityWorld.setComponent(winMapObjectiveTrigger, new InstantTriggerComponent());
        entityWorld.setComponent(winMapObjectiveTrigger, new CustomTargetComponent(mapObjective));
        int winMapObjectiveEffect = entityWorld.createEntity();
        entityWorld.setComponent(winMapObjectiveEffect, new FinishObjectiveComponent());
        entityWorld.setComponent(winMapObjectiveTrigger, new TriggeredEffectComponent(winMapObjectiveEffect));
        entityWorld.setComponent(winMapObjectiveTrigger, new TriggerOnceComponent(true));

        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void initializePlayer(EntityWorld entityWorld, int playerEntity) {
        super.initializePlayer(entityWorld, playerEntity);
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.removeComponent(characterEntity, PassivesComponent.class);
        entityWorld.removeComponent(characterEntity, AutoAttackComponent.class);
        entityWorld.setComponent(characterEntity, new GoldComponent(200));
        entityWorld.setComponent(characterEntity, new IsBindedComponent(Float.MAX_VALUE));
        // Display values on shop usage
        int displayValuesOnBuyTrigger = entityWorld.createEntity();
        entityWorld.setComponent(displayValuesOnBuyTrigger, new ShopUsageTriggerComponent());
        entityWorld.setComponent(displayValuesOnBuyTrigger, new CustomTargetComponent(playerEntity));
        int displayValuesEffect = entityWorld.createEntity();
        entityWorld.setComponent(displayValuesEffect, new DisplayPlayerAnnouncementComponent("\"Your gold expenses: \" + source.shopGoldExpenses_total", -1));
        entityWorld.setComponent(displayValuesOnBuyTrigger, new TriggeredEffectComponent(displayValuesEffect));
        entityWorld.setComponent(displayValuesOnBuyTrigger, new TriggerSourceComponent(characterEntity));
        // Disable income trigger
        int disableIncomeTrigger = entityWorld.createEntity();
        entityWorld.setComponent(disableIncomeTrigger, new InstantTriggerComponent());
        entityWorld.setComponent(disableIncomeTrigger, new CustomTargetComponent(characterEntity));
        int disableIncomeEffect = entityWorld.createEntity();
        int disableIncomeBuff = entityWorld.createEntity();
        int disableIncomeBuffContinuousAttributes = entityWorld.createEntity();
        entityWorld.setComponent(disableIncomeBuffContinuousAttributes, new DisableGoldPerSecondComponent());
        entityWorld.setComponent(disableIncomeBuff, new ContinuousAttributesComponent(disableIncomeBuffContinuousAttributes));
        entityWorld.setComponent(disableIncomeEffect, new AddBuffComponent(disableIncomeBuff, -1));
        entityWorld.setComponent(disableIncomeTrigger, new TriggeredEffectComponent(disableIncomeEffect));
        entityWorld.setComponent(disableIncomeTrigger, new TriggerOnceComponent());
        entityWorld.setComponent(disableIncomeTrigger, new TriggerDelayComponent(1));
        ApplyAddBuffsSystem.addBuff(entityWorld, characterEntity, disableIncomeBuff, -1);
        // Enable income trigger
        int enableIncomeTrigger = entityWorld.createEntity();
        entityWorld.setComponent(enableIncomeTrigger, new InstantTriggerComponent());
        entityWorld.setComponent(enableIncomeTrigger, new CustomTargetComponent(characterEntity));
        int enableIncomeEffect = entityWorld.createEntity();
        entityWorld.setComponent(enableIncomeEffect, new RemoveBuffComponent(disableIncomeBuff));
        entityWorld.setComponent(enableIncomeTrigger, new TriggeredEffectComponent(enableIncomeEffect));
        entityWorld.setComponent(enableIncomeTrigger, new TriggerOnceComponent());
        // Waves
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int spawnCasterEntity = entityWorld.createEntity();
        Vector2f[] spawnPositions = new Vector2f[]{
                new Vector2f(167, 180),
                new Vector2f(122, 180),
                new Vector2f(87, 180),
                new Vector2f(33, 180)
        };
        entityWorld.setComponent(spawnCasterEntity, new PositionComponent(spawnPositions[playerIndex]));
        entityWorld.setComponent(spawnCasterEntity, new TeamComponent(1));
        int spawnSourceEntity = entityWorld.createEntity();
        entityWorld.setComponent(spawnSourceEntity, new EffectSourceComponent(spawnCasterEntity));
        int waves = 18;
        int[] creepsPerWave = new int[]{ 6, 6, 4, 6, 6, 7, 28, 1, 6, 6, 2, 1, 6, 6, 3, 6, 3, 1 };
        int[][] spawnTriggers = new int[waves][];
        for (int i = 0; i < waves; i++) {
            int[] waveSpawnTriggers = new int[creepsPerWave[i]];
            for (int r = 0; r < waveSpawnTriggers.length; r++) {
                EntityWrapper spawnTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                spawnTrigger.setComponent(new InstantTriggerComponent());
                spawnTrigger.setComponent(new CustomTargetComponent(nexus));
                EntityWrapper spawnEffect = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation.setComponent(new SpawnTemplateComponent("../maps/etherdesert/templates/wave_" + i));
                spawnInformation.setComponent(new SpawnMoveToTargetComponent());
                spawnInformation.setComponent(new SpawnAttackMoveComponent());
                spawnEffect.setComponent(new SpawnComponent(spawnInformation.getId()));
                spawnTrigger.setComponent(new TriggeredEffectComponent(spawnEffect.getId()));
                float spawnDelay = ((i == 6) ? 0.5f : 1.25f);
                spawnTrigger.setComponent(new TriggerDelayComponent(13 + (spawnDelay * r)));
                spawnTrigger.setComponent(new TriggerOnceComponent(true));
                waveSpawnTriggers[r] = spawnTrigger.getId();
            }
            spawnTriggers[i] = waveSpawnTriggers;
        }
        int[] activateSpawnsTriggers = new int[waves];
        int[] activateNextWaveTriggers = new int[waves];
        for (int i = 0; i < waves; i++) {
            EntityWrapper activateSpawnsTriggger = entityWorld.getWrapped(entityWorld.createEntity());
            activateSpawnsTriggger.setComponent(new InstantTriggerComponent());
            activateSpawnsTriggger.setComponent(new CustomTargetComponent(spawnSourceEntity));
            EntityWrapper activateSpawnEffect = entityWorld.getWrapped(entityWorld.createEntity());
            activateSpawnEffect.setComponent(new AddEffectTriggersComponent(spawnTriggers[i]));
            activateSpawnsTriggger.setComponent(new TriggeredEffectComponent(activateSpawnEffect.getId()));
            activateSpawnsTriggger.setComponent(new TriggerOnceComponent(true));
            activateSpawnsTriggers[i] = activateSpawnsTriggger.getId();

            EntityWrapper activateNextWaveTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            activateNextWaveTrigger.setComponent(new TeamDeathTriggerComponent());
            activateNextWaveTrigger.setComponent(new CustomTargetComponent(spawnCasterEntity));
            activateNextWaveTrigger.setComponent(new TriggerOnceComponent(true));
            activateNextWaveTriggers[i] = activateNextWaveTrigger.getId();

            if (i == 0) {
                activateSpawnsTriggger.setComponent(new TriggerSourceComponent(entity));
                activateNextWaveTrigger.setComponent(new TriggerSourceComponent(spawnCasterEntity));
            }
        }
        for (int i = 0; i < waves; i++) {
            int activateNextWaveTrigger = activateNextWaveTriggers[i];
            EntityWrapper activateNextWaveEffect = entityWorld.getWrapped(entityWorld.createEntity());
            if (i < (waves - 1)){
                activateNextWaveEffect.setComponent(new AddEffectTriggersComponent(enableIncomeTrigger, disableIncomeTrigger, respawnTeamTrigger, activateSpawnsTriggers[i + 1], activateNextWaveTriggers[i + 1]));
            } else {
                activateNextWaveEffect.setComponent(new AddEffectTriggersComponent(winMapObjectiveTrigger));
            }
            entityWorld.setComponent(activateNextWaveTrigger, new TriggeredEffectComponent(activateNextWaveEffect.getId()));
        }
    }

    @Override
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity) {
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        float intervalX = 6;
        float x = (100 + ((1.5f - playerIndex) * intervalX));
        entityWorld.setComponent(characterEntity, new PositionComponent(new Vector2f(x, 26)));
        entityWorld.setComponent(characterEntity, new DirectionComponent(new Vector2f(0, 1)));
        entityWorld.setComponent(characterEntity, new TeamComponent(0));
        entityWorld.removeComponent(characterEntity, HitboxActiveComponent.class);
        entityWorld.removeComponent(characterEntity, IsTargetableComponent.class);
        entityWorld.removeComponent(characterEntity, IsVulnerableComponent.class);
    }
}
