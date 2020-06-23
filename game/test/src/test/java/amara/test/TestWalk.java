package amara.test;

import amara.applications.ingame.network.messages.objects.commands.StopWalkInDirectionCommand;
import amara.applications.ingame.network.messages.objects.commands.WalkInDirectionCommand;
import amara.applications.ingame.network.messages.objects.commands.WalkToTargetCommand;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TestWalk extends CommandingPlayerTest {

    @Test
    public void testWalkToTarget() {
        onLogicStart();

        queueCommand(new WalkToTargetCommand(new Vector2f(20, 30)));
        tickSeconds(5);

        onLogicEnd();
        assertEquals(20, getX(character), EPSILON);
        assertEquals(30, getY(character), EPSILON);
    }

    @Test
    public void testWalkInDirection() {
        float walkSpeed = 6.75f;
        onLogicStart();

        queueCommand(new WalkInDirectionCommand(new Vector2f(1, 0)));
        tickSeconds(5);
        queueCommand(new StopWalkInDirectionCommand());
        tickSeconds(2);

        onLogicEnd();
        assertEquals(10 + (walkSpeed * 5), getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
    }
}
