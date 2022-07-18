package amara.applications.ingame.maps;

import amara.applications.ingame.entitysystem.CustomGameTemplates;
import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.buffs.KeepOnDeathComponent;
import amara.applications.ingame.entitysystem.components.buffs.OnBuffAddEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.conditions.HasBuffConditionComponent;
import amara.applications.ingame.entitysystem.components.conditions.NotExistingConditionComponent;
import amara.applications.ingame.entitysystem.components.costs.BuffStacksCostComponent;
import amara.applications.ingame.entitysystem.components.effects.FinishObjectiveComponent;
import amara.applications.ingame.entitysystem.components.effects.buffs.AddBuffComponent;
import amara.applications.ingame.entitysystem.components.effects.buffs.RemoveBuffComponent;
import amara.applications.ingame.entitysystem.components.effects.buffs.stacks.AddStacksComponent;
import amara.applications.ingame.entitysystem.components.effects.general.AddEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.effects.general.RemoveEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.effects.general.RemoveEntityComponent;
import amara.applications.ingame.entitysystem.components.effects.movement.RelativeTeleportComponent;
import amara.applications.ingame.entitysystem.components.effects.physics.SetScaleComponent;
import amara.applications.ingame.entitysystem.components.effects.spawns.SpawnComponent;
import amara.applications.ingame.entitysystem.components.effects.units.*;
import amara.applications.ingame.entitysystem.components.general.CustomCleanupComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.items.ItemActiveComponent;
import amara.applications.ingame.entitysystem.components.items.ItemIDComponent;
import amara.applications.ingame.entitysystem.components.maps.MapObjectiveComponent;
import amara.applications.ingame.entitysystem.components.objectives.MissingEntitiesComponent;
import amara.applications.ingame.entitysystem.components.objectives.OpenObjectiveComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.HitboxActiveComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerIndexComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent;
import amara.applications.ingame.entitysystem.components.spawns.SpawnBuffsComponent;
import amara.applications.ingame.entitysystem.components.spawns.SpawnTemplateComponent;
import amara.applications.ingame.entitysystem.components.spells.CastCostComponent;
import amara.applications.ingame.entitysystem.components.spells.InstantEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.spells.triggers.CastedEffectTriggersComponent;
import amara.applications.ingame.entitysystem.components.spells.triggers.CastedSpellComponent;
import amara.applications.ingame.entitysystem.components.targets.AcceptAlliesComponent;
import amara.applications.ingame.entitysystem.components.targets.AcceptUntargetableComponent;
import amara.applications.ingame.entitysystem.components.targets.RequireAllBuffsComponent;
import amara.applications.ingame.entitysystem.components.targets.RequireNoBuffsComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.units.types.IsStructureComponent;
import amara.applications.ingame.entitysystem.components.visuals.ModelComponent;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.applications.ingame.entitysystem.systems.effects.buffs.stacks.StackUtil;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;

import java.util.ArrayList;
import java.util.Collections;

public class Map_Vegas extends Map {

    private int benchPlaces = 9;
    private int boardPlaces = 28;
    private int unitsCount = CustomGameTemplates.MAP_VEGAS_UNIT_COSTS.length;
    private int unitUpgradeAmount = 3;
    private int unitUpgradeLevels = 3;

    private int teamPlayersCount;
    private int[] players;
    private int[] shops;
    private int[] freeBenchPlacesBuffs;
    private int[] freeBoardPlacesBuffs;
    private int[] freeBenchPlaceBuffs;
    private int[] fullBenchPlaceBuffs;
    private int[] freeBoardPlaceBuffs;
    private int[] fullBoardPlaceBuffs;
    private int[] unitOnBoardBuffs;
    private int[] unitOnBenchBuffs;
    private int[] putTargetBuffs;
    private int[] freeBenchPlaceStackTriggers;
    private int[] freeBoardPlaceStackTriggers;
    private int[][] enemies;
    private int[][] prepareCharacterTriggers;
    private int[][] nextPreparationTriggers;
    private int[][] teamDeathAddWinTriggers;
    private int[][] teamDeathAddFinishedFightTriggers;
    private int[][] teamDeathRemoveEnemyTeamDeathTriggersTriggers;
    private int[][] nextFightTriggers;
    private int[][][] unitTypeBuffs;
    private int[][][] unitTypeUpgradeTargetBuffs;
    private int[][][] unitUpgradeUpgradeTriggers;
    private int[][][] unitUpgradeRemoveTriggers;

