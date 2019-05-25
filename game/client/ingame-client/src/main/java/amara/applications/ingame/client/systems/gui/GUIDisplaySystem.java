/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.applications.display.gui.GameScreenController;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class GUIDisplaySystem<ScreenControllerType extends GameScreenController> implements EntitySystem {

    public GUIDisplaySystem(int playerEntity, ScreenControllerType screenController) {
        this.playerEntity = playerEntity;
        this.screenController = screenController;
    }
    protected int playerEntity;
    protected ScreenControllerType screenController;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        if (screenController.isVisible()) {
            PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
            if (playerCharacterComponent != null) {
                update(entityWorld, deltaSeconds, playerCharacterComponent.getEntity());
            }
        }
    }
    
    protected abstract void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity);
    
    protected boolean hasComponentChanged(ComponentMapObserver observer, int entity, Class... componentClasses) {
        for (Class componentClass : componentClasses) {
            if (observer.getNew().hasComponent(entity, componentClass)
             || observer.getChanged().hasComponent(entity, componentClass)) {
                return true;
            }
        }
        return false;
    }
}
