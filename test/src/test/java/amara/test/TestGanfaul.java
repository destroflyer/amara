package amara.test;

import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.SpellIndex;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestGanfaul extends CommandingPlayerTest {

    public TestGanfaul() {
        characterTemplate = "units/ganfaul";
    }

    @Test
    public void testQ() {
        int spell = createEntity("spells/ganfaul_binding/base");
        entityWorld.setComponent(character, new SpellsComponent(spell));
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(new SpellIndex(SpellIndex.SpellSet.SPELLS, 0), new Vector2f(1, 0)));
        tickSeconds(1);
        assertTrue(entityWorld.hasComponent(targetDummy, IsBindedComponent.class));
        tickSeconds(2);

        onLogicEnd();
        assertFalse(entityWorld.hasComponent(targetDummy, IsBindedComponent.class));
        assertEquals(940, getHealth(targetDummy));
    }

    @Test
    public void testW() {
        int spell = createEntity("spells/ganfaul_laser/base");
        entityWorld.setComponent(character, new SpellsComponent(spell));
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(new SpellIndex(SpellIndex.SpellSet.SPELLS, 0), new Vector2f(1, 0)));
        tickSeconds(2);
        assertEquals(800, getHealth(targetDummy));
        tickSeconds(2);

        onLogicEnd();
        assertEquals(760, getHealth(targetDummy));
    }

    @Test
    public void testE() {
        int spell = createEntity("spells/ganfaul_backflip/base");
        entityWorld.setComponent(character, new SpellsComponent(spell));
        entityWorld.setComponent(character, new DirectionComponent(new Vector2f(-1, 0)));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(new SpellIndex(SpellIndex.SpellSet.SPELLS, 0)));
        tickSeconds(2);

        onLogicEnd();
        assertEquals(18, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
    }

    @Test
    public void testR() {
        int spell = createEntity("spells/ganfaul_ult/base");
        entityWorld.setComponent(character, new SpellsComponent(spell));
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(new SpellIndex(SpellIndex.SpellSet.SPELLS, 0), new Vector2f(1, 0)));
        tickSeconds(5);

        onLogicEnd();
        assertEquals(800, getHealth(targetDummy));
    }
}
