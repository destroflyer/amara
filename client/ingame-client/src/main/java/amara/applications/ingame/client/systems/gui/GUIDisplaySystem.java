/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.applications.display.gui.GameScreenController;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class GUIDisplaySystem<ScreenControllerType extends GameScreenController> implements EntitySystem {

    public GUIDisplaySystem(PlayerAppState playerAppState, ScreenControllerType screenController) {
        this.playerAppState = playerAppState;
        this.screenController = screenController;
    }
    protected PlayerAppState playerAppState;
    protected ScreenControllerType screenController;
    private int lastUpdatedInspectedEntity;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        if (screenController.isVisible()) {
            int inspectedEntity = getInspectedEntity();
            if (inspectedEntity != lastUpdatedInspectedEntity) {
                onInspectionUpdated(entityWorld, inspectedEntity);
                lastUpdatedInspectedEntity = inspectedEntity;
            }
            PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(getPlayerEntity(), PlayerCharacterComponent.class);
            if (playerCharacterComponent != null) {
                update(entityWorld, deltaSeconds, playerCharacterComponent.getEntity());
            }
        }
    }

    protected abstract void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity);

    protected void onInspectionUpdated(EntityWorld entityWorld, int inspectedEntity) {

    }

    protected boolean hasComponentChanged(ComponentMapObserver observer, int entity, Class... componentClasses) {
        for (Class componentClass : componentClasses) {
            if (observer.getNew().hasComponent(entity, componentClass)
             || observer.getChanged().hasComponent(entity, componentClass)) {
                return true;
            }
        }
        return false;
    }

    protected int getPlayerEntity() {
        return playerAppState.getPlayerEntity();
    }

    protected int getInspectedEntity() {
        return playerAppState.getInspectedEntity();
    }
}
