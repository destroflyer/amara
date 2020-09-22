package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent;
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
public class TestElvenArcher extends CommandingPlayerTest {

    public TestElvenArcher() {
        characterTemplate = "units/elven_archer";
    }
    private static final String NAME_PASSIVE_BUFF = "Elvish Bow";
    private static final String NAME_Q_ATTACK_BUFF = "Enchanted Arrowheads";
    private static final String NAME_Q_SLOW_BUFF = "Stuck Arrowhead";

    @Test
    public void testP() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));
        assertEquals(0.75f, getAttackSpeed(character), EPSILON);
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertEquals(1, getBuffStacks(character, NAME_PASSIVE_BUFF));
        assertEquals(0.7875f, getAttackSpeed(character), EPSILON);
        tickSeconds(10);
        assertEquals(8, getBuffStacks(character, NAME_PASSIVE_BUFF));
        assertEquals(1.05f, getAttackSpeed(character), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));
        assertEquals(0.75f, getAttackSpeed(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Hit_AutoAttack() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(933.3334f, getHealth(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Hit_W() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(866.6667f, getHealth(targetDummy), EPSILON);
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Hit_R() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(4);
        assertEquals(8000.0166f, getHealth(targetDummy1), EPSILON);
        assertEquals(8000.0166f, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Decay() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        assertEquals(50, getArmor(targetDummy), EPSILON);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(900, getHealth(targetDummy), EPSILON);
        assertEquals(40, getArmor(targetDummy), EPSILON);
        tickSeconds(3);
        assertEquals(50, getArmor(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        assertEquals(50, getArmor(targetDummy), EPSILON);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        assertEquals(50, getArmor(targetDummy), EPSILON);

        onLogicEnd(false, false);
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

    @Test
    public void testR_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(9800.002f, getHealth(targetDummy1), EPSILON);
        assertEquals(9800.002f, getHealth(targetDummy2), EPSILON);
        tickSeconds(1);
        assertEquals(9466.672f, getHealth(targetDummy1), EPSILON);
        assertEquals(9466.672f, getHealth(targetDummy2), EPSILON);
        tickSeconds(2);
        assertEquals(9000.01f, getHealth(targetDummy1), EPSILON);
        assertEquals(9000.01f, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(8, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(12, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(4);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }
}
