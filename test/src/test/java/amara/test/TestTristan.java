package amara.test;

import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.SpellIndex;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestTristan extends CommandingPlayerTest {

    public TestTristan() {
        characterTemplate = "units/tristan";
    }

    @Test
    public void testR() {
        int spell = createEntity("spells/tristan_ult/base");
        entityWorld.setComponent(character, new SpellsComponent(spell));
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(new SpellIndex(SpellIndex.SpellSet.SPELLS, 0), new Vector2f(1, 0)));
        tickSeconds(5);

        onLogicEnd();
        assertEquals(923, getHealth(targetDummy));
    }
}
