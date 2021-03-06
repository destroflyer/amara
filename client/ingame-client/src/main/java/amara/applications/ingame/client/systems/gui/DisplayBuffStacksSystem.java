package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.units.BuffsComponent;
import amara.libraries.entitysystem.EntityWorld;

public class DisplayBuffStacksSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayBuffStacksSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        updateBuffStacks(entityWorld, true, characterEntity);
        updateBuffStacks(entityWorld, false, getInspectedEntity());
    }

    // Observing the whole entity hierarchy is probably more expensive than simply iterating + updating every frame
    private void updateBuffStacks(EntityWorld entityWorld, boolean playerOrInspection, int entity) {
        BuffsComponent buffsComponent = entityWorld.getComponent(entity, BuffsComponent.class);
        if (buffsComponent != null) {
            for (int buffStatusEntity : buffsComponent.getBuffStatusEntities()) {
                StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
                boolean hasStacks = false;
                if (stacksComponent != null) {
                    if (playerOrInspection) {
                        screenController.setPlayerBuffStacks(buffStatusEntity, stacksComponent.getStacks());
                    } else {
                        screenController.setInspectionBuffStacks(buffStatusEntity, stacksComponent.getStacks());
                    }
                    hasStacks = true;
                }
                if (playerOrInspection) {
                    screenController.setPlayerBuffStacksVisible(buffStatusEntity, hasStacks);
                } else {
                    screenController.setInspectionBuffStacksVisible(buffStatusEntity, hasStacks);
                }
            }
        }
    }
}
