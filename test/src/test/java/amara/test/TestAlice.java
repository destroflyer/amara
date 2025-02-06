package amara.test;

import amara.applications.ingame.entitysystem.components.units.IsAutoAttackEnabledComponent;
import amara.applications.ingame.entitysystem.components.units.IsSpellsEnabledComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.network.messages.objects.commands.UpgradeSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastPositionalSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TestAlice extends CommandingPlayerTest {

    public TestAlice() {
        characterTemplate = "units/alice";
    }
    private static final String NAME_GLITTER = "Bubble Glitter";
    private static final String NAME_Q_PROJECTILE = "White Bubble";
    private static final String NAME_Q_BUFF = "Bubbled";
    private static final String NAME_W_BUFF = "Thunderstruck";
    private static final String NAME_W_OBJECT = "Thunder";
    private static final String NAME_E_PROJECTILE = "Pink Bubble";
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
    public void testQ_Base_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(20, getMovementSpeed(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
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
    public void testQ_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(20, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertEquals(20, getX(projectile), EPSILON);
        assertEquals(10, getY(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        tickSeconds(6);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(20, getMovementSpeed(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(920, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_BUFF));
        assertTrue(isKnockuped(targetDummy));
        tickSeconds(1.5f);
        assertEquals(920, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_BUFF));
        assertTrue(isKnockuped(targetDummy));
        tickSeconds(0.25f);
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
    public void testQ_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(20, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertEquals(20, getX(projectile), EPSILON);
        assertEquals(10, getY(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        tickSeconds(6);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
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
    public void testQ_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertEquals(20, getX(projectile), EPSILON);
        assertEquals(10, getY(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));
        assertFalse(isKnockuped(targetDummy));
        tickSeconds(6);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8.5f));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 11.5f));
        int targetDummy3 = createTargetDummy(new Vector2f(24, 10));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(0.25f);
        int object = findEntity(NAME_W_OBJECT);
        assertEquals(20, getX(object), EPSILON);
        assertEquals(10, getY(object), EPSILON);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertTrue(isFullHealth(targetDummy3));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);
        tickSeconds(0.5f);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(isFullHealth(targetDummy3));
        assertTrue(hasBuff(targetDummy1, NAME_W_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(7, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);
        tickSeconds(0.75f);
        assertNull(findEntity(NAME_W_OBJECT));
        tickSeconds(0.75f);
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int object = findEntity(NAME_W_OBJECT);
        assertEquals(20, getX(object), EPSILON);
        assertEquals(10, getY(object), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_W_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_PopQ() {
        int targetDummy = createTargetDummy(new Vector2f(20, 16));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 12.5f)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(12.5f, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_PopE() {
        int targetDummy = createTargetDummy(new Vector2f(20, 16));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 12.5f)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(12.5f, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 6));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 14));
        int targetDummy3 = createTargetDummy(new Vector2f(28, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(0.25f);
        int object = findEntity(NAME_W_OBJECT);
        assertEquals(20, getX(object), EPSILON);
        assertEquals(10, getY(object), EPSILON);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertTrue(isFullHealth(targetDummy3));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);
        tickSeconds(0.5f);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(isFullHealth(targetDummy3));
        assertTrue(hasBuff(targetDummy1, NAME_W_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(7, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);
        tickSeconds(0.75f);
        assertNull(findEntity(NAME_W_OBJECT));
        tickSeconds(0.75f);
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int object = findEntity(NAME_W_OBJECT);
        assertEquals(20, getX(object), EPSILON);
        assertEquals(10, getY(object), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_W_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_PopQ() {
        int targetDummy = createTargetDummy(new Vector2f(20, 18));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 14.5f)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(14.5f, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_PopE() {
        int targetDummy = createTargetDummy(new Vector2f(20, 18));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 14.5f)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(14.5f, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8.5f));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 11.5f));
        int targetDummy3 = createTargetDummy(new Vector2f(24, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(0.25f);
        int object = findEntity(NAME_W_OBJECT);
        assertEquals(20, getX(object), EPSILON);
        assertEquals(10, getY(object), EPSILON);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertTrue(isFullHealth(targetDummy3));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);
        tickSeconds(0.5f);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(isFullHealth(targetDummy3));
        assertTrue(hasBuff(targetDummy1, NAME_W_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(4, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(4, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);
        tickSeconds(0.75f);
        assertNull(findEntity(NAME_W_OBJECT));
        tickSeconds(0.75f);
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy3), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int object = findEntity(NAME_W_OBJECT);
        assertEquals(20, getX(object), EPSILON);
        assertEquals(10, getY(object), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_W_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_PopQ() {
        int targetDummy = createTargetDummy(new Vector2f(20, 16));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 12.5f)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(12.5f, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_PopE() {
        int targetDummy = createTargetDummy(new Vector2f(20, 16));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 12.5f)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(20, 10)));
        tickSeconds(1);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(12.5f, getY(glitter), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8.5f));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 11.5f));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        assertNotNull(findEntity(NAME_E_PROJECTILE));
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_E_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_PROJECTILE));
        assertEquals(880, getHealth(targetDummy1), EPSILON);
        assertEquals(880, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_E_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_E_BUFF));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(19, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy1, NAME_E_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(0.5f);
        assertFalse(hasBuff(targetDummy1, NAME_E_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(3.5f);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_E_PROJECTILE);
        assertEquals(20, getX(projectile), EPSILON);
        assertEquals(10, getY(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_E_BUFF));
        tickSeconds(6);
        assertNull(findEntity(NAME_E_PROJECTILE));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8.5f));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 11.5f));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        assertNotNull(findEntity(NAME_E_PROJECTILE));
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_E_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_E_BUFF));
        assertEquals(0, getIncomingDamageAmplification(targetDummy1), EPSILON);
        assertEquals(0, getIncomingDamageAmplification(targetDummy2), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_PROJECTILE));
        assertEquals(880, getHealth(targetDummy1), EPSILON);
        assertEquals(880, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_E_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_E_BUFF));
        assertEquals(0.1f, getIncomingDamageAmplification(targetDummy1), EPSILON);
        assertEquals(0.1f, getIncomingDamageAmplification(targetDummy2), EPSILON);
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(19, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy1, NAME_E_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(0.5f);
        assertFalse(hasBuff(targetDummy1, NAME_E_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_E_BUFF));
        assertEquals(0, getIncomingDamageAmplification(targetDummy1), EPSILON);
        assertEquals(0, getIncomingDamageAmplification(targetDummy2), EPSILON);
        tickSeconds(3.5f);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_E_PROJECTILE);
        assertEquals(20, getX(projectile), EPSILON);
        assertEquals(10, getY(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_E_BUFF));
        assertEquals(0, getIncomingDamageAmplification(targetDummy), EPSILON);
        tickSeconds(6);
        assertNull(findEntity(NAME_E_PROJECTILE));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8.5f));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 11.5f));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        assertNotNull(findEntity(NAME_E_PROJECTILE));
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_E_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_PROJECTILE));
        assertEquals(880, getHealth(targetDummy1), EPSILON);
        assertEquals(880, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_E_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_E_BUFF));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(19, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(1.25f);
        assertTrue(hasBuff(targetDummy1, NAME_E_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(0.5f);
        assertFalse(hasBuff(targetDummy1, NAME_E_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_E_BUFF));
        tickSeconds(3.25f);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_E_PROJECTILE);
        assertEquals(20, getX(projectile), EPSILON);
        assertEquals(10, getY(projectile), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(hasBuff(targetDummy, NAME_E_BUFF));
        assertEquals(0, getIncomingDamageAmplification(targetDummy), EPSILON);
        tickSeconds(6);
        assertNull(findEntity(NAME_E_PROJECTILE));
        int glitter = findEntity(NAME_GLITTER);
        assertEquals(20, getX(glitter), EPSILON);
        assertEquals(10, getY(glitter), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GLITTER));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base() {
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
        tickSeconds(0.1f);
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
        LinkedList<Integer> glitters = findEntities_SortByX(NAME_GLITTER);
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

    @Test
    public void testR_Upgrade1() {
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

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_R));
        tickSeconds(0.1f);
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
        tickSeconds(0.75f);
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
        LinkedList<Integer> glitters = findEntities_SortByX(NAME_GLITTER);
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

    @Test
    public void testR_Upgrade2() {
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

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_R));
        tickSeconds(0.1f);
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
        assertEquals(8, getWalkSpeed(character), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
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
        assertEquals(10, getWalkSpeed(character), EPSILON);
        assertEquals(12.5, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(12.5, getWalkSpeed(targetDummy1), EPSILON);
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
        assertEquals(8, getWalkSpeed(character), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        // Glitters spawned
        LinkedList<Integer> glitters = findEntities_SortByX(NAME_GLITTER);
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
