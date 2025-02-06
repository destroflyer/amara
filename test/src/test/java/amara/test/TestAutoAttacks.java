package amara.test;

import amara.applications.ingame.entitysystem.components.units.AutoAttackComponent;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import com.jme3.math.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAutoAttacks extends CommandingPlayerTest {

    @Test
    public void testMeleeAutoAttack() {
        int autoAttack = createEntity("spells/melee_autoattack");
        entityWorld.setComponent(character, new AutoAttackComponent(autoAttack));
        int targetDummy = createTargetDummy(new Vector2f(12, 10));
        onLogicStart();

        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertEquals(966.6667f, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testRangedAutoAttack() {
        int autoAttack = createEntity("spells/ranged_autoattack");
        entityWorld.setComponent(character, new AutoAttackComponent(autoAttack));
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1);
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertEquals(966.6667f, getHealth(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }
}
