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
public abstract class PlayersDisplaySystem implements EntitySystem {

    public PlayersDisplaySystem(ScreenController_HUD screenController_HUD){
        this.screenController_HUD = screenController_HUD;
    }
    protected ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(screenController_HUD.isVisible()){
            preUpdate(entityWorld, deltaSeconds);
            for(int playerEntity : entityWorld.getEntitiesWithAll(PlayerIndexComponent.class)){
                update(entityWorld, deltaSeconds, playerEntity);
            }
        }
    }
    
    protected void preUpdate(EntityWorld entityWorld, float deltaSeconds){
        
    }
    
    protected abstract void update(EntityWorld entityWorld, float deltaSeconds, int playerEntity);
    
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