    @Override
    public void load(EntityWorld entityWorld, int teamPlayersCount) {
        this.teamPlayersCount = teamPlayersCount;

        int audioBackgroundMusic = entityWorld.createEntity();
        entityWorld.setComponent(audioBackgroundMusic, new AudioComponent("Sounds/music/citadel.ogg"));
        entityWorld.setComponent(audioBackgroundMusic, new AudioVolumeComponent(1.75f));
        entityWorld.setComponent(audioBackgroundMusic, new AudioLoopComponent());
        entityWorld.setComponent(audioBackgroundMusic, new AudioGlobalComponent());
        entityWorld.setComponent(audioBackgroundMusic, new StartPlayingAudioComponent());

        int rounds = 50;
        players = new int[teamPlayersCount];
        enemies = new int[rounds][teamPlayersCount];
        prepareCharacterTriggers = new int[rounds][teamPlayersCount];
        nextPreparationTriggers = new int[rounds][teamPlayersCount];
        teamDeathAddWinTriggers = new int[rounds][teamPlayersCount];
        teamDeathAddFinishedFightTriggers = new int[rounds][teamPlayersCount];
        teamDeathRemoveEnemyTeamDeathTriggersTriggers = new int[rounds][teamPlayersCount];
        nextFightTriggers = new int[rounds][teamPlayersCount];
        int[][] activateNextRoundTriggers = new int[rounds][teamPlayersCount];

        int[] finishedFightsBuffs = new int[teamPlayersCount / 2];
        for (int i = 0; i < finishedFightsBuffs.length; i++) {
            int finishedFightsBuff = entityWorld.createEntity();
            entityWorld.setComponent(finishedFightsBuff, new NameComponent("Finished fight"));
            finishedFightsBuffs[i] = finishedFightsBuff;
        }

        int resetFinishedFightsTrigger = entityWorld.createEntity();
        entityWorld.setComponent(resetFinishedFightsTrigger, new HasBuffsTriggerComponent(finishedFightsBuffs));
        entityWorld.setComponent(resetFinishedFightsTrigger, new CustomTargetComponent(entity));
        int resetFinishedFightsEffect = entityWorld.createEntity();
        entityWorld.setComponent(resetFinishedFightsEffect,new RemoveBuffComponent(finishedFightsBuffs));
        entityWorld.setComponent(resetFinishedFightsTrigger, new TriggeredEffectComponent(resetFinishedFightsEffect));
        entityWorld.setComponent(resetFinishedFightsTrigger, new TriggerSourceComponent(entity));

        int wonFightsBuff = entityWorld.createEntity();
        entityWorld.setComponent(wonFightsBuff, new NameComponent("Won fights"));

        Vector2f[] playerPositions = new Vector2f[teamPlayersCount];
        for (int i = 0; i < playerPositions.length; i++) {
            playerPositions[i] = new Vector2f(getPlayerX(i), getPlayerY(i));
        }

        shops = new int[teamPlayersCount];
        freeBenchPlacesBuffs = new int[teamPlayersCount];
        freeBoardPlacesBuffs = new int[teamPlayersCount];
        freeBenchPlaceBuffs = new int[teamPlayersCount];
        fullBenchPlaceBuffs = new int[teamPlayersCount];
        freeBoardPlaceBuffs = new int[teamPlayersCount];
        fullBoardPlaceBuffs = new int[teamPlayersCount];
        putTargetBuffs = new int[teamPlayersCount];
        unitOnBoardBuffs = new int[teamPlayersCount];
        unitOnBenchBuffs = new int[teamPlayersCount];
        freeBenchPlaceStackTriggers = new int[teamPlayersCount];
        freeBoardPlaceStackTriggers = new int[teamPlayersCount];
        unitTypeBuffs = new int[teamPlayersCount][unitsCount][unitUpgradeLevels];
        unitTypeUpgradeTargetBuffs = new int[teamPlayersCount][unitsCount][unitUpgradeLevels];
        unitUpgradeUpgradeTriggers = new int[teamPlayersCount][unitsCount][unitUpgradeLevels];
        unitUpgradeRemoveTriggers = new int[teamPlayersCount][unitsCount][unitUpgradeLevels];
        int[] fightingBuffs = new int[teamPlayersCount];
        for (int playerIndex = 0; playerIndex < teamPlayersCount; playerIndex++) {
            Vector2f playerPosition = playerPositions[playerIndex];

            int shop = entityWorld.createEntity();
            float shopX = playerPosition.getX() + 10;
            float shopY = playerPosition.getY() + 20;
            entityWorld.setComponent(shop, new IsAlwaysVisibleComponent());
            entityWorld.setComponent(shop, new ModelComponent("Models/chest/skin.xml"));
            entityWorld.setComponent(shop, new PositionComponent(new Vector2f(shopX, shopY)));
            entityWorld.setComponent(shop, new DirectionComponent(new Vector2f(0, -1)));
            entityWorld.setComponent(shop, new ShopItemsComponent(
                    "items/vegas_unit_0",
                    "items/vegas_unit_1",
                    "items/vegas_unit_2"
            ));
            entityWorld.setComponent(shop, new ShopRangeComponent(100));
            entityWorld.setComponent(shop, new TeamComponent(playerIndex + 1));
            shops[playerIndex] = shop;

            int freeBenchPlacesBuff = entityWorld.createEntity();
            entityWorld.setComponent(freeBenchPlacesBuff, new NameComponent("Free bench places"));
            freeBenchPlacesBuffs[playerIndex] = freeBenchPlacesBuff;

            int freeBoardPlacesBuff = entityWorld.createEntity();
            entityWorld.setComponent(freeBoardPlacesBuff, new NameComponent("Free board places"));
            freeBoardPlacesBuffs[playerIndex] = freeBoardPlacesBuff;

            int freeBenchPlaceBuff = entityWorld.createEntity();
            entityWorld.setComponent(freeBenchPlaceBuff, new NameComponent("Free bench place"));
            entityWorld.setComponent(freeBenchPlaceBuff, new CustomCleanupComponent());
            freeBenchPlaceBuffs[playerIndex] = freeBenchPlaceBuff;

            int fullBenchPlaceBuff = entityWorld.createEntity();
            entityWorld.setComponent(fullBenchPlaceBuff, new NameComponent("Full bench place"));
            entityWorld.setComponent(fullBenchPlaceBuff, new CustomCleanupComponent());
            fullBenchPlaceBuffs[playerIndex] = fullBenchPlaceBuff;

            int freeBoardPlaceBuff = entityWorld.createEntity();
            entityWorld.setComponent(freeBoardPlaceBuff, new NameComponent("Free board place"));
            entityWorld.setComponent(freeBoardPlaceBuff, new CustomCleanupComponent());
            freeBoardPlaceBuffs[playerIndex] = freeBoardPlaceBuff;

            int fullBoardPlaceBuff = entityWorld.createEntity();
            entityWorld.setComponent(fullBoardPlaceBuff, new NameComponent("Full board place"));
            entityWorld.setComponent(fullBoardPlaceBuff, new CustomCleanupComponent());
            fullBoardPlaceBuffs[playerIndex] = fullBoardPlaceBuff;

            int putTargetBuff = entityWorld.createEntity();
            entityWorld.setComponent(putTargetBuff, new NameComponent("Put target"));
            entityWorld.setComponent(putTargetBuff, new CustomCleanupComponent());
            putTargetBuffs[playerIndex] = putTargetBuff;

            int unitOnBoardBuff = entityWorld.createEntity();
            entityWorld.setComponent(unitOnBoardBuff, new NameComponent("Unit on board"));
            entityWorld.setComponent(unitOnBoardBuff, new KeepOnDeathComponent());
            entityWorld.setComponent(unitOnBoardBuff, new CustomCleanupComponent());
            unitOnBoardBuffs[playerIndex] = unitOnBoardBuff;

            int unitOnBenchBuff = entityWorld.createEntity();
            entityWorld.setComponent(unitOnBenchBuff, new NameComponent("Unit on bench"));
            entityWorld.setComponent(unitOnBenchBuff, new CustomCleanupComponent());
            unitOnBenchBuffs[playerIndex] = unitOnBenchBuff;

            for (int i = 0; i < benchPlaces; i++) {
                int benchPlace = entityWorld.createEntity();
                float benchPlaceX = getPlayerX(playerIndex) + 43 + (i * 7);
                float benchPlaceY = getPlayerY(playerIndex) + 60;
                entityWorld.setComponent(benchPlace, new IsStructureComponent());
                entityWorld.setComponent(benchPlace, new PositionComponent(new Vector2f(benchPlaceX, benchPlaceY)));
                entityWorld.setComponent(benchPlace, new DirectionComponent(new Vector2f(0, -1)));
                entityWorld.setComponent(benchPlace, new ModelComponent("Models/hexagon/skin.xml"));
                ApplyAddBuffsSystem.addBuff(entityWorld, benchPlace, freeBenchPlaceBuff);
                ApplyAddBuffsSystem.addBuff(entityWorld, benchPlace, putTargetBuff);
            }

            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 5; y++) {
                    int boardPlace = entityWorld.createEntity();
                    float boardPlaceX = getPlayerX(playerIndex) + 50 + (x * 7);
                    float boardPlaceY = getPlayerY(playerIndex) + 70 + (y * 5.25f);
                    if ((y % 2) == 0) {
                        boardPlaceX += 3.5f;
                    }
                    entityWorld.setComponent(boardPlace, new IsStructureComponent());
                    entityWorld.setComponent(boardPlace, new PositionComponent(new Vector2f(boardPlaceX, boardPlaceY)));
                    entityWorld.setComponent(boardPlace, new DirectionComponent(new Vector2f(0, -1)));
                    entityWorld.setComponent(boardPlace, new ModelComponent("Models/hexagon/skin.xml"));
                    ApplyAddBuffsSystem.addBuff(entityWorld, boardPlace, freeBoardPlaceBuff);
                    ApplyAddBuffsSystem.addBuff(entityWorld, boardPlace, putTargetBuff);
                }
            }

