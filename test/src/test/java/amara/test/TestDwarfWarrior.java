package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent;
import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.AggroTargetComponent;
import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent;
import amara.applications.ingame.entitysystem.components.units.IsAutoAttackEnabledComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastPositionalSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSingleTargetSpellCommand;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestDwarfWarrior extends CommandingPlayerTest {

    public TestDwarfWarrior() {
        characterTemplate = "units/dwarf_warrior";
    }
    private static final String NAME_Q_ATTACK_BUFF = "Stop! Hammer Time";
    private static final String NAME_W_STACKS_BUFF = "Urgent Feast";
    private static final String NAME_W_CONSUME_BUFF = "Full Stomach";

    @Test
    public void testP() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        assertEquals(0, getTotalShieldAmount(character), EPSILON);
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertEquals(50, getTotalShieldAmount(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, true);
    }

    @Test
    public void testQ_NoStack() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        assertEquals(28, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertEquals(914.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(28, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, true);
    }

    @Test
    public void testQ_Stack() {
        onLogicStart();

        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        entityWorld.setComponent(targetDummy, new HealthComponent(1));
        assertEquals(28, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertFalse(isAlive(targetDummy));
        assertEquals(29, getArmor(character), EPSILON);
        assertEquals(31, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, true);
    }

    @Test
    public void testQ_Decay() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(9);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(20));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        entityWorld.removeComponent(character, IsAutoAttackEnabledComponent.class);
        onLogicStart();

        assertEquals(0, getBuffStacks(character, NAME_W_STACKS_BUFF));
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(15);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(10, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertEquals(357.2986f, getHealth(character), EPSILON);
        // Consume #1 --> Successful (10 stacks -> 5 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(457.8735f, getHealth(character), EPSILON);
        assertEquals(5, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #2 --> Successful (5 stacks -> 0 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(559.6176f, getHealth(character), EPSILON);
        assertEquals(0, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #3 --> Unsuccessful (Not enough stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(561.3616f, getHealth(character), EPSILON);
        tickSeconds(5);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastSingleTargetSpellCommand(SPELL_INDEX_E, targetDummy));
        tickSeconds(1);
        assertEquals(20, getX(targetDummy), EPSILON);
        assertEquals(10, getY(targetDummy), EPSILON);
        assertEquals(812.1333f, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1.5f);
        assertEquals(25, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy1), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy2), EPSILON);
        assertTrue(isKnockuped(targetDummy1));
        assertTrue(isKnockuped(targetDummy2));
        tickSeconds(1);
        assertFalse(isKnockuped(targetDummy1));
        assertFalse(isKnockuped(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_NoHit() {
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1.5f);

        onLogicEnd(false, false);
    }
}
