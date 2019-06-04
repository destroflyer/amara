/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayHudNameSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayHudNameSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, NameComponent.class);
        checkChange(observer, "player", characterEntity);
        checkChange(observer, "inspection", getInspectedEntity());
    }

    private void checkChange(ComponentMapObserver observer, String uiPrefix, int entity) {
        checkChange(uiPrefix, observer.getNew().getComponent(entity, NameComponent.class));
        checkChange(uiPrefix, observer.getChanged().getComponent(entity, NameComponent.class));
    }

    private void checkChange(String uiPrefix, NameComponent changedCameComponent) {
        if (changedCameComponent != null) {
            check(uiPrefix, changedCameComponent);
        }
    }

    @Override
    protected void onInspectionUpdated(EntityWorld entityWorld, int inspectedEntity) {
        super.onInspectionUpdated(entityWorld, inspectedEntity);
        check("inspection", entityWorld.getComponent(inspectedEntity, NameComponent.class));
    }

    private void check(String uiPrefix, NameComponent nameComponent) {
        String displayedName = ((nameComponent != null) ? nameComponent.getName() : "<Unnamed>");
        screenController.setName(uiPrefix, displayedName);
    }
}
