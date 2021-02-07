package amara.applications.ingame.client.systems.filters;

import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.libraries.applications.display.appstates.PostFilterAppState;
import amara.libraries.applications.display.filters.GrayScaleFilter;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

import java.util.function.Supplier;

public class PlayerDeathDisplaySystem implements EntitySystem {

    public PlayerDeathDisplaySystem(Supplier<Integer> playerEntity, PostFilterAppState postFilterAppState) {
        this.playerEntity = playerEntity;
        this.postFilterAppState = postFilterAppState;
    }
    private Supplier<Integer> playerEntity;
    private PostFilterAppState postFilterAppState;
    private GrayScaleFilter grayscaleFilter = new GrayScaleFilter();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity.get(), PlayerCharacterComponent.class);
        if (playerCharacterComponent != null) {
            int characterEntity = playerCharacterComponent.getEntity();
            ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
            if (observer.getNew().hasComponent(characterEntity, IsAliveComponent.class)) {
                postFilterAppState.removeFilter(grayscaleFilter);
            } else if (observer.getRemoved().hasComponent(characterEntity, IsAliveComponent.class)) {
                postFilterAppState.addFilter(grayscaleFilter);
            }
        }
    }
}
