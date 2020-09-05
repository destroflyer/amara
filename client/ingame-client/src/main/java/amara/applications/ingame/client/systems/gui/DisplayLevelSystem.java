/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.LevelComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayLevelSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayLevelSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, LevelComponent.class);
        checkChange(observer, "player", characterEntity);
        checkChange(observer, "inspection", getInspectedEntity());
    }

    private void checkChange(ComponentMapObserver observer, String uiPrefix, int entity) {
        checkChange(uiPrefix, observer.getNew().getComponent(entity, LevelComponent.class));
        checkChange(uiPrefix, observer.getChanged().getComponent(entity, LevelComponent.class));
    }

    private void checkChange(String uiPrefix, LevelComponent changedLevelComponent) {
        if (changedLevelComponent != null) {
            check(uiPrefix, changedLevelComponent);
        }
    }

    @Override
    protected void onInspectionUpdated(EntityWorld entityWorld, int inspectedEntity) {
        super.onInspectionUpdated(entityWorld, inspectedEntity);
        check("inspection", entityWorld.getComponent(inspectedEntity, LevelComponent.class));
    }

    private void check(String uiPrefix, LevelComponent levelComponent) {
        if (levelComponent != null) {
            screenController.setLevel(uiPrefix, levelComponent.getLevel());
        } else {
            screenController.hideLevel(uiPrefix);
        }
    }
}
