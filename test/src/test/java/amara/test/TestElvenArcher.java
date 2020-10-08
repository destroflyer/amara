package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusPercentageCriticalChanceComponent;
import amara.applications.ingame.entitysystem.components.buffs.ContinuousAttributesComponent;
import amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.UpgradeSpellCommand;
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
    private static final String NAME_W_PROJECTILE = "Piercing Arrow";
    private static final String NAME_E_ATTACK_BUFF = "I Did a Barrel Roll";
    private static final String NAME_E_WALK_SPEED_BUFF = "Do a Barrel Roll";
    private static final String NAME_R_PROJECTILE = "One of Many Arrows";

    @Test
    public void testP() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));
        assertEquals(0.77f, getAttackSpeed(character), EPSILON);
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertEquals(1, getBuffStacks(character, NAME_PASSIVE_BUFF));
        assertEquals(0.8085f, getAttackSpeed(character), EPSILON);
        tickSeconds(10);
        assertEquals(8, getBuffStacks(character, NAME_PASSIVE_BUFF));
        assertEquals(1.078f, getAttackSpeed(character), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_PASSIVE_BUFF));
        assertEquals(0.77f, getAttackSpeed(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Hit_AutoAttack() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertEquals(944.6667f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Hit_W() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(880, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Hit_R() {
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
        assertEquals(8400.01f, getHealth(targetDummy1), EPSILON);
        assertEquals(8400.01f, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(7, getWalkSpeed(targetDummy2), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Decay() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit_AutoAttack() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertEquals(934.6667f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit_W() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(870, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit_R() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(4);
        assertEquals(8100.0093f, getHealth(targetDummy1), EPSILON);
        assertEquals(8100.0093f, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));
        assertEquals(7, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(7, getWalkSpeed(targetDummy2), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Decay() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Hit_AutoAttack() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertEquals(944.6667f, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(6, getWalkSpeed(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Hit_W() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(880, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(6, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Hit_R() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(4);
        assertEquals(8400.01f, getHealth(targetDummy1), EPSILON);
        assertEquals(8400.01f, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));
        assertEquals(6, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(6, getWalkSpeed(targetDummy2), EPSILON);
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(targetDummy1, NAME_Q_SLOW_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_Q_SLOW_BUFF));
        assertEquals(10, getWalkSpeed(targetDummy1), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Decay() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_ATTACK_BUFF));
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_Q_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_W_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_W_PROJECTILE));
        assertEquals(900, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_W_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(1);
        assertNull(findEntity(NAME_W_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        assertEquals(50, getArmor(targetDummy), EPSILON);
        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_W_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_W_PROJECTILE));
        assertEquals(900, getHealth(targetDummy), EPSILON);
        assertEquals(40, getArmor(targetDummy), EPSILON);
        tickSeconds(3);
        assertEquals(50, getArmor(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        assertEquals(50, getArmor(targetDummy), EPSILON);
        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_W_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(1);
        assertNull(findEntity(NAME_W_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));
        assertEquals(50, getArmor(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        int projectile = findEntity(NAME_W_PROJECTILE);
        assertEquals(30, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_W_PROJECTILE));
        assertEquals(900, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_W, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        int projectile = findEntity(NAME_W_PROJECTILE);
        assertEquals(30, getMovementSpeed(projectile), EPSILON);
        tickSeconds(1.5f);
        assertNull(findEntity(NAME_W_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base() {
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        assertEquals(16, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        assertEquals(16, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertTrue(hasBuff(character, NAME_E_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_E_ATTACK_BUFF));
        assertEquals(918, getHealth(targetDummy), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Decay() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        assertEquals(16, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertTrue(hasBuff(character, NAME_E_ATTACK_BUFF));
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_E_ATTACK_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(0.5f);
        assertEquals(16, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertTrue(hasBuff(character, NAME_E_WALK_SPEED_BUFF));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_E_WALK_SPEED_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(13, findEntities(NAME_R_PROJECTILE).size());
        assertEquals(9800.002f, getHealth(targetDummy1), EPSILON);
        assertEquals(9800.002f, getHealth(targetDummy2), EPSILON);
        tickSeconds(1);
        assertEquals(9466.672f, getHealth(targetDummy1), EPSILON);
        assertEquals(9466.672f, getHealth(targetDummy2), EPSILON);
        tickSeconds(2);
        assertEquals(9000.01f, getHealth(targetDummy1), EPSILON);
        assertEquals(9000.01f, getHealth(targetDummy2), EPSILON);
        tickSeconds(2);
        assertEquals(0, findEntities(NAME_R_PROJECTILE).size());

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(8, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(12, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(25, findEntities(NAME_R_PROJECTILE).size());
        tickSeconds(3);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        tickSeconds(2);
        assertEquals(0, findEntities(NAME_R_PROJECTILE).size());

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        int guaranteedCritBuff = entityWorld.createEntity();
        int guaranteedCritBuffContinuousAttributes = entityWorld.createEntity();
        entityWorld.setComponent(guaranteedCritBuffContinuousAttributes, new BonusPercentageCriticalChanceComponent(1));
        entityWorld.setComponent(guaranteedCritBuff, new ContinuousAttributesComponent(guaranteedCritBuffContinuousAttributes));
        ApplyAddBuffsSystem.addBuff(entityWorld, character, guaranteedCritBuff);
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(13, findEntities(NAME_R_PROJECTILE).size());
        assertEquals(9599.998f, getHealth(targetDummy1), EPSILON);
        assertEquals(9599.998f, getHealth(targetDummy2), EPSILON);
        tickSeconds(1);
        assertEquals(8933.328f, getHealth(targetDummy1), EPSILON);
        assertEquals(8933.328f, getHealth(targetDummy2), EPSILON);
        tickSeconds(2);
        assertEquals(7999.9917f, getHealth(targetDummy1), EPSILON);
        assertEquals(7999.9917f, getHealth(targetDummy2), EPSILON);
        tickSeconds(2);
        assertEquals(0, findEntities(NAME_R_PROJECTILE).size());

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(8, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(12, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(25, findEntities(NAME_R_PROJECTILE).size());
        tickSeconds(3);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        tickSeconds(2);
        assertEquals(0, findEntities(NAME_R_PROJECTILE).size());

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 12));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(23, findEntities(NAME_R_PROJECTILE).size());
        assertEquals(9800.002f, getHealth(targetDummy1), EPSILON);
        assertEquals(9800.002f, getHealth(targetDummy2), EPSILON);
        tickSeconds(1);
        assertEquals(9466.672f, getHealth(targetDummy1), EPSILON);
        assertEquals(9466.672f, getHealth(targetDummy2), EPSILON);
        tickSeconds(2);
        assertEquals(9000.01f, getHealth(targetDummy1), EPSILON);
        assertEquals(9000.01f, getHealth(targetDummy2), EPSILON);
        tickSeconds(2);
        assertEquals(0, findEntities(NAME_R_PROJECTILE).size());

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(8, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(12, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(35, findEntities(NAME_R_PROJECTILE).size());
        tickSeconds(3);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        tickSeconds(2);
        assertEquals(0, findEntities(NAME_R_PROJECTILE).size());

        onLogicEnd(false, false);
    }
}
