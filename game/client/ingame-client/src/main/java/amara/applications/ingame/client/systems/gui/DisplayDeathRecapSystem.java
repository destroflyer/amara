/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.core.Util;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayDeathRecapSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayDeathRecapSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        if(observer.getRemoved().hasComponent(characterEntity, IsAliveComponent.class)){
            String text = "[No damage history existing]";
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(characterEntity, DamageHistoryComponent.class);
            if(damageHistoryComponent != null){
                float totalDamage = 0;
                for(int i=0;i<damageHistoryComponent.getEntries().length;i++){
                    totalDamage += damageHistoryComponent.getEntries()[i].getDamage();
                }
                text = ((int) totalDamage) + " total damage over " + Util.round(damageHistoryComponent.getLastDamageTime() - damageHistoryComponent.getFirstDamageTime(), 3) + " seconds\n";
                for(int i=0;i<damageHistoryComponent.getEntries().length;i++){
                    DamageHistoryComponent.DamageHistoryEntry entry = damageHistoryComponent.getEntries()[i];
                    String sourceName = null;
                    if(entry.getSourceEntity() != -1){
                        NameComponent nameComponent = entityWorld.getComponent(entry.getSourceEntity(), NameComponent.class);
                        if(nameComponent != null){
                            sourceName = nameComponent.getName();
                        }
                        else{
                            sourceName = "[Unnamed unit]";
                        }
                    }
                    String sourceSpellName = null;
                    if(entry.getSourceSpellEntity() != -1){
                        NameComponent nameComponent = entityWorld.getComponent(entry.getSourceSpellEntity(), NameComponent.class);
                        if(nameComponent != null){
                            sourceSpellName = nameComponent.getName();
                        }
                        else{
                            sourceSpellName = "[Unnamed spell]";
                        }
                    }
                    text += "\n";
                    if(sourceName != null){
                        text += sourceName + " -> ";
                    }
                    if(sourceSpellName != null){
                        text += sourceSpellName + " -> ";
                    }
                    text += ((int) entry.getDamage()) + " " + entry.getDamageType().name().toLowerCase();
                }
            }
            screenController.setDeathLayersVisible(true);
            screenController.setDeathRecapText(text);
            screenController.setDeathRecapVisible(false);
        }
        else if(observer.getNew().hasComponent(characterEntity, IsAliveComponent.class)){
            screenController.setDeathLayersVisible(false);
        }
    }
}
