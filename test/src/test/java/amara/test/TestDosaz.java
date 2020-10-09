package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.BonusFlatMaximumHealthComponent;
import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.BaseAttributesComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.applications.ingame.network.messages.objects.commands.UpgradeSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.WalkToTargetCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastPositionalSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSingleTargetSpellCommand;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestDosaz extends CommandingPlayerTest {

    public TestDosaz() {
        characterTemplate = "units/dosaz";
    }
    private static final String NAME_TOMBSTONE = "Tombstone";
    private static final String NAME_GHOST = "Ghost";
    private static final String NAME_Q_DAMAGE_AMPLIFICATION_BUFF = "Cursed";
    private static final String NAME_Q_PROJECTILE = "Skull";
    private static final String NAME_WALL_PART = "Bone Wall Part";
    private static final String NAME_WALL_VISUAL = "Bone Wall Visual";
    private static final String NAME_R_CHARACTER_BUFF = "Power From the Beyond";
    private static final String NAME_R_TARGET_BUFF = "Lifedrained";

    @Test
    public void testP_Touch() {
        onLogicStart();

        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy, new HealthComponent(0));
        tickSeconds(1);
        int tombstone = findEntity(NAME_TOMBSTONE);
        assertEquals(20, getX(tombstone), EPSILON);
        assertEquals(10, getY(tombstone), EPSILON);
        queueCommand(new WalkToTargetCommand(new Vector2f(20, 10)));
        tickSeconds(2);
        assertNull(findEntity(NAME_TOMBSTONE));
        int ghost = findEntity(NAME_GHOST);
        assertEquals(20, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_TOMBSTONE));
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testP_NoTrigger() {
        onLogicStart();

        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy, new HealthComponent(0));
        tickSeconds(1);
        int tombstone = findEntity(NAME_TOMBSTONE);
        assertEquals(20, getX(tombstone), EPSILON);
        assertEquals(10, getY(tombstone), EPSILON);
        tickSeconds(30);
        assertNull(findEntity(NAME_TOMBSTONE));
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(18, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_Miss_SpawnGhost() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(18, getMovementSpeed(projectile), EPSILON);
        tickSeconds(4);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));
        int ghost = findEntity(NAME_GHOST);
        assertEquals(30, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Base_TouchTombstone_SpawnGhost() {
        onLogicStart();

        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy, new HealthComponent(0));
        tickSeconds(1);
        assertNotNull(findEntity(NAME_TOMBSTONE));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(18, getMovementSpeed(projectile), EPSILON);
        tickSeconds(4);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertNull(findEntity(NAME_TOMBSTONE));
        LinkedList<Integer> ghosts = findEntities(NAME_GHOST);
        assertEquals(2, ghosts.size());
        int ghost1 = ghosts.get(0);
        assertEquals(20, getX(ghost1), EPSILON);
        assertEquals(10, getY(ghost1), EPSILON);
        int ghost2 = ghosts.get(1);
        assertEquals(30, getX(ghost2), EPSILON);
        assertEquals(10, getY(ghost2), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(18, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_Q_DAMAGE_AMPLIFICATION_BUFF));
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy, NAME_Q_DAMAGE_AMPLIFICATION_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_Miss_SpawnGhost() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(18, getMovementSpeed(projectile), EPSILON);
        tickSeconds(4);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));
        int ghost = findEntity(NAME_GHOST);
        assertEquals(30, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade1_TouchTombstone_SpawnGhost() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 0));
        tickSeconds(1);
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy, new HealthComponent(0));
        tickSeconds(1);
        assertNotNull(findEntity(NAME_TOMBSTONE));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(18, getMovementSpeed(projectile), EPSILON);
        tickSeconds(4);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertNull(findEntity(NAME_TOMBSTONE));
        LinkedList<Integer> ghosts = findEntities(NAME_GHOST);
        assertEquals(2, ghosts.size());
        int ghost1 = ghosts.get(0);
        assertEquals(20, getX(ghost1), EPSILON);
        assertEquals(10, getY(ghost1), EPSILON);
        int ghost2 = ghosts.get(1);
        assertEquals(30, getX(ghost2), EPSILON);
        assertEquals(10, getY(ghost2), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(0.5f);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertEquals(940, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_Miss_SpawnGhost() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(4);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertTrue(isFullHealth(targetDummy));
        int ghost = findEntity(NAME_GHOST);
        assertEquals(30, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Upgrade2_TouchTombstone_SpawnGhost() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(0, 1));
        tickSeconds(1);
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy, new HealthComponent(0));
        tickSeconds(1);
        assertNotNull(findEntity(NAME_TOMBSTONE));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(1);
        int projectile = findEntity(NAME_Q_PROJECTILE);
        assertEquals(25, getMovementSpeed(projectile), EPSILON);
        tickSeconds(4);
        assertNull(findEntity(NAME_Q_PROJECTILE));
        assertNull(findEntity(NAME_TOMBSTONE));
        LinkedList<Integer> ghosts = findEntities(NAME_GHOST);
        assertEquals(2, ghosts.size());
        int ghost1 = ghosts.get(0);
        assertEquals(20, getX(ghost1), EPSILON);
        assertEquals(10, getY(ghost1), EPSILON);
        int ghost2 = ghosts.get(1);
        assertEquals(30, getX(ghost2), EPSILON);
        assertEquals(10, getY(ghost2), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Base() {
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 20)));
        tickSeconds(2);
        int ghost = findEntity(NAME_GHOST);
        assertEquals(20, getX(ghost), EPSILON);
        assertEquals(20, getY(ghost), EPSILON);
        queueCommand(new CastSingleTargetSpellCommand(SPELL_INDEX_W, ghost));
        tickSeconds(0.5f);
        assertEquals(10, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(20, getX(ghost), EPSILON);
        assertEquals(20, getY(ghost), EPSILON);
        tickSeconds(0.5f);
        assertEquals(20, getX(character), EPSILON);
        assertEquals(20, getY(character), EPSILON);
        assertEquals(10, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade1() {
        onLogicStart();

        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new UpgradeSpellCommand(1, 0));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 20)));
        tickSeconds(2);
        int ghost = findEntity(NAME_GHOST);
        assertEquals(20, getX(ghost), EPSILON);
        assertEquals(20, getY(ghost), EPSILON);
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        queueCommand(new CastSingleTargetSpellCommand(SPELL_INDEX_W, ghost));
        tickSeconds(0.5f);
        assertEquals(10, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(20, getX(ghost), EPSILON);
        assertEquals(20, getY(ghost), EPSILON);
        tickSeconds(0.5f);
        assertEquals(20, getX(character), EPSILON);
        assertEquals(20, getY(character), EPSILON);
        assertEquals(10, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        assertEquals(76, getArmor(character), EPSILON);
        assertEquals(45, getMagicResistance(character), EPSILON);
        tickSeconds(3);
        assertEquals(61, getArmor(character), EPSILON);
        assertEquals(30, getMagicResistance(character), EPSILON);
        tickSeconds(16);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Upgrade2() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(1, 1));
        tickSeconds(1);
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(20, 20)));
        tickSeconds(2);
        int ghost = findEntity(NAME_GHOST);
        assertEquals(20, getX(ghost), EPSILON);
        assertEquals(20, getY(ghost), EPSILON);
        assertEquals(0, getIncomingDamageAmplification(character), EPSILON);
        queueCommand(new CastSingleTargetSpellCommand(SPELL_INDEX_W, ghost));
        tickSeconds(0.5f);
        assertEquals(10, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
        assertEquals(20, getX(ghost), EPSILON);
        assertEquals(20, getY(ghost), EPSILON);
        assertEquals(-0.8f, getIncomingDamageAmplification(character), EPSILON);
        tickSeconds(0.5f);
        assertEquals(20, getX(character), EPSILON);
        assertEquals(20, getY(character), EPSILON);
        assertEquals(10, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        assertEquals(0, getIncomingDamageAmplification(character), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Base() {
        onLogicStart();

        int targetDummy1 = createTargetDummy(new Vector2f(18, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(22, 10));
        entityWorld.setComponent(targetDummy1, new TeamComponent(1));
        entityWorld.setComponent(targetDummy2, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy1, new HealthComponent(0));
        entityWorld.setComponent(targetDummy2, new HealthComponent(0));
        tickSeconds(1);
        assertEquals(2, findEntities(NAME_TOMBSTONE).size());
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        // Wall
        int wallVisual = findEntity(NAME_WALL_VISUAL);
        assertEquals(20, getX(wallVisual), EPSILON);
        assertEquals(10, getY(wallVisual), EPSILON);
        assertEquals(13, findEntities(NAME_WALL_PART).size());
        // Tombstones -> Ghosts
        assertNull(findEntity(NAME_TOMBSTONE));
        LinkedList<Integer> ghosts = findEntities(NAME_GHOST);
        assertEquals(2, ghosts.size());
        int ghost1 = ghosts.get(0);
        assertEquals(18, getX(ghost1), EPSILON);
        assertEquals(10, getY(ghost1), EPSILON);
        int ghost2 = ghosts.get(1);
        assertEquals(22, getX(ghost2), EPSILON);
        assertEquals(10, getY(ghost2), EPSILON);
        tickSeconds(3.5f);
        assertEquals(13, findEntities(NAME_WALL_PART).size());
        assertNotNull(findEntity(NAME_WALL_VISUAL));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_WALL_PART));
        assertNull(findEntity(NAME_WALL_VISUAL));
        tickSeconds(16);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade1() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 0));
        tickSeconds(1);
        int targetDummy1 = createTargetDummy(new Vector2f(24, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(28, 10));
        entityWorld.setComponent(targetDummy1, new TeamComponent(1));
        entityWorld.setComponent(targetDummy2, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy1, new HealthComponent(0));
        entityWorld.setComponent(targetDummy2, new HealthComponent(0));
        tickSeconds(1);
        assertEquals(2, findEntities(NAME_TOMBSTONE).size());
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(26, 10)));
        tickSeconds(0.5f);
        // Wall
        int wallVisual = findEntity(NAME_WALL_VISUAL);
        assertEquals(26, getX(wallVisual), EPSILON);
        assertEquals(10, getY(wallVisual), EPSILON);
        assertEquals(13, findEntities(NAME_WALL_PART).size());
        // Tombstones -> Ghosts
        assertNull(findEntity(NAME_TOMBSTONE));
        LinkedList<Integer> ghosts = findEntities(NAME_GHOST);
        assertEquals(2, ghosts.size());
        int ghost1 = ghosts.get(0);
        assertEquals(24, getX(ghost1), EPSILON);
        assertEquals(10, getY(ghost1), EPSILON);
        int ghost2 = ghosts.get(1);
        assertEquals(28, getX(ghost2), EPSILON);
        assertEquals(10, getY(ghost2), EPSILON);
        tickSeconds(3.5f);
        assertEquals(13, findEntities(NAME_WALL_PART).size());
        assertNotNull(findEntity(NAME_WALL_VISUAL));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_WALL_PART));
        assertNull(findEntity(NAME_WALL_VISUAL));
        tickSeconds(16);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testE_Upgrade2() {
        onLogicStart();

        queueCommand(new UpgradeSpellCommand(2, 1));
        tickSeconds(1);
        int targetDummy1 = createTargetDummy(new Vector2f(18, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(22, 10));
        entityWorld.setComponent(targetDummy1, new TeamComponent(1));
        entityWorld.setComponent(targetDummy2, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy1, new HealthComponent(0));
        entityWorld.setComponent(targetDummy2, new HealthComponent(0));
        tickSeconds(1);
        assertEquals(2, findEntities(NAME_TOMBSTONE).size());
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_E, new Vector2f(20, 10)));
        tickSeconds(0.5f);
        // Wall
        int wallVisual = findEntity(NAME_WALL_VISUAL);
        assertEquals(20, getX(wallVisual), EPSILON);
        assertEquals(10, getY(wallVisual), EPSILON);
        assertEquals(13, findEntities(NAME_WALL_PART).size());
        // Tombstones -> Ghosts
        assertNull(findEntity(NAME_TOMBSTONE));
        LinkedList<Integer> ghosts = findEntities(NAME_GHOST);
        assertEquals(2, ghosts.size());
        int ghost1 = ghosts.get(0);
        assertEquals(18, getX(ghost1), EPSILON);
        assertEquals(10, getY(ghost1), EPSILON);
        int ghost2 = ghosts.get(1);
        assertEquals(22, getX(ghost2), EPSILON);
        assertEquals(10, getY(ghost2), EPSILON);
        tickSeconds(6.5f);
        assertEquals(13, findEntities(NAME_WALL_PART).size());
        assertNotNull(findEntity(NAME_WALL_VISUAL));
        tickSeconds(0.5f);
        assertNull(findEntity(NAME_WALL_PART));
        assertNull(findEntity(NAME_WALL_VISUAL));
        tickSeconds(16);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Base() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        assertEquals(1400, getHealth(character), EPSILON);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_R));
        tickSeconds(1);
        assertEquals(5, findEntities(NAME_GHOST).size());
        assertTrue(hasBuff(character, NAME_R_CHARACTER_BUFF));
        assertEquals(1700, getHealth(character), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9900, getHealth(targetDummy), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9733.336f, getHealth(targetDummy), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9533.338f, getHealth(targetDummy), EPSILON);
        tickSeconds(13);
        assertFalse(hasBuff(character, NAME_R_CHARACTER_BUFF));
        assertEquals(1400, getHealth(character), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(7100.012f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade1() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        assertEquals(1400, getHealth(character), EPSILON);
        queueCommand(new UpgradeSpellCommand(3, 0));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_R));
        tickSeconds(1);
        assertEquals(5, findEntities(NAME_GHOST).size());
        assertTrue(hasBuff(character, NAME_R_CHARACTER_BUFF));
        assertEquals(1700, getHealth(character), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9900, getHealth(targetDummy), EPSILON);
        assertEquals(7, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9733.336f, getHealth(targetDummy), EPSILON);
        assertEquals(7, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9533.338f, getHealth(targetDummy), EPSILON);
        assertEquals(7, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(13);
        assertFalse(hasBuff(character, NAME_R_CHARACTER_BUFF));
        assertEquals(1400, getHealth(character), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(7100.012f, getHealth(targetDummy), EPSILON);
        assertEquals(10, getWalkSpeed(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Upgrade2() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatMaximumHealthComponent(10000));
        onLogicStart();

        assertEquals(1400, getHealth(character), EPSILON);
        queueCommand(new UpgradeSpellCommand(3, 1));
        tickSeconds(1);
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_R));
        tickSeconds(1);
        assertEquals(5, findEntities(NAME_GHOST).size());
        assertTrue(hasBuff(character, NAME_R_CHARACTER_BUFF));
        assertEquals(2000, getHealth(character), EPSILON);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9900, getHealth(targetDummy), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9733.336f, getHealth(targetDummy), EPSILON);
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(9533.338f, getHealth(targetDummy), EPSILON);
        tickSeconds(13);
        assertFalse(hasBuff(character, NAME_R_CHARACTER_BUFF));
        assertEquals(1400, getHealth(character), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_R_TARGET_BUFF));
        assertEquals(7100.012f, getHealth(targetDummy), EPSILON);
        tickSeconds(5);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }
}
