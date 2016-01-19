/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplaySpellsCooldownsSystem extends GUIDisplaySystem{

    public DisplaySpellsCooldownsSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class, RemainingCooldownComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(selectedEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(selectedEntity, SpellsComponent.class));
        SpellsComponent spellsComponent = entityWorld.getComponent(selectedEntity, SpellsComponent.class);
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
                        screenController_HUD.showSpellCooldown(i, remainingCooldownComponent.getDuration());
                    }
                    else{
                        screenController_HUD.hideSpellCooldown(i);
                    }
                }
                else{
                    screenController_HUD.hideSpellCooldown(i);
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
            screenController_HUD.showSpellCooldown(spellIndex, remainingCooldownComponent.getDuration());
        }
    }
    
    private void checkCooldownRemoved(RemainingCooldownComponent remainingCooldownComponent, int spellIndex){
        if(remainingCooldownComponent != null){
            screenController_HUD.hideSpellCooldown(spellIndex);
        }
    }
}
