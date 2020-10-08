package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
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
public class TestTristan extends CommandingPlayerTest {

    public TestTristan() {
        characterTemplate = "units/tristan";
    }
    private static final String NAME_PASSIVE_BUFF = "Courage";
    private static final String NAME_W_ATTACK_BUFF = "Cripple";
    private static final String NAME_W_SLOW_BUFF = "Crippled";

    @Test
    public void testP_Active() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy, new IsCharacterComponent());
        onLogicStart();

        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertTrue(hasBuff(character, NAME_PASSIVE_BUFF));
        assertEquals(9.2f, getMovementSpeed(character), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testP_Inactive() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));
        assertEquals(8, getMovementSpeed(character), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(10, 13));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertEquals(946.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(946.6667f, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(character, new HealthComponent(300));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        assertEquals(301.1882f, getHealth(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertTrue(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(914.6667f, getHealth(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Decay() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(915.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(915.6667f, getHealth(targetDummy2), EPSILON);
        assertTrue(isStunned(targetDummy1));
        assertTrue(isStunned(targetDummy2));
        tickSeconds(1);
        assertFalse(isStunned(targetDummy1));
        assertFalse(isStunned(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(10, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(isStunned(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(10, 5));
        int targetDummy2 = createTargetDummy(new Vector2f(15, 10));
        int targetDummy3 = createTargetDummy(new Vector2f(10, 15));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(889.4f, getHealth(targetDummy1), EPSILON);
        assertEquals(20, getX(targetDummy1), EPSILON);
        assertEquals(5, getY(targetDummy1), EPSILON);
        assertEquals(889.4f, getHealth(targetDummy2), EPSILON);
        assertEquals(25, getX(targetDummy2), EPSILON);
        assertEquals(10, getY(targetDummy2), EPSILON);
        assertEquals(889.4f, getHealth(targetDummy3), EPSILON);
        assertEquals(20, getX(targetDummy3), EPSILON);
        assertEquals(15, getY(targetDummy3), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        assertEquals(20, getX(targetDummy), EPSILON);
        assertEquals(10, getY(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }
}
