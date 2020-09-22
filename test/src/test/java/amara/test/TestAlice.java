package amara.test;

import amara.applications.ingame.entitysystem.components.units.IsAutoAttackEnabledComponent;
import amara.applications.ingame.entitysystem.components.units.IsSpellsEnabledComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.network.messages.objects.commands.casting.CastPositionalSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestAlice extends CommandingPlayerTest {

    public TestAlice() {
        characterTemplate = "units/alice";
    }
    private static final String NAME_GLITTER = "Bubble Glitter";
    private static final String NAME_Q_BUFF = "Bubbled";
    private static final String NAME_W_BUFF = "Thunderstruck";
    private static final String NAME_E_BUFF = "Charmed";
    private static final String NAME_R_BUFF = "I'm Blue";

    @Test
    public void testP_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 7));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 13));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        tickSeconds(6);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy1), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy2), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testP_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(7);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(1);
        assertEquals(920, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_BUFF));
        assertTrue(isKnockuped(targetDummy));
        tickSeconds(1.5f);
        assertEquals(853.3333f, getHealth(targetDummy), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        tickSeconds(6);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8.5f));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 11.5f));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_W_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_W_BUFF));
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_PopQ() {
        int targetDummy = createTargetDummy(new Vector2f(20, 13));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_PopE() {
        int targetDummy = createTargetDummy(new Vector2f(20, 13));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8.5f));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 11.5f));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(1);
        assertEquals(880, getHealth(targetDummy1), EPSILON);
        assertEquals(880, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_E_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_E_BUFF));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(19, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(1.5f);
        assertFalse(hasBuff(targetDummy1, NAME_E_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(3.5f);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_E_BUFF));
        tickSeconds(6);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testR() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy1, new TeamComponent(1));
        entityWorld.setComponent(targetDummy2, new TeamComponent(1));
        entityWorld.setComponent(targetDummy1, new IsAutoAttackEnabledComponent());
        entityWorld.setComponent(targetDummy2, new IsAutoAttackEnabledComponent());
        entityWorld.setComponent(targetDummy1, new IsSpellsEnabledComponent());
        entityWorld.setComponent(targetDummy2, new IsSpellsEnabledComponent());
        entityWorld.setComponent(targetDummy1, new IsCharacterComponent());
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_R));
        tickSeconds(0.5f);
        // Buff not added yet
        assertFalse(hasBuff(character, NAME_R_BUFF));
        assertFalse(hasBuff(targetDummy1, NAME_R_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_R_BUFF));
        assertTrue(isTargetable(character));
        assertTrue(isTargetable(targetDummy1));
        assertTrue(isTargetable(targetDummy2));
        assertTrue(isVulnerable(character));
        assertTrue(isVulnerable(targetDummy1));
        assertTrue(isVulnerable(targetDummy2));
        assertTrue(isAutoAttackEnabled(character));
        assertTrue(isAutoAttackEnabled(targetDummy1));
        assertTrue(isAutoAttackEnabled(targetDummy2));
        assertTrue(isSpellsEnabled(character));
        assertTrue(isSpellsEnabled(targetDummy1));
        assertTrue(isSpellsEnabled(targetDummy2));
        tickSeconds(1);
        // Buff added
        assertTrue(hasBuff(character, NAME_R_BUFF));
        assertTrue(hasBuff(targetDummy1, NAME_R_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_R_BUFF));
        assertFalse(isTargetable(character));
        assertFalse(isTargetable(targetDummy1));
        assertTrue(isTargetable(targetDummy2));
        assertFalse(isVulnerable(character));
        assertFalse(isVulnerable(targetDummy1));
        assertTrue(isVulnerable(targetDummy2));
        assertFalse(isAutoAttackEnabled(character));
        assertFalse(isAutoAttackEnabled(targetDummy1));
        assertTrue(isAutoAttackEnabled(targetDummy2));
        assertFalse(isSpellsEnabled(character));
        assertFalse(isSpellsEnabled(targetDummy1));
        assertTrue(isSpellsEnabled(targetDummy2));
        tickSeconds(4);
        // Buff removed
        assertFalse(hasBuff(character, NAME_R_BUFF));
        assertFalse(hasBuff(targetDummy1, NAME_R_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_R_BUFF));
        assertTrue(isTargetable(character));
        assertTrue(isTargetable(targetDummy1));
        assertTrue(isTargetable(targetDummy2));
        assertTrue(isVulnerable(character));
        assertTrue(isVulnerable(targetDummy1));
        assertTrue(isVulnerable(targetDummy2));
        assertTrue(isAutoAttackEnabled(character));
        assertTrue(isAutoAttackEnabled(targetDummy1));
        assertTrue(isAutoAttackEnabled(targetDummy2));
        assertTrue(isSpellsEnabled(character));
        assertTrue(isSpellsEnabled(targetDummy1));
        assertTrue(isSpellsEnabled(targetDummy2));
        // Glitters spawned
        LinkedList<Integer> glitters = findEntities(NAME_GLITTER);
        assertEquals(2, glitters.size());
        int glitter1 = glitters.get(0);
        assertEquals(10, getX(glitter1), EPSILON);
        assertEquals(10, getY(glitter1), EPSILON);
        int glitter2 = glitters.get(1);
        assertEquals(15, getX(glitter2), EPSILON);
        assertEquals(10, getY(glitter2), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }
}
