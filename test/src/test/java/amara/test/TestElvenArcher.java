package amara.test;

import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.SpellIndex;
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

    @Test
    public void testE() {
        int autoattack = entityWorld.getComponent(character, AutoAttackComponent.class).getAutoAttackEntity();
        int spell = createEntity("spells/elven_archer_roll/base," + autoattack);
        entityWorld.setComponent(character, new SpellsComponent(spell));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(new SpellIndex(SpellIndex.SpellSet.SPELLS, 0), new Vector2f(1, 0)));
        tickSeconds(2);

        onLogicEnd();
        assertEquals(16, getX(character), EPSILON);
        assertEquals(10, getY(character), EPSILON);
    }
}
