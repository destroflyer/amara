package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastLinearSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
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
    private static final String NAME_W_ATTACK_BUFF = "Cripple";
    private static final String NAME_W_SLOW_BUFF = "Crippled";

    @Test
    public void testQ_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(10, 13));
        entityWorld.setComponent(targetDummy2, new IsCharacterComponent());
        entityWorld.setComponent(character, new HealthComponent(300));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertEquals(946.6667f, getHealth(targetDummy1), EPSILON);
        assertEquals(946.6667f, getHealth(targetDummy2), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Miss() {
        entityWorld.setComponent(character, new HealthComponent(300));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertEquals(301.0331f, getHealth(character), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testW() {
        int targetDummy = createTargetDummy(new Vector2f(13, 10));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_W_ATTACK_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(0.5f);
        assertFalse(hasBuff(character, NAME_W_ATTACK_BUFF));
        assertTrue(hasBuff(targetDummy, NAME_W_SLOW_BUFF));
        assertEquals(916.6667f, getHealth(targetDummy), EPSILON);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertFalse(hasBuff(targetDummy, NAME_W_SLOW_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testR() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        queueCommand(new CastLinearSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(1, 0)));
        tickSeconds(5);
        assertEquals(923.3333f, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }
}
