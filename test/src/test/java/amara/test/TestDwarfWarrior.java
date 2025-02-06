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
import amara.applications.ingame.network.messages.objects.commands.UpgradeSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastPositionalSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSingleTargetSpellCommand;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testQ_Base_NoStack() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertEquals(892.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, true);
    }

    @Test
    public void testQ_Base_Stack() {
        onLogicStart();

        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        entityWorld.setComponent(targetDummy, new HealthComponent(1));
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertFalse(isAlive(targetDummy));
        assertEquals(62, getArmor(character), EPSILON);
        assertEquals(31, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, true);
    }

    @Test
    public void testQ_Base_Decay() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(9);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_NoStack() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertEquals(892.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(true, true);
    }

    @Test
    public void testQ_Upgrade1_Stack() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        entityWorld.setComponent(targetDummy, new HealthComponent(1));
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertFalse(isAlive(targetDummy));
        assertEquals(62.5f, getArmor(character), EPSILON);
        assertEquals(31, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(true, true);
    }

    @Test
    public void testQ_Upgrade1_Decay() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(9);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(true, true);
    }

    @Test
    public void testQ_Upgrade2_NoStack() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertEquals(892.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(true, true);
    }

    @Test
    public void testQ_Upgrade2_Stack() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        entityWorld.setComponent(targetDummy, new HealthComponent(1));
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        assertFalse(isAlive(targetDummy));
        assertEquals(62, getArmor(character), EPSILON);
        assertEquals(31.5f, getMagicResistance(character), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(true, true);
    }

    @Test
    public void testQ_Upgrade2_Decay() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(9);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(true, true);
    }

    @Test
    public void testW_Base() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(50));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        entityWorld.removeComponent(character, IsAutoAttackEnabledComponent.class);
        onLogicStart();

        assertEquals(0, getBuffStacks(character, NAME_W_STACKS_BUFF));
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(15);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(10, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertEquals(974.7349f, getHealth(character), EPSILON);
        // Consume #1 --> Successful (10 stacks -> 5 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1076.1338f, getHealth(character), EPSILON);
        assertEquals(5, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #2 --> Successful (5 stacks -> 0 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1180.3772f, getHealth(character), EPSILON);
        assertEquals(0, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #3 --> Unsuccessful (Not enough stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1184.6206f, getHealth(character), EPSILON);
        tickSeconds(5);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(50));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        entityWorld.removeComponent(character, IsAutoAttackEnabledComponent.class);
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        assertEquals(0, getBuffStacks(character, NAME_W_STACKS_BUFF));
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(20);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(15, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertEquals(833.46295f, getHealth(character), EPSILON);
        // Consume #1 --> Successful (15 stacks -> 10 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(934.8637f, getHealth(character), EPSILON);
        assertEquals(10, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #2 --> Successful (10 stacks -> 5 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1039.1108f, getHealth(character), EPSILON);
        assertEquals(5, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #3 --> Successful (5 stacks -> 0 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1143.3542f, getHealth(character), EPSILON);
        assertEquals(0, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #4 --> Unsuccessful (Not enough stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1147.5977f, getHealth(character), EPSILON);
        tickSeconds(5);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));

        onLogicEnd(true, true);
    }

    @Test
    public void testW_Upgrade2() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(50));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        entityWorld.removeComponent(character, IsAutoAttackEnabledComponent.class);
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        assertEquals(0, getBuffStacks(character, NAME_W_STACKS_BUFF));
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(15);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(10, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertEquals(974.7349f, getHealth(character), EPSILON);
        // Consume #1 --> Successful (10 stacks -> 5 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1076.1338f, getHealth(character), EPSILON);
        assertEquals(6, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #2 --> Successful (6 stacks -> 2 stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1180.3772f, getHealth(character), EPSILON);
        assertEquals(2, getBuffStacks(character, NAME_W_STACKS_BUFF));
        assertTrue(hasBuff(character, NAME_W_CONSUME_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));
        resetCooldown(character, SPELL_INDEX_W);
        // Consume #3 --> Unsuccessful (Not enough stacks)
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.5f);
        assertEquals(1184.6206f, getHealth(character), EPSILON);
        tickSeconds(5);
        assertFalse(hasBuff(character, NAME_W_CONSUME_BUFF));

        onLogicEnd(true, true);
    }

    @Test
    public void testE_Base() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastSingleTargetSpellCommand(SPELL_INDEX_E, targetDummy));
        tickSeconds(1);
        assertEquals(20, getX(targetDummy), EPSILON);
        assertEquals(10, getY(targetDummy), EPSILON);
        assertEquals(798.9334f, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(13, 12));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(20, getX(targetDummy1), EPSILON);
        assertEquals(8, getY(targetDummy1), EPSILON);
        assertEquals(20, getX(targetDummy2), EPSILON);
        assertEquals(12, getY(targetDummy2), EPSILON);
        assertEquals(798.9334f, getHealth(targetDummy1), EPSILON);
        assertEquals(798.9334f, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(13, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(13, getX(targetDummy), EPSILON);
        assertEquals(20, getY(targetDummy), EPSILON);
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastSingleTargetSpellCommand(SPELL_INDEX_E, targetDummy));
        tickSeconds(1);
        assertEquals(23, getX(targetDummy), EPSILON);
        assertEquals(10, getY(targetDummy), EPSILON);
        assertEquals(798.9334f, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1);
        assertEquals(25, getMovementSpeed(character), EPSILON);
        tickSeconds(0.5f);
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
    public void testR_Base_NoHit() {
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1);
        assertEquals(25, getMovementSpeed(character), EPSILON);
        tickSeconds(0.5f);
        assertEquals(25, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        int targetDummy3 = createTargetDummy(new Vector2f(25, 15));
        int targetDummy4 = createTargetDummy(new Vector2f(30, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1);
        assertEquals(25, getMovementSpeed(character), EPSILON);
        tickSeconds(0.5f);
        assertEquals(25, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy1), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy2), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy3), EPSILON);
        assertEquals(933.3333f, getHealth(targetDummy4), EPSILON);
        assertTrue(isKnockuped(targetDummy1));
        assertTrue(isKnockuped(targetDummy2));
        assertTrue(isKnockuped(targetDummy3));
        assertTrue(isKnockuped(targetDummy4));
        tickSeconds(1);
        assertFalse(isKnockuped(targetDummy1));
        assertFalse(isKnockuped(targetDummy2));
        assertFalse(isKnockuped(targetDummy3));
        assertFalse(isKnockuped(targetDummy4));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_NoHit() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1);
        assertEquals(25, getMovementSpeed(character), EPSILON);
        tickSeconds(0.5f);
        assertEquals(25, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(15, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1);
        assertEquals(35, getMovementSpeed(character), EPSILON);
        tickSeconds(0.5f);
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
    public void testR_Upgrade2_NoHit() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(25, 10)));
        tickSeconds(1);
        assertEquals(35, getMovementSpeed(character), EPSILON);
        tickSeconds(0.5f);
        assertEquals(25, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }
}
