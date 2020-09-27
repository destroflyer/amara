package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.ManaComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestGarmon extends CommandingPlayerTest {

    public TestGarmon() {
        characterTemplate = "units/garmon";
    }
    private static final String NAME_PASSIVE_BUFF = "Stargazer";

    @Test
    public void testP() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(character, new ManaComponent(100));
        onLogicStart();

        assertTrue(hasBuff(character, NAME_PASSIVE_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertEquals(151.55f, getMana(character), EPSILON);
        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));
        queueCommand(new StopCommand());
        tickSeconds(8);
        assertTrue(hasBuff(character, NAME_PASSIVE_BUFF));

        onLogicEnd(true, true);
    }

    @Test
    public void testQ_Hit() {
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
    public void testQ_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(5);
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(960, getHealth(targetDummy), EPSILON);
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertEquals(800, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(5);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

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
    public void testR_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(30, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(5);
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertEquals(800, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(30, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(5);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }
}
