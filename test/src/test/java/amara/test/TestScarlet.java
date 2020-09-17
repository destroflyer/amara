package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.AggroTargetComponent;
import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent;
import amara.applications.ingame.entitysystem.components.units.IsAutoAttackEnabledComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestScarlet extends CommandingPlayerTest {

    public TestScarlet() {
        characterTemplate = "units/scarlet";
    }
    private static final String BUFF_NAME_PASSIVE = "Mark of the Assasin";

    @Test
    public void testP_Triggered() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, BUFF_NAME_PASSIVE));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(targetDummy, BUFF_NAME_PASSIVE));
        assertEquals(880, getHealth(targetDummy), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, false);
    }

    @Test
    public void testP_NotTriggered() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, BUFF_NAME_PASSIVE));
        tickSeconds(5);
        assertFalse(hasBuff(targetDummy, BUFF_NAME_PASSIVE));
        assertEquals(940, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(940, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, BUFF_NAME_PASSIVE));
        tickSeconds(5);
        assertFalse(hasBuff(targetDummy, BUFF_NAME_PASSIVE));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Blocking() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(10));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(0.75f);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(500, getHealth(character), EPSILON);
        assertTrue(hasBuff(targetDummy, BUFF_NAME_PASSIVE));
        tickSeconds(5);
        assertFalse(hasBuff(targetDummy, BUFF_NAME_PASSIVE));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_NotBlocking() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Hit_NoReset() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(21, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, BUFF_NAME_PASSIVE));
        assertTrue(hasBuff(targetDummy2, BUFF_NAME_PASSIVE));
        assertNotEquals(0, getRemainingCooldown(character, SPELL_INDEX_E), EPSILON);
        tickSeconds(5);
        assertFalse(hasBuff(targetDummy1, BUFF_NAME_PASSIVE));
        assertFalse(hasBuff(targetDummy2, BUFF_NAME_PASSIVE));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Hit_Reset() {
        int targetDummy = createTargetDummy(new Vector2f(14, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(21, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(840, getHealth(targetDummy), EPSILON);
        assertEquals(0, getRemainingCooldown(character, SPELL_INDEX_E), EPSILON);
        assertTrue(hasBuff(targetDummy, BUFF_NAME_PASSIVE));
        tickSeconds(5);
        assertFalse(hasBuff(targetDummy, BUFF_NAME_PASSIVE));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Hit_ExecuteAndResetE() {
        int targetDummy1 = createTargetDummy(new Vector2f(23, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(23, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummy1, new HealthComponent(1000));
        entityWorld.setComponent(targetDummy2, new HealthComponent(1000));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 1)));
        tickSeconds(0.5f);
        resetCooldown(character, SPELL_INDEX_Q);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, -1)));
        tickSeconds(0.5f);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertFalse(isAlive(targetDummy1));
        assertFalse(isAlive(targetDummy2));
        assertEquals(0, getRemainingCooldown(character, SPELL_INDEX_E), EPSILON);

        onLogicEnd(true, false);
    }

    @Test
    public void testR_Hit_NoExecuteAndResetE() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertEquals(800, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, BUFF_NAME_PASSIVE));
        assertTrue(hasBuff(targetDummy2, BUFF_NAME_PASSIVE));
        tickSeconds(5);
        assertFalse(hasBuff(targetDummy1, BUFF_NAME_PASSIVE));
        assertFalse(hasBuff(targetDummy2, BUFF_NAME_PASSIVE));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }
}
