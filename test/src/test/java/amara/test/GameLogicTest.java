package amara.test;

import amara.applications.ingame.entitysystem.CustomGameTemplates;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.systems.ai.ExecuteAIActionsSystem;
import amara.applications.ingame.entitysystem.templates.CustomSerializer_Ingame;
import amara.applications.ingame.network.messages.objects.commands.Command;
import amara.applications.ingame.network.messages.objects.commands.PlayerCommand;
import amara.applications.ingame.server.entitysystem.GameLogic;
import amara.applications.ingame.shared.maps.MapFileHandler;
import amara.applications.master.server.games.Game;
import amara.applications.ingame.shared.maps.Map;
import amara.core.files.FileAssets;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameLogicTest {

    private final float TICK_LENGTH = (1f / 60);
    protected final float EPSILON = 0.001f;

    private static boolean wasSetupDone = false;

    protected EntityWorld entityWorld = new EntityWorld();
    private Game gameMock;
    private LinkedList<PlayerCommand> playerCommandsQueue = new LinkedList<>();
    private ExecuteAIActionsSystem.EntityBotsMap entityBotsMapMock;

    private GameLogic gameLogic;

    private Set<Integer> initialEntities;

    @BeforeAll
    public static void setup() {
        if (!wasSetupDone) {
            FileAssets.readRootFile();
            CustomSerializer_Ingame.registerClasses();
            CustomGameTemplates.registerLoader();
            wasSetupDone = true;
        }
    }

    @BeforeEach
    public void initializeGameLogic() {
        gameMock = mock(Game.class);

        Map map = MapFileHandler.load("empty");
        when(gameMock.getMap()).thenReturn(map);
        when(gameMock.getTeamsCount()).thenReturn(2);

        entityBotsMapMock = mock(ExecuteAIActionsSystem.EntityBotsMap.class);

        gameLogic = new GameLogic(entityWorld, gameMock, playerCommandsQueue, entityBotsMapMock);

        gameLogic.initialize();
    }

    void onLogicStart() {
        // Automatic entity processes like updating attributes, adding initial buffs, etc.
        tick();
        initialEntities = entityWorld.getEntitiesWithAll();
    }

    void onLogicEnd(boolean expectRemovedEntities, boolean expectNewEntities) {
        tickSeconds(5);
        Set<Integer> resultingEntities = entityWorld.getEntitiesWithAll();
        boolean shouldFail = false;
        if (!expectRemovedEntities) {
            List<Integer> removedEntities = initialEntities.stream().filter(entity -> !resultingEntities.contains(entity)).toList();
            if (removedEntities.size() > 0) {
                for (Integer removedEntity : removedEntities) {
                    System.err.println("Unexpected removed entity: #" + removedEntity);
                }
                shouldFail = true;
            }
        }
        if (!expectNewEntities) {
            List<Integer> newEntities = resultingEntities.stream().filter(entity -> !initialEntities.contains(entity)).toList();
            if (newEntities.size() > 0) {
                for (Integer newEntity : newEntities) {
                    System.err.println("Unexpected new entity: " + getEntityDebugString(newEntity));
                }
                shouldFail = true;
            }
        }
        if (shouldFail) {
            fail();
        }
    }

    private String getEntityDebugString(int entity) {
        String componentsString = "";
        for (Object component : entityWorld.getComponents(entity)) {
            if (componentsString.length() > 0) {
                componentsString += ", ";
            }
            componentsString += component.getClass().getSimpleName();
        }
        return "#" + entity + " (" + componentsString + ")";
    }

    int createTargetDummy(Vector2f position) {
        int targetDummy = createEntity("units/target_dummy");
        entityWorld.setComponent(targetDummy, new PositionComponent(position));
        return targetDummy;
    }

    protected void queueCommand(Command command) {
        playerCommandsQueue.add(new PlayerCommand(0, command));
    }

    protected void tickSeconds(float seconds) {
        for (float i = 0; i < seconds; i += TICK_LENGTH) {
            tick();
        }
    }

    protected void tick() {
        for (EntitySystem entitySystem : gameLogic.getEntitySystems()) {
            entitySystem.update(entityWorld, TICK_LENGTH);
        }
    }

    protected int createEntity(String template) {
        int entity = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, entity, template);
        return entity;
    }
}
