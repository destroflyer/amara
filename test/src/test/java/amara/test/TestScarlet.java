package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.AggroTargetComponent;
import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent;
import amara.applications.ingame.entitysystem.components.units.IsAutoAttackEnabledComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.UpgradeSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestScarlet extends CommandingPlayerTest {

    public TestScarlet() {
        characterTemplate = "units/scarlet";
    }
    private static final String NAME_PASSIVE_BUFF = "Mark of the Assasin";
    private static final String NAME_Q_PROJECTILE = "Kunai";
    private static final String NAME_W_OBJECT = "Kunai Barrier";
    private static final String NAME_W_WALK_SPEED_BUFF = "In the Vortex";

    @Test
    public void testP_Triggered() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        assertEquals(856, getHealth(targetDummy), EPSILON);
        queueCommand(new StopCommand());

        onLogicEnd(false, false);
    }

    @Test
    public void testP_NotTriggered() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        assertEquals(940, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(0.75f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(20, 8));
        int targetDummy2 = createTargetDummy(new Vector2f(20, 10));
        int targetDummy3 = createTargetDummy(new Vector2f(20, 12));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(0.25f);
        assertEquals(3, findEntities(NAME_Q_PROJECTILE).size());
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy1), EPSILON);
        assertEquals(940, getHealth(targetDummy2), EPSILON);
        assertEquals(940, getHealth(targetDummy3), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy3, NAME_PASSIVE_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy3, NAME_PASSIVE_BUFF));

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
        assertEquals(3, findEntities(NAME_Q_PROJECTILE).size());
        tickSeconds(0.75f);
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
        assertNotNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy1), EPSILON);
        assertEquals(940, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        tickSeconds(0.25f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

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
        tickSeconds(0.75f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_Blocking() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(10));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_W_OBJECT));
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(0.75f);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(1400, getHealth(character), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        tickSeconds(1.25f);
        assertNull(findEntity(NAME_W_OBJECT));
        tickSeconds(1.75f);
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base_NotBlocking() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_W_OBJECT));
        tickSeconds(2);
        assertNull(findEntity(NAME_W_OBJECT));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_Blocking() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(10));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_W_OBJECT));
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(0.75f);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(1400, getHealth(character), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        tickSeconds(1.25f);
        assertNotNull(findEntity(NAME_W_OBJECT));
        tickSeconds(1);
        assertNull(findEntity(NAME_W_OBJECT));
        tickSeconds(0.75f);
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1_NotBlocking() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_W_OBJECT));
        tickSeconds(2);
        assertNotNull(findEntity(NAME_W_OBJECT));
        tickSeconds(1);
        assertNull(findEntity(NAME_W_OBJECT));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_Blocking() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(10));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.createReader().loadTemplate(entityWorld, targetDummyAutoAttack, "spells/ranged_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_WALK_SPEED_BUFF));
        assertEquals(8, getWalkSpeed(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_W_OBJECT));
        assertTrue(hasBuff(character, NAME_W_WALK_SPEED_BUFF));
        assertEquals(10.4, getWalkSpeed(character), EPSILON);
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(0.75f);
        entityWorld.removeComponent(targetDummy, AggroTargetComponent.class);
        assertEquals(1400, getHealth(character), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_BUFF));
        tickSeconds(1.25f);
        assertNull(findEntity(NAME_W_OBJECT));
        assertFalse(hasBuff(character, NAME_W_WALK_SPEED_BUFF));
        assertEquals(8, getWalkSpeed(character), EPSILON);
        tickSeconds(1.75f);
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2_NotBlocking() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_W_WALK_SPEED_BUFF));
        assertEquals(8, getWalkSpeed(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(0.25f);
        assertNotNull(findEntity(NAME_W_OBJECT));
        assertTrue(hasBuff(character, NAME_W_WALK_SPEED_BUFF));
        assertEquals(10.4, getWalkSpeed(character), EPSILON);
        tickSeconds(2);
        assertNull(findEntity(NAME_W_OBJECT));
        assertFalse(hasBuff(character, NAME_W_WALK_SPEED_BUFF));
        assertEquals(8, getWalkSpeed(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(21, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base_Miss() {
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
    public void testE_Upgrade1_Hit_Reset() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(21, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(840, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        assertEquals(0, getRemainingCooldown(character, SPELL_INDEX_E), EPSILON);
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Hit_NoReset() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(21, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        assertEquals(11, getRemainingCooldown(character, SPELL_INDEX_E), EPSILON);
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertEquals(11, getRemainingCooldown(character, SPELL_INDEX_E), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(21, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(900, getHealth(targetDummy1), EPSILON);
        assertEquals(900, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(14, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(18, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2_ResetOnKill() {
        setRemainingCooldown(character, SPELL_INDEX_E, 10);
        onLogicStart();

        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        entityWorld.setComponent(targetDummy, new IsCharacterComponent());
        entityWorld.setComponent(targetDummy, new HealthComponent(1));
        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        assertFalse(isAlive(targetDummy));
        assertEquals(0, getRemainingCooldown(character, SPELL_INDEX_E), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Hit_Execute() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummy1, new HealthComponent(1000));
        entityWorld.setComponent(targetDummy2, new HealthComponent(1000));
        entityWorld.setComponent(targetDummy1, new IsCharacterComponent());
        entityWorld.setComponent(targetDummy2, new IsCharacterComponent());
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(-1, 0)));
        tickSeconds(1);
        assertFalse(isAlive(targetDummy1));
        assertFalse(isAlive(targetDummy2));

        onLogicEnd(true, false);
    }

    @Test
    public void testR_Base_Hit_NoExecute() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertEquals(800, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 20));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Hit_Execute() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummy1, new HealthComponent(1000));
        entityWorld.setComponent(targetDummy2, new HealthComponent(1000));
        entityWorld.setComponent(targetDummy1, new IsCharacterComponent());
        entityWorld.setComponent(targetDummy2, new IsCharacterComponent());
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(-1, 0)));
        tickSeconds(1);
        assertFalse(isAlive(targetDummy1));
        assertFalse(isAlive(targetDummy2));

        onLogicEnd(true, false);
    }

    @Test
    public void testR_Upgrade1_Hit_NoExecute() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertEquals(800, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Hit_Stun() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(740, getHealth(targetDummy1), EPSILON);
        assertEquals(800, getHealth(targetDummy2), EPSILON);
        assertTrue(isStunned(targetDummy1));
        assertFalse(isStunned(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Hit_Execute() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        int targetDummyBaseAttributes1 = entityWorld.getComponent(targetDummy1, BaseAttributesComponent.class).getBonusAttributesEntity();
        int targetDummyBaseAttributes2 = entityWorld.getComponent(targetDummy2, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes1, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummyBaseAttributes2, new BonusFlatMaximumHealthComponent(10000));
        entityWorld.setComponent(targetDummy1, new HealthComponent(1000));
        entityWorld.setComponent(targetDummy2, new HealthComponent(1000));
        entityWorld.setComponent(targetDummy1, new IsCharacterComponent());
        entityWorld.setComponent(targetDummy2, new IsCharacterComponent());
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(-1, 0)));
        tickSeconds(1);
        assertFalse(isAlive(targetDummy1));
        assertFalse(isAlive(targetDummy2));
        assertEquals(0, getRemainingCooldown(character, SPELL_INDEX_R), EPSILON);

        onLogicEnd(true, false);
    }

    @Test
    public void testR_Upgrade2_Hit_NoExecute() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertEquals(800, getHealth(targetDummy1), EPSILON);
        assertEquals(800, getHealth(targetDummy2), EPSILON);
        assertTrue(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertTrue(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));
        assertEquals(89, getRemainingCooldown(character, SPELL_INDEX_R), EPSILON);
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy1, NAME_PASSIVE_BUFF));
        assertFalse(hasBuff(targetDummy2, NAME_PASSIVE_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2_Miss() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 20));
        int targetDummy2 = createTargetDummy(new Vector2f(16, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy1));
        assertTrue(isFullHealth(targetDummy2));
        assertEquals(89, getRemainingCooldown(character, SPELL_INDEX_R), EPSILON);

        onLogicEnd(false, false);
    }
}
