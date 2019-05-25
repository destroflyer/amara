/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayMapSpellsCooldownsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayMapSpellsCooldownsSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, MapSpellsComponent.class, RemainingCooldownComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(characterEntity, MapSpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(characterEntity, MapSpellsComponent.class));
        MapSpellsComponent mapSpellsComponent = entityWorld.getComponent(characterEntity, MapSpellsComponent.class);
        if(mapSpellsComponent != null){
            checkCurrentSpellsCooldowns(observer, mapSpellsComponent.getSpellsEntities());
        }
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, MapSpellsComponent mapSpellsComponent){
        if(mapSpellsComponent != null){
            int[] spells = mapSpellsComponent.getSpellsEntities();
            for(int i=0;i<2;i++){
                if(i < spells.length){
                    RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(spells[i], RemainingCooldownComponent.class);
                    if(remainingCooldownComponent != null){
                        screenController.showMapSpellCooldown(i, remainingCooldownComponent.getDuration());
                    }
                    else{
                        screenController.hideMapSpellCooldown(i);
                    }
                }
                else{
                    screenController.hideMapSpellCooldown(i);
                }
            }
        }
    }
    
    private void checkCurrentSpellsCooldowns(ComponentMapObserver observer, int[] spells){
        for(int i=0;i<2;i++){
            if(i < spells.length){
                checkCooldownChanged(observer.getNew().getComponent(spells[i], RemainingCooldownComponent.class), i);
                checkCooldownChanged(observer.getChanged().getComponent(spells[i], RemainingCooldownComponent.class), i);
                checkCooldownRemoved(observer.getRemoved().getComponent(spells[i], RemainingCooldownComponent.class), i);
            }
        }
    }
    
    private void checkCooldownChanged(RemainingCooldownComponent remainingCooldownComponent, int spellIndex){
        if(remainingCooldownComponent != null){
            screenController.showMapSpellCooldown(spellIndex, remainingCooldownComponent.getDuration());
        }
    }
    
    private void checkCooldownRemoved(RemainingCooldownComponent remainingCooldownComponent, int spellIndex){
        if(remainingCooldownComponent != null){
            screenController.hideMapSpellCooldown(spellIndex);
        }
    }
}
