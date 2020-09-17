package amara.test;

import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestElvenArcher extends CommandingPlayerTest {

    public TestElvenArcher() {
        characterTemplate = "units/elven_archer";
    }

    @Test
    public void testE() {
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(2);
        assertEquals(16, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }
}
