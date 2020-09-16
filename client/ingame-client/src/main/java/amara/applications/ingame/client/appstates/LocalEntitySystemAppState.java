/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import amara.libraries.applications.display.ingame.maps.MapHeightmap;
import amara.libraries.entitysystem.EntitySystem;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class LocalEntitySystemAppState extends EntitySystemDisplayAppState<IngameClientApplication> {

    public LocalEntitySystemAppState() {
        
    }
    private Node entitiesNode = new Node();
    private EntitySceneMap entitySceneMap = new EntitySceneMap(entitiesNode);
    private EntitySystem[] parallelNetworkSystems = ParallelNetworkSystems.generateSystems();
    private boolean isInitialWorldLoaded;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(entitiesNode);
        IngameNetworkAppState ingameNetworkAppState = getAppState(IngameNetworkAppState.class);
        ingameNetworkAppState.addMessageBackend(new GameStartedBackend(entityWorld, getAppState(LoadingScreenAppState.class)));
        ingameNetworkAppState.addMessageBackend(new GameCrashedBackend(mainApplication));
        ingameNetworkAppState.addMessageBackend(new GameOverBackend(mainApplication));
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        addEntitySystem(playerAppState.getPlayerTeamSystem());
        MapAppState mapAppState = getAppState(MapAppState.class);
        MapHeightmap mapHeightmap = mapAppState.getMapHeightmap();
        PositionSystem positionSystem = new PositionSystem(entitySceneMap, mapHeightmap);
        addEntitySystem(positionSystem);
        addEntitySystem(new CollisionDebugSystem(getAppState(MapObstaclesAppState.class).getObstaclesNode()));
        ColorizerSystem colorizerSystem = new ColorizerSystem(entitySceneMap);
        addEntitySystem(new BushesSystem(entitySceneMap, colorizerSystem, playerAppState.getPlayerEntity()));
        addEntitySystem(new TeamModelSystem(playerAppState.getPlayerTeamSystem()));
        addEntitySystem(new ModelSystem(entitySceneMap, mainApplication));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new MarkHoveredUnitsSystem(entitySceneMap, playerAppState.getPlayerTeamSystem(), getAppState(IngameMouseCursorAppState.class)));
        addEntitySystem(new KnockupVisualisationSystem(entitySceneMap, positionSystem));
        addEntitySystem(new BuffVisualisationSystem_Aland_Passive(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Aland_Passive_Full(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Backporting(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_BaronNashor(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Blood(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Bubble_Blue(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Bubble_Default(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Bubble_White(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Burning(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Charm(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Dosaz_Teleport(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Dosaz_Ult(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_DwarfWarrior_Empowered(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Electrified(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Empowered(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Frost(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Ganfaul_Binded(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Healing(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Intervention(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Maria_Passive(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Meditating(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_RobinsGift(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Scarlet_Passive(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Slap(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Slow(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_SonicWaveMark(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Tristan_Empowered(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Turbo(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Wither(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Golden_Eagle(entitySceneMap));
        addEntitySystem(new StealthSystem(colorizerSystem));
        addEntitySystem(colorizerSystem);
    }

    public void onInitialWorldLoaded() {
        addEntitySystem(new AudioSystem(getAppState(AudioAppState.class), getAppState(IngameCameraAppState.class)));
        MapAppState mapAppState = getAppState(MapAppState.class);
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        HealthBarStyleManager healthBarStyleManager = new HealthBarStyleManager();
        HUDAttachmentsSystem hudAttachmentsSystem = new HUDAttachmentsSystem(mainApplication.getGuiNode(), mainApplication.getCamera(), mapAppState.getMapHeightmap(), healthBarStyleManager, playerAppState.getOwnTeamVisionSystem());
        addEntitySystem(hudAttachmentsSystem);
        EntityHeightMap entityHeightMap = new EntityHeightMap(entitySceneMap);
        addEntitySystem(new HealthBarSystem(hudAttachmentsSystem, entityHeightMap, healthBarStyleManager, playerAppState.getPlayerTeamSystem()));
        addEntitySystem(new StunVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new SilenceVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new TitleSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new GoldChangeSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new ExperienceChangeSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new LevelUpNotificationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new MinionAggroIndicatorSystem(hudAttachmentsSystem, entityHeightMap, playerAppState.getPlayerEntity()));
        addEntitySystem(new ReactionVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new PopupSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new WaterSpeedSystem(mapAppState));
        addEntitySystem(new CinematicsSystem(getAppState(CinematicAppState.class)));
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

    public Node getEntitiesNode() {
        return entitiesNode;
    }

    public EntitySceneMap getEntitySceneMap() {
        return entitySceneMap;
    }
}
