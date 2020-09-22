package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.applications.ingame.network.messages.objects.commands.WalkToTargetCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastPositionalSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
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
    private static final String NAME_Q_BUFF = "Cursed";
    private static final String NAME_WALL_PART = "Bone Wall Part";
    private static final String NAME_WALL_VISUAL = "Bone Wall Visual";
    private static final String NAME_R_BUFF = "Power From the Beyond";

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
    public void testQ_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(1);
        assertTrue(hasBuff(targetDummy, NAME_Q_BUFF));
        assertEquals(940, getHealth(targetDummy), EPSILON);
        tickSeconds(3);
        assertFalse(hasBuff(targetDummy, NAME_Q_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Miss_SpawnGhost() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(5);
        assertTrue(isFullHealth(targetDummy));
        int ghost = findEntity(NAME_GHOST);
        assertEquals(30, getX(ghost), EPSILON);
        assertEquals(10, getY(ghost), EPSILON);
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_TouchTombstone_SpawnGhost() {
        onLogicStart();

        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        entityWorld.setComponent(targetDummy, new TeamComponent(1));
        tickSeconds(1);
        entityWorld.setComponent(targetDummy, new HealthComponent(0));
        tickSeconds(1);
        assertNotNull(findEntity(NAME_TOMBSTONE));
        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_Q, new Vector2f(30, 10)));
        tickSeconds(5);
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
    public void testE() {
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
        tickSeconds(1);
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
        tickSeconds(20);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }

    @Test
    public void testR() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_R));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_R_BUFF));
        assertEquals(5, findEntities(NAME_GHOST).size());
        tickSeconds(15);
        assertFalse(hasBuff(character, NAME_R_BUFF));
        tickSeconds(5);
        assertNull(findEntity(NAME_GHOST));

        onLogicEnd(false, false);
    }
}
