/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.HealthComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.players.ClientComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import com.jme3.math.Vector2f;
import org.junit.Before;

/**
 *
 * @author Carl
 */
public class CommandingPlayerTest extends GameLogicTest {

    protected String characterTemplate = "units/annie";
    protected int player;
    protected int character;

    @Before
    public void initializePlayer() {
        player = entityWorld.createEntity();
        character = createEntity(characterTemplate);
        entityWorld.setComponent(character, new ClientComponent(0));
        entityWorld.setComponent(character, new PlayerCharacterComponent(character));
        entityWorld.setComponent(character, new PositionComponent(new Vector2f(10, 10)));
        entityWorld.setComponent(character, new DirectionComponent(new Vector2f(0, 1)));
        entityWorld.setComponent(character, new TeamComponent(1));
    }

    protected int createTargetDummy(Vector2f position) {
        int targetDummy = createEntity("units/target_dummy");
        entityWorld.setComponent(targetDummy, new PositionComponent(position));
        entityWorld.setComponent(targetDummy, new DirectionComponent(new Vector2f(0, 1)));
        entityWorld.setComponent(targetDummy, new TeamComponent(2));
        return targetDummy;
    }

    protected int getHealth(int entity) {
        return (int) entityWorld.getComponent(entity, HealthComponent.class).getValue();
    }

    protected float getX(int entity) {
        return getPosition(entity).getX();
    }

    protected float getY(int entity) {
        return getPosition(entity).getY();
    }

    private Vector2f getPosition(int entity) {
        return entityWorld.getComponent(entity, PositionComponent.class).getPosition();
    }
}
