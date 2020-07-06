package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.items.BagComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

public class DisplayBagSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayBagSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, BagComponent.class);
        checkChange(entityWorld, observer.getNew().getComponent(characterEntity, BagComponent.class));
        checkChange(entityWorld, observer.getChanged().getComponent(characterEntity, BagComponent.class));
    }

    private void checkChange(EntityWorld entityWorld, BagComponent bagComponent) {
        if (bagComponent != null) {
            screenController.setPlayerBagItems(entityWorld, bagComponent.getItemEntities());
        }
    }
}
