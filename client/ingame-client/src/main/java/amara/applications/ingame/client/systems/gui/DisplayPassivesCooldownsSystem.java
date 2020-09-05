/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayPassivesCooldownsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayPassivesCooldownsSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PassivesComponent.class, RemainingCooldownComponent.class);
        checkChangedPassives(entityWorld, observer.getNew().getComponent(characterEntity, PassivesComponent.class));
        checkChangedPassives(entityWorld, observer.getChanged().getComponent(characterEntity, PassivesComponent.class));
        PassivesComponent passivesComponent = entityWorld.getComponent(characterEntity, PassivesComponent.class);
        if(passivesComponent != null){
            checkCurrentPassivesCooldowns(observer, passivesComponent.getPassiveEntities());
        }
    }
    
    private void checkChangedPassives(EntityWorld entityWorld, PassivesComponent passivesComponent){
        if(passivesComponent != null){
            int[] passives = passivesComponent.getPassiveEntities();
            if(passives.length > 0){
                RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(passives[0], RemainingCooldownComponent.class);
                if(remainingCooldownComponent != null){
                    screenController.showPlayer_PassiveCooldown(remainingCooldownComponent.getDuration());
                }
                else{
                    screenController.hidePlayer_PassiveCooldown();
                }
            } else {
                screenController.hidePlayer_PassiveCooldown();
            }
        }
    }
    
    private void checkCurrentPassivesCooldowns(ComponentMapObserver observer, int[] passives){
        if(passives.length > 0){
            checkCooldownChanged(observer.getNew().getComponent(passives[0], RemainingCooldownComponent.class));
            checkCooldownChanged(observer.getChanged().getComponent(passives[0], RemainingCooldownComponent.class));
            checkCooldownRemoved(observer.getRemoved().getComponent(passives[0], RemainingCooldownComponent.class));
        }
    }
    
    private void checkCooldownChanged(RemainingCooldownComponent remainingCooldownComponent){
        if(remainingCooldownComponent != null){
            screenController.showPlayer_PassiveCooldown(remainingCooldownComponent.getDuration());
        }
    }
    
    private void checkCooldownRemoved(RemainingCooldownComponent remainingCooldownComponent){
        if(remainingCooldownComponent != null){
            screenController.hidePlayer_PassiveCooldown();
        }
    }
}
