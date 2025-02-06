package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.UpgradeSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTristan extends CommandingPlayerTest {

    public TestTristan() {
        characterTemplate = "units/tristan";
    }
    private static final String NAME_PASSIVE_BUFF = "Courage";
    private static final String NAME_Q_OBJECT = "Tristan Spin Impact";
    private static final String NAME_W_ATTACK_BUFF = "Cripple";
    private static final String NAME_W_SLOW_BUFF = "Crippled";
    private static final String NAME_E_OBJECT = "Tristan Smash Impact";
    private static final String NAME_R_OBJECT = "Tristan Ult Impact";

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
    public void testQ_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(10, 13));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_OBJECT));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_OBJECT));
        assertEquals(946.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(946.6667f, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_OBJECT));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_OBJECT));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(10, 7));
        int targetDummy2 = createTargetDummy(new Vector2f(10, 13));
        int targetDummy3 = createTargetDummy(new Vector2f(13, 10));
        entityWorld.setComponent(targetDummy3, new IsCharacterComponent());
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_OBJECT));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_OBJECT));
        assertEquals(920, getHealth(targetDummy1), EPSILON);
        assertEquals(920, getHealth(targetDummy2), EPSILON);
        assertEquals(946.6667f, getHealth(targetDummy3), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_OBJECT));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_OBJECT));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(10, 7));
        int targetDummy2 = createTargetDummy(new Vector2f(10, 13));
        int targetDummy3 = createTargetDummy(new Vector2f(13, 10));
        entityWorld.setComponent(targetDummy1, new IsCharacterComponent());
        entityWorld.setComponent(targetDummy2, new IsCharacterComponent());
        entityWorld.setComponent(character, new HealthComponent(300));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_OBJECT));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_OBJECT));
        assertEquals(946.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(946.6667f, getHealth(targetDummy2), EPSILON);
        assertEquals(946.6667f, getHealth(targetDummy3), EPSILON);
        assertEquals(504.2929f, getHealth(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(character, new HealthComponent(300));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_OBJECT));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertEquals(304.2929f, getHealth(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertEquals(892.6667f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(5, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(1);
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Decay() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        tickSeconds(5);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertEquals(892.6667f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(5, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(0.25f);
        assertEquals(872.6667f, getHealth(targetDummy), EPSILON);
        tickSeconds(0.25f);
        assertEquals(852.6667f, getHealth(targetDummy), EPSILON);
        tickSeconds(0.25f);
        assertEquals(832.6667f, getHealth(targetDummy), EPSILON);
        tickSeconds(0.25f);
        assertEquals(812.6667f, getHealth(targetDummy), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Decay() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        tickSeconds(5);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertEquals(892.6667f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(2, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(1);
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Decay() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        tickSeconds(5);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_E_OBJECT));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_OBJECT));
        assertEquals(904.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(904.6667f, getHealth(targetDummy2), EPSILON);
        assertTrue(isStunned(targetDummy1));
        assertTrue(isStunned(targetDummy2));
        tickSeconds(1.25f);
        assertFalse(isStunned(targetDummy1));
        assertFalse(isStunned(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(10, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_E_OBJECT));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertFalse(isStunned(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_E_OBJECT));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_OBJECT));
        assertEquals(904.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(904.6667f, getHealth(targetDummy2), EPSILON);
        assertTrue(isStunned(targetDummy1));
        assertTrue(isStunned(targetDummy2));
        tickSeconds(1.25f);
        assertTrue(isStunned(targetDummy1));
        assertTrue(isStunned(targetDummy2));
        tickSeconds(0.25f);
        assertFalse(isStunned(targetDummy1));
        assertFalse(isStunned(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(10, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_E_OBJECT));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertFalse(isStunned(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_E_OBJECT));
        assertEquals(250, getTotalShieldAmount(character), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_OBJECT));
        assertEquals(0, getTotalShieldAmount(character), EPSILON);
        assertEquals(904.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(904.6667f, getHealth(targetDummy2), EPSILON);
        assertTrue(isStunned(targetDummy1));
        assertTrue(isStunned(targetDummy2));
        tickSeconds(1.25f);
        assertFalse(isStunned(targetDummy1));
        assertFalse(isStunned(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(10, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_E_OBJECT));
        assertEquals(250, getTotalShieldAmount(character), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_E_OBJECT));
        assertEquals(0, getTotalShieldAmount(character), EPSILON);
        assertTrue(isFullHealth(targetDummy));
        assertFalse(isStunned(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(10, 5));
        int targetDummy2 = createTargetDummy(new Vector2f(15, 10));
        int targetDummy3 = createTargetDummy(new Vector2f(10, 15));
        int targetDummy4 = createTargetDummy(new Vector2f(10, 18));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_R_OBJECT));
        tickSeconds(0.75f);
        assertEquals(882.8f, getHealth(targetDummy1), EPSILON);
        assertEquals(20, getX(targetDummy1), EPSILON);
        assertEquals(5, getY(targetDummy1), EPSILON);
        assertEquals(882.8f, getHealth(targetDummy2), EPSILON);
        assertEquals(25, getX(targetDummy2), EPSILON);
        assertEquals(10, getY(targetDummy2), EPSILON);
        assertEquals(882.8f, getHealth(targetDummy3), EPSILON);
        assertEquals(20, getX(targetDummy3), EPSILON);
        assertEquals(15, getY(targetDummy3), EPSILON);
        assertTrue(isFullHealth(targetDummy4));
        assertEquals(10, getX(targetDummy4), EPSILON);
        assertEquals(18, getY(targetDummy4), EPSILON);
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_R_OBJECT));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_R_OBJECT));
        tickSeconds(1);
        assertNull(findEntity(NAME_R_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertEquals(20, getX(targetDummy), EPSILON);
        assertEquals(10, getY(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(10, 2));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 10));
        int targetDummy3 = createTargetDummy(new Vector2f(10, 18));
        int targetDummy4 = createTargetDummy(new Vector2f(10, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_R_OBJECT));
        tickSeconds(0.75f);
        assertEquals(882.8f, getHealth(targetDummy1), EPSILON);
        assertEquals(20, getX(targetDummy1), EPSILON);
        assertEquals(2, getY(targetDummy1), EPSILON);
        assertEquals(882.8f, getHealth(targetDummy2), EPSILON);
        assertEquals(28, getX(targetDummy2), EPSILON);
        assertEquals(10, getY(targetDummy2), EPSILON);
        assertEquals(882.8f, getHealth(targetDummy3), EPSILON);
        assertEquals(20, getX(targetDummy3), EPSILON);
        assertEquals(18, getY(targetDummy3), EPSILON);
        assertTrue(isFullHealth(targetDummy4));
        assertEquals(10, getX(targetDummy4), EPSILON);
        assertEquals(20, getY(targetDummy4), EPSILON);
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_R_OBJECT));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_R_OBJECT));
        tickSeconds(1);
        assertNull(findEntity(NAME_R_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertEquals(20, getX(targetDummy), EPSILON);
        assertEquals(10, getY(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(10, 5));
        int targetDummy2 = createTargetDummy(new Vector2f(15, 10));
        int targetDummy3 = createTargetDummy(new Vector2f(10, 15));
        int targetDummy4 = createTargetDummy(new Vector2f(10, 18));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_R_OBJECT));
        tickSeconds(0.75f);
        assertEquals(882.8f, getHealth(targetDummy1), EPSILON);
        assertEquals(20, getX(targetDummy1), EPSILON);
        assertEquals(5, getY(targetDummy1), EPSILON);
        assertEquals(882.8f, getHealth(targetDummy2), EPSILON);
        assertEquals(25, getX(targetDummy2), EPSILON);
        assertEquals(10, getY(targetDummy2), EPSILON);
        assertEquals(882.8f, getHealth(targetDummy3), EPSILON);
        assertEquals(20, getX(targetDummy3), EPSILON);
        assertEquals(15, getY(targetDummy3), EPSILON);
        assertTrue(isFullHealth(targetDummy4));
        assertEquals(10, getX(targetDummy4), EPSILON);
        assertEquals(18, getY(targetDummy4), EPSILON);
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_R_OBJECT));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_R_OBJECT));
        tickSeconds(1);
        assertNull(findEntity(NAME_R_OBJECT));
        assertTrue(isFullHealth(targetDummy));
        assertEquals(20, getX(targetDummy), EPSILON);
        assertEquals(10, getY(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }
}
