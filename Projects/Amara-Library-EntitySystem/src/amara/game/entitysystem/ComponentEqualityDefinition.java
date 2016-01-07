package amara.game.entitysystem;

/**
 *
 * @author Philipp
 */
public interface ComponentEqualityDefinition {
    boolean areComponentsEqual(Object componentA, Object componentB);
}
