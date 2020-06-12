/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.applications.ingame.entitysystem.CustomGameTemplates;
import amara.applications.ingame.entitysystem.GameLogic;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.systems.ai.ExecuteAIActionsSystem;
import amara.applications.ingame.entitysystem.templates.CustomSerializer_Ingame;
import amara.applications.ingame.network.messages.objects.commands.Command;
import amara.applications.ingame.network.messages.objects.commands.PlayerCommand;
import amara.applications.ingame.shared.games.Game;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapPhysicsInformation;
import amara.applications.master.network.messages.objects.GameSelection;
import amara.applications.master.network.messages.objects.GameSelectionPlayer;
import amara.core.Queue;
import amara.core.files.FileAssets;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 *
 * @author Carl
 */
public class GameLogicTest {

    private final float TICK_LENGTH = (1f / 60);
    protected final float EPSILON = 0.0001f;

    private static boolean wasSetupDone = false;

    protected EntityWorld entityWorld = new EntityWorld();
    @Mock
    private Game gameMock;
    @Mock
    private Map mapMock;
    @Mock
    private GameSelection gameSelectionMock;
    private Queue<PlayerCommand> playerCommandsQueue = new Queue<>();
    @Mock
    private ExecuteAIActionsSystem.EntityBotsMap entityBotsMapMock;

    private GameLogic gameLogic;

    private Set<Integer> initialEntities;

    @BeforeClass
    public static void setup() {
        if (!wasSetupDone) {
            FileAssets.readRootFile();
            CustomSerializer_Ingame.registerClasses();
            CustomGameTemplates.registerLoader();
            wasSetupDone = true;
        }
    }

    @Before
    public void initializeGameLogic() {
        when(gameMock.getMap()).thenReturn(mapMock);
        when(mapMock.getPhysicsInformation()).thenReturn(new MapPhysicsInformation(100, 100, 0.1f, 1, new ArrayList<>()));
        when(gameMock.getGameSelection()).thenReturn(gameSelectionMock);
        when(gameSelectionMock.getTeams()).thenReturn(new GameSelectionPlayer[][]{ null, null });

        gameLogic = new GameLogic(entityWorld, gameMock, playerCommandsQueue, entityBotsMapMock);

        gameLogic.initialize();
    }

    void onLogicStart() {
        initialEntities = entityWorld.getEntitiesWithAll();
    }

    void onLogicEnd() {
        Set<Integer> resultingEntities = entityWorld.getEntitiesWithAll();
        if (!resultingEntities.equals(initialEntities)) {
            System.err.println("Entity leak - Expected: " + initialEntities.size() + ", Actual: " + resultingEntities.size());
            initialEntities.stream().filter(entity -> !resultingEntities.contains(entity)).forEach(entity -> {
                System.err.println("Removed: " + getEntityDebugString(entity));
            });
            resultingEntities.stream().filter(entity -> !initialEntities.contains(entity)).forEach(entity -> {
                System.err.println("New: " + getEntityDebugString(entity));
            });
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
        return "#" + entity + ": " + componentsString;
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
        EntityTemplate.loadTemplate(entityWorld, entity, template);
        return entity;
    }
}
