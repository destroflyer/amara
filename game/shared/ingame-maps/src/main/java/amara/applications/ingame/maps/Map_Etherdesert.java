/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.effects.general.*;
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
        String[] shopItemTemplateNames = new String[5];
        for (int i = 0; i < shopItemTemplateNames.length; i++) {
            shopItemTemplateNames[i] = "items/etherdesert_tower_" + i;
        }
        shop.setComponent(new ShopItemsComponent(shopItemTemplateNames));
        shop.setComponent(new ShopRangeComponent(20));
        shop.setComponent(new TeamComponent(0));
        // Nexus
        EntityWrapper nexus = entityWorld.getWrapped(entityWorld.createEntity());
        nexus.setComponent(new NameComponent("Nexus"));
        nexus.setComponent(new IsStructureComponent());
        nexus.setComponent(new IsAlwaysVisibleComponent());
        nexus.setComponent(new ModelComponent("Models/column/skin_nexus.xml"));
        nexus.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
        nexus.setComponent(new PositionComponent(new Vector2f(100, 39)));
        nexus.setComponent(new DirectionComponent(new Vector2f(0, -1)));
        nexus.setComponent(new IsAliveComponent());
        int nexusBaseAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatMaximumHealthComponent(2500));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatArmorComponent(500));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatMagicResistanceComponent(500));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatHealthRegenerationComponent(10));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatAttackDamageComponent(1000));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatAttackSpeedComponent(50));
        nexus.setComponent(new BaseAttributesComponent(nexusBaseAttributesEntity));
        EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack");
        autoAttack.setComponent(new RangeComponent(1000));
        nexus.setComponent(new AutoAttackComponent(autoAttack.getId()));
        nexus.setComponent(new AutoAggroComponent(24));
        nexus.setComponent(new RequestUpdateAttributesComponent());
        nexus.setComponent(new IsTargetableComponent());
        nexus.setComponent(new IsVulnerableComponent());
        nexus.setComponent(new TeamComponent(0));
        // Waves
        int respawnTeamTrigger = entityWorld.createEntity();
        entityWorld.setComponent(respawnTeamTrigger, new InstantTriggerComponent());
        entityWorld.setComponent(respawnTeamTrigger, new TeamTargetComponent(0));
        int respawnTeamEffect = entityWorld.createEntity();
        entityWorld.setComponent(respawnTeamEffect, new RespawnComponent());
        entityWorld.setComponent(respawnTeamTrigger, new TriggeredEffectComponent(respawnTeamEffect));
        entityWorld.setComponent(respawnTeamTrigger, new TriggerOnceComponent());
        // MapObjective
        int mapObjective = entityWorld.createEntity();
        entityWorld.setComponent(mapObjective, new MissingEntitiesComponent(nexus.getId()));
        entityWorld.setComponent(mapObjective, new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(mapObjective));
        // MapObjective - Win Trigger
        int winMapObjectiveTrigger = entityWorld.createEntity();
        entityWorld.setComponent(winMapObjectiveTrigger, new InstantTriggerComponent());
        entityWorld.setComponent(winMapObjectiveTrigger, new CustomTargetComponent(mapObjective));
        int winMapObjectiveEffect = entityWorld.createEntity();
        entityWorld.setComponent(winMapObjectiveEffect, new FinishObjectiveComponent());
        entityWorld.setComponent(winMapObjectiveTrigger, new TriggeredEffectComponent(winMapObjectiveEffect));
        entityWorld.setComponent(winMapObjectiveTrigger, new TriggerOnceComponent(true));

        Vector2f[] spawnPositions = new Vector2f[]{
            new Vector2f(33, 180),
            new Vector2f(87, 180),
            new Vector2f(122, 180),
            new Vector2f(167, 180)
        };
        int[] creepsPerWave = new int[]{ 6, 6, 4, 6, 6, 6, 20, 1 };
        for (Vector2f spawnPosition : spawnPositions) {
            int spawnCasterEntity = entityWorld.createEntity();
            entityWorld.setComponent(spawnCasterEntity, new PositionComponent(spawnPosition));
            entityWorld.setComponent(spawnCasterEntity, new TeamComponent(1));
            int spawnSourceEntity = entityWorld.createEntity();
            entityWorld.setComponent(spawnSourceEntity, new EffectCastSourceComponent(spawnCasterEntity));

            int waves = 8;
            int[][] spawnTriggers = new int[waves][];
            for (int i = 0; i < waves; i++) {
                int[] waveSpawnTriggers = new int[creepsPerWave[i]];
                for (int r = 0; r < waveSpawnTriggers.length; r++) {
                    EntityWrapper spawnTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                    spawnTrigger.setComponent(new InstantTriggerComponent());
                    spawnTrigger.setComponent(new CustomTargetComponent(nexus.getId()));
                    EntityWrapper spawnEffect = entityWorld.getWrapped(entityWorld.createEntity());
                    EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
                    spawnInformation.setComponent(new SpawnTemplateComponent("../maps/etherdesert/templates/wave_" + i));
                    spawnInformation.setComponent(new SpawnMoveToTargetComponent());
                    spawnInformation.setComponent(new SpawnAttackMoveComponent());
                    spawnEffect.setComponent(new SpawnComponent(spawnInformation.getId()));
                    spawnTrigger.setComponent(new TriggeredEffectComponent(spawnEffect.getId()));
                    spawnTrigger.setComponent(new TriggerDelayComponent(5 + (1.25f * r)));
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
                    activateNextWaveEffect.setComponent(new AddEffectTriggersComponent(activateSpawnsTriggers[i + 1], activateNextWaveTriggers[i + 1], respawnTeamTrigger));
                } else {
                    activateNextWaveEffect.setComponent(new AddEffectTriggersComponent(winMapObjectiveTrigger));
                }
                entityWorld.setComponent(activateNextWaveTrigger, new TriggeredEffectComponent(activateNextWaveEffect.getId()));
            }
        }

        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void initializePlayer(EntityWorld entityWorld, int playerEntity) {
        super.initializePlayer(entityWorld, playerEntity);
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.removeComponent(characterEntity, AutoAttackComponent.class);
        entityWorld.setComponent(characterEntity, new GoldComponent(200));
        entityWorld.setComponent(characterEntity, new IsBindedComponent(Float.MAX_VALUE));
        // GoldPerTime Buff
        int goldPerTimeBuffEntity = entityWorld.createEntity();
        int goldPerTimeBuffAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(goldPerTimeBuffAttributesEntity, new BonusFlatGoldPerSecondComponent(3));
        entityWorld.setComponent(goldPerTimeBuffEntity, new ContinuousAttributesComponent(goldPerTimeBuffAttributesEntity));
        entityWorld.setComponent(goldPerTimeBuffEntity, new KeepOnDeathComponent());
        ApplyAddBuffsSystem.addBuff(entityWorld, characterEntity, goldPerTimeBuffEntity);
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
