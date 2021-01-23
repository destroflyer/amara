package amara.applications.ingame.client.appstates;

import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.network.backends.*;
import amara.applications.ingame.client.systems.audio.*;
import amara.applications.ingame.client.systems.cinematics.*;
import amara.applications.ingame.client.systems.filters.*;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.applications.ingame.client.systems.visualisation.buffs.*;
import amara.applications.ingame.client.systems.visualisation.effects.crodwcontrol.*;
import amara.applications.ingame.client.systems.visualisation.effects.reactions.*;
import amara.applications.ingame.client.systems.visualisation.healthbars.*;
import amara.applications.ingame.entitysystem.synchronizing.*;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.entitysystem.EntitySystem;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class LocalEntitySystemAppState extends EntitySystemDisplayAppState<IngameClientApplication> {

    private Node nodeVisibleToMouse = new Node();
    private Node nodeNotVisibleToMouse = new Node();
    private EntitySceneMap entitySceneMap = new EntitySceneMap();
    private EntitySystem[] parallelNetworkSystems = ParallelNetworkSystems.generateSystems();
    private boolean isInitialWorldLoaded;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        IngameNetworkAppState ingameNetworkAppState = getAppState(IngameNetworkAppState.class);
        ingameNetworkAppState.addMessageBackend(new GameStartedBackend(entityWorld, getAppState(LoadingScreenAppState.class)));
        ingameNetworkAppState.addMessageBackend(new GameCrashedBackend(mainApplication));
        ingameNetworkAppState.addMessageBackend(new GameOverBackend(mainApplication));
        mainApplication.getRootNode().attachChild(nodeVisibleToMouse);
        mainApplication.getRootNode().attachChild(nodeNotVisibleToMouse);
        addEntitySystem(new AttachEntityNodesSystem(entitySceneMap, nodeVisibleToMouse, nodeNotVisibleToMouse));
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        addEntitySystem(playerAppState.getPlayerTeamSystem());
        MapAppState mapAppState = getAppState(MapAppState.class);
        AssetManager assetManager = mainApplication.getAssetManager();
        PositionSystem positionSystem = new PositionSystem(entitySceneMap, mapAppState.getMapHeightmap());
        addEntitySystem(positionSystem);
        addEntitySystem(new CollisionDebugSystem(getAppState(MapObstaclesAppState.class).getObstaclesNode(), assetManager));
        addEntitySystem(new TeamModelSystem(playerAppState.getPlayerTeamSystem()));
        addEntitySystem(new ModelSystem(entitySceneMap, assetManager));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new MarkHoveredUnitsSystem(entitySceneMap, playerAppState.getPlayerTeamSystem(), getAppState(IngameMouseCursorAppState.class), playerAppState::getCursorHoveredEntity, assetManager));
        addEntitySystem(new KnockupVisualisationSystem(entitySceneMap, positionSystem));
        addEntitySystem(new BuffVisualisationSystem_Aland_Passive(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Aland_Passive_Full(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Backporting(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_BaronNashor(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Blood(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Bubble_Blue(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Bubble_Default(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Bubble_White(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Burning(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Charm(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Dosaz_Teleport(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Dosaz_Ult(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_DwarfWarrior_Empowered(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Electrified(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Empowered(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Frost(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Ganfaul_Binded(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Healing(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Intervention(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Maria_Passive(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Meditating(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_RobinsGift(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Scarlet_Passive(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Slap(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Slow(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_SonicWaveMark(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Tristan_Empowered(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Turbo(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Wither(entitySceneMap, assetManager));
        addEntitySystem(new BuffVisualisationSystem_Golden_Eagle(entitySceneMap, assetManager));
    }

    public void onInitialWorldLoaded() {
        addEntitySystem(new AudioSystem(getAppState(AudioAppState.class), getAppState(IngameCameraAppState.class)));
        MapAppState mapAppState = getAppState(MapAppState.class);
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        AssetManager assetManager = mainApplication.getAssetManager();

        // HUD attachments
        EntityHeightMap entityHeightMap = new EntityHeightMap(entitySceneMap);
        HealthBarStyleManager healthBarStyleManager = new HealthBarStyleManager();
        HUDAttachmentsSystem hudAttachmentsSystem = new HUDAttachmentsSystem(mainApplication.getGuiNode(), mainApplication.getCamera(), mapAppState.getMapHeightmap(), healthBarStyleManager, playerAppState.getOwnTeamVisionSystem());
        addEntitySystem(new HealthBarSystem(hudAttachmentsSystem, entityHeightMap, assetManager, healthBarStyleManager, playerAppState.getPlayerTeamSystem()));
        addEntitySystem(new StunVisualisationSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(new SilenceVisualisationSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(new TitleSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(new GoldChangeSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(new ExperienceChangeSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(new LevelUpNotificationSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(new MinionAggroIndicatorSystem(hudAttachmentsSystem, entityHeightMap, assetManager, playerAppState.getPlayerEntity()));
        addEntitySystem(new ReactionVisualisationSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(new PopupSystem(hudAttachmentsSystem, entityHeightMap, assetManager));
        addEntitySystem(hudAttachmentsSystem);

        // Colorizers
        ColorizerSystem colorizerSystem = new ColorizerSystem(entitySceneMap);
        addEntitySystem(new BushesSystem(entitySceneMap, colorizerSystem, mainApplication.getAssetManager(), playerAppState.getPlayerEntity()));
        addEntitySystem(new StealthSystem(colorizerSystem));
        addEntitySystem(colorizerSystem);

        // Other
        addEntitySystem(new WaterSpeedSystem(mapAppState));
        addEntitySystem(new CinematicsSystem(getAppState(CinematicAppState.class)));

        // Needs to best be the very last client system, so no nodes for removed entities are leaked when they would be requested again after being removed
        addEntitySystem(new DetachEntityNodesSystem(entitySceneMap));

        isInitialWorldLoaded = true;
    }

    @Override
    public void update(float lastTimePerFrame) {
        if (isInitialWorldLoaded) {
            // Has to start AFTER the whole initial entity world has been loaded, because otherwise entities referencing each other might not be ready yet
            // This is especially important when reconnecting
            super.update(lastTimePerFrame);
            GameSynchronizingUtil.simulateSecondFrames(entityWorld, lastTimePerFrame, simulatedTimePerFrame -> {
                for (EntitySystem parallelNetworkSystem : parallelNetworkSystems) {
                    parallelNetworkSystem.update(entityWorld, simulatedTimePerFrame);
                }
            });
        }
    }

    public int getEntity(Spatial spatial) {
        while (spatial != null) {
            Integer entity = spatial.getUserData("entity");
            if (entity != null) {
                return entity;
            }
            spatial = spatial.getParent();
        }
        return -1;
    }

    public EntitySceneMap getEntitySceneMap() {
        return entitySceneMap;
    }

    public Node getNodeVisibleToMouse() {
        return nodeVisibleToMouse;
    }
}
