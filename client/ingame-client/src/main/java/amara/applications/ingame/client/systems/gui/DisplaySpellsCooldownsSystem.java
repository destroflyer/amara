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
public class DisplaySpellsCooldownsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplaySpellsCooldownsSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class, RemainingCooldownComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(characterEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(characterEntity, SpellsComponent.class));
        SpellsComponent spellsComponent = entityWorld.getComponent(characterEntity, SpellsComponent.class);
        if(spellsComponent != null){
            checkCurrentSpellsCooldowns(observer, spellsComponent.getSpellsEntities());
        }
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent){
        if(spellsComponent != null){
            int[] spells = spellsComponent.getSpellsEntities();
            for(int i=0;i<4;i++){
                if(i < spells.length){
                    RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(spells[i], RemainingCooldownComponent.class);
                    if(remainingCooldownComponent != null){
                        screenController.showPlayer_SpellCooldown(i, remainingCooldownComponent.getDuration());
                    }
                    else{
                        screenController.hidePlayer_SpellCooldown(i);
                    }
                }
                else{
                    screenController.hidePlayer_SpellCooldown(i);
                }
            }
        }
    }
    
    private void checkCurrentSpellsCooldowns(ComponentMapObserver observer, int[] spells){
        for(int i=0;i<4;i++){
            if(i < spells.length){
                checkCooldownChanged(observer.getNew().getComponent(spells[i], RemainingCooldownComponent.class), i);
                checkCooldownChanged(observer.getChanged().getComponent(spells[i], RemainingCooldownComponent.class), i);
                checkCooldownRemoved(observer.getRemoved().getComponent(spells[i], RemainingCooldownComponent.class), i);
            }
        }
    }
    
    private void checkCooldownChanged(RemainingCooldownComponent remainingCooldownComponent, int spellIndex){
        if(remainingCooldownComponent != null){
            screenController.showPlayer_SpellCooldown(spellIndex, remainingCooldownComponent.getDuration());
        }
    }
    
    private void checkCooldownRemoved(RemainingCooldownComponent remainingCooldownComponent, int spellIndex){
        if(remainingCooldownComponent != null){
            screenController.hidePlayer_SpellCooldown(spellIndex);
        }
    }
}