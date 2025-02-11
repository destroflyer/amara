package amara.test;

import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGanfaul extends CommandingPlayerTest {

    public TestGanfaul() {
        characterTemplate = "units/ganfaul";
    }

    @Test
    public void testQ() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isBinded(targetDummy));
        tickSeconds(2);
        assertFalse(isBinded(targetDummy));
        assertEquals(940, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(2);
        assertEquals(800, getHealth(targetDummy), EPSILON);
        tickSeconds(2);
        assertEquals(760, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE() {
        entityWorld.setComponent(character, new DirectionComponent(new Vector2f(-1, 0)));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_E));
        tickSeconds(2);
        assertEquals(18, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(5);
        assertEquals(800, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }
}
