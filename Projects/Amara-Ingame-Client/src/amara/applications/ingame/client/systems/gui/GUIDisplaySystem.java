/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class GUIDisplaySystem implements EntitySystem{

    public GUIDisplaySystem(int playerEntity, ScreenController_HUD screenController_HUD){
        this.playerEntity = playerEntity;
        this.screenController_HUD = screenController_HUD;
    }
    protected int playerEntity;
    protected ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(screenController_HUD.isVisible()){
            PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
            if(playerCharacterComponent != null){
                update(entityWorld, deltaSeconds, playerCharacterComponent.getEntity());
            }
        }
    }
    
    protected abstract void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity);
    
    protected boolean hasComponentChanged(ComponentMapObserver observer, int entity, Class... componentClasses){
        for(Class componentClass : componentClasses){
            if(observer.getNew().hasComponent(entity, componentClass)
            || observer.getChanged().hasComponent(entity, componentClass)){
                return true;
            }
        }
        return false;
    }
}
