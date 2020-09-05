package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.BuffsComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

public class DisplayBuffsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayBuffsSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, BuffsComponent.class);
        checkChange(entityWorld, observer, true, characterEntity);
        checkChange(entityWorld, observer, false, getInspectedEntity());
    }

    private void checkChange(EntityWorld entityWorld, ComponentMapObserver observer, boolean playerOrInspection, int entity) {
        checkChange(entityWorld, playerOrInspection, observer.getNew().getComponent(entity, BuffsComponent.class));
        checkChange(entityWorld, playerOrInspection, observer.getChanged().getComponent(entity, BuffsComponent.class));
    }

    private void checkChange(EntityWorld entityWorld, boolean playerOrInspection, BuffsComponent changedBuffsComponent) {
        if (changedBuffsComponent != null) {
            check(entityWorld, playerOrInspection, changedBuffsComponent);
        }
    }

    @Override
    protected void onInspectionUpdated(EntityWorld entityWorld, int inspectedEntity) {
        super.onInspectionUpdated(entityWorld, inspectedEntity);
        check(entityWorld, false, entityWorld.getComponent(inspectedEntity, BuffsComponent.class));
    }

    private void check(EntityWorld entityWorld, boolean playerOrInspection, BuffsComponent buffsComponent) {
        int[] buffStatusEntities = ((buffsComponent != null) ? buffsComponent.getBuffStatusEntities() : new int[0]);
        if (playerOrInspection) {
            screenController.setPlayerBuffs(entityWorld, buffStatusEntities);
        } else {
            screenController.setInspectionBuffs(entityWorld, buffStatusEntities);
        }
    }
}
