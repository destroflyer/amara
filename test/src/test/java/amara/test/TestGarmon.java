package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.ManaComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.UpgradeSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TestGarmon extends CommandingPlayerTest {

    public TestGarmon() {
        characterTemplate = "units/garmon";
    }
    private static final String NAME_PASSIVE_BUFF = "Stargazer";
    private static final String NAME_Q_PROJECTILE = "Energetic Mass";
    private static final String NAME_W_BUFF = "Lasered";
    private static final String NAME_R_PROJECTILE = "Energetic Missile";
    private static final String NAME_R_OBJECT = "Energetic Missile Impact";

    @Test
    public void testP() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(character, new ManaComponent(100));
        onLogicStart();

        assertTrue(hasBuff(character, NAME_PASSIVE_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertEquals(153.41f, getMana(character), EPSILON);
        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));
        queueCommand(new StopCommand());
        tickSeconds(8);
        assertTrue(hasBuff(character, NAME_PASSIVE_BUFF));

        onLogicEnd(true, true);
    }

    @Test
    public void testQ_Base_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy), EPSILON);
        assertTrue(isBinded(targetDummy));
        tickSeconds(1);
        assertFalse(isBinded(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(1.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy), EPSILON);
        assertTrue(isBinded(targetDummy));
        tickSeconds(1);
        assertTrue(isBinded(targetDummy));
        tickSeconds(0.5f);
        assertFalse(isBinded(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(1.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(25, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(0.5f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));;
        assertEquals(940, getHealth(targetDummy1), EPSILON);
        assertTrue(isFullHealth(targetDummy2));
        assertTrue(isBinded(targetDummy1));
        assertFalse(isBinded(targetDummy2));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy1), EPSILON);
        assertEquals(940, getHealth(targetDummy2), EPSILON);
        assertTrue(isBinded(targetDummy1));
        assertTrue(isBinded(targetDummy2));
        tickSeconds(0.75f);
        assertFalse(isBinded(targetDummy1));
        assertTrue(isBinded(targetDummy2));
        tickSeconds(1);
        assertFalse(isBinded(targetDummy1));
        assertFalse(isBinded(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(1.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(960, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_BUFF));
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_BUFF));
        tickSeconds(2);
        assertEquals(800, getHealth(targetDummy), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        tickSeconds(2);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        assertEquals(50, getMagicResistance(targetDummy), EPSILON);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(957.1429f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(40, getMagicResistance(targetDummy), EPSILON);
        tickSeconds(1);
        assertEquals(785.7144f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(40, getMagicResistance(targetDummy), EPSILON);
        tickSeconds(2);
        assertEquals(785.7144f, getHealth(targetDummy), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(50, getMagicResistance(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        assertEquals(50, getMagicResistance(targetDummy1), EPSILON);
        assertEquals(50, getMagicResistance(targetDummy2), EPSILON);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertEquals(50, getMagicResistance(targetDummy1), EPSILON);
        assertEquals(50, getMagicResistance(targetDummy2), EPSILON);
        tickSeconds(2);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertEquals(50, getMagicResistance(targetDummy1), EPSILON);
        assertEquals(50, getMagicResistance(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(960f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(8.5, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(8.5, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(2);
        assertEquals(800, getHealth(targetDummy), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);
        tickSeconds(2);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertFalse(hasBuff(targetDummy1, NAME_W_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_W_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base() {
        entityWorld.setComponent(character, new DirectionComponent(new Vector2f(-1, 0)));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_E));
        tickSeconds(0.6f);
        assertEquals(18, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1() {
        entityWorld.setComponent(character, new DirectionComponent(new Vector2f(-1, 0)));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_E));
        tickSeconds(0.6f);
        assertEquals(20, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2() {
        entityWorld.setComponent(character, new DirectionComponent(new Vector2f(-1, 0)));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_E));
        tickSeconds(0.4f);
        assertEquals(18, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(60, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(63, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(2);
        assertNotNull(findEntity(NAME_R_PROJECTILE));
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertEquals(800, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(60, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(63, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(2);
        assertNotNull(findEntity(NAME_R_PROJECTILE));
        tickSeconds(5);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(60, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(63, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(2);
        assertNotNull(findEntity(NAME_R_PROJECTILE));
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        tickSeconds(1);
        assertEquals(600, getHealth(targetDummy1), EPSILON);
        assertEquals(600, getHealth(targetDummy2), EPSILON);
        LinkedList<Integer> objects = findEntities_SortByX(NAME_R_OBJECT);
        assertEquals(2, objects.size());
        int object1 = objects.get(0);
        assertEquals(60, getX(object1), EPSILON);
        assertEquals(10, getY(object1), EPSILON);
        int object2 = objects.get(1);
        assertEquals(63, getX(object2), EPSILON);
        assertEquals(10, getY(object2), EPSILON);
        tickSeconds(1);
        assertNull(findEntity(NAME_R_OBJECT));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(60, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(63, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(2);
        assertNotNull(findEntity(NAME_R_PROJECTILE));
        tickSeconds(5);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertNull(findEntity(NAME_R_OBJECT));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(30, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(63, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(2);
        assertNotNull(findEntity(NAME_R_PROJECTILE));
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertTrue(isFullHealth(targetDummy2));
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertEquals(633.3333f, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(30, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(63, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(2);
        assertNotNull(findEntity(NAME_R_PROJECTILE));
        tickSeconds(5);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }
}