            int fightingBuff = entityWorld.createEntity();
            entityWorld.setComponent(fightingBuff, new NameComponent("Fighting"));
            fightingBuffs[playerIndex] = fightingBuff;

            for (int unitIndex = 0; unitIndex < unitsCount; unitIndex++) {
                for (int level = (unitUpgradeLevels - 1); level >= 0; level--) {
                    int unitTypeBuff = entityWorld.createEntity();
                    entityWorld.setComponent(unitTypeBuff, new NameComponent("Unit type #" + unitIndex + " level " + (level + 1)));
                    entityWorld.setComponent(unitTypeBuff, new KeepOnDeathComponent());
                    entityWorld.setComponent(unitTypeBuff, new CustomCleanupComponent());
                    unitTypeBuffs[playerIndex][unitIndex][level] = unitTypeBuff;

                    int unitTypeUpgradeTargetBuff = entityWorld.createEntity();
                    entityWorld.setComponent(unitTypeUpgradeTargetBuff, new NameComponent("Unit type #" + unitIndex + " level " + (level + 1) + " upgrade target"));
                    entityWorld.setComponent(unitTypeUpgradeTargetBuff, new KeepOnDeathComponent());
                    entityWorld.setComponent(unitTypeUpgradeTargetBuff, new CustomCleanupComponent());
                    unitTypeUpgradeTargetBuffs[playerIndex][unitIndex][level] = unitTypeUpgradeTargetBuff;

                    if (level < (unitUpgradeLevels - 1)) {
                        // Add upgrade target buff when not existing yet
                        int markUpgradeTargetTrigger = entityWorld.createEntity();
                        entityWorld.setComponent(markUpgradeTargetTrigger, new TargetTargetComponent());
                        int markUpgradeTargetCondition = entityWorld.createEntity();
                        entityWorld.setComponent(markUpgradeTargetCondition, new BuffTargetsTargetComponent( unitTypeUpgradeTargetBuffs[playerIndex][unitIndex][level] ));
                        entityWorld.setComponent(markUpgradeTargetCondition, new NotExistingConditionComponent());
                        entityWorld.setComponent(markUpgradeTargetTrigger, new TriggerConditionsComponent(markUpgradeTargetCondition));
                        int markUpgradeTargetEffect = entityWorld.createEntity();
                        entityWorld.setComponent(markUpgradeTargetEffect, new AddBuffComponent(new int[]{ unitTypeUpgradeTargetBuffs[playerIndex][unitIndex][level] }, -1));
                        entityWorld.setComponent(markUpgradeTargetTrigger, new TriggeredEffectComponent(markUpgradeTargetEffect));
                        entityWorld.setComponent(markUpgradeTargetTrigger, new TriggerSourceComponent(entity));

                        entityWorld.setComponent(unitTypeBuff, new OnBuffAddEffectTriggersComponent(markUpgradeTargetTrigger));

                        // Upgrade - Upgrade target
                        int upgradeUpgradeTrigger = entityWorld.createEntity();
                        entityWorld.setComponent(upgradeUpgradeTrigger, new BuffTargetsAmountTriggerComponent(unitTypeBuff, unitUpgradeAmount));
                        int upgradeUpgradeRules = entityWorld.createEntity();
                        entityWorld.setComponent(upgradeUpgradeRules, new AcceptUntargetableComponent());
                        entityWorld.setComponent(upgradeUpgradeRules, new AcceptAlliesComponent());
                        entityWorld.setComponent(upgradeUpgradeRules, new RequireAllBuffsComponent(unitTypeUpgradeTargetBuff));
                        entityWorld.setComponent(upgradeUpgradeRules, new MaximumTargetsComponent(1));
                        entityWorld.setComponent(upgradeUpgradeTrigger, new RuleTargetComponent(upgradeUpgradeRules));
                        int upgradeUpgradeEffect = entityWorld.createEntity();
                        entityWorld.setComponent(upgradeUpgradeEffect, new LevelUpComponent());
                        entityWorld.setComponent(upgradeUpgradeEffect, new RemoveBuffComponent(unitTypeBuff, unitTypeUpgradeTargetBuff));
                        entityWorld.setComponent(upgradeUpgradeEffect, new AddBuffComponent(new int[]{unitTypeBuffs[playerIndex][unitIndex][level + 1]}, -1));
                        entityWorld.setComponent(upgradeUpgradeEffect, new SetScaleComponent(level + 2));
                        entityWorld.setComponent(upgradeUpgradeTrigger, new TriggeredEffectComponent(upgradeUpgradeEffect));
                        unitUpgradeUpgradeTriggers[playerIndex][unitIndex][level] = upgradeUpgradeTrigger;

                        // Upgrade - Remove non-targets
                        int upgradeRemoveTrigger = entityWorld.createEntity();
                        entityWorld.setComponent(upgradeRemoveTrigger, new BuffTargetsAmountTriggerComponent(unitTypeBuff, unitUpgradeAmount));
                        int upgradeRemoveRules = entityWorld.createEntity();
                        entityWorld.setComponent(upgradeRemoveRules, new AcceptUntargetableComponent());
                        entityWorld.setComponent(upgradeRemoveRules, new AcceptAlliesComponent());
                        entityWorld.setComponent(upgradeRemoveRules, new RequireAllBuffsComponent(unitTypeBuff));
                        entityWorld.setComponent(upgradeRemoveRules, new RequireNoBuffsComponent(unitTypeUpgradeTargetBuff));
                        entityWorld.setComponent(upgradeRemoveRules, new MaximumTargetsComponent(2));
                        entityWorld.setComponent(upgradeRemoveTrigger, new RuleTargetComponent(upgradeRemoveRules));
                        int upgradeRemoveEffect = entityWorld.createEntity();
                        entityWorld.setComponent(upgradeRemoveEffect, new RemoveEntityComponent());
                        entityWorld.setComponent(upgradeRemoveTrigger, new TriggeredEffectComponent(upgradeRemoveEffect));
                        unitUpgradeRemoveTriggers[playerIndex][unitIndex][level] = upgradeRemoveTrigger;
                    }
                }
            }
        }

        for (int round = 0; round < rounds; round++) {

            ArrayList<Integer> randomOrderPlayers = new ArrayList<>();
            for (int playerIndex = 0; playerIndex < teamPlayersCount; playerIndex++) {
                randomOrderPlayers.add(playerIndex);
            }
            Collections.shuffle(randomOrderPlayers);

            int[] roundEnemies = new int[teamPlayersCount];
            Vector2f[] fightPositions = new Vector2f[teamPlayersCount];
            for (int i = 0; i < randomOrderPlayers.size(); i += 2) {
                int player1 = randomOrderPlayers.get(i);
                int player2 = randomOrderPlayers.get(i + 1);
                roundEnemies[player1] = player2;
                roundEnemies[player2] = player1;

                float x = getX(i / 2);
                fightPositions[player1] = new Vector2f(x, getY(1));
                fightPositions[player2] = new Vector2f(x, getY(2));
            }
            enemies[round] = roundEnemies;

            for (int playerIndex = 0; playerIndex < teamPlayersCount; playerIndex++) {
                int fightIndex = (randomOrderPlayers.indexOf(playerIndex) / 2);
                Vector2f playerPosition = playerPositions[playerIndex];

                // Preparation
                int prepareCharacterTrigger = entityWorld.createEntity();
                entityWorld.setComponent(prepareCharacterTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(prepareCharacterTrigger, new BuffTargetsTargetComponent(unitOnBoardBuffs[playerIndex]));
                int prepareCharacterEffect = entityWorld.createEntity();
                entityWorld.setComponent(prepareCharacterEffect, new AddGoldComponent(3));
                entityWorld.setComponent(prepareCharacterTrigger, new TriggeredEffectComponent(prepareCharacterEffect));
                entityWorld.setComponent(prepareCharacterTrigger, new TriggerOnceComponent());

                int prepareUnitsTrigger = entityWorld.createEntity();
                entityWorld.setComponent(prepareUnitsTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(prepareUnitsTrigger, new BuffTargetsTargetComponent(unitOnBoardBuffs[playerIndex]));
                int prepareUnitsEffect = entityWorld.createEntity();
                entityWorld.setComponent(prepareUnitsEffect, new RespawnComponent());
                entityWorld.setComponent(prepareUnitsEffect, new RemoveAutoAggroComponent());
                entityWorld.setComponent(prepareUnitsEffect, new RemoveBuffComponent(fightingBuffs[playerIndex]));
                entityWorld.setComponent(prepareUnitsEffect, new AddBuffComponent(new int[]{ putTargetBuffs[playerIndex] }, -1));
                entityWorld.setComponent(prepareUnitsTrigger, new TriggeredEffectComponent(prepareUnitsEffect));
                entityWorld.setComponent(prepareUnitsTrigger, new TriggerOnceComponent());

                int prepareBoardTrigger = entityWorld.createEntity();
                entityWorld.setComponent(prepareBoardTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(prepareBoardTrigger, new BuffTargetsTargetComponent(freeBoardPlaceBuffs[playerIndex]));
                int prepareBoardEffect = entityWorld.createEntity();
                entityWorld.setComponent(prepareBoardEffect, new AddBuffComponent(new int[]{ putTargetBuffs[playerIndex] }, -1));
                entityWorld.setComponent(prepareBoardTrigger, new TriggeredEffectComponent(prepareBoardEffect));
                entityWorld.setComponent(prepareBoardTrigger, new TriggerOnceComponent());

                int refreshShopTrigger = entityWorld.createEntity();
                entityWorld.setComponent(refreshShopTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(refreshShopTrigger, new CustomTargetComponent(shops[playerIndex]));
                int refreshShopEffect = entityWorld.createEntity();
                // entityWorld.setComponent(refreshShopEffect, new RefreshTODO());
                entityWorld.setComponent(refreshShopTrigger, new TriggeredEffectComponent(refreshShopEffect));
                entityWorld.setComponent(refreshShopTrigger, new TriggerOnceComponent());

                // Fight
                int fightUnitsTrigger = entityWorld.createEntity();
                entityWorld.setComponent(fightUnitsTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(fightUnitsTrigger, new BuffTargetsTargetComponent(unitOnBoardBuffs[playerIndex]));
                int fightUnitsEffect = entityWorld.createEntity();
                entityWorld.setComponent(fightUnitsEffect, new RelativeTeleportComponent(playerPosition, fightPositions[playerIndex]));
                entityWorld.setComponent(fightUnitsEffect, new SetAutoAggroComponent(-1));
                entityWorld.setComponent(fightUnitsEffect, new RemoveBuffComponent(putTargetBuffs[playerIndex]));
                entityWorld.setComponent(fightUnitsEffect, new AddBuffComponent(new int[]{ fightingBuffs[playerIndex] }, -1));
                entityWorld.setComponent(fightUnitsTrigger, new TriggeredEffectComponent(fightUnitsEffect));
                entityWorld.setComponent(fightUnitsTrigger, new TriggerOnceComponent());

                int fightBoardTrigger = entityWorld.createEntity();
                entityWorld.setComponent(fightBoardTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(fightBoardTrigger, new BuffTargetsTargetComponent(freeBoardPlaceBuffs[playerIndex]));
                int fightBoardEffect = entityWorld.createEntity();
                entityWorld.setComponent(fightBoardEffect, new RemoveBuffComponent(putTargetBuffs[playerIndex]));
                entityWorld.setComponent(fightBoardTrigger, new TriggeredEffectComponent(fightBoardEffect));
                entityWorld.setComponent(fightBoardTrigger, new TriggerOnceComponent());

                int teamDeathAddWinTrigger = entityWorld.createEntity();
                entityWorld.setComponent(teamDeathAddWinTrigger, new NoBuffTargetsTriggerComponent(fightingBuffs[playerIndex]));
                int teamDeathAddWinEffect = entityWorld.createEntity();
                entityWorld.setComponent(teamDeathAddWinEffect, new AddBuffComponent(new int[]{ wonFightsBuff }, -1));
                entityWorld.setComponent(teamDeathAddWinEffect, new AddStacksComponent(wonFightsBuff, 1));
                entityWorld.setComponent(teamDeathAddWinEffect, new AddGoldComponent(1));
                entityWorld.setComponent(teamDeathAddWinTrigger, new TriggeredEffectComponent(teamDeathAddWinEffect));
                entityWorld.setComponent(teamDeathAddWinTrigger, new TriggerOnceComponent());

                int teamDeathAddFinishedFightTrigger = entityWorld.createEntity();
                entityWorld.setComponent(teamDeathAddFinishedFightTrigger, new NoBuffTargetsTriggerComponent(fightingBuffs[playerIndex]));
                entityWorld.setComponent(teamDeathAddFinishedFightTrigger, new CustomTargetComponent(entity));
                int teamDeathAddFinishedFightEffect = entityWorld.createEntity();
                entityWorld.setComponent(teamDeathAddFinishedFightEffect, new AddBuffComponent(new int[]{ finishedFightsBuffs[fightIndex] }, -1));
                entityWorld.setComponent(teamDeathAddFinishedFightTrigger, new TriggeredEffectComponent(teamDeathAddFinishedFightEffect));
                entityWorld.setComponent(teamDeathAddFinishedFightTrigger, new TriggerOnceComponent());

                int teamDeathRemoveEnemyTeamDeathTriggersTrigger = entityWorld.createEntity();
                entityWorld.setComponent(teamDeathRemoveEnemyTeamDeathTriggersTrigger, new SourceTargetComponent());
                entityWorld.setComponent(teamDeathRemoveEnemyTeamDeathTriggersTrigger, new NoBuffTargetsTriggerComponent(fightingBuffs[playerIndex]));
                entityWorld.setComponent(teamDeathRemoveEnemyTeamDeathTriggersTrigger, new TriggerOnceComponent());

                // The team death triggers have to be delayed one frame, so they are checked after the units received the fighting buffs
                int addTeamDeathTriggersTrigger = entityWorld.createEntity();
                entityWorld.setComponent(addTeamDeathTriggersTrigger, new InstantTriggerComponent());
                entityWorld.setComponent(addTeamDeathTriggersTrigger, new SourceTargetComponent());
                int addTeamDeathTriggersEffect = entityWorld.createEntity();
                entityWorld.setComponent(addTeamDeathTriggersEffect, new AddEffectTriggersComponent(teamDeathAddWinTrigger, teamDeathAddFinishedFightTrigger, teamDeathRemoveEnemyTeamDeathTriggersTrigger));
                entityWorld.setComponent(addTeamDeathTriggersTrigger, new TriggeredEffectComponent(addTeamDeathTriggersEffect));
                entityWorld.setComponent(addTeamDeathTriggersTrigger, new TriggerOnceComponent());

                // Trigger next round

                int nextPreparationTrigger = entityWorld.createEntity();
                int nextPreparationEffect = entityWorld.createEntity();
                entityWorld.setComponent(nextPreparationEffect, new AddEffectTriggersComponent(prepareCharacterTrigger, prepareUnitsTrigger, prepareBoardTrigger, refreshShopTrigger));
                entityWorld.setComponent(nextPreparationTrigger, new TriggeredEffectComponent(nextPreparationEffect));
                entityWorld.setComponent(nextPreparationTrigger, new TriggerOnceComponent());

                int nextFightTrigger = entityWorld.createEntity();
                int nextFightEffect = entityWorld.createEntity();
                entityWorld.setComponent(nextFightEffect, new AddEffectTriggersComponent(fightUnitsTrigger, fightBoardTrigger, addTeamDeathTriggersTrigger));
                entityWorld.setComponent(nextFightTrigger, new TriggeredEffectComponent(nextFightEffect));
                entityWorld.setComponent(nextFightTrigger, new TriggerDelayComponent(10));
                entityWorld.setComponent(nextFightTrigger, new TriggerOnceComponent());

                int activateNextRoundTrigger = entityWorld.createEntity();
                entityWorld.setComponent(activateNextRoundTrigger, new TriggerOnceComponent());

                if (round == 0) {
                    entityWorld.setComponent(nextPreparationTrigger, new InstantTriggerComponent());
                    entityWorld.setComponent(nextPreparationTrigger, new TriggerSourceComponent(entity));
                    entityWorld.setComponent(nextFightTrigger, new InstantTriggerComponent());
                    entityWorld.setComponent(nextFightTrigger, new TriggerSourceComponent(entity));
                    entityWorld.setComponent(activateNextRoundTrigger, new InstantTriggerComponent());
                    entityWorld.setComponent(activateNextRoundTrigger, new TriggerSourceComponent(entity));
                } else {
                    entityWorld.setComponent(nextPreparationTrigger, new HasBuffsTriggerComponent(finishedFightsBuffs));
                    entityWorld.setComponent(nextFightTrigger, new HasBuffsTriggerComponent(finishedFightsBuffs));
                    entityWorld.setComponent(activateNextRoundTrigger, new HasBuffsTriggerComponent(finishedFightsBuffs));
                }

                prepareCharacterTriggers[round][playerIndex] = prepareCharacterTrigger;
                nextPreparationTriggers[round][playerIndex] = nextPreparationTrigger;
                teamDeathAddWinTriggers[round][playerIndex] = teamDeathAddWinTrigger;
                teamDeathAddFinishedFightTriggers[round][playerIndex] = teamDeathAddFinishedFightTrigger;
                teamDeathRemoveEnemyTeamDeathTriggersTriggers[round][playerIndex] = teamDeathRemoveEnemyTeamDeathTriggersTrigger;
                nextFightTriggers[round][playerIndex] = nextFightTrigger;
                activateNextRoundTriggers[round][playerIndex] = activateNextRoundTrigger;
            }
        }

        int mapObjective = entityWorld.createEntity();
        entityWorld.setComponent(mapObjective, new MissingEntitiesComponent(entity));
        entityWorld.setComponent(mapObjective, new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(mapObjective));

        for (int round = 0; round < rounds; round++) {
            for (int playerIndex = 0; playerIndex < teamPlayersCount; playerIndex++) {
                int activateNextRoundTrigger = activateNextRoundTriggers[round][playerIndex];
                int activateNextRoundEffect = entityWorld.createEntity();
                if (round < (rounds - 1)) {
                    entityWorld.setComponent(activateNextRoundTrigger, new CustomTargetComponent(entity));
                    int nextPreparationTrigger = nextPreparationTriggers[round + 1][playerIndex];
                    int nextFightTrigger = nextFightTriggers[round + 1][playerIndex];
                    int nextActivateNextRoundTrigger = activateNextRoundTriggers[round + 1][playerIndex];
                    entityWorld.setComponent(activateNextRoundEffect, new AddEffectTriggersComponent(nextPreparationTrigger, nextFightTrigger, nextActivateNextRoundTrigger));
                } else {
                    entityWorld.setComponent(activateNextRoundTrigger, new CustomTargetComponent(mapObjective));
                    entityWorld.setComponent(activateNextRoundEffect, new FinishObjectiveComponent());
                }
                entityWorld.setComponent(activateNextRoundTrigger, new TriggeredEffectComponent(activateNextRoundEffect));
            }
        }
    }

    @Override
    public void initializePlayer(EntityWorld entityWorld, int playerEntity) {
        super.initializePlayer(entityWorld, playerEntity);
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.removeComponent(characterEntity, PassivesComponent.class);
        entityWorld.removeComponent(characterEntity, AutoAttackComponent.class);
        entityWorld.setComponent(characterEntity, new GoldComponent(5));
        entityWorld.setComponent(characterEntity, new IsBindedComponent(Float.MAX_VALUE));
        entityWorld.setComponent(characterEntity, new SightRangeComponent(999));
        entityWorld.setComponent(characterEntity, new SpellsUpgradePointsComponent(0));

        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        players[playerIndex] = playerEntity;
        entityWorld.setComponent(freeBenchPlaceStackTriggers[playerIndex], new CustomTargetComponent(characterEntity));
        entityWorld.setComponent(freeBoardPlaceStackTriggers[playerIndex], new CustomTargetComponent(characterEntity));

        if (playerIndex == (teamPlayersCount - 1)) {
            for (int currentPlayerIndex = 0; currentPlayerIndex < teamPlayersCount; currentPlayerIndex++) {
                int ownCharacterEntity = entityWorld.getComponent(players[currentPlayerIndex], PlayerCharacterComponent.class).getEntity();
                for (int round = 0; round < nextPreparationTriggers.length; round++) {
                    int enemyPlayerEntity = players[enemies[round][currentPlayerIndex]];
                    int enemyPlayerIndex = entityWorld.getComponent(enemyPlayerEntity, PlayerIndexComponent.class).getIndex();
                    int enemyCharacterEntity = entityWorld.getComponent(enemyPlayerEntity, PlayerCharacterComponent.class).getEntity();
                    entityWorld.setComponent(prepareCharacterTriggers[round][currentPlayerIndex], new CustomTargetComponent(ownCharacterEntity));
                    entityWorld.setComponent(nextPreparationTriggers[round][currentPlayerIndex], new CustomTargetComponent(ownCharacterEntity));
                    entityWorld.setComponent(nextFightTriggers[round][currentPlayerIndex], new CustomTargetComponent(ownCharacterEntity));
                    entityWorld.setComponent(teamDeathAddWinTriggers[round][currentPlayerIndex], new CustomTargetComponent(enemyCharacterEntity));
                    // Remove the team death triggers of the enemy when winning a round
                    int teamDeathRemoveEnemyTeamDeathTriggersTrigger = teamDeathRemoveEnemyTeamDeathTriggersTriggers[round][currentPlayerIndex];
                    int teamDeathRemoveEnemyTeamDeathTriggersEffect = entityWorld.createEntity();
                    entityWorld.setComponent(teamDeathRemoveEnemyTeamDeathTriggersEffect, new RemoveEffectTriggersComponent(new int[] { teamDeathAddWinTriggers[round][enemyPlayerIndex], teamDeathAddFinishedFightTriggers[round][enemyPlayerIndex], teamDeathRemoveEnemyTeamDeathTriggersTriggers[round][enemyPlayerIndex] }, false));
                    entityWorld.setComponent(teamDeathRemoveEnemyTeamDeathTriggersTrigger, new TriggeredEffectComponent(teamDeathRemoveEnemyTeamDeathTriggersEffect));
                }
            }
        }

        ApplyAddBuffsSystem.addBuff(entityWorld, characterEntity, freeBenchPlacesBuffs[playerIndex]);
        ApplyAddBuffsSystem.addBuff(entityWorld, characterEntity, freeBoardPlacesBuffs[playerIndex]);
        StackUtil.addStacks(entityWorld, characterEntity, freeBenchPlacesBuffs[playerIndex], benchPlaces);
        StackUtil.addStacks(entityWorld, characterEntity, freeBoardPlacesBuffs[playerIndex], boardPlaces);

        int spellSwap = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, spellSwap, "spells/vegas_swap_mark(spellIndex=0,putTargetBuff=" + putTargetBuffs[playerIndex]
            + ",freeBenchPlacesBuff=" + freeBenchPlacesBuffs[playerIndex] + ",freeBenchPlaceBuff=" + freeBenchPlaceBuffs[playerIndex] + ",fullBenchPlaceBuff=" + fullBenchPlaceBuffs[playerIndex] + ",unitOnBenchBuff=" + unitOnBenchBuffs[playerIndex]
            + ",freeBoardPlacesBuff=" + freeBoardPlacesBuffs[playerIndex] + ",freeBoardPlaceBuff=" + freeBoardPlaceBuffs[playerIndex] + ",fullBoardPlaceBuff=" + fullBoardPlaceBuffs[playerIndex] + ",unitOnBoardBuff=" + unitOnBoardBuffs[playerIndex]
        + ")");

        int spellSell = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, spellSell, "spells/vegas_sell(unitOnBenchBuff=" + unitOnBenchBuffs[playerIndex] + ",unitOnBoardBuff=" + unitOnBoardBuffs[playerIndex] + ")");
        for (int unitIndex = 0; unitIndex < unitsCount; unitIndex++) {
            for (int level = 0; level < unitUpgradeLevels; level++) {
                // Add gold
                int spellEffectGold = entityWorld.createEntity();
                entityWorld.setComponent(spellEffectGold, new CastedSpellComponent(spellSell));
                int effectTriggerGold = entityWorld.createEntity();
                entityWorld.setComponent(effectTriggerGold, new SourceCasterTargetComponent());
                int conditionGold = entityWorld.createEntity();
                entityWorld.setComponent(conditionGold, new TargetTargetComponent());
                entityWorld.setComponent(conditionGold, new HasBuffConditionComponent(unitTypeBuffs[playerIndex][unitIndex][level], 0));
                entityWorld.setComponent(effectTriggerGold, new TriggerConditionsComponent(conditionGold));
                int effectGold = entityWorld.createEntity();
                entityWorld.setComponent(effectGold, new AddGoldComponent(CustomGameTemplates.MAP_VEGAS_UNIT_COSTS[unitIndex] * (level + 1)));
                entityWorld.setComponent(effectTriggerGold, new TriggeredEffectComponent(effectGold));
                entityWorld.setComponent(effectTriggerGold, new TriggerSourceComponent(spellSell));
                entityWorld.setComponent(spellEffectGold, new CastedEffectTriggersComponent(effectTriggerGold));

                // Transfer upgrade target buff
                int spellEffectTransfer = entityWorld.createEntity();
                entityWorld.setComponent(spellEffectTransfer, new CastedSpellComponent(spellSell));
                int effectTriggerTransfer = entityWorld.createEntity();
                entityWorld.setComponent(effectTriggerTransfer, new BuffTargetsTargetComponent(unitTypeBuffs[playerIndex][unitIndex][level]));
                entityWorld.setComponent(effectTriggerTransfer, new ExcludeTargetTargetComponent());
                entityWorld.setComponent(effectTriggerTransfer, new MaximumTargetsComponent(1));
                int conditionTransfer = entityWorld.createEntity();
                entityWorld.setComponent(conditionTransfer, new TargetTargetComponent());
                entityWorld.setComponent(conditionTransfer, new HasBuffConditionComponent(unitTypeUpgradeTargetBuffs[playerIndex][unitIndex][level], 0));
                entityWorld.setComponent(effectTriggerTransfer, new TriggerConditionsComponent(conditionTransfer));
                int effectTransfer = entityWorld.createEntity();
                entityWorld.setComponent(effectTransfer, new AddBuffComponent(new int[] { unitTypeUpgradeTargetBuffs[playerIndex][unitIndex][level] }, -1));
                entityWorld.setComponent(effectTriggerTransfer, new TriggeredEffectComponent(effectTransfer));
                entityWorld.setComponent(effectTriggerTransfer, new TriggerSourceComponent(spellSell));
                entityWorld.setComponent(spellEffectTransfer, new CastedEffectTriggersComponent(effectTriggerTransfer));

                entityWorld.setComponent(unitUpgradeUpgradeTriggers[playerIndex][unitIndex][level], new TriggerSourceComponent(characterEntity));
                entityWorld.setComponent(unitUpgradeRemoveTriggers[playerIndex][unitIndex][level], new TriggerSourceComponent(characterEntity));
            }
        }

        entityWorld.setComponent(characterEntity, new SpellsComponent(spellSwap, -1, spellSell));
    }

    @Override
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity) {
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        float x = getPlayerX(playerIndex) + 50;
        float y = getPlayerY(playerIndex) + 50;
        entityWorld.setComponent(characterEntity, new PositionComponent(new Vector2f(x, y)));
        entityWorld.setComponent(characterEntity, new DirectionComponent(new Vector2f(0, 1)));
        entityWorld.setComponent(characterEntity, new TeamComponent(playerIndex + 1));
        entityWorld.removeComponent(characterEntity, HitboxActiveComponent.class);
        entityWorld.removeComponent(characterEntity, IsTargetableComponent.class);
        entityWorld.removeComponent(characterEntity, IsVulnerableComponent.class);
    }

    @Override
    public void initializeItem(EntityWorld entityWorld, int itemEntity, int buyerEntity) {
        super.initializeItem(entityWorld, itemEntity, buyerEntity);
        int unitIndex = Integer.parseInt(entityWorld.getComponent(itemEntity, ItemIDComponent.class).getID().substring("vegas_unit_".length()));
        int teamEntity = entityWorld.getComponent(buyerEntity, TeamComponent.class).getTeamEntity();
        int playerIndex = teamEntity - 1;
        int itemActiveEntity = entityWorld.getComponent(itemEntity, ItemActiveComponent.class).getSpellEntity();
        // Target
        int[] instantEffectTriggerEntities = entityWorld.getComponent(itemActiveEntity, InstantEffectTriggersComponent.class).getEffectTriggerEntities();
        entityWorld.setComponent(instantEffectTriggerEntities[0], new BuffTargetsTargetComponent(freeBenchPlaceBuffs[playerIndex]));
        int effectEntity = entityWorld.getComponent(instantEffectTriggerEntities[0], TriggeredEffectComponent.class).getEffectEntity();
        // Spawn
        int[] spawnInformationEntities = entityWorld.getComponent(effectEntity, SpawnComponent.class).getSpawnInformationEntites();
        entityWorld.setComponent(spawnInformationEntities[0], new SpawnTemplateComponent("../Maps/vegas/templates/unit_" + unitIndex + "(playerCharacter=" + buyerEntity + ",putTargetBuff=" + putTargetBuffs[playerIndex]
            + ",freeBenchPlacesBuff=" + freeBenchPlacesBuffs[playerIndex] + ",freeBenchPlaceBuff=" + freeBenchPlaceBuffs[playerIndex] + ",fullBenchPlaceBuff=" + fullBenchPlaceBuffs[playerIndex] + ",unitOnBenchBuff=" + unitOnBenchBuffs[playerIndex]
            + ",freeBoardPlacesBuff=" + freeBoardPlacesBuffs[playerIndex] + ",freeBoardPlaceBuff=" + freeBoardPlaceBuffs[playerIndex] + ",fullBoardPlaceBuff=" + fullBoardPlaceBuffs[playerIndex] + ",unitOnBoardBuff=" + unitOnBoardBuffs[playerIndex]
        + ")"));
        entityWorld.setComponent(spawnInformationEntities[0], new SpawnBuffsComponent(unitOnBenchBuffs[playerIndex], putTargetBuffs[playerIndex], unitTypeBuffs[playerIndex][unitIndex][0]));
        entityWorld.setComponent(effectEntity, new RemoveBuffComponent(freeBenchPlaceBuffs[playerIndex], putTargetBuffs[playerIndex]));
        entityWorld.setComponent(effectEntity, new AddBuffComponent(new int[]{ fullBenchPlaceBuffs[playerIndex] }, -1));
        // CastCost
        int castCost = entityWorld.createEntity();
        entityWorld.setComponent(castCost, new BuffStacksCostComponent(freeBenchPlacesBuffs[playerIndex], 1));
        entityWorld.setComponent(itemActiveEntity, new CastCostComponent(castCost));
    }

    private float getPlayerX(int playerIndex) {
        int column = (playerIndex % 4);
        return getX(column);
    }

    private float getX(int column) {
        return (column * 128);
    }

    private float getPlayerY(int playerIndex) {
        int row = ((playerIndex / 4) * 3);
        return getY(row);
    }

    private float getY(int row) {
        return (row * 128);
    }
}
